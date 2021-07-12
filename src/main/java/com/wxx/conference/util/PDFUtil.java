package com.wxx.conference.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Blob;
import java.util.List;

/**
 * Created by thinkpad on 2020-10-20.
 */
public class PDFUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PDFUtil.class);
    public static final String separator = File.separator;
    public static void main(String[] args) {
//        READPDF("E:\\QRCode\\hrfiles\\00022486\\2.pdf");
    }


    /**
     * 读取pdf中文字信息(全部)
     */
    public static String READPDF(String inputFile,String fileName){
        //创建文档对象
        PDDocument doc =null;
        String content="";
        try {
            //加载一个pdf对象
            doc =PDDocument.load(new File(inputFile));
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper textStripper =new PDFTextStripper("GBK");
            content=textStripper.getText(doc);
//            content = content.replaceAll(" ","");
//            content = content.replaceAll("\\r\\n","");
//            vo.setContent(content);
//            System.out.println("内容:"+content);
            logger.info("文件《" + fileName + "》，文字内容抽取成功，共" + doc.getNumberOfPages() + "页");
//            System.out.println("全部页数"+doc.getNumberOfPages());
            //关闭文档
            doc.close();
        } catch (Exception e) {
            logger.error("文件《"+fileName+"》，文字内容抽取失败，失败原因为："+e.toString());
            e.printStackTrace();
            // TODO: handle exception
        }
        return content;
    }
}
