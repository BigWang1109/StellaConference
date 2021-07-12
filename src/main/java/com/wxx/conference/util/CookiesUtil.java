package com.wxx.conference.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2021-4-28.
 */
public class CookiesUtil {
    /**
     * 封装cookie
     * */
    public static String makeCookieValue(List<String> pojos, String pojo) {
        JSONArray array = new JSONArray();
        if (pojos.size() <= 0) {
            JSONObject json = new JSONObject();
            json.put("key", pojo);
            array.add(json);
        } else {
            List<String> ids = new ArrayList<String>();
            for (String item : pojos) {
                JSONObject json = new JSONObject();
                json.put("key", item);
                array.add(json);
                ids.add(item);
            }
            if (!ids.contains(pojo)) {//新添加进来的产品信息如果购物车中已存在就不进行重封装
                JSONObject json = new JSONObject();
                json.put("key", pojo);
                array.add(json);
            }
        }
        return array.toString();
    }
    public static String makeCookieValues(List<String> pojos, String[] codes) {
        JSONArray array = new JSONArray();
        if (pojos.size() <= 0) {
            for(int i=0;i<codes.length;i++){
                JSONObject json = new JSONObject();
                json.put("key", codes[i]);
                array.add(json);
            }
        } else {
            List<String> ids = new ArrayList<String>();
//            for (String item : pojos) {
//                JSONObject json = new JSONObject();
//                json.put("key", item);
//                array.add(json);
//                ids.add(item);
//            }
            for(String code : codes){
//                if (!ids.contains(code)) {//新添加进来的产品信息如果购物车中已存在就不进行重封装
                    JSONObject json = new JSONObject();
                    json.put("key", code);
                    array.add(json);
//                }
            }
        }
        return array.toString();
    }
    //重新封装
    public static String makeCookieValue(List<String> cart) {
        JSONArray array = new JSONArray();
        for (String item : cart) {
            JSONObject json = new JSONObject();
            json.put("key", item);
            array.add(json);
        }
        return array.toString();
    }
    /**
     * 获取cookie
     * */
    public static Cookie getCookie(String cookieName,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cart_cookie = null;
        for(Cookie cookie : cookies){
            if(cookieName.equals(cookie.getName())){
                cart_cookie = cookie;
            }
        }
        return cart_cookie;
    }
    /**
     * 获取cookie中存储的cart
     * */
    public static List<String> getCartInCookie(String cookieName,HttpServletRequest request,HttpServletResponse response){
        List<String> items = new ArrayList<String>();
        String value = "";
        try{
            Cookie cart_cookie = getCookie(cookieName,request);
            if(cart_cookie != null){
                value = URLDecoder.decode(cart_cookie.getValue(), "utf-8");
                if(value != null && !"".equals(value)){
                    JSONArray array = JSONArray.fromObject(value);
                    for(int i=0;i<array.size();i++){
                        JSONObject json = JSONObject.fromObject(array.get(i));
                        items.add(json.get("key").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }
    /**
     * 添加员工编码至cookie
     * */
    public void addCart(String cookieName,String psncode,HttpServletRequest request,HttpServletResponse response){
        try{
            List<String> pojos = getCartInCookie(cookieName,request,response);
            Cookie cookie;
            if (pojos.size() <= 0) {
                if (getCookie(cookieName,request) == null) {
                    // 制作购物车cookie数据
                    cookie = new Cookie("cart", URLEncoder.encode(makeCookieValue(pojos, psncode), "utf-8"));
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    response.addCookie(cookie);// 添加cookie
                } else {
                    cookie = getCookie(cookieName,request);
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    cookie.setValue(URLEncoder.encode(makeCookieValue(pojos, psncode)));
                    response.addCookie(cookie);// 添加cookie
                }
            }
            // 当获取的购物车列表不为空时
            else {
                // 获取名为"cart"的cookie
                cookie = getCookie(cookieName,request);
                cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                String value = makeCookieValue(pojos, psncode);
                cookie.setValue(URLEncoder.encode(value)); // 设置value
                response.addCookie(cookie);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加员工编码至cookie
     * */
    public static void addCodesToCart(String cookieName,String[] psncodes,HttpServletRequest request,HttpServletResponse response){
        try{
            List<String> pojos = getCartInCookie(cookieName,request,response);
            Cookie cookie;
            if (pojos.size() <= 0) {
                if (getCookie(cookieName,request) == null) {
                    // 制作购物车cookie数据
                    cookie = new Cookie(cookieName, URLEncoder.encode(makeCookieValues(pojos, psncodes), "utf-8"));
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    response.addCookie(cookie);// 添加cookie
                } else {
                    cookie = getCookie(cookieName,request);
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    cookie.setValue(URLEncoder.encode(makeCookieValues(pojos, psncodes),"utf-8"));
                    response.addCookie(cookie);// 添加cookie
                }
            }
            // 当获取的购物车列表不为空时
            else {
                // 获取名为"cart"的cookie
                cookie = getCookie(cookieName,request);
                cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                String value = makeCookieValues(pojos, psncodes);
                cookie.setValue(URLEncoder.encode(value,"utf-8")); // 设置value
                response.addCookie(cookie);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 从cookie中删除员工编号
     * */
    public void delCartById(String cookieName,String psncode,HttpServletRequest request,HttpServletResponse response){
        try {
            List<String> pojoss = getCartInCookie(cookieName,request, response);
            List<String> pojos = new ArrayList<String>();
            for(String pojo:pojoss){
                if(!pojo.equals(psncode)){
                    pojos.add(pojo);
                }
            }
            Cookie cookie = getCookie(cookieName,request);
            cookie.setPath("/");// 设置在该项目下都可以访问该cookie
            cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
            String value = makeCookieValue(pojos);
//            cookie.setValue(value);
            cookie.setValue(URLEncoder.encode(value,"utf-8")); // 设置value
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
