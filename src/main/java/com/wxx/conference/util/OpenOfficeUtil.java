package com.wxx.conference.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;


import java.io.File;

/**
 * Created by thinkpad on 2020-10-21.
 */
public class OpenOfficeUtil {
    public static void main(String[] args) {
        File sourceFile = new File("E:\\QRCode\\hrfiles\\00022486\\2.docx");
        File targetFile = new File("E:\\QRCode\\hrfiles\\00022486\\2.pdf");
        convert(sourceFile,targetFile);
    }
    public static void convert(File sourceFile, File targetFile) {
//soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
        try {
            // 1: 打开连接
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();

            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            // 2:获取Format
//            DocumentFormatRegistry factory = new BasicDocumentFormatRegistry();
//            DocumentFormat inputDocumentFormat = factory
//                    .getFormatByFileExtension(getExtensionName(sourceFile.getAbsolutePath()));
//            DocumentFormat outputDocumentFormat = factory
//                    .getFormatByFileExtension(getExtensionName(targetFile.getAbsolutePath()));
            // 3:执行转换
            converter.convert(sourceFile,  targetFile);
            connection.disconnect();
        } catch (Exception e) {
//            log.info("文档转换PDF失败");
        }
    }
}
