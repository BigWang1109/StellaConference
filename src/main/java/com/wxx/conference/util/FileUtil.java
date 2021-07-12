package com.wxx.conference.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 王曉旭
 */
public class FileUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static final String separator = File.separator;

    //文件复制
    public static void saveFileFromInputStream(InputStream stream, String filename) throws IOException {
        FileOutputStream fs=new FileOutputStream(filename);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread=stream.read(buffer))!=-1){
            bytesum+=byteread;
            fs.write(buffer,0,byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }

    //文件复制
    public static boolean copy(String fileFrom, String fileTo){
        try {
            FileInputStream in = new FileInputStream(fileFrom);
            FileOutputStream out = new FileOutputStream(fileTo);
            byte[] bt = new byte[1024];
            int count;
            while ((count = in.read(bt)) > 0){
                out.write(bt, 0, count);
            }
            in.close();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 将图片保存至本地
     * */
    public static void savePhotoToLocal(byte[] data,String path){
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 将图片保存至本地
     * */
    public static void savePhotoToLocalNew(byte[] data,String path){
        try {
            FileOutputStream imageOutput = new FileOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.flush();
            imageOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 将文件保存只本地
     * @param data 文件byte数组;
     * @param path 文件存储目录
     * @param filename 包含后缀的文件名
     * */
    public static void saveFileToLocal(byte[] data,String path,String filename){
        try{
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            String newPath = path + separator + filename;
//            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(newPath));
            FileOutputStream output = new FileOutputStream(new File(newPath));
            output.write(data, 0, data.length);
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveFileToLocalNew(byte[] data,String path,String filename){
        try{
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            String newPath = path + separator + filename;
//            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(newPath));
            OutputStream output = new FileOutputStream(new File(newPath));
            InputStream in = new ByteArrayInputStream(data);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) !=-1) {
                output.write(buffer, 0, len);
            }
//            output.write(data, 0, data.length);
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 从本地读取图片
     * */
    public static byte[] getImgFromLocal(String path){
        byte[] data = null;
        FileImageInputStream input;
        try{
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    public static void imageCompress(String path,String compressPath) throws Exception{
        File file = new File(path);
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        File imageCompress = new File(compressPath);
//        if(imageCompress.exists()){
//            if(deleteDir(imageCompress)){
//                System.out.println("删除成功！");
//            }else{
//                System.out.println("删除失败！");
//            }
//        }
//        imageCompress.mkdirs();
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        System.out.println("图片大小为:"+file.length());
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        if(file.length() > 100000){
            imgWriteParams.setCompressionQuality(0.5F);
        }else{
            imgWriteParams.setCompressionQuality(0.8F);
        }
//        imgWriteParams.setCompressionQuality(0.5F);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel =ImageIO.read(file).getColorModel();
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(16, 16)));

        InputStream in = new FileInputStream(file);
        BufferedImage image = ImageIO.read(file);
        Integer heigth = image.getHeight();
        Integer width = image.getWidth();
        src = ImageIO.read(file);
        out = new FileOutputStream(imageCompress.getAbsolutePath()+separator+file.getName());
        imgWrier.reset();
        // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
        // OutputStream构造
        imgWrier.setOutput(ImageIO.createImageOutputStream(out));
        // 调用write方法，就可以向输入流写图片
        imgWrier.write(null, new IIOImage(src, null, null),
                imgWriteParams);
        out.flush();
        out.close();
    }

    public static File UrlDownloadFile(String urlPath,String downloadDir){
        File file = null;
        try{
            URL url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)conn;
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.connect();
            int fileLength = httpURLConnection.getContentLength();
            System.out.println("目标文件大小为:"+fileLength/(1024 * 1024) + "MB");
            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
//            String fileName = "20190524.txt";
            String fileName = "default.pdf";
            String path = downloadDir + "\\" + fileName;
            file = new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[]buf = new byte[1024];
            while((size = bin.read(buf)) != -1){
                len += size;
                out.write(buf,0,size);
                System.out.println("已下载--->"+len * 100 /fileLength + "%\n");
            }
            bin.close();
            out.close();
            System.out.println("文件下载成功");
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }
    /**
     * @param ip 服务器地址
     * @param port 服务器端口
     * @param username ssh用户名
     * @param password ssh用户密码
     * @param tarDir 目标文件地址
     * @param destDir 下载文件地址
     * @param fileName 文件名称
     * */
    public static void SCPDownLoadFile(String ip,int port,String username,String password,String tarDir,String destDir,String fileName){
        Connection connection = new Connection(ip, port);
        try{
            connection.connect();
            boolean flag = connection.authenticateWithPassword(username,password);
            if(flag){
                logger.info("认证成功");
                SCPClient scpClient = connection.createSCPClient();
                SCPInputStream ins = scpClient.get(tarDir);
                File file = new File(destDir);
                if(!file.exists()){
                    file.mkdirs();
                }
                File newFile = new File(destDir + fileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] b = new byte[4096];
                int i;
                while ((i = ins.read(b)) != -1){
                    fos.write(b,0, i);
                }
                fos.flush();
                fos.close();
                ins.close();
                connection.close();
                logger.info(fileName+"文件下载成功");
            }else{
                logger.info("认证失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * @param docPath
     * @param pdfPath
     * */
    public static void wordToPdf(String docPath,String pdfPath){
        try{
            //doc路径
            Document document = new Document(docPath);
            //pdf路径
            File outputFile = new File(pdfPath);
            //操作文档保存
            //linux需设置windows语言包，否则转换后是乱码
            FontSettings.setFontsFolder("/usr/share/fonts" + File.separator, true);
            document.save(outputFile.getAbsolutePath(), com.aspose.words.SaveFormat.PDF);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
