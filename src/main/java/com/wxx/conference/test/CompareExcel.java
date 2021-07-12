package com.wxx.conference.test;

import com.wxx.conference.util.ExcelUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by thinkpad on 2018-3-1.
 */
public class CompareExcel {

    public static String hrPath = "E:\\测试文件\\泛海控股系统公司基本信息情况表档案.xls";
    public static String ddPath = "E:\\测试文件\\关联法人信息表.xlsx";
    public static String path = "E:\\测试文件\\result.xls";

    public static ExcelUtil excelUtil = new ExcelUtil();

    public static void compare(){

        File hr = new File(hrPath);
        File dd = new File(ddPath);

        ArrayList hrList = excelUtil.readExcel2003(hr);
        ArrayList ddList = excelUtil.readExcel(dd);
        ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

        for(int i=3;i<hrList.size();i++){
            ArrayList<Object> hrColList = (ArrayList<Object>)hrList.get(i);
            String str = String.valueOf(hrColList.get(3));
            str = str.replaceAll(" ","");
            str = str.replaceAll(",","");
            str = str.replaceAll("，","");
//            str = str.replaceAll("\\n","");
            str = str.replaceAll("\t","");
            str = str.replaceAll("司","司,");
            System.out.println(str);
            if(str.indexOf("\n")>0){
                String []tmp = str.split("\n");
                int len = tmp.length;
                StringBuffer sb1 = new StringBuffer();
                StringBuffer sb2 = new StringBuffer();
                for(int j=0;j<len;j++){
                    if(tmp[j].indexOf(",")>0){
                        String []str1 = tmp[j].split(",");
                        if(str1.length > 1){
                            sb1.append(str1[0]+"\n");
                            sb2.append(str1[1]+"\n");
                        }
                    }else{
                        sb2.append(tmp[j]+"\n");
                    }
                }
                hrColList.set(3,sb1);
                hrColList.set(4,sb2);
            }else{
                if(str.indexOf(",")>0){
                    String []tmp = str.split(",");
                    if(tmp.length > 1){
                        hrColList.set(3,tmp[0]);
                        hrColList.set(4, tmp[1]);
                    }
                }
            }

            result.add(hrColList);
        }
        ExcelUtil.writeExcel(result,path);
    }
    public static void main(String []args){
        compare();
    }

}
