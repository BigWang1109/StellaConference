package com.wxx.conference.controller.portal;

import com.auth0.jwt.interfaces.Claim;
import com.wxx.conference.common.Pagination;
import com.wxx.conference.model.portal.OrgMember;
import com.wxx.conference.service.portal.PortalService;
import com.wxx.conference.util.JWTUtil;
import com.wxx.conference.util.ZtreeUtil;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2019-11-6.
 */
@RestController
@RequestMapping(value = "portal")
public class PortalController {

    @Autowired
    private PortalService service;
    //门户主界面跳转,普通用户进入门户主界面，管理员进入管理后台
    @RequestMapping(value = "main/{account}")
    public ModelAndView portalMain(@PathVariable(value="account") String account,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        OrgMember orgMember = service.getOrgMember(account);
        String token = request.getHeader("Authorization");
        Map<String, Claim> map = JWTUtil.verifyToken(token);
        mv.addObject("orgMember", orgMember);
        if(orgMember.getIS_ADMIN() == 0){
            mv.setViewName("portal/main");
        }else{
//            String nodes = ZtreeUtil.toJSON(service.getOrgUnitList(), service.getMaxPathLengthFromOrgUnit());
//            mv.addObject("nodes", nodes);
//            mv.setViewName("portal/systemmain");
            mv.setViewName("portal/systemMainFrame");
        }
//        mv.setViewName("redirect:/portal/enterMain");
        return mv;
    }
    @RequestMapping(value = "enterMain")
    public ModelAndView enterMain(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String token = request.getParameter("token");
        String userid = request.getParameter("userid");
        mv.addObject("token",token);
        mv.addObject("userid",userid);
        mv.setViewName("portal/main");
        return mv;
    }
    //用户登录校验
    @RequestMapping(value = "portalLogin/{account}/{password}")
    public Map portalLogin(@PathVariable(value = "account") String account,@PathVariable(value = "password") String password,HttpServletRequest request, HttpServletResponse response){
        Map map = new HashMap();
        OrgMember orgMember = service.getOrgMember(account);
        String token = JWTUtil.createToken(account);
        if(orgMember.getCODE()!=null){
//            Cookie cookie = new Cookie("token", token);
//            System.out.println(token);
//            response.addCookie(cookie);
            map.put("flag", "success");
            map.put("token",token);
//            map.put("is_admin",orgMember.getIS_ADMIN());
        }else{
            map.put("flag", "error");
        }
        return map;
    }
    //用户登录校验
    @RequestMapping(value = "checkLogin/{account}/{password}")
    public ModelAndView checkLogin(@PathVariable(value = "account") String account,@PathVariable(value = "password") String password,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String token = JWTUtil.createToken(account);
        mv.addObject("token",token);
        mv.addObject("userid",account);
        mv.setViewName("portal/main.jsp");
        return mv;
    }

    @RequestMapping(value = "authorityManage")
    public ModelAndView authorityManage(){
        ModelAndView mv = new ModelAndView();
        String nodes = ZtreeUtil.toJSON(service.getOrgUnitList(), service.getMaxPathLengthFromOrgUnit());
        mv.addObject("nodes", nodes);
        mv.setViewName("portal/systemmain");

        return mv;
    }
    @RequestMapping(value="getAuthUnit/{systemid}")
    public ModelAndView getAuthUnit(@PathVariable(value = "systemid") String systemid){
        ModelAndView mv = new ModelAndView();
        String nodes = ZtreeUtil.toJSONPlusChecked(service.getOrgUnitListPlusSystemID(systemid), service.getMaxPathLengthFromOrgUnit());
        mv.addObject("nodes", nodes);
        mv.setViewName("portal/systemAuthorization");
        return mv;
    }

    @RequestMapping(value = "systemManage")
    public ModelAndView systemManage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("portal/systemConfigMain");
        return mv;
    }

    //登录界面跳转
    @RequestMapping(value = "login")
    public ModelAndView adminEnter(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("portal/login");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "loadOrgMember/{ORG_DEPARTMENT_ID}")
    public Map<String,Object> loadOrgMemberByDepartmentID(Pagination pagination,@PathVariable(value = "ORG_DEPARTMENT_ID") String ORG_DEPARTMENT_ID){
        Map<String,Object> json = service.loadOrgMemberByDepartmentID(pagination,ORG_DEPARTMENT_ID);
        return json;
    }

    @ResponseBody
    @RequestMapping(value="loadOrgMemberByAccountId/{ORG_ACCOUNT_ID}")
    public Map<String,Object> loadOrgMemberByAccountId(Pagination pagination,@PathVariable(value = "ORG_ACCOUNT_ID") String ORG_ACCOUNT_ID){
        Map<String,Object> json = service.loadOrgMemberByAccountId(pagination, ORG_ACCOUNT_ID);
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "loadSystem")
    public Map<String,Object> loadSystem(Pagination pagination){
        Map<String,Object> json = service.loadSystemListByPage(pagination);
        return json;
    }
    @RequestMapping(value = "portalZtree")
    public ModelAndView testZtree(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("portal/zTree");
        return mv;
    }
    @RequestMapping(value = "addWaterMark")
    public ModelAndView addWaterMark(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView();
        String wmtext = URLDecoder.decode(request.getParameter("wmtext"), "UTF-8");
        String isShowTime = request.getParameter("isShowTime");
        String transparency = request.getParameter("transparency");
        String wFontSize = request.getParameter("wFontSize");
//        String wmtext = request.getParameter("wmtext");
        mv.addObject("wmtext",wmtext);
        mv.addObject("isShowTime",isShowTime);
        mv.addObject("transparency",transparency);
        mv.addObject("wFontSize",wFontSize);
        mv.setViewName("portal/addWaterMark");
        return mv;
    }

    @RequestMapping(value = "waterMarkEnter")
    public ModelAndView waterMarkEnter(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("portal/editWaterMarkText");
        return mv;
    }
}
