package com.wxx.conference.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;

/**
 * Created by thinkpad on 2021-6-9.
 */
public class WaterMarkUtil {
    public static String addTxtWaterMaker(String inputFile, String outputFile, String waterMarkName){
//        logger.debug("==========addTxtWaterMaker==========start");
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            // 使用系统字体
//            BaseFont base = BaseFont.createFont("C:/WINDOWS/Fonts/Simplified Arabic", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Rectangle pageRect;
            PdfGState gs = new PdfGState();
            //设置文字透明度
            gs.setFillOpacity(0.6f);
            gs.setStrokeOpacity(0.6f);
            //获取pdf总页数
            int total = reader.getNumberOfPages() + 1;
            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH;
            int textW;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            //得到文字的宽高
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());
            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                //得到一个覆盖在上层的水印文字
                under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                //设置水印文字颜色
                under.setColorFill(BaseColor.LIGHT_GRAY);
                //设置水印文字和大小
                under.setFontAndSize(base, 12);
//                under.setFontAndSize(base, 50);
                //这个position主要是为了在换行加水印时能往右偏移起始坐标
                int position = 0;
                int interval = -3;
                for (int height = interval + textH; height < pageRect.getHeight(); height = height + textH * 6) {
                    for (int width = interval + textW -position * 150; width < pageRect.getWidth() + textW; width = width + textW) {
                        //添加水印文字，水印文字成25度角倾斜
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW , height - textH, 25);
                    }
//                    position++;
                    position+=textW;
                }
                // 添加水印文字
                under.endText();
            }
            //关闭流
            stamper.close();
            reader.close();
//            logger.debug("==========addTxtWaterMaker==========end");
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("文件{}增加水印异常:{}",inputFile,e.getMessage());
            return inputFile;
        }
        return outputFile;
    }
    public static String addTxtWaterMakerPro(String inputFile, String outputFile, String waterMarkName,String fontSize,String opacity){
//        logger.debug("==========addTxtWaterMaker==========start");
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            // 使用系统字体
//            BaseFont base = BaseFont.createFont("C:/WINDOWS/Fonts/Simplified Arabic", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Rectangle pageRect;
            PdfGState gs = new PdfGState();
            //设置文字透明度
            gs.setFillOpacity(Float.parseFloat(opacity));
            gs.setStrokeOpacity(Float.parseFloat(opacity));
            //获取pdf总页数
            int total = reader.getNumberOfPages() + 1;
            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH;
            int textW;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            //得到文字的宽高
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());
            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                //得到一个覆盖在上层的水印文字
                under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                //设置水印文字颜色
                under.setColorFill(BaseColor.LIGHT_GRAY);
                //设置水印文字和大小
//                under.setFontAndSize(base, 12);
                under.setFontAndSize(base, Integer.parseInt(fontSize));
                //这个position主要是为了在换行加水印时能往右偏移起始坐标
                int position = 0;
                int interval = -3;
                for (int height = interval + textH; height < pageRect.getHeight(); height = height + textH * (Integer.parseInt(fontSize)/4)) {
                    for (int width = interval + textW -position * 150; width < pageRect.getWidth() + textW; width = width + textW * 3) {
                        //添加水印文字，水印文字成25度角倾斜
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW , height - textH, 25);
                    }
//                    if(textW < 200){
//                        position+=15;
//                    }else{
//                        position+=textW;
//                    }
                    position++;
                }
                // 添加水印文字
                under.endText();
            }
            //关闭流
            stamper.close();
            reader.close();
//            logger.debug("==========addTxtWaterMaker==========end");
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("文件{}增加水印异常:{}",inputFile,e.getMessage());
            return inputFile;
        }
        return outputFile;
    }
}
