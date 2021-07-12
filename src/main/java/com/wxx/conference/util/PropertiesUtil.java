package com.wxx.conference.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;


/**
 * 
 * @description 配置文件加载
 * @author wxx
 * @date 2016-11-29
 * @time 下午06:16:27
 *
 * 调用方式  PropertiesUtil.getProperty("key");
 *
 */
public class PropertiesUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties properties;

	public static void loadconfig() {
		logger.info("常用配置信息开始加载...");
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("globals.properties");
		try {
			try {
				properties=new Properties();
				properties.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("加载配置文件异常。",e);
			} finally {
				inputStream.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("常用配置信息加载成功。");
	}

	public static String getProperty(String key) {
		if(null==properties){
			loadconfig();
		}
		return properties.getProperty(key);
	}
}
