package com.wxx.conference.controller.dingding;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by thinkpad on 2020-9-30.
 */
@RestController
@RequestMapping(value = "DD")
public class DDController {
    /**
     * 简历查询钉钉入口，首先进入系统登录页面，根据corpId获取临时授权码
     * */
    @RequestMapping(value = "ddEnter")
    public ModelAndView ddEnter(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("corpId","ding1d3c3af9301ce0d535c2f4657eb6378f");
        mv.setViewName("dingding/dLogin");
        return mv;
    }

    /**
     * 薪资查询钉钉入口，首先进入系统登录页面，根据corpId获取临时授权码
     * */
    @RequestMapping(value = "DDSalaryEnter")
    public ModelAndView DDSalaryEnter(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("corpId","ding1d3c3af9301ce0d535c2f4657eb6378f");
        mv.setViewName("dingding/SalaryLogin");
        return mv;
    }
}
