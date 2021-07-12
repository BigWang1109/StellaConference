package com.wxx.conference.test;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifThumbnailDirectory;
import com.sun.java.util.jar.pack.*;
import com.wxx.conference.util.FileUtil;
import com.wxx.conference.util.HibernateHRDBUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.util.*;

/**
 * Created by thinkpad on 2020-6-11.
 */
public class test {
    public static void main(String[] args) {
        try{
//            test3();
//            rotateImg();
//            getImg("10001341");
//            getImg("00025766");
//            byte[] data = getImg("10001341");
//            createImg(data);
//            testSet();
//            FileUtil.downloadFile("http://localhost/QRCode/testpdf/default.pdf","E:\\QRCode\\testDown");
//            FileUtil.SCPDownLoadFile();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void testSet(){
        HashSet res = new HashSet();
        HashSet set1 = new HashSet();
        HashSet set2 = new HashSet();
        set1.add("00025766");
        set1.add("00022486");
        set1.add("123456");

        set2.add("00025766");
        set2.add("123456");

        res.addAll(set1);
        res.retainAll(set2);

        System.out.println(res.size());
    }
    public static byte[] getImg(String PSNCODE){
        Session session = null;
        byte[] data = null;
        try {
            session = HibernateHRDBUtil.getSession();
//            String hql = "select previewphoto from oabi.zgfh_bi_test_photo where CODE='10001341'";
            String hql = "select photo from bd_psndoc where CODE='88888888'";
//            String hql = "select photo from oabi.zgfh_bi_psndoc2020 where PSNCODE='00025766'";
//            String hql = "select photo from oabi.zgfh_bi_test_photo where CODE=:PSNCODE";
            Query query = session.createSQLQuery(hql);
//            query.setString("CODE", PSNCODE);
            java.util.List array = query.list();
            if (array.size() > 0) {
                Object obj =  array.get(0);
                if (obj != null){
                    Blob blob = (Blob) obj;
                    long size = blob.length();
                    data = blob.getBytes(1,(int)size);
//                    String str = DatatypeConverter.printBase64Binary(data);
//                    String str = new String(blob.getBytes(1,(int)size),"GBK");
//                    data = DatatypeConverter.parseBase64Binary(str);
//                    System.out.println(str);

//                    InputStream msgContent = blob.getBinaryStream();
//                    ByteArrayOutputStream output = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[100];
//                    int n = 0;
//                    while (-1 != (n = msgContent.read(buffer))) {
//                        output.write(buffer, 0, n);
//                    }
//                    String result ="data:image/jpg;base64,"+new BASE64Encoder().encode(output.toByteArray()) ;
//                    System.out.println(result);
//                    data = new BASE64Decoder().decodeBuffer(result.trim());
                }
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
    public static void createImg(byte[] data){
        String path = "E:\\QRCode\\test2\\10001341.JPG";
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data,0,data.length);
            imageOutput.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void rotateImg() throws Exception{
        File jpegFile = new File("E:\\QRCode\\test\\00000017.JPG");
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
//        Directory dir = metadata.getDirectories();
//        int ori = metadata.getFirstDirectoryOfType(ExifDirectoryBase.TAG_ORIENTATION);

        for(Directory dir : metadata.getDirectories()){
            for (Tag tag : dir.getTags()) {
//                String tag_name = tag.getTagName();
//                String des = tag.getDescription();
//                System.out.println(tag_name+":"+des);
                System.out.println(tag);
            }
        }
    }

    public static void testRotate() throws Exception{
        String path = "E:\\QRCode\\test\\00000017.JPG";
        File img = new File(path);
        BufferedImage old_img = (BufferedImage) ImageIO.read(img);
        int w = old_img.getWidth();
        int h = old_img.getHeight();

        BufferedImage new_img = new BufferedImage(h, w, BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = new_img.createGraphics();

        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform) (origXform.clone());
    // center of rotation is center of the panel
        double xRot = w / 2.0;
        newXform.rotate(Math.toRadians(270.0), xRot, xRot); //旋转270度

        g2d.setTransform(newXform);
    // draw image centered in panel
        g2d.drawImage(old_img, 0, 0, null);
    // Reset to Original
        g2d.setTransform(origXform);
    //写到新的文件
        FileOutputStream out = new FileOutputStream("D:\\test2.jpg");
        try {
            ImageIO.write(new_img, "JPG", out);
        } finally {
            out.close();
        }
    }
    public static void test2() throws Exception{
        File file = new File("E:\\QRCode\\test\\00000017.JPG");
        Metadata metadata = JpegMetadataReader.readMetadata(file);
        Directory directory = metadata.getFirstDirectoryOfType(ExifThumbnailDirectory.class);
        int orientation=0;
        if(directory != null && directory.containsTag(ExifThumbnailDirectory.TAG_ORIENTATION)){ // Exif信息中有保存方向,把信息复制到缩略图
            orientation = directory.getInt(ExifThumbnailDirectory.TAG_ORIENTATION); // 原图片的方向信息
            System.out.println(orientation);
        }
        if(orientation == 8){
            directory.setInt(ExifThumbnailDirectory.TAG_ORIENTATION,1);
        }

//        if(orientation == 8){
//            System.out.println("rotate 270");
//            BufferedImage src = ImageIO.read(file);
//            BufferedImage des = Rotate(src, 270);
//            ImageIO.write(des,"jpg", new File("E:\\QRCode\\test\\result.JPG"));
//        }
    }

    public static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // transform
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion
        if (angel >= 90) {
            if(angel / 90 % 2 == 1){
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

    public static void test3(){
        String path = "E:\\QRCode\\test\\00025766.JPG";
        try{
            FileUtil.imageCompress(path,path);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
