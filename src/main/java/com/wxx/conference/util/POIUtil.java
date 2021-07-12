package com.wxx.conference.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Iterator;

/**
 * Created by thinkpad on 2020-10-21.
 */
public class POIUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(POIUtil.class);
    public static void main(String[] args) {
//        String content = readDocx("E:\\QRCode\\hrfiles\\00022486\\1.docx");
//        System.out.println(content);
    }
    public static String readDoc(String filePath,String fileName){
        FileInputStream ins;
        String content = "";
        try{
            ins  = new FileInputStream(filePath);
            WordExtractor extractor = new WordExtractor(ins);
            content = extractor.getText();
            logger.info("文件《" + fileName + "》，文字内容抽取成功");
        }catch (Exception e){
            logger.error("文件《"+fileName+"》，文字内容抽取失败，失败原因为："+e.toString());
            e.printStackTrace();
        }
        return content;
    }
    public static String readDocx(String filePath,String fileName){
        FileInputStream ins;
        String content = "";
        try{
            ins  = new FileInputStream(filePath);
            XWPFDocument doc = new XWPFDocument(ins);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            content = replaceBlank(extractor.getText());
            logger.info("文件《" + fileName + "》，文字内容抽取成功");
        }catch (Exception e){
            logger.error("文件《"+fileName+"》，文字内容抽取失败，失败原因为："+e.toString());
            e.printStackTrace();
        }
        return content;
    }
    public static String readXls(String path,String fileName)
    {
        String text="";
        try
        {
            FileInputStream is =  new FileInputStream(path);
            HSSFWorkbook excel=new HSSFWorkbook(is);
            //获取第一个sheet
            HSSFSheet sheet0=excel.getSheetAt(0);
            for (Iterator rowIterator=sheet0.iterator();rowIterator.hasNext();)
            {
                HSSFRow row=(HSSFRow) rowIterator.next();
                for (Iterator iterator=row.cellIterator();iterator.hasNext();)
                {
                    HSSFCell cell=(HSSFCell) iterator.next();
                    //根据单元的的类型 读取相应的结果
                    if(cell.getCellType()== CellType.STRING) text+=cell.getStringCellValue()+"\t";
                    else if(cell.getCellType()==CellType.NUMERIC) text+=cell.getNumericCellValue()+"\t";
                    else if(cell.getCellType()==CellType.FORMULA) text+=cell.getCellFormula()+"\t";
                }
                text+="\n";
            }
            logger.info("文件《" + fileName + "》，文字内容抽取成功");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            logger.error("文件《"+fileName+"》，文字内容抽取失败，失败原因为："+e.toString());
            e.printStackTrace();
        }

        return text;
    }
    public static String readXlsx(String path,String fileName) {
        String text = "";
        try {
            OPCPackage pkg = OPCPackage.open(path);
            XSSFWorkbook excel = new XSSFWorkbook(pkg);
            //获取第一个sheet
            XSSFSheet sheet0 = excel.getSheetAt(0);
            for (Iterator rowIterator = sheet0.iterator(); rowIterator.hasNext(); ) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                for (Iterator iterator = row.cellIterator(); iterator.hasNext(); ) {
                    XSSFCell cell = (XSSFCell) iterator.next();
                    //根据单元的的类型 读取相应的结果
                    if (cell.getCellType() == CellType.STRING) text += cell.getStringCellValue() + "\t";
                    else if (cell.getCellType() == CellType.NUMERIC)
                        text += cell.getNumericCellValue() + "\t";
                    else if (cell.getCellType() == CellType.FORMULA) text += cell.getCellFormula() + "\t";
                }
                text += "\n";
            }
            logger.info("文件《" + fileName + "》，文字内容抽取成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("文件《"+fileName+"》，文字内容抽取失败，失败原因为："+e.toString());
            e.printStackTrace();
        }

        return text;
    }
    public static String replaceBlank(String content){
        content = content.replaceAll("\\n","");
        content = content.replaceAll("\\r\\n","");
        return content;
    }
}
