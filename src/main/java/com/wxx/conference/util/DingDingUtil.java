package com.wxx.conference.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by thinkpad on 2020-9-30.
 */
public class DingDingUtil {

    private static final String appId = "dingoav1wmfsmqapy5pb28";
    private static final String Secret = "HzyhchCW401h9Gh08GwnVRzklY6OroJurtVjkMULoOg1SnvnUtIkYIdpfW8BSuxc";
    private static final String appKey = "dingtpme6oisoz0zswhp";
    private static final String appSecret = "jiTx_BhtoFs5dNhpe0XtK9LWVXzRt1JyCIeUmETBqG2Sxk7MU0tR-PIHN8BQuSwg";
    public static void main(String[] args) {
        try{
//            getUserInfo();
            String token = getDingDingToken();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getDingDingToken(){
        String token = "";
        try{
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(appKey);
            request.setAppsecret(appSecret);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            token = response.getAccessToken();
            System.out.println("token:"+token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static String getTicket(String accessToken){
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setTopHttpMethod("GET");
        String ticket = "";
        try{
            OapiGetJsapiTicketResponse execute = client.execute(req, accessToken);
            ticket = execute.getTicket();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ticket;
    }
    public static String sign(String ticket, String nonceStr, long timeStamp, String url){
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        String sign = "";
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(plain.getBytes("UTF-8"));
            sign = byteToHex(sha1.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    // 字节数组转化成十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    public static String getUserId(String token,String authCode){
        String userId = "";
        try{
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(authCode);
            request.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client.execute(request, token);
            userId = response.getUserid();
            System.out.println("userId:"+userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userId;
    }
    public static OapiUserGetuserinfoResponse getUserInfo(String token,String authCode){
        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
        try{
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(authCode);
            request.setHttpMethod("GET");
            response = client.execute(request, token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    public static String getCode(){

        //https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=dingoav1wmfsmqapy5pb28&response_type=code&scope=snsapi_auth&state=STATE&redirect_uri=http://10.0.125.112:8080/StellaConference/CV/CVSearchEnter
        String url = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid="+appId+"&response_type=code&scope=snsapi_auth&state=STATE&redirect_uri=REDIRECT_URI";
        String rurl = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=dingoav1wmfsmqapy5pb28&response_type=code&scope=snsapi_auth&state=STATE&redirect_uri=http://10.0.125.112:8080/StellaConference/CV/CVSearchEnter";
        HttpPost httpput = new HttpPost(url);
        httpput.setHeader("Content-Type", "application/json");
        DefaultHttpClient client = new DefaultHttpClient();
        try{
            HttpResponse httpResponse = client.execute(httpput);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String str = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String getTmpAuthCode(String timestamp,String signature){
        String url = "https://oapi.dingtalk.com/sns/getuserinfo_bycode?accessKey="+appId+"&timestamp="+timestamp+"&signature="+signature;
        HttpPost httpput = new HttpPost(url);
        httpput.setHeader("Content-Type", "application/json");
        DefaultHttpClient client = new DefaultHttpClient();
        try{
            HttpResponse httpResponse = client.execute(httpput);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String str = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void getUserInfo(String tmpAuthCode) throws Exception{
        DefaultDingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(tmpAuthCode);
        OapiSnsGetuserinfoBycodeResponse response = client.execute(req,appId,appSecret);
        response.getUserInfo();
    }

    // encoding参数使用utf-8
    public static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded.replace("+", "%20").replace("*", "%2A")
                    .replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToEncodeUri", e);
        }
    }
}
