package com.wxx.conference.test;

import com.wxx.conference.model.HR.ZGFH_BI_TEST_PHOTO;
import com.wxx.conference.model.HR.zgfh_bi_psndoc;
import com.wxx.conference.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mozilla.universalchardet.UniversalDetector;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by thinkpad on 2020-9-28.
 */
public class testBase64 {
    public static final String separator = File.separator;
    public static void main(String[] args) {
        getImgNew("00286");
//        getFileById("1002A11000000001RP7O","110788");
//        getImgNew("10001341");
//        getImgNew("88888888");
//        getImg("10001341");
    }
    public static byte[] getImgNew(String PSNCODE){
        Session session = null;
        byte[] data = null;
        String rootPath = "E:"+separator+"QRCode"+separator+"headImg";
//        String rootPath = PropertiesUtil.getProperty("imgPath");
//        String rootPath = separator +"soft"+separator+"module"+separator+"apache-tomcat-7.0.105"+separator+"webapps"+separator+"headImg";
        String originalImgPath = rootPath + separator + PSNCODE+".JPG";
        String compressImgPath = rootPath + separator + "compress" + separator + PSNCODE +".JPG";
        String compressPath = rootPath + separator + "compress";
        String defaultImg = rootPath + separator + "default.JPG";

        try {
            File file = new File(originalImgPath);
            if(!file.exists()){
                session = HibernateHRDBUtil.getSession();
                String hql = "select PHOTO from oabi.zgfh_bi_psndoc2020 where PSNCODE=:PSNCODE";
//                String hql = "select PHOTO from oabi.ZGFH_BI_TEST_PHOTO where CODE=:PSNCODE";
//                String hql = "select previewphoto from oabi.ZGFH_BI_TEST_PHOTO where CODE=:PSNCODE";
//                String hql = "select PHOTO from oabi.ZGFH_BI_TEST_PHOTO where CODE=:PSNCODE";
//                String hql = "select PHOTO from bd_psndoc where CODE=:PSNCODE";
                Query query = session.createSQLQuery(hql);
                query.setString("PSNCODE", PSNCODE);
                List array = query.list();
                if (array.size() > 0) {
                    Object obj =  array.get(0);
                    if (obj != null){
                        Blob blob = (Blob) obj;
                        long size = blob.length();
                        data = blob.getBytes(1,(int)size);
                        String res = new sun.misc.BASE64Encoder().encode(data);
                        System.out.println(res);
                        int start = res.indexOf("9j")-1;
                        System.out.println("start:"+start);
                        res = res.substring(start,res.length());
//                        UniversalDetector detector = new UniversalDetector(null);
//                        detector.handleData(data, 0, data.length);
//                        detector.dataEnd();
//                        String encoding = detector.getDetectedCharset();
//                        detector.reset();
//                        System.out.println(encoding);
//                        ByteArrayOutputStream out = new ByteArrayOutputStream();
//                        ObjectOutputStream sout = new ObjectOutputStream(out);
//                        sout.writeObject(obj);
//                        data = out.toByteArray();
//                        String encoded = new BASE64Encoder().encode(data);
//                       String res = new String(data,"GBK");
                        System.out.println(res);
//                       String res = encode(data);
//                        System.out.println(res);

//                        data = res.getBytes("UTF-8");
//                        String encoded = new BASE64Encoder().encode(data);
//                        System.out.println(encoded);
                        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
                        data = dec.decodeBuffer(res);
//                        encoded = new BASE64Encoder().encode(data);
//                        System.out.println(encoded);
                    }
                }
                if(data!=null && data.length > 0){
                    FileUtil.savePhotoToLocalNew(data, originalImgPath);
                    FileUtil.imageCompress(originalImgPath, compressPath);
                    data = FileUtil.getImgFromLocal(compressImgPath);
                }else{
                    data = FileUtil.getImgFromLocal(defaultImg);
                }
            }else{
                data = FileUtil.getImgFromLocal(compressImgPath);
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
        return data;
    }
    public static byte[] getImg(String PSNCODE){
        Session session = null;
        byte[] data = null;
        String rootPath = "E:"+separator+"QRCode"+separator+"headImg";
//        String rootPath = PropertiesUtil.getProperty("imgPath");
//        String rootPath = separator +"soft"+separator+"module"+separator+"apache-tomcat-7.0.105"+separator+"webapps"+separator+"headImg";
        String originalImgPath = rootPath + separator + PSNCODE+".jpg";
        String compressImgPath = rootPath + separator + "compress" + separator + PSNCODE +".jpg";
        String compressPath = rootPath + separator + "compress";
        String defaultImg = rootPath + separator + "default.JPG";

        try {
            File file = new File(originalImgPath);
            if(!file.exists()){
                session = HibernateHRDBUtil.getSession();
//                String hql = "select PHOTO from oabi.zgfh_bi_psndoc2020 where PSNCODE=:PSNCODE";
                String hql = "select PHOTO from ZGFH_BI_TEST_PHOTO where CODE=:PSNCODE";
                Query hquery = session.createQuery(hql);
                hquery.setString("PSNCODE", PSNCODE);
                List<ZGFH_BI_TEST_PHOTO> plist = hquery.list();
                ZGFH_BI_TEST_PHOTO zgfh = plist.get(0);
                data = zgfh.getPHOTO();
                if(data!=null && data.length > 0){
                    FileUtil.savePhotoToLocalNew(data, originalImgPath);
                    FileUtil.imageCompress(originalImgPath, compressPath);
                    data = FileUtil.getImgFromLocal(compressImgPath);
                }else{
                    data = FileUtil.getImgFromLocal(defaultImg);
                }
            }else{
                data = FileUtil.getImgFromLocal(compressImgPath);
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
        return data;
    }

    public static byte[] getFileById(String PK_ATTACHMENT,String PSNCODE){
        Session session = null;
        byte[] data = null;
//        String rootPath = "E:"+separator+"QRCode"+separator+"testDoc"+separator+PSNCODE;
        String rootPath = PropertiesUtil.getProperty("filePath")+separator+PSNCODE;
//        String rootPath = separator +"soft"+separator+"module"+separator+"apache-tomcat-7.0.105"+separator+"webapps"+separator+"headImg";;
        try {
            File file = new File(rootPath+separator+PK_ATTACHMENT+".pdf");
            if(!file.exists()){
                session = HibernateZQDBUtil.getSession();
                String hql = "select photo from ePhoto where eid=(select eid from eemployee where badge=:badge)";
                Query query = session.createSQLQuery(hql);
                query.setString("badge", PSNCODE);
                String ePhoto = "";
                String fileType = "";
                String fileName = "";
                List array = query.list();
                if (array.size() > 0) {
                    Object[] obj =  (Object[])array.get(0);
//                    Blob blob = (Blob) obj[0];
//                    long size = blob.length();
//                    data = blob.getBytes(1, (int) size);
                    ePhoto = obj[1].toString();
                    System.out.println(ePhoto);
                }
//                if(data!=null && data.length > 0){
//                    fileType = attachment_name.substring(attachment_name.lastIndexOf("."), attachment_name.length());
//                    fileName = attachment_name.substring(0, attachment_name.lastIndexOf("."));
//                    FileUtil.saveFileToLocalNew(data, rootPath, PK_ATTACHMENT + fileType);
//
//                    //如果是不pdf文件需先转换为pdf，目前只能转换.doc文件
//                    if(!fileType.equals(".pdf")){
////                        FileUtil.wordToPdf(rootPath+separator+PK_ATTACHMENT+".doc",rootPath+separator+PK_ATTACHMENT+".pdf");
////                        FileConverter fileConverter = new FileConverter(rootPath+separator+PK_ATTACHMENT+fileType);
////                        fileConverter.office2pdf();
//                    }
////                    data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+".pdf");
//                }
            }
            else{
//                data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+".pdf");
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
        return data;
    }

    public boolean customerExists(String phoneNumber){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        int count = 0;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("开始尝试连接NC数据库！");
//            String url = "jdbc:oracle:" + "thin:@10.0.125.200:1521:orcl";
            String url = "jdbc:oracle:thin:@"+PropertiesUtil.getProperty("NCURL");
//            String user = "ps_wh";
            String user = PropertiesUtil.getProperty("NCUser");
//            String password = "1";
            String password = PropertiesUtil.getProperty("NCPass");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("NC连接成功");
            String sql = "select count(*) from fhjszs.view_ps_customer_wh where vpreferredtel like ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, "%"+phoneNumber+"%");
            result = pre.executeQuery();
            while(result.next()){
                count = result.getInt(1);
                System.out.println(count);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count == 0?false:true;
    }

}
