package com.wxx.conference.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by thinkpad on 2019-11-7.
 */
public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);
    public static void main(String[] args) {
        String address = "http://10.0.125.7/seeyon/rest/token";
        String uName = "fhkg";
        String uPass = "fhkg123456";
        String destUrl = "http://10.0.125.7/seeyon/rest/orgAccounts";
        requireJsonData(address,uName,uPass,destUrl);
    }
    //将http请求body中的数据转换为字符串后返回
    public static String formRequestString(HttpServletRequest request){
        String valueStr = "";
        try{
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while((s = br.readLine()) != null){
                sb.append(s);
            }
            valueStr = sb.toString();
        }catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return valueStr;
    }
    public static String requireJsonData(String address, String uName, String uPass, String destUrl){
        String mark ="";
        String result = "";

        //(1) 首先发送put请求从OA服务器获取认证信息
        HttpClient httpClient = HttpClients.custom().setConnectionManager(new PoolingHttpClientConnectionManager()).build();
        HttpPost httpput = new HttpPost(address);
        httpput.setHeader("Content-Type", "application/json");
        try {
            JSONObject param = new JSONObject();
            param.put("userName", uName);      //rest账户用户名
            param.put("password", uPass);   //rest账户密码
            httpput.setEntity(new StringEntity(param.toString()));
            HttpResponse httpResponse = httpClient.execute(httpput);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
                result.replaceAll("\r", "");
                JSONObject obj = JSONObject.parseObject(result);
                mark = (String) obj.get("id");
                logger.debug("Token获取成功！");
            } else {
                httpput.abort();
                logger.debug("获取Token时状态码返回异常，请检查请求参数配置...");
            }
        } catch (ClientProtocolException e1) {
            logger.error("<-- 客户端协议异常... {} -->" , e1);
        } catch (IOException e2) {
            logger.error("<-- IO异常... {} -->" , e2);
        } finally {
            httpput.releaseConnection();
            logger.debug("<-- put请求已释放 -->");
        }

        //(2)发送携带了认证信息的GET请求获取OA系统中机构人员数据
        HttpGet httpGet = new HttpGet(destUrl);
        httpGet.setHeader("token", mark);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
                result.replaceAll("\r", "");
                logger.info("OA系统结果返回成功！");
            } else {
                httpGet.abort();
                logger.info("获取数据时状态码返回异常，请检查请求参数配置...");
            }
        } catch (ClientProtocolException e1) {
            logger.error("<-- 客户端协议异常... {} -->" , e1);
        } catch (IOException e2) {
            logger.error("<-- IO异常... {} -->" , e2);
        } finally {
            httpGet.releaseConnection();
            logger.debug("<-- get请求已释放 -->");
        }
        return result;  //返回从OA获取到的json串
    }
}
