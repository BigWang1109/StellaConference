package com.wxx.conference.test;

import com.wxx.conference.util.ExcelUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by thinkpad on 2017-11-28.
 */
public class GenerateExcel {

    public static  ExcelUtil excelUtil = new ExcelUtil();

//        public static String path = "E:\\工作\\钉钉\\离职人员\\201802离职人员信息.xls";
        public static String path = "E:\\工作\\钉钉\\离职人员\\201802解聘人员信息.xls";

    public static ArrayList rowList;

//    public  GenerateExcel(){
//        File file = new File(path);
//        rowList = excelUtil.readExcel2003(file);
//    }

    public static void init(){
        File file = new File(path);
        rowList = excelUtil.readExcel2003(file);
    }

    public static ArrayList getCompanyName(){

        ArrayList companyList = new ArrayList();
        ArrayList tempList = new ArrayList();
        for(int i=1;i<rowList.size();i++){
            ArrayList<Object> colList = (ArrayList<Object>)rowList.get(i);
            String companyName = String.valueOf(colList.get(3));
            tempList.add(companyName);
        }
        Set set = new HashSet();
        for(Object obj:tempList){
            if(set.add(obj)){
                companyList.add(obj);
            }
        }
        return companyList;
    }

    public static void generate(ArrayList companyList){

        for(int i=0;i<companyList.size();i++){
            String companyName = String.valueOf(companyList.get(i));
            ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
            result.add((ArrayList<Object>)rowList.get(0));
            String path = "E:\\工作\\钉钉\\离职人员\\201802解聘\\"+companyName+"(解聘).xls";
//            String path = "E:\\工作\\钉钉\\离职人员\\201802离职\\"+companyName+"(离职).xls";
            for(int j=1;j<rowList.size();j++){
                ArrayList colList = (ArrayList<Object>)rowList.get(j);
                String colName = String.valueOf(colList.get(3));
                if(companyName.equals(colName)){
                    result.add(colList);
//                    rowList.remove(1);
                }else{
                    if(result.size()>1){
                        ExcelUtil.writeExcel(result,path);
                        break;
                    }else{
                        continue;
                    }
                }
            }
        }

    }
    public static void main(String []args){

        init();
        ArrayList companyList = getCompanyName();
        generate(companyList);

    }
}
