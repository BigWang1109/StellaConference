package com.wxx.conference.service.HR;

import com.wxx.conference.model.HR.zgfh_bi_wadoc;
import com.wxx.conference.util.HibernateHRDBUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2020-11-25.
 */
@Service
public class SalaryService {
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
            for (String item : pojos) {
                JSONObject json = new JSONObject();
                json.put("key", item);
                array.add(json);
                ids.add(item);
            }
            for(String code : codes){
                if (!ids.contains(code)) {//新添加进来的产品信息如果购物车中已存在就不进行重封装
                    JSONObject json = new JSONObject();
                    json.put("key", code);
                    array.add(json);
                }
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
    public static Cookie getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cart_cookie = null;
        for(Cookie cookie : cookies){
            if("cart".equals(cookie.getName())){
                cart_cookie = cookie;
            }
        }
        return cart_cookie;
    }
    /**
     * 获取cookie中存储的cart
     * */
    public List<String> getCartInCookie(HttpServletRequest request,HttpServletResponse response){
        List<String> items = new ArrayList<String>();
        String value = "";
        try{
            Cookie cart_cookie = getCookie(request);
            if(cart_cookie != null){
                value = URLDecoder.decode(cart_cookie.getValue(),"utf-8");
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
    public void addCart(String psncode,HttpServletRequest request,HttpServletResponse response){
        try{
            List<String> pojos = getCartInCookie(request,response);
            Cookie cookie;
            if (pojos.size() <= 0) {
                if (getCookie(request) == null) {
                    // 制作购物车cookie数据
                    cookie = new Cookie("cart", URLEncoder.encode(makeCookieValue(pojos, psncode), "utf-8"));
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    response.addCookie(cookie);// 添加cookie
                } else {
                    cookie = getCookie(request);
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    cookie.setValue(URLEncoder.encode(makeCookieValue(pojos, psncode)));
                    response.addCookie(cookie);// 添加cookie
                }
            }
            // 当获取的购物车列表不为空时
            else {
                // 获取名为"cart"的cookie
                cookie = getCookie(request);
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
    public void addCodesToCart(String[] psncodes,HttpServletRequest request,HttpServletResponse response){
        try{
            List<String> pojos = getCartInCookie(request,response);
            Cookie cookie;
            if (pojos.size() <= 0) {
                if (getCookie(request) == null) {
                    // 制作购物车cookie数据
                    cookie = new Cookie("cart", URLEncoder.encode(makeCookieValues(pojos, psncodes), "utf-8"));
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    response.addCookie(cookie);// 添加cookie
                } else {
                    cookie = getCookie(request);
                    cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                    cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                    cookie.setValue(URLEncoder.encode(makeCookieValues(pojos, psncodes)));
                    response.addCookie(cookie);// 添加cookie
                }
            }
            // 当获取的购物车列表不为空时
            else {
                // 获取名为"cart"的cookie
                cookie = getCookie(request);
                cookie.setPath("/");// 设置在该项目下都可以访问该cookie
                cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
                String value = makeCookieValues(pojos, psncodes);
                cookie.setValue(URLEncoder.encode(value)); // 设置value
                response.addCookie(cookie);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 从cookie中删除员工编号
     * */
    public void delCartById(String psncode,HttpServletRequest request,HttpServletResponse response){
        try {
            List<String> pojoss = getCartInCookie(request, response);
            List<String> pojos = new ArrayList<String>();
            for(String pojo:pojoss){
                if(!pojo.equals(psncode)){
                    pojos.add(pojo);
                }
            }
            Cookie cookie = getCookie(request);
            cookie.setPath("/");// 设置在该项目下都可以访问该cookie
            cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
            String value = makeCookieValue(pojos);
//            cookie.setValue(value);
            cookie.setValue(URLEncoder.encode(value)); // 设置value
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void changeCart(String psncode,HttpServletRequest request,HttpServletResponse response){
        try {
            List<String> cartVos = getCartInCookie(request, response);
            List<String> carts = new ArrayList<String>();
            //减少
//            if (type.equals("0")) {
//                for (购物车POJO cartVo : cartVos) {
//                    if (cartVo.getId() == id) {
//                        购物车POJO cart = new 购物车POJO();
//                        cart.setId(id);
//                        cart.setNum(num - 1);
//                        carts.add(cart);
//                    } else {
//                        购物车POJO cart = new 购物车POJO();
//                        cart = cartVo;
//                        carts.add(cart);
//                    }
//                }
//                //增加
//            } else if (type.equals("1")) {
//                for (购物车POJO cartVo : cartVos) {
//                    if (cartVo.getId() == id) {
//                        购物车POJO cart = new 购物车POJO();
//                        cart.setId(id);
//                        cart.setNum(num + 1);
//                        carts.add(cart);
//                    } else {
//                        购物车POJO cart = new 购物车POJO();
//                        cart = cartVo;
//                        carts.add(cart);
//                    }
//                }
//            }
            Cookie cookie = getCookie(request);
            cookie.setPath("/");// 设置在该项目下都可以访问该cookie
            cookie.setMaxAge(60 * 30);// 设置cookie有效时间为30分钟
            String value = makeCookieValue(carts, psncode);
            cookie.setValue(URLEncoder.encode(value)); // 设置value
            response.addCookie(cookie);
        } catch (Exception e) {
        }
    }

    /**
     * 根据员工编号获取定调薪
     * */
    public ArrayList getWadocByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_wadoc> list = new ArrayList<zgfh_bi_wadoc>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select PSNCODE,PSNNAME,BEGINDATE,LEV,NMONEY,REASON,LASTFLAG,RECORDNUM from oabi.zgfh_bi_wadoc where PSNCODE=:PSNCODE order by BEGINDATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_wadoc wadoc = new zgfh_bi_wadoc();
                    wadoc.setPSNCODE(checkIsNull(obj[0]));
                    wadoc.setPSNNAME(checkIsNull(obj[1]));
                    wadoc.setBEGINDATE(checkIsNull(obj[2]));
                    wadoc.setLEV(checkIsNull(obj[3]));
                    wadoc.setNMONEY(checkIsNull(obj[4]));
                    wadoc.setREASON(checkIsNull(obj[5]));
                    wadoc.setLASTFLAG(checkIsNull(obj[6]));
                    list.add(wadoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编号获取最新的定调薪
     * */
    public zgfh_bi_wadoc getWadocByCodeLately(String PSNCODE) {
        Session session = null;
        zgfh_bi_wadoc wadoc = new zgfh_bi_wadoc();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select PSNCODE,PSNNAME,BEGINDATE,LEV,NMONEY,REASON,LASTFLAG,RECORDNUM from oabi.zgfh_bi_wadoc where PSNCODE=:PSNCODE and LASTFLAG='Y' order by BEGINDATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    wadoc.setPSNCODE(checkIsNull(obj[0]));
                    wadoc.setPSNNAME(checkIsNull(obj[1]));
                    wadoc.setBEGINDATE(checkIsNull(obj[2]));
                    wadoc.setLEV(checkIsNull(obj[3]));
                    wadoc.setNMONEY(checkIsNull(obj[4]));
                    wadoc.setREASON(checkIsNull(obj[5]));
                    wadoc.setLASTFLAG(checkIsNull(obj[6]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return wadoc;
    }
    /**
     * 判断数据库中获取的字段数据是否为空，为空返回“---”
     * */
    public String checkIsNull(Object obj){
        if(obj != null){
            return obj.toString();
        }else{
            return "---";
        }
    }
}
