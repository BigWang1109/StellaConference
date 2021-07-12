package com.wxx.conference.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;


public class SendOfFile  {

	protected static void process() throws Exception {
		// 获取Servlet连接并设置请求的方法
		String url = "http://10.0.6.149:9090/service/XChangeServlet";
		URL realURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) realURL
				.openConnection();
		connection.setDoOutput(true);
		//test
//		connection.setDoInput(true);
		connection.setRequestProperty("Content-type", "text/xml");
		connection.setRequestMethod("POST");
		// 将Document对象写入连接的输出流中
		File file = new File("C:/Users/thinkpad/Desktop/voucherDemo.xml");
		BufferedOutputStream out = new BufferedOutputStream(
				connection.getOutputStream());
		BufferedInputStream input = new BufferedInputStream(
				new FileInputStream(file));
		int length;
		byte[] buffer = new byte[1000];
		while ((length = input.read(buffer, 0, 1000)) != -1) {
			out.write(buffer, 0, length);
		}
		input.close();
		out.close();

		// 从连接的输入流中取得回执信息
		InputStream inputStream = connection.getInputStream();
		Document resDoc = (Document) getDocumentBuilder().parse(inputStream); // 解析为Doc对象
		// 对回执结果的后续处理…
		// 解析document文档转化成String
		DOMSource domSource = new DOMSource(resDoc);

		StringWriter writer = new StringWriter();

		StreamResult result = new StreamResult(writer);

		TransformerFactory tf = TransformerFactory.newInstance();

		Transformer transformer = tf.newTransformer();

		transformer.transform(domSource, result);

		System.out.println(writer.toString());

	}

//	@Override
//	public PreAlertObject executeTask(BgWorkingContext arg0)
//			throws BusinessException {
//		try {
//			process();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static DocumentBuilder getDocumentBuilder() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false);
		dbf.setNamespaceAware(true);
		try {
			return dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Failed to construct XML Parser!");
		}
	}

	public static  void main(String []args){
		try {
			process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
