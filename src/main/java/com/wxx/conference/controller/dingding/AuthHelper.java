package com.wxx.conference.controller.dingding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxx.conference.model.dingding.JsapiTicket;
import com.wxx.conference.util.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;



/**
 * AccessToken和jsticket的获取封装
 */
public class AuthHelper {

    private static final String appKey = "dingtpme6oisoz0zswhp";
    private static final String appSecret = "jiTx_BhtoFs5dNhpe0XtK9LWVXzRt1JyCIeUmETBqG2Sxk7MU0tR-PIHN8BQuSwg";
    private static final String agentId = "855629345";
    private static final String corpId = "ding1d3c3af9301ce0d535c2f4657eb6378f";
    /**
     * 调整到1小时50分钟
     */
    public static final long cacheTime = 1000 * 60 * 55 * 2;

    /**
     * 在此方法中，为了避免频繁获取access_token，
     * 在距离上一次获取access_token时间在两个小时之内的情况，
     * 将直接从持久化存储中读取access_token
     *
     * 因为access_token和jsapi_ticket的过期时间都是7200秒
     * 所以在获取access_token的同时也去获取了jsapi_ticket
     * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
     * 具体信息请查看开发者文档--权限验证配置
     */
    public static String getAccessToken() throws Exception {
        long curTime = System.currentTimeMillis();
        JSONObject accessTokenValue = (JSONObject) DDFileUtil.getValue("accesstoken", appKey);
        String accToken = "";
        JSONObject jsontemp = new JSONObject();
        if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
            try {
//                ServiceFactory serviceFactory = ServiceFactory.getInstance();
//                CorpConnectionService corpConnectionService = serviceFactory.getOpenService(CorpConnectionService.class);
//                accToken = corpConnectionService.getCorpToken(appKey, appSecret);
                accToken = DingDingUtil.getDingDingToken();
                // save accessToken
                JSONObject jsonAccess = new JSONObject();
                jsontemp.clear();
                jsontemp.put("access_token", accToken);
                jsontemp.put("begin_time", curTime);
                jsonAccess.put(appKey, jsontemp);
                //真实项目中最好保存到数据库中
                DDFileUtil.write2File(jsonAccess, "accesstoken");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return accessTokenValue.getString("access_token");
        }
        return accToken;
    }

    /**
     * 获取JSTicket, 用于js的签名计算
     * 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
     */
    public static String getJsapiTicket(String accessToken) throws Exception {
        JSONObject jsTicketValue = (JSONObject) DDFileUtil.getValue("jsticket", appKey);
        long curTime = System.currentTimeMillis();
        String jsTicket = "";

//        if (jsTicketValue == null || curTime -
//                jsTicketValue.getLong("begin_time") >= cacheTime) {
//            ServiceFactory serviceFactory;
            try {
//                serviceFactory = ServiceFactory.getInstance();
//                JsapiService jsapiService = serviceFactory.getOpenService(JsapiService.class);
//
//                JsapiTicket JsapiTicket = jsapiService.getJsapiTicket(accessToken, "jsapi");

                jsTicket = DingDingUtil.getTicket(accessToken);

                JSONObject jsonTicket = new JSONObject();
                JSONObject jsontemp = new JSONObject();
                jsontemp.clear();
                jsontemp.put("ticket", jsTicket);
                jsontemp.put("begin_time", curTime);
                jsonTicket.put(appKey, jsontemp);
                DDFileUtil.write2File(jsonTicket, "jsticket");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsTicket;
//        }
//    else {
//            return jsTicketValue.getString("ticket");
//        }
    }

    public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
        try {
            return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
        } catch (Exception ex) {
            throw new OApiException(0, ex.getMessage());
        }
    }

    /**
     * 计算当前请求的jsapi的签名数据<br/>
     * <p>
     * 如果签名数据是通过ajax异步请求的话，签名计算中的url必须是给用户展示页面的url
     *
     * @param request
     * @return
     */
    public static String getConfig(HttpServletRequest request) {
        String urlString = request.getRequestURL().toString();
        String queryString = request.getQueryString();

        String queryStringEncode = null;
        String url;
        if (queryString != null) {
            queryStringEncode = URLDecoder.decode(queryString);
            url = urlString + "?" + queryStringEncode;
        } else {
            url = urlString;
        }
        /**
         * 确认url与配置的应用首页地址一致
         */
        System.out.println(url);

        /**
         * 随机字符串
         */
        String nonceStr = "abcdefg";
        long timeStamp = System.currentTimeMillis() / 1000;
//        String signedUrl = url;
        String signedUrl = "http://10.0.6.169:8060/DD/ddEnter";
        String accessToken = null;
        String ticket = null;
        String signature = null;

        try {
            accessToken = AuthHelper.getAccessToken();

            ticket = AuthHelper.getJsapiTicket(accessToken);
            signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> configValue = new HashMap<String, Object>();
        configValue.put("jsticket", ticket);
        configValue.put("signature", signature);
        configValue.put("nonceStr", nonceStr);
        configValue.put("timeStamp", timeStamp);
        configValue.put("corpId", corpId);
        configValue.put("agentId", agentId);

        String config = JSON.toJSONString(configValue);
        return config;
    }

    public static String getSsoToken() throws Exception {
        String url = "https://oapi.dingtalk.com/sso/gettoken?corpid=" + corpId + "&corpsecret=" + appSecret;
        JSONObject response = HttpHelper.httpGet(url);
        String ssoToken;
        if (response.containsKey("access_token")) {
            ssoToken = response.getString("access_token");
        } else {
            throw new Exception("Sso_token");
        }
        return ssoToken;

    }
}
