package com.wxx.conference.controller.YTCX;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.wxx.conference.common.Pagination;
import com.wxx.conference.model.HR.condition_obj;
import com.wxx.conference.model.HR.zgfh_bi_psndoc;
import com.wxx.conference.model.HR.ztree_obj;
import com.wxx.conference.model.portal.OrgMember;
import com.wxx.conference.service.HR.SalaryService;
import com.wxx.conference.service.YTCX.YTCXService;
import com.wxx.conference.util.*;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by thinkpad on 2021-3-18.
 */
@RestController
@RequestMapping(value = "YTCX")
public class YTCXController {

    @Autowired
    private YTCXService service;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private com.wxx.conference.util.JedisUtil JedisUtil;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(YTCXController.class);

    /**
     * 移动端简历查询入口
     * */
    @RequestMapping(value = "CVSearchEnter")
    public ModelAndView CVSearchEnter(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        String authCode = request.getParameter("usercode");
        if(authCode == null || "".equals(authCode)){
            mv.setViewName("YTCX/YTCXError");
        }else if(!service.checkAccessRole(authCode)){
            mv.setViewName("YTCX/forbidAccess");
        }else{
            Jedis jedis = JedisUtil.getJedis();
            String userName = jedis.get(authCode);
            if(userName != null && !"".equals(userName)){
                logger.info("该用户信息已存在，用户id为："+authCode);
            }else{
                zgfh_bi_psndoc psndoc = service.userSearchByCode(authCode);
                userName = psndoc.getPSNNAME();
                jedis.set(authCode,userName);
                logger.info("该用户信息不存在，缓存进redis");
            }
            int num = service.updateLog(authCode, userName, "", "0");
            if(num > 0){
                logger.info("日志插入成功");
            }else{
                logger.error("日志插入失败");
            }
            jedis.close();
//            int count = service.getCount(authCode);
            int count = service.getCountNew(authCode);
            mv.addObject("count", count);
            mv.addObject("authCode",authCode);
            mv.setViewName("YTCX/YTCXSearch");
        }
        return mv;
    }

    /**
     * 移动端简历查询入口测试
     * */
    @RequestMapping(value = "CVSearchEnterTest/{authCode}")
    public ModelAndView CVSearchEnterTest(@PathVariable(value = "authCode") String authCode,HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        //用户进入以后创建一个cookie用于存放要pk的员工号
//        salaryService.addCart("", request, response);
        int count = service.getCount(authCode);
        mv.addObject("count", count);
        mv.addObject("authCode",authCode);
        mv.setViewName("YTCX/YTCXSearch");
//        mv.setViewName("HR/error");
        return mv;
    }

    /**
     * 钉钉移动端简历查询入口
     * @param authCode 钉钉免登授权码(5分钟失效且只能使用一次)，根据授权码获取userId，检查userId是否有授权，若无授权则不允许访问
     * */
    @RequestMapping(value = "CVSearchEnterDD/{authCode}")
    public ModelAndView CVSearchEnterDD(@PathVariable(value = "authCode") String authCode){
        ModelAndView mv = new ModelAndView();
        int count = service.getCount("");
        mv.addObject("count", count);
        String token = DingDingUtil.getDingDingToken();
        OapiUserGetuserinfoResponse response = DingDingUtil.getUserInfo(token, authCode);
        String userId = response.getUserid();
        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(userId);
        if(userName != null && !"".equals(userName)){
            logger.info("该用户信息已存在，用户id为："+userId);
        }else{
            JSONObject jsonObj = new JSONObject(response.getBody());
            userName = jsonObj.getString("name");
            jedis.set(userId,userName);
            logger.info("该用户信息不存在，缓存进redis");
        }
        ArrayList access_users = (ArrayList)jedis.lrange("access_users",0,jedis.llen("access_users"));

        if(userId != null && access_users.contains(userId)){
//            String jwtToken = JWTUtil.createTokenNew(userId, userName);
            String jwtToken = JWTUtil.createToken(userId);
            mv.addObject("token",jwtToken);
            mv.addObject("userId",userId);
            mv.setViewName("HR/CVSearchForDD");
        }else{
            logger.error("用户:("+userId+")无授权，无法访问");
            mv.setViewName("HR/error");
        }
        return mv;
    }
    /**
     * 根据关键词全文检索，支持多关键词(分页)
     * */
    @RequestMapping(value = "CVSearchByPage/{searchVal}/{index}")
    public ArrayList CVSearchByPage(@PathVariable(value = "searchVal") String searchVal,@PathVariable(value = "index") int index) throws Exception{
        searchVal = URLDecoder.decode(searchVal,"UTF-8");
//        ArrayList list = service.CVSearchByName(searchVal);
        List roleList = service.getRoleList("10010216");
        HashSet codeSet = service.getPsncodeList(searchVal, roleList);
        ArrayList list = service.searchByPsncodeByPage(codeSet, index,roleList);
//        count++;
//        System.out.println(count);
        return list;
    }
    /**
     * 根据关键词全文检索，支持多关键词(分页)
     * */
    @RequestMapping(value = "CVSearchByPageTest/{searchVal}/{index}/{authcode}")
    public ArrayList CVSearchByPageTest(@PathVariable(value = "searchVal") String searchVal,@PathVariable(value = "index") int index,@PathVariable(value = "authcode") String authcode) throws Exception{
        searchVal = URLDecoder.decode(searchVal,"UTF-8");
//        ArrayList list = service.CVSearchByName(searchVal);
        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(authcode);
        logger.info("用户:("+userName+")执行了查询，查询关键词为：("+searchVal+")");
        int num = service.updateLog(authcode,userName,searchVal,"1");
        if(num > 0){
            logger.info("日志插入成功");
        }else{
            logger.error("日志插入失败");
        }
        jedis.close();
        List roleList = service.getRoleList(authcode);
        HashSet codeSet = service.getPsncodeList(searchVal,roleList);
        ArrayList list = service.searchByPsncodeByPage(codeSet, index,roleList);
//        count++;
//        System.out.println(count);
        return list;
    }
    /**
     * 根据关键词全文检索，支持多关键词(分页)
     * */
    @RequestMapping(value = "CVSearchByPageNew/{searchVal}/{index}/{authcode}")
    public ArrayList CVSearchByPageNew(@PathVariable(value = "searchVal") String searchVal,@PathVariable(value = "index") int index,@PathVariable(value = "authcode") String authcode) throws Exception{
        searchVal = URLDecoder.decode(searchVal,"UTF-8");
//        ArrayList list = service.CVSearchByName(searchVal);
        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(authcode);
        logger.info("用户:("+userName+")执行了查询，查询关键词为：("+searchVal+")");
        int num = service.updateLog(authcode,userName,searchVal,"1");
        if(num > 0){
            logger.info("日志插入成功");
        }else{
            logger.error("日志插入失败");
        }
        jedis.close();
        List roleList = service.getRoleListNew(authcode);
        HashSet codeSet = service.getPsncodeListNew(searchVal, roleList);
        ArrayList list = service.searchByPsncodeByPage(codeSet, index,roleList);
//        count++;
//        System.out.println(count);
        return list;
    }
    /**
     * 根据关键词全文检索，支持多关键词,header中设置登录用户名，用于记录操作日志
     * */
    @RequestMapping(value = "CVSearchForDD/{searchVal}/{userId}")
    public ArrayList CVSearchForDD(@PathVariable(value = "searchVal") String searchVal,@PathVariable(value = "userId") String userId,HttpServletRequest request,HttpServletResponse response) throws Exception{
        searchVal = URLDecoder.decode(searchVal,"UTF-8");

//        ArrayList list = service.CVSearchByName(searchVal);
//        String userName = request.getHeader("Authorization");
//        String userName = request.getParameter("userName");
        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(userId);
        logger.info("用户:("+userName+")执行了查询，查询关键词为：("+searchVal+")");
        HashSet codeSet = service.getPsncodeList(searchVal, new ArrayList());
        ArrayList list = service.searchByPsncode(codeSet);
//        count++;
//        System.out.println(count);
        return list;
    }
    /**
     * 校验header中携带的token，token有效执行查询，token失效跳转error界面
     * */
    @RequestMapping(value = "checkToken")
    public Boolean checkToken(HttpServletRequest request, HttpServletResponse response){
        Boolean flag;
        String token = request.getHeader("Authorization");
        DecodedJWT jwt = JWTUtil.verifyTokenNew(token);
        if(jwt != null){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }
    /**
     * token超时页面跳转
     * */
    @RequestMapping(value = "enterError")
    public ModelAndView enterError(){
        ModelAndView mv =  new ModelAndView();
        mv.setViewName("HR/tokenExpire");
        return mv;
    }
    /**
     * 移动端根据员工编号获取员工简历信息
     * */
    @RequestMapping(value = "userSearchByCode/{userid}")
    public ModelAndView userSearchByCode(@PathVariable(value = "userid") String userid,HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView();
        zgfh_bi_psndoc psndoc = service.userSearchByCode(userid);
        ArrayList workList = service.getWorkExprienceByCode(userid);
        ArrayList eduList = service.getEduExprienceByCode(userid);
        ArrayList kpiList = service.getKPIByCode(userid);
        ArrayList trainList = service.getTrainByCode(userid);
        ArrayList contList = service.getContByCode(userid);
        ArrayList titleList = service.getTitleByCode(userid);
        ArrayList fileList = service.getFileListByCode(userid);
        ArrayList famList = service.getFamilyInfoByCode(userid);
        Boolean isAndroid = false;
        String agent= request.getHeader("user-agent");
        if(agent.contains("Android")){
            isAndroid = true;
        }
//        service.gengerateQRCode(userid);
        mv.addObject("psndoc",psndoc);
        mv.addObject("workList",workList);
        mv.addObject("eduList",eduList);
        mv.addObject("kpiList",kpiList);
        mv.addObject("trainList",trainList);
        mv.addObject("contList",contList);
        mv.addObject("titleList",titleList);
        mv.addObject("fileList", fileList);
        mv.addObject("famList", famList);
        mv.addObject("isAndroid",isAndroid);
        mv.setViewName("YTCX/YTCXInfo");
        return mv;
    }
    /**
     * 移动端根据员工编号获取员工简历信息
     * */
    @RequestMapping(value = "userSearchByCodeNew/{psncode}/{userId}")
    public ModelAndView userSearchByCodeNew(@PathVariable(value = "psncode") String psncode,@PathVariable(value = "userId") String userId,HttpServletRequest request,HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView();
        zgfh_bi_psndoc psndoc = service.userSearchByCode(psncode);
        ArrayList workList = service.getWorkExprienceByCode(psncode);
        ArrayList eduList = service.getEduExprienceByCode(psncode);
        ArrayList kpiList = service.getKPIByCode(psncode);
        ArrayList trainList = service.getTrainByCode(psncode);
        ArrayList contList = service.getContByCode(psncode);
        ArrayList titleList = service.getTitleByCode(psncode);
        ArrayList fileList = service.getFileListByCode(psncode);
        ArrayList famList = service.getFamilyInfoByCode(psncode);
        String psnName = psndoc.getPSNNAME();
        Boolean isAndroid = false;
        String agent= request.getHeader("user-agent");
        if(agent.contains("Android")){
            isAndroid = true;
        }
        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(userId);
        logger.info("用户:(" + userName + ")查看了(" + psnName + ")的简历");
        int num = service.updateLog(userId,userName,psnName,"2");
        if(num > 0){
            logger.info("日志插入成功");
        }else{
            logger.error("日志插入失败");
        }
        logger.info("用户:(" + userName + ")查看了(" + psnName + ")的简历");
        jedis.close();
//        service.gengerateQRCode(userid);
        mv.addObject("psndoc",psndoc);
        mv.addObject("workList",workList);
        mv.addObject("eduList",eduList);
        mv.addObject("kpiList",kpiList);
        mv.addObject("trainList",trainList);
        mv.addObject("contList",contList);
        mv.addObject("titleList",titleList);
        mv.addObject("fileList", fileList);
        mv.addObject("famList", famList);
        mv.addObject("isAndroid",isAndroid);
        mv.addObject("userName",userName);
        mv.addObject("userId",userId);
        mv.setViewName("YTCX/YTCXInfo");
        return mv;
    }
    /**
     * 移动端根据员工编号获取员工简历信息(钉钉)
     * */
    @RequestMapping(value = "userSearchByCodeForDD/{psncode}/{userId}")
    public ModelAndView userSearchByCodeForDD(@PathVariable(value = "psncode") String psncode,@PathVariable(value = "userId") String userId) throws Exception{
        ModelAndView mv = new ModelAndView();
        zgfh_bi_psndoc psndoc = service.userSearchByCode(psncode);
        ArrayList workList = service.getWorkExprienceByCode(psncode);
        ArrayList eduList = service.getEduExprienceByCode(psncode);
        ArrayList kpiList = service.getKPIByCode(psncode);
        ArrayList trainList = service.getTrainByCode(psncode);
        ArrayList contList = service.getContByCode(psncode);
        ArrayList titleList = service.getTitleByCode(psncode);
        ArrayList fileList = service.getFileListByCode(psncode);
        String psnName = psndoc.getPSNNAME();

        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(userId);
        logger.info("用户:(" + userName + ")查看了(" + psnName + ")的简历");

//        service.gengerateQRCode(userid);
        mv.addObject("psndoc",psndoc);
        mv.addObject("workList",workList);
        mv.addObject("eduList",eduList);
        mv.addObject("kpiList",kpiList);
        mv.addObject("trainList",trainList);
        mv.addObject("contList",contList);
        mv.addObject("titleList",titleList);
        mv.addObject("fileList",fileList);
        mv.addObject("userName",userName);
        mv.addObject("userId",userId);
//        mv.setViewName("HR/CVInfo");
        mv.setViewName("YTCX/YTCXInfoForDD");

        return mv;
    }
    /**
     * 根据员工编码获取头像
     * */
    @RequestMapping(value = "getPictureByCode/{PSNCODE}")
    public void getPictureByCode(@PathVariable(value = "PSNCODE") String PSNCODE,HttpServletRequest request, HttpServletResponse response){
        try{
            response.setContentType("image/jpeg");
            ServletOutputStream outs=response.getOutputStream();
//            byte [] bs = service.getImgNew(PSNCODE);
            byte [] bs = service.getImgPro(PSNCODE);
            if(bs != null){
                outs.write(bs);
            }
            outs.flush();
        }catch (Exception e){
            logger.error("获取用户"+PSNCODE+"头像失败");
            e.printStackTrace();
        }
    }
    /**
     * pdf在线预览界面跳转
     * */
    @RequestMapping(value = "turnToPdfView/{PK_ATTACHMENT}/{PSNCODE}")
    public ModelAndView turnToPdfView(@PathVariable(value = "PK_ATTACHMENT") String PK_ATTACHMENT,@PathVariable(value = "PSNCODE") String PSNCODE){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("HR/PdfView");
//        mv.setViewName("HR/PdfViewM3");
        PK_ATTACHMENT = PK_ATTACHMENT.replaceAll(" ","");
        PSNCODE = PSNCODE.replaceAll(" ","");
        mv.addObject("PK_ATTACHMENT", PK_ATTACHMENT);
        mv.addObject("PSNCODE",PSNCODE);
        return mv;
    }
    /**
     * pdf在线预览界面跳转
     * */
    @RequestMapping(value = "turnToPdfViewPC/{PK_ATTACHMENT}/{PSNCODE}")
    public ModelAndView turnToPdfViewPC(@PathVariable(value = "PK_ATTACHMENT") String PK_ATTACHMENT,@PathVariable(value = "PSNCODE") String PSNCODE){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("HR/PdfViewPC");
        PK_ATTACHMENT = PK_ATTACHMENT.replaceAll(" ","");
        PSNCODE = PSNCODE.replaceAll(" ","");
        mv.addObject("PK_ATTACHMENT", PK_ATTACHMENT);
        mv.addObject("PSNCODE",PSNCODE);
        return mv;
    }
    /**
     * 根据文件ID获取pdf文件
     * */
    @RequestMapping(value = "getPdfByCode/{PK_ATTACHMENT}/{PSNCODE}")
    public void getPdfByCode(@PathVariable(value = "PK_ATTACHMENT") String PK_ATTACHMENT,@PathVariable(value = "PSNCODE") String PSNCODE,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String realname = service.getFileById(PK_ATTACHMENT).getATTACHMENT_NAME();
        try{
            response.setContentType("application/pdf");
            ServletOutputStream outs=response.getOutputStream();
            byte [] bs = service.downloadFileFormat(PK_ATTACHMENT, PSNCODE,realname);
            if(bs != null){
                outs.write(bs);
            }
            outs.flush();
        }catch (Exception e){
            logger.error("文件id:"+PK_ATTACHMENT+"获取失败");
            e.printStackTrace();
        }
    }
    /**
     * 根据员工编号获取工作经历
     * */
    @RequestMapping(value = "getWorkExprienceByCode/{PSNCODE}")
    public ArrayList getWorkExprienceByCode(@PathVariable(value = "PSNCODE") String PSNCODE){
        ArrayList list = service.getWorkExprienceByCode(PSNCODE);
        return list;
    }
    //按原文件类型下载附件
    @RequestMapping(value="/downloadFile/{PK_ATTACHMENT}/{PSNCODE}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable String PK_ATTACHMENT,@PathVariable String PSNCODE,HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 处理文件名
        String realname = service.getFileById(PK_ATTACHMENT).getATTACHMENT_NAME();
        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        response.setContentType("application/x-msdownload");
//        response.setContentType("application/docx");
        response.setCharacterEncoding("UTF-8");
        // 创建输出流
        ServletOutputStream outs=response.getOutputStream();
        // 创建缓冲区
        byte bs[] = service.downloadFile(PK_ATTACHMENT, PSNCODE,realname);
//        String res = new sun.misc.BASE64Encoder().encode(bs);
//        res = res.substring(36,res.length());
//        bs = new sun.misc.BASE64Decoder().decodeBuffer(res);
//        System.out.println(res);
        if(bs != null){
//            outs.write(bs);
            outs.write(bs,0,bs.length);
        }
        // 关闭输出流
        outs.flush();
        outs.close();
    }
    //下载转换为PDF格式的附件
    @RequestMapping(value="/downloadFileFormat/{PK_ATTACHMENT}/{PSNCODE}", method = RequestMethod.GET)
    public void downloadFileFormat(@PathVariable String PK_ATTACHMENT,@PathVariable String PSNCODE,HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 处理文件名
        String realname = service.getFileById(PK_ATTACHMENT).getATTACHMENT_NAME();
        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        response.setContentType("application/x-msdownload");
//        response.setContentType("application/docx");
        response.setCharacterEncoding("UTF-8");
        // 创建输出流
        ServletOutputStream outs=response.getOutputStream();
        // 创建缓冲区
        byte bs[] = service.downloadFileFormat(PK_ATTACHMENT, PSNCODE, realname);
        if(bs != null){
//            outs.write(bs);
            outs.write(bs,0,bs.length);
        }
        // 关闭输出流
        outs.flush();
        outs.close();
    }
    /**
     * system用户登录跳转，显示权限、日志菜单
     * */
    @RequestMapping(value = "sysEnter")
    public ModelAndView sysEnter(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("iden","system");
        mv.setViewName("portal/sysMainFrameYTCX");
        return mv;
    }
    /**
     * admin用户登录跳转，显示权限菜单
     * */
    @RequestMapping(value = "adminEnter")
    public ModelAndView adminEnter(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("iden","admin");
        mv.setViewName("portal/sysMainFrameYTCX");
        return mv;
    }
    /**
     * 权限配置页面跳转
     * */
    @RequestMapping(value = "authorityManage")
    public ModelAndView authorityManage(){
        ModelAndView mv = new ModelAndView();
//        String nodes = YTCXZtreeUtil.toJSON();
//        mv.addObject("nodes", nodes);
        mv.setViewName("portal/sysMainYTCX");
        return mv;
    }
    /**
     * 获取权限表中的用户
     * */
    @ResponseBody
    @RequestMapping(value="getRoleUserList")
     public Map<String,Object> getRoleUserList(Pagination pagination){
        Map<String,Object> json = service.loadOrgMemberByAccountId(pagination);
        return json;
    }
    /**
     * 根据用户编码获取权限列表
     * */
    @RequestMapping(value = "getRoleList/{psncode}")
    public ModelAndView getRoleList(@PathVariable String psncode){
        ModelAndView mv = new ModelAndView();
        String nodes = YTCXZtreeUtil.toJSONBycode(psncode);
        mv.addObject("nodes", nodes);
        mv.setViewName("YTCX/YTCXRoleList");
        return mv;
    }
    /**
     * 更新用户的权限列表
     * */
    @RequestMapping(value = "updateRoleList")
    public int updateRoleList(@RequestParam(value = "nodesArr[]") String[] nodesArr,@RequestParam(value = "psncode")String psncode,HttpServletRequest request,HttpServletResponse response){

        System.out.println(nodesArr);

//        List<ztree_obj> orgList = JsonUtils.json2List(checkedNodes, ztree_obj.class);

        service.updateRoleList(nodesArr, psncode);
        return 0;
    }
    /**
     * 增加用户界面跳转
     * */
    @RequestMapping(value = "turnToAddUser")
    public ModelAndView turnToAddUser(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("YTCX/addUser");
        return mv;
    }
    /**
     * 增加用户，权限为空
     * */
    @RequestMapping(value = "addUser")
    public void addUser(@RequestParam(value = "codes[]") String[] codes){

        service.addUser(codes);

    }
    /**
     * 删除用户及其权限
     * */
    @RequestMapping(value = "delUser")
    public void delUser(@RequestParam(value = "codes[]") String[] codes){
        service.delUser(codes);
    }
    /**
     * 日志查询界面跳转
     * */
    @RequestMapping(value = "turnToLogQuery")
    public ModelAndView turnToLogQuery(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("YTCX/logQuery");
        return mv;
    }
    /**
     * 加载日志
     * */
    @ResponseBody
    @RequestMapping(value="logQuery")
    public Map<String,Object> logQuery(Pagination pagination){
        Map<String,Object> json = service.logQuery(pagination);
        return json;
    }

    //用户登录校验
    @RequestMapping(value = "portalLogin")
    public Map portalLogin(@RequestParam(value = "account") String account,@RequestParam(value = "password") String password){
        Map map = new HashMap();
        String token = JWTUtil.createToken(account);
        if(account.equals("admin") && password.equals("admin")){

            map.put("flag", "admin");
//            map.put("token",token);

        }else if(account.equals("system") && password.equals("system")){
            map.put("flag", "system");
        }else{
            map.put("flag", "error");
        }
        return map;
    }

    //登录界面跳转
    @RequestMapping(value = "login")
    public ModelAndView login(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("YTCX/login");
        return mv;
    }
}
