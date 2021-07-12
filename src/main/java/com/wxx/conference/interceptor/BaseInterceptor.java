package com.wxx.conference.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by wxx on 16-11-26.
 */
public class BaseInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        //System.out.println("BaseInterceptor.afterCompletion 当前用户:"+ SessionUtil.getInstance().getUsername());

        // TODO Auto-generated method stub
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
//        if (arg3 != null) {
//            String uri = arg0.getRequestURI();
//            Map<String, Object> model = arg3.getModel();
//            //header.jsp 页面使用
//            model.put("RequestURI", uri);
//        }
        // TODO Auto-generated method stub
    }

    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        // TODO Auto-generated method stub
        //System.out.println("BaseInterceptor.preHandle 当前用户:"+ SessionUtil.getInstance().getUsername());
        //SessionUtil.getInstance().initSession(arg0.getSession());
//        String uri = arg0.getRequestURI();
//        logger.info(uri);
        return true;
    }
}