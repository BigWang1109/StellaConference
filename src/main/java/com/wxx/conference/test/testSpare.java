package com.wxx.conference.test;


import com.aspose.words.Document;

import java.io.File;

/**
 * Created by thinkpad on 2020-9-24.
 */
public class testSpare {
    public static void main(String[] args) throws Exception{
        //doc路径
        Document document = new Document("E:\\QRCode\\testDoc\\test.doc");
        //pdf路径
        File outputFile = new File("E:\\QRCode\\testDoc\\test.pdf");
        //操作文档保存
        document.save(outputFile.getAbsolutePath(), com.aspose.words.SaveFormat.PDF);
    }

}
