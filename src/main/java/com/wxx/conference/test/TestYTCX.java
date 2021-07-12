package com.wxx.conference.test;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxx.conference.util.MDUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;



public class TestYTCX {

	public static String token = "";
	public static Long orgAccountId;
	public static DefaultHttpClient client = new DefaultHttpClient();

	public static void getNC(){
//		String url = "http://10.0.125.17:8080/StellaConference/nc/checkIsFollowed";
//		String url = "http://localhost:8060/nc/checkIsFollowed";
		String url = "http://localhost:8060/nc/checkIsOld";
		HttpPost httpput = new HttpPost(url);
		httpput.setHeader("Content-Type", "application/json");
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		long timeStamp = new Date().getTime();
//		System.out.println(timeStamp);
		String appid = "bEOBRzSe3Q70zpuh";
		String appKey = "v9XB4EF7jAA5fkDf";
		String token = MDUtil.md5(appid+timeStamp+appKey);
//		System.out.println(token);
		try{
			Map<String,Object> header = new HashMap<String, Object>();
			header.put("appid",appid);
			header.put("timestamp",timeStamp);
			header.put("token",token);

			Map<String,String> data = new HashMap<String, String>();
			data.put("checkType","tel");
			data.put("checkValue","13554510567/13986169042");
			String dataJson = mapper.writeValueAsString(data);

			Map<String,Object> postData = new HashMap<String, Object>();
			postData.put("header",header);
			postData.put("data", dataJson);

			String postDataJson = mapper.writeValueAsString(postData);
//			JSONObject param = new JSONObject(postData);
			StringEntity entity = new StringEntity(postDataJson,"utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpput.setEntity(entity);
			HttpResponse httpResponse = client.execute(httpput);
//			System.out.println(httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(httpResponse.getEntity());
				Map returnData = mapper.readValue(str, Map.class);
				Map resultData = (Map)returnData.get("data");
				int errCode = (Integer)resultData.get("errcode");
				String errMsg = (String)resultData.get("errmsg");
				if(errCode==0){
					Map contentMap = (Map)resultData.get("content");
//					String res = contentMap.get("IsFollowed").toString();
					String res = contentMap.get("IsOldCst").toString();
					System.out.println("返回结果:"+res);
				}else{
					System.out.println("返回错误信息:"+errMsg);
				}
//				HttpEntity httpEntity = httpResponse.getEntity();
//				result = EntityUtils.toString(httpEntity);
//				result.replaceAll("\r", "");
//				JSONObject obj = new JSONObject(result);
//				token = (String) obj.get("id");
				System.out.println(result);
			} else {
				httpput.abort();
			}
		}catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			httpput.releaseConnection();
		}
	}

	public static void getToken(){
		String url = "http://172.40.2.87/seeyon/rest/token";
		String result = "";

		HttpPost httpput = new HttpPost(url);
		httpput.setHeader("Content-Type", "application/json");
		try {
			JSONObject param = new JSONObject();
			param.put("userName", "ceshi");      //rest账户用户名
			param.put("password", "123456");   //rest账户密码
			httpput.setEntity(new StringEntity(param.toString()));
			HttpResponse httpResponse = client.execute(httpput);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);
				result.replaceAll("\r", "");
				JSONObject obj = new JSONObject(result);
				token = (String) obj.get("id");
				System.out.println(result);
			} else {
				httpput.abort();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			httpput.releaseConnection();
		}
	}
	public static void getOrgAccount(String param){
		String result = "";
		param = URLEncoder.encode(param);
		String url = "http://172.40.2.87/seeyon/rest/orgAccount/name/" + param;

		HttpGet httpput = new HttpGet(url);
		httpput.setHeader("token", token);
//		httpput2.setHeader("Content-Type","application/json; charset=UTF-8");
		try {
			HttpResponse httpResponse = client.execute(httpput);
			System.out.println(" ----------------" + httpResponse.getStatusLine().getStatusCode() + "-----------------------");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);
				result.replaceAll("\r", "");
				JSONObject jObject = new JSONObject(result);
				orgAccountId = jObject.getLong("id");
				System.out.println("orgAccountId:"+orgAccountId);
				System.out.println(jObject.toString());

			} else {
				httpput.abort();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			httpput.releaseConnection();
		}
	}
	public static void getOrgDepartments(){
		String result = "";
		String param = URLEncoder.encode(String.valueOf(orgAccountId));
//		String url = "http://172.40.2.87/seeyon/rest/orgDepartments/" + String.valueOf(orgAccountId);
		String url = "http://172.40.2.87/seeyon/rest/orgDepartments/"+param;

		HttpGet httpput = new HttpGet(url);
		httpput.setHeader("token", token);
//		httpput2.setHeader("Content-Type","application/json; charset=UTF-8");
		try {
			HttpResponse httpResponse = client.execute(httpput);
			System.out.println(" ----------------" + httpResponse.getStatusLine().getStatusCode() + "-----------------------");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);
				result.replaceAll("\r", "");
				JSONArray array = new JSONArray(result);
				System.out.println(array.length());
				for(int i=0;i<array.length();i++) {
//					System.out.println(array.get(i).toString());
					JSONObject jsonObject = new JSONObject(array.get(i).toString());
//					if("".equals(jsonObject.getString("code"))) {
//						System.out.println(jsonObject.getString("name")+ "----" + jsonObject.getString("code"));
//						System.out.println("----------------------------------");
//					}
					System.out.println(jsonObject.toString());
				}

			} else {
				httpput.abort();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			httpput.releaseConnection();
		}
	}
	public static void getOrgMemberes(){
		String result = "";
		String param = URLEncoder.encode(String.valueOf(orgAccountId));
		String url = "http://172.40.2.87/seeyon/rest/orgMembers/"+param;

		HttpGet httpput = new HttpGet(url);
		httpput.setHeader("token", token);
//		httpput2.setHeader("Content-Type","application/json; charset=UTF-8");
		try {
			HttpResponse httpResponse = client.execute(httpput);
			System.out.println(" ----------------" + httpResponse.getStatusLine().getStatusCode() + "-----------------------");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);
				result.replaceAll("\r", "");
				JSONArray array = new JSONArray(result);
				System.out.println(array.length());
				for(int i=0;i<array.length();i++) {
//					System.out.println(array.get(i).toString());
					JSONObject jsonObject = new JSONObject(array.get(i).toString());
//					if("".equals(jsonObject.getString("code"))) {
//						System.out.println(jsonObject.getString("name")+ "----" + jsonObject.getString("code"));
//						System.out.println("----------------------------------");
//					}
					System.out.println(jsonObject.toString());
				}

			} else {
				httpput.abort();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			httpput.releaseConnection();
		}
	}
	public static void main(String[] args) {
//		String param = "测试公司";
//		String param = "北京分公司";
//		getToken();
//		getOrgAccount(param);
//		getOrgDepartments();
//		getOrgMemberes();
		getNC();
	}
	

}

