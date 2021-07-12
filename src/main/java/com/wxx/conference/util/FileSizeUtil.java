/**
 * @Title: FileSizeUtil.java
 * @Package net.openfree.common.util
 * @date 2015-10-16 下午13:13:13
 */
package com.wxx.conference.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

/**
 * 
 * @ClassName: FileSizeUtil
 * @Description: 一个将Byte单位文件大小转换为KB和MB单位
 * @Author:wxx
 * @Company:泛海控股
 */
public class FileSizeUtil {
	
	/**
	 * 
	 * @Title: formatFileSize
	 * @Description: 一个将Byte单位文件大小转换为B\KB\MB\GB单位(String)
	 * @param fileSize
	 * @return
	 *
	 */
	public static String formatFileSize(String fileSize){
		long _fileSize = Long.parseLong(fileSize);
		/**size 如果小于1024,以B单位返回;小于1024 * 1024,以KB单位返回;小于1024 * 1024 * 1024,以MB单位返回;否则以GB单位返回*/
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (_fileSize < 1024) {
			f = (float) ((float) _fileSize / (float) 1);
			return (df.format(new Float(f).doubleValue())+"B");
		}else if (_fileSize < 1024 * 1024) {
			f = (float) ((float) _fileSize / (float) 1024);
			return (df.format(new Float(f).doubleValue())+"KB");
		}else if(_fileSize < 1024 * 1024 * 1024){
			f = (float) ((float) _fileSize / (float) (1024 * 1024));
			return (df.format(new Float(f).doubleValue())+"MB");
		}else{
			f = (float) ((float) _fileSize / (float) (1024 * 1024 * 1024));
			return (df.format(new Float(f).doubleValue())+"GB");
		}
	}
	
	/**
	 * 
	 * @Title: formatFileSize
	 * @Description: 一个将Byte单位文件大小转换为B\KB\MB\GB单位(Long)
	 * @param fileSize
	 *
	 */
	public static String formatFileSize(long fileSize){
		/**size 如果小于1024,以B单位返回;小于1024 * 1024,以KB单位返回;小于1024 * 1024 * 1024,以MB单位返回;否则以GB单位返回*/
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (fileSize < 1024) {
			f = (float) ((float) fileSize / (float) 1);
			return (df.format(new Float(f).doubleValue())+"B");
		}else if (fileSize < 1024 * 1024) {
			f = (float) ((float) fileSize / (float) 1024);
			return (df.format(new Float(f).doubleValue())+"KB");
		}else if(fileSize < 1024 * 1024 * 1024){
			f = (float) ((float) fileSize / (float) (1024 * 1024));
			return (df.format(new Float(f).doubleValue())+"MB");
		}else{
			f = (float) ((float) fileSize / (float) (1024 * 1024 * 1024));
			return (df.format(new Float(f).doubleValue())+"GB");
		}
	}
	
	/**
	 * 
	 * @Title: getFileSize
	 * @Description: 获取文件大小
	 * @param file
	 * @return
	 * @throws Exception
	 *
	 */
	public static long getFileSizes(File file) throws Exception{
		long size=0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size= fis.available();
			if(fis !=null) fis.close();
		} else {
			throw new FileNotFoundException(file.getName()+"文件不存在");
		}
		return size;
	}
	
	/**
	 * 
	 * @Title: getFloderSize
	 * @Description: 递归取得文件夹大小
	 * @param floder
	 * @return
	 * @throws Exception
	 *
	 */
	public static long getFloderSize(File floder)throws Exception{
		long size = 0;
		if(floder==null){
			return size;
		}
		if (!floder.exists()) {
			throw new FileNotFoundException(floder.getName()+"文件不存在");
		} else if(floder.isDirectory()) {
			 File fList[] = floder.listFiles();
			 for (int i = 0; i < fList.length; i++){
				 if (fList[i].isDirectory()){
					 size = size + getFloderSize(fList[i]);
				 }else{
					 size = size + fList[i].length();
				 }
			 }
		}else if(floder.isFile()){
			//floder 是文件不是文件夹直接返回文件大小
			size = size + floder.length();
		}
		return size;
	}

	/**
	 * 
	 * @Title: getFloderListNum
	 * @Description: 递归求取目录文件个数
	 * @param floder
	 * @return
	 * @throws Exception
	 *
	 */
	public static long getFloderListNum(File floder)throws Exception{
		long size = 0;
		if (!floder.exists()) {
			throw new FileNotFoundException(floder.getName()+"文件不存在");
		} else if(floder.isDirectory()) {
			File flist[] = floder.listFiles();
			size=flist.length;
			for (int i = 0; i < flist.length; i++) {
				if (flist[i].isDirectory()) {
					size = size + getFloderListNum(flist[i]);
					size--;//不计算文件目录
				}
			}
		}else if(floder.isFile()){
			size = 1L;
		}
		return size;
	}
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		File testFile = new File("C:\\WINDOWS");
		try {
			System.out.println(FileSizeUtil.getFloderListNum(testFile));
			System.out.println(FileSizeUtil.getFloderSize(testFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("总共花费时间为：" + (endTime - startTime) + "毫秒...");
	}
}

