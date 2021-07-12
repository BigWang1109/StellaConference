package com.wxx.conference.controller.NC;

import com.wxx.conference.common.Pagination;
import com.wxx.conference.service.NC.NCService;
import com.wxx.conference.util.CookieUtil;
import com.wxx.conference.util.HttpClientUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2020-4-30.
 */
@RestController
@RequestMapping(value = "nc")
public class NController {

    @Autowired
    private NCService service;
    private static final Logger logger = LoggerFactory.getLogger(NController.class);

//    登录界面跳转
    @RequestMapping(value = "checkCustomer")
    public ModelAndView checkCustomer(HttpServletRequest request,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        String corp = service.getCorp(request);
        if(service.checkOAToken(request)){
            mv.setViewName("NC/checkCustomer");
            mv.addObject("corp", corp);
        }else if(!service.checkOAToken(request)){
            mv.setViewName("NC/forbidAccess");
        }
        return mv;
    }
    @RequestMapping(value = "checkCustomerForDDWH")
     public ModelAndView checkCustomerForDDWH(HttpServletRequest request,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        String corp = "wh";
        mv.setViewName("NC/checkCustomerForDD");
        mv.addObject("corp", corp);
        return mv;
    }
    @RequestMapping(value = "checkCustomerForDDBJ")
    public ModelAndView checkCustomerForDDBJ(HttpServletRequest request,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        String corp = "bj";
        mv.setViewName("NC/checkCustomerForDD");
        mv.addObject("corp", corp);
        return mv;
    }

    @RequestMapping(value = "searchCustomerInfo")
    public ModelAndView searchCustomerInfo(HttpServletRequest request,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        String corp = service.getCorp(request);
        if(service.checkOAToken(request)){
            mv.setViewName("NC/searchCustomerInfo");
            mv.addObject("corp",corp);
        }else if(!service.checkOAToken(request)){
            mv.setViewName("NC/forbidAccess");
        }
        return mv;
    }

    @RequestMapping(value = "searchCustomerInfoForDD")
    public ModelAndView searchCustomerInfoForDD(HttpServletRequest request,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        String corp = "wh";
//        if(service.checkOAToken(request)){
//            mv.setViewName("NC/searchCustomerInfo");
//            mv.addObject("corp",corp);
//        }else if(!service.checkOAToken(request)){
//            mv.setViewName("NC/forbidAccess");
//        }
        mv.setViewName("NC/searchCustomerInfo");
        mv.addObject("corp",corp);
        return mv;
    }

    @RequestMapping(value = "searchCustomer/{checkValue}/{checkType}/{corp}")
    public ArrayList SearchCustomer(@PathVariable(value = "checkValue") String checkValue,@PathVariable(value = "checkType") String checkType,@PathVariable(value = "corp") String corp,HttpServletRequest request){
//        ModelAndView mv = new ModelAndView();
        String requestIp = service.getRequestIP(request);
        ArrayList resList = service.searchCustomer(checkValue,checkType,requestIp,corp);
//        mv.addObject("resList",resList);
//        mv.setViewName("NC/checkCustomer");ArrayList
//        System.out.println(resList.size());
        return resList;
    }

//    @RequestMapping(value="checkCustomerExists/{phoneNumber}")
//    public int CheckCustomerExists(@PathVariable(value = "phoneNumber") String phoneNumber){
//        boolean flag = service.customerExists(phoneNumber);
//        int res = flag?1:0;
//        return res;
//    }

//    @RequestMapping(value="checkCustomerExists/{checkValue}/{checkType}")
//    public int CheckCustomerExists(@PathVariable(value = "checkValue") String checkValue,@PathVariable(value = "checkType") String checkType){
////        boolean flag = service.customerExistsNew(checkValue, checkType);
//        int json = service.customerExistsNew(checkValue, checkType);
////        int res = flag?1:0;
//        return json;
//    }

//    @RequestMapping(value="CheckCustomerFollow/{checkValue}/{checkType}")
//    public int CheckCustomerFollow(@PathVariable(value = "checkValue") String checkValue,@PathVariable(value = "checkType") String checkType){
////        boolean flag = service.customerExistsNew(checkValue, checkType);
//        int json = service.customerFollow(checkValue, checkType);
////        int res = flag?1:0;
//        return json;
//    }
    @RequestMapping(value = "checkIsFollowed")
    @ResponseBody
    public Map<String,Object> checkIsFollowed(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        String requestIp = service.getRequestIP(request);
        logger.info("访问源IP地址为：" + requestIp);
        String valueStr = HttpClientUtil.formRequestString(request);
        responseMap = service.formResponseMap(valueStr,responseMap,1);
        return responseMap;
    }
    @RequestMapping(value = "checkIsOld")
    @ResponseBody
    public Map<String,Object> checkIsOld(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        String requestIp = service.getRequestIP(request);
        logger.info("访问源IP地址为："+requestIp);
        String valueStr = HttpClientUtil.formRequestString(request);
        responseMap = service.formResponseMap(valueStr,responseMap,0);
        return responseMap;
    }
    @RequestMapping(value = "checkIsRec")
    @ResponseBody
    public Map<String,Object> checkIsRec(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        String requestIp = service.getRequestIP(request);
        logger.info("访问源IP地址为："+requestIp);
        String valueStr = HttpClientUtil.formRequestString(request);
        responseMap = service.formResponseMapNew(valueStr, responseMap, 0);
        return responseMap;
    }
    @ResponseBody
    @RequestMapping(value = "loadCustomerInfo/{corp}")
    public Map<String,Object> loadCustomerInfo(@PathVariable(value = "corp") String corp,Pagination pagination,HttpServletRequest request){
        String requestIp = service.getRequestIP(request);
//        String corp = service.getCorp(request);
        Map<String,Object> json = service.loadCustomerInfo(pagination, requestIp,corp);
        return json;
    }
    @RequestMapping(value = "initializeOption/{type}/{corp}")
    public List initializeOption(@PathVariable(value = "type") String type,@PathVariable(value = "corp") String corp){
        List projectNameList = service.initializeOption(type,corp);
        JSONArray jsonArray = JSONArray.fromObject(projectNameList);
        return jsonArray;
    }
    @RequestMapping(value = "initializeOption/{type}/{vpreferredtel}/{corp}")
    public List initializeOption(@PathVariable(value = "type") String type,@PathVariable(value = "vpreferredtel") String vpreferredtel,@PathVariable(value = "corp") String corp){
        List projectNameList = service.initializeOptionNew(type, vpreferredtel,corp);
        JSONArray jsonArray = JSONArray.fromObject(projectNameList);
        return jsonArray;
    }
}
