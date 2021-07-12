package com.wxx.conference.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by thinkpad on 2020-10-21.
 */
public class FileConverter {
    private static final int environment = 1;// 环境 1：Windows 2：Linux
    public static final String separator = File.separator;
    /**
     * OpenOffice相关配置
     */
    private static final String OpenOffice_HOME = PropertiesUtil.getProperty("OpenOffice_HOME");
//    private static final String OpenOffice_HOME = "/opt/openoffice4";
    private static final String host_Str = "127.0.0.1";
    private static final String port_Str = "8100";

    /**
     * SWFTools相关配置
     */
    private static final String SWFTools_HOME = "D:/DevelopmentTools/SWFTools";


    private String fileString;// (只涉及PDF2swf路径问题)
    private String outputPath = "";// 输入路径 ，如果不设置就输出在默认 的位置
    private String fileName;

    private File pdfFile;
    private File swfFile;
    private File docFile;

    public FileConverter(String fileString) {
        ini(fileString);
        System.out.println("文件路径" + fileString);
    }

    /**
     * <p>
     * setFile 重新设置file
     * </p>
     *
     * @param fileString
     * @return void
     * @author XinLau
     * @since 2019年4月15日上午11:33:46
     */
    public void setFile(String fileString) {
        ini(fileString);
    }

    /**
     * <p>
     * ini 初始化
     * </p>
     *
     * @param fileString
     * @return void
     * @author XinLau
     * @since 2019年4月15日上午11:34:03
     */
    private void ini(String fileString) {
        this.fileString = fileString;
        fileName = fileString.substring(0, fileString.lastIndexOf("."));
        docFile = new File(fileString);
        pdfFile = new File(fileName + ".pdf");
        swfFile = new File(fileName + ".swf");
    }

    /**
     * <p>
     * office2pdf 转为PDF
     * </p>
     *
     * @throws Exception
     * @return void
     * @author XinLau
     * @since 2019年4月15日上午11:34:44
     */
    public void office2pdf() throws Exception {
        if (docFile.exists()) {
            if (!pdfFile.exists()) {
                try {
                    // 启动OpenOffice的服务
//                    String command = OpenOffice_HOME + "\\program\\soffice.exe -headless -accept=\"socket,host=" + host_Str
//                            + ",port=" + port_Str + ";urp;\"-nofirststartwizard";
//                    String command = OpenOffice_HOME + separator +"program"+separator+"soffice.exe -headless -accept=\"socket,host=" + host_Str
//                            + ",port=" + port_Str + ";urp;\"-nofirststartwizard";
//                    String command = OpenOffice_HOME + separator +"program"+separator+"./soffice.exe -headless -accept=\"socket,host=" + host_Str
//                            + ",port=" + port_Str + ";urp;\"-nofirststartwizard";
//                    System.out.println("###\n" + command);
//                    Process pro = Runtime.getRuntime().exec(command);
                    // 连接openoffice服务
                    OpenOfficeConnection connection = new SocketOpenOfficeConnection(host_Str, Integer.parseInt(port_Str));
                    connection.connect();
                    // 转换
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                    converter.convert(docFile, pdfFile);
                    // 关闭连接和服务 close the connection
                    connection.disconnect();
//                    pro.destroy();
                    System.out.println("****pdf转换成功，PDF输出： " + pdfFile.getPath() + "****");
                } catch (java.net.ConnectException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，openoffice 服务未启动！****");
                    throw e;
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，读取转换文件 失败****");
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                System.out.println("****已经转换为pdf，不需要再进行转化 ****");
            }
        } else {
            System.out.println("****swf转换器异常，需要转换的文档不存在， 无法转换****");
        }
    }

    /**
     * <p>
     * pdf2swf 转换成 swf,删除pdf文件
     * </p>
     *
     * @throws Exception
     * @return void
     * @author XinLau
     * @since 2019年4月15日上午11:35:31
     */
    private void pdf2swf() throws Exception {
        Runtime r = Runtime.getRuntime();
        if (!swfFile.exists()) {
            if (pdfFile.exists()) {
                if (environment == 1) {// windows环境处理
                    try {
                        // 注意修改 SWFTools工具的安装路径
                        String processArdess = SWFTools_HOME + "/pdf2swf.exe " + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9";
                        Long fileLength = pdfFile.length();
                        //processArdess = processArdess + " -s poly2bitmap";
                        Process p = r.exec(processArdess);
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.print(loadStream(p.getErrorStream()));
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.println("****swf转换成功，文件输出： " + swfFile.getPath() + "****");
                        if (pdfFile.exists()) {
                            pdfFile.delete();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }
                } else if (environment == 2) {// linux环境处理
                    try {
                        Process p = r.exec("pdf2swf" + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.print(loadStream(p.getErrorStream()));
                        System.err.println("****swf转换成功，文件输出： " + swfFile.getPath() + "****");
                        if (pdfFile.exists()) {
                            pdfFile.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            } else {
                System.out.println("****pdf不存在,无法转换****");
            }
        } else {
            System.out.println("****swf已经存在不需要转换****");
        }
    }

    /**
     *
     * <p>
     * pdfConverterSwf pdf文件转成swf文件，保留pdf文件
     * </p>
     *
     * @throws Exception
     * @return void
     * @author XinLau
     * @since 2019年4月16日上午9:14:34
     */
    public void pdfConverterSwf() throws Exception {
        Runtime r = Runtime.getRuntime();
        if (!swfFile.exists()) {
            if (pdfFile.exists()) {
                if (environment == 1) {// windows环境处理
                    try {
                        // 注意修改 SWFTools工具的安装路径
                        String processArdess = SWFTools_HOME + "/pdf2swf.exe " + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9";
                        Long fileLength = pdfFile.length();
                        Process p = r.exec(processArdess);
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.print(loadStream(p.getErrorStream()));
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.println("****swf转换成功，文件输出： " + swfFile.getPath() + "****");
						/*
						 * if (pdfFile.exists()) { pdfFile.delete(); }
						 */
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }
                } else if (environment == 2) {// linux环境处理
                    try {
                        Process p = r.exec("pdf2swf" + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.print(loadStream(p.getErrorStream()));
                        System.err.println("****swf转换成功，文件输出： " + swfFile.getPath() + "****");
						/*
						 * if (pdfFile.exists()) { pdfFile.delete(); }
						 */
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            } else {
                System.out.println("****pdf不存在,无法转换****");
            }
        } else {
            System.out.println("****swf已经存在不需要转换****");
        }
    }

    static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }

    /**
     * <p>
     * conver 转换主方法
     * </p>
     *
     * @return
     * @return boolean
     * @author XinLau
     * @since 2019年4月15日上午11:36:32
     */
    public boolean conver() {
        if (swfFile.exists()) {
            System.out.println("****swf转换器开始工作，该文件已经转换为 swf****");
            return true;
        }
        if (environment == 1) {
            System.out.println("****swf转换器开始工作，当前设置运行环境 windows****");
        } else {
            System.out.println("****swf转换器开始工作，当前设置运行环境 linux****");
        }
        try {
            office2pdf();
            pdf2swf();
            //pdfConverterSwf();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("文件存在吗？" + swfFile);
        if (swfFile.exists()) {
            System.out.println("存在");
            return true;
        } else {
            System.out.println("不存在");
            return false;
        }
    }

    /**
     * <p>
     * getswfPath 返回文件路径
     * </p>
     *
     * @return String
     * @author XinLau
     * @since 2019年4月15日上午11:37:17
     */
    public String getswfPath() {
        if (this.swfFile.exists()) {
            String tempString = swfFile.getPath();
            tempString = tempString.replaceAll("\\\\", "/");
            System.out.println("最后文件路径为" + tempString);
            return tempString;
        } else {
            return "文件不存在";
        }
    }

    /**
     * <p>
     * setOutputPath 设置输出路径
     * </p>
     *
     * @param outputPath
     * @return void
     * @author XinLau
     * @since 2019年4月15日上午11:37:40
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
        if (!outputPath.equals("")) {
            String realName = fileName.substring(fileName.lastIndexOf("/"), fileName.lastIndexOf("."));
            if (outputPath.charAt(outputPath.length()) == '/') {
                swfFile = new File(outputPath + realName + ".swf");
            } else {
                swfFile = new File(outputPath + realName + ".swf");
            }
        }
    }

    public static void main(String[] args) {

        String fileName = "C:\\Users\\thinkpad\\Desktop\\HR招标\\金蝶\\中国民生信托人力资源管理系统项目--技术标.docx";
        FileConverter fileConverter = new FileConverter(fileName);
//
//        try {
//            //fileConverter.pdf2swf();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        try {
			fileConverter.office2pdf();
//			String tempString = fileConverter.swfFile.getPath();
//			tempString = tempString.replaceAll("\\\\", "/");
//			System.out.println("最后文件路径为" + tempString);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//        boolean mark = fileConverter.conver();
//
//        String path = fileConverter.getswfPath();
//        System.out.println(path);
//
//        path = path.substring(path.lastIndexOf("/property-web"));
//        System.out.println(path);

    }

}
