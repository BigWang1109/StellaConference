package com.wxx.conference.controller.HR;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.wxx.conference.model.HR.zgfh_bi_psndoc;
import com.wxx.conference.model.HR.zgfh_bi_wadoc;
import com.wxx.conference.service.HR.CVService;
import com.wxx.conference.service.HR.SalaryService;
import com.wxx.conference.util.CookieUtil;
import com.wxx.conference.util.DingDingUtil;
import com.wxx.conference.util.JWTUtil;
import com.wxx.conference.util.JedisUtil;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by thinkpad on 2020-11-25.
 */
@RestController
@RequestMapping("salary")
public class SalaryController {

    @Autowired
    private CVService service;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private com.wxx.conference.util.JedisUtil JedisUtil;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SalaryController.class);

    /**
     * 薪资查询入口
     * */
    @RequestMapping(value = "SalarySearchEnter")
    public ModelAndView SalarySearchEnter(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        int count = service.getCount();
        int num = (salaryService.getCartInCookie(request, response)).size();
        mv.addObject("num",num);
        mv.addObject("count", count);
        mv.setViewName("HR/SalarySearch");
        return mv;
    }
    /**
     * 钉钉移动端薪资查询入口
     * @param authCode 钉钉免登授权码(5分钟失效且只能使用一次)，根据授权码获取userId，检查userId是否有授权，若无授权则不允许访问
     * */
    @RequestMapping(value = "SalarySearchEnterDD/{authCode}")
    public ModelAndView CVSearchEnterDD(@PathVariable(value = "authCode") String authCode,HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        int count = service.getCount();
        int num = (salaryService.getCartInCookie(request, response)).size();
        mv.addObject("count", count);
        mv.addObject("num",num);
        String token = DingDingUtil.getDingDingToken();
        OapiUserGetuserinfoResponse Oapiresponse = DingDingUtil.getUserInfo(token, authCode);
        String userId = Oapiresponse.getUserid();
        Jedis jedis = JedisUtil.getJedis();
        String userName = jedis.get(userId);
        if(userName != null && !"".equals(userName)){
            logger.info("该用户信息已存在，用户id为："+userId);
        }else{
            JSONObject jsonObj = new JSONObject(Oapiresponse.getBody());
            userName = jsonObj.getString("name");
            jedis.set(userId,userName);
            logger.info("该用户信息不存在，缓存进redis");
        }
        ArrayList access_users = (ArrayList)jedis.lrange("access_users",0,jedis.llen("access_users"));

        if(userId != null && access_users.contains(userId)){
//            String jwtToken = JWTUtil.createTokenNew(userId, userName);
//            String jwtToken = JWTUtil.createToken(userId);
//            jedis.set(userId,jwtToken);
//            mv.addObject("token",jwtToken);
//            mv.addObject("userId",userId);
            mv.setViewName("HR/SalarySearch");
        }else{
            logger.error("用户:("+userId+")无授权，无法访问");
            mv.setViewName("HR/error");
        }
        return mv;
    }
    /**
     * 比较人员列表跳转
     * */
    @RequestMapping(value = "turnToPkList")
    public ModelAndView turnToPkList(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        List<String> psncodeList = salaryService.getCartInCookie(request, response);
        List<zgfh_bi_psndoc> psndocList = new ArrayList<zgfh_bi_psndoc>();
        for(int i=0;i<psncodeList.size();i++){
//            HashMap map = new HashMap();
            zgfh_bi_psndoc psndoc = service.userSearchByCode(psncodeList.get(i));
//            map.put(psndoc.getPSNCODE(),psndoc);
            psndocList.add(psndoc);
        }
        mv.addObject("psndocList",psndocList);
//        mv.addObject("psncodeList",psncodeList);
        mv.setViewName("HR/ShowPkList");
        return mv;
    }
    /**
     * 比较界面
     * 从cookie中获取要对比的人员
     * */
    @RequestMapping(value = "turnToPkFrame")
    public ModelAndView turnToPkFrame(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        List<String> psncodeList = salaryService.getCartInCookie(request, response);
        List<zgfh_bi_psndoc> psndocList = new ArrayList<zgfh_bi_psndoc>();
        List<zgfh_bi_wadoc> wadocList = new ArrayList<zgfh_bi_wadoc>();
        for(int i=0;i<psncodeList.size();i++){
//            HashMap map = new HashMap();
            zgfh_bi_psndoc psndoc = service.userSearchByCode(psncodeList.get(i));
            zgfh_bi_wadoc wadoc = salaryService.getWadocByCodeLately(psncodeList.get(i));
//            map.put(psndoc.getPSNCODE(),psndoc);
            psndocList.add(psndoc);
            wadocList.add(wadoc);
        }
        mv.addObject("psndocList",psndocList);
        mv.addObject("wadocList",wadocList);
        mv.setViewName("HR/PKFrame");
        return mv;
    }
    /**
     * 人员对比界面
     * 根据选人界面勾选的结果来进行对比，不从cookie中读取
     * */
    @RequestMapping(value = "turnToPkFrameNew")
    public ModelAndView turnToPkFrameNew(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("codes");
        code = code.substring(0,code.length()-1);
        String []codes = code.split(",");
        ModelAndView mv = new ModelAndView();
        List<zgfh_bi_psndoc> psndocList = new ArrayList<zgfh_bi_psndoc>();
        List<zgfh_bi_wadoc> wadocList = new ArrayList<zgfh_bi_wadoc>();
        for(int i=0;i<codes.length;i++){
            zgfh_bi_psndoc psndoc = service.userSearchByCode(codes[i]);
            zgfh_bi_wadoc wadoc = salaryService.getWadocByCodeLately(codes[i]);
            psndocList.add(psndoc);
            wadocList.add(wadoc);
        }
        mv.addObject("psndocList",psndocList);
        mv.addObject("wadocList",wadocList);
        mv.setViewName("HR/PKFrame");
        return mv;
    }
    /**
     * 将单个人员编码添加至cookie
     * */
    @RequestMapping(value = "addToCookie/{psncode}")
    public void addToCookie(@PathVariable(value = "psncode") String psncode,HttpServletRequest request,HttpServletResponse response){
        salaryService.addCart(psncode, request, response);
    }
    /**
     * 将多个人员编码添加至cookie
     * */
    @ResponseBody
    @RequestMapping(value = "addCoodesToCookie", method = RequestMethod.POST)
    public void addCoodesToCookie(@RequestParam(value = "codes[]") String[] codes,HttpServletRequest request,HttpServletResponse response){
//        for(int i=0;i<codes.length;i++){
//            salaryService.addCart(codes[i], request, response);
//        }
        salaryService.addCodesToCart(codes, request, response);
    }

    @ResponseBody
    @RequestMapping(value = "getCartNumFromCookie", method = RequestMethod.POST)
    public int getCartNumFromCookie(HttpServletRequest request,HttpServletResponse response){
        int num = (salaryService.getCartInCookie(request, response)).size();
        return num;
    }

    /**
     * 传递数组时，requestParam中需带[]，否则400
     * 从cookie中删除人员编码（多个）
     * */
    @ResponseBody
    @RequestMapping(value = "delFromCookie", method = RequestMethod.POST)
    public void delFromCookie(@RequestParam("codes[]") String[] codes,HttpServletRequest request,HttpServletResponse response){
        for(int i=0;i<codes.length;i++){
            salaryService.delCartById(codes[i], request, response);
        }

    }

    /**
     *从cookie中删除人员编码（单个）
     * */
    @ResponseBody
    @RequestMapping(value = "delCookie/{code}", method = RequestMethod.POST)
    public void delCookie(@PathVariable(value = "code") String code,HttpServletRequest request,HttpServletResponse response){
            salaryService.delCartById(code,request,response);
    }
    /**
     * 根据关键词全文检索，支持多关键词
     * */
    @RequestMapping(value = "CVSearch/{searchVal}")
    public ArrayList CVSearch(@PathVariable(value = "searchVal") String searchVal) throws Exception{
        searchVal = URLDecoder.decode(searchVal, "UTF-8");
//        ArrayList list = service.CVSearchByName(searchVal);
        HashSet codeSet = service.getPsncodeList(searchVal);
        ArrayList list = service.searchByPsncode(codeSet);
//        count++;
//        System.out.println(count);
        return list;
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
        ArrayList wadocList = salaryService.getWadocByCode(userid);
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
        mv.addObject("wadocList", wadocList);
        mv.addObject("isAndroid",isAndroid);
        mv.setViewName("HR/SalaryInfo");
        return mv;
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

    @RequestMapping(value = "getTokenFromRedis/{userId}")
    public String getTokenFromRedis(String userId){
        Jedis jedis = JedisUtil.getJedis();
        String token = jedis.get(userId);
        return token;
    }

}
