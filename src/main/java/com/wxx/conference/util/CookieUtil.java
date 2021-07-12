/**  
 * @Title: CookieUtil.java
 * @Package net.openfree.common.util
 * @date 2015-10-16 下午13:13:13
 */
package com.wxx.conference.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: CookieUtil
 * @Description: Cookie工具类
 * @Author:wxx
 * @Company:泛海控股
 */
public class CookieUtil {
	
	/**
	 * 
	 * @Title: addCookie
	 * @Description: TODO
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 *
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		if(maxAge>0) cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @Title: getCookieByName
	 * @Description: TODO
	 * @param request
	 * @param name
	 * @return
	 *
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		if(cookieMap.containsKey(name)){
			Cookie cookie = (Cookie)cookieMap.get(name);
			return cookie;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getCookieValueByName
	 * @Description: TODO
	 * @param request
	 * @param name
	 * @return
	 *
	 */
	public static String getCookieValueByName(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		if(cookieMap.containsKey(name)){
			Cookie cookie = (Cookie)cookieMap.get(name);
			return cookie.getValue();
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * @Title: ReadCookieMap
	 * @Description: TODO
	 * @param request
	 * @return
	 *
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){ 
		Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){
			for(Cookie cookie : cookies){
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
