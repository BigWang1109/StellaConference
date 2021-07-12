package com.wxx.conference.util;

import com.wxx.conference.service.portal.PortalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by thinkpad on 2019-11-12.
 */

public class ZtreeUtil {
    private static int count;

    private static List orgUnitList;

    public static String toJSON(List UnitList,int maxLength){
        count = 0;
        orgUnitList = UnitList;
        String treeNode = getZtree(0,"0000","");
        String str = "\\}\\{";
        String tar = "\\}\\,\\{";
        treeNode = treeNode.replaceAll(str,tar);
        treeNode = treeNode.substring(0, treeNode.length() - 2 * count);

//        SaveZTreeJson(treeNode);
        System.out.println(treeNode);
        return treeNode;
    }
    public static String toJSONPlusChecked(List UnitList,int maxLength){
        count = 0;
        orgUnitList = UnitList;
        String treeNode = getZtreePlusChecked(0, "0000", "");
        String str = "\\}\\{";
        String tar = "\\}\\,\\{";
        treeNode = treeNode.replaceAll(str,tar);
        treeNode = treeNode.substring(0, treeNode.length() - 2 * count);

//        SaveZTreeJson(treeNode);
        System.out.println(treeNode);
        return treeNode;
    }

//    public static void main(String[] args) {
//        SaveZTreeJson("hello world");
//    }

    public static void SaveZTreeJson(String treeNode){
        String zTreeJsonURL = PropertiesUtil.getProperty("zTreeJsonURL");
        try{
            File file = new File(zTreeJsonURL);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream (file);
//            byte[] bytes = new byte[treeNode.length()];
            byte[] bytes = treeNode.getBytes();
//            int b = bytes.length;
            fos.write(bytes);
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getZtree(int index,String root,String res){
        for(int i=index;i<orgUnitList.size();i++){
            Object[] obj = (Object[]) orgUnitList.get(i);
            if(!res.contains(obj[0].toString())){
                if(hasChildren(obj[2].toString(),i)){
                    res += "{id:\""+obj[0].toString()+"\",name:\""+obj[1].toString()+"\",type:\""+obj[3].toString()+"\",children:[";
                    res = getZtree(i+1,obj[2].toString(),res);
                    res += "]}";
                }else if(hasBrothers(obj[2].toString(),i)){
                    res += "{id:\""+obj[0].toString()+"\",name:\""+obj[1].toString()+"\",type:\""+obj[3].toString()+"\"},";
                }else{
                    res += "{id:\""+obj[0].toString()+"\",name:\""+obj[1].toString()+"\",type:\""+obj[3].toString()+"\"}";
                    for(int j=0;j<getLevel(obj[2].toString(),i);j++){
                        count++;
                        res += "]}";
                    }
                    return res;
                }
            }
        }
        return res;
    }
    public static String getZtreePlusChecked(int index,String root,String res){
        for(int i=index;i<orgUnitList.size();i++){
            Object[] obj = (Object[]) orgUnitList.get(i);
            if(!res.contains(obj[0].toString())){
                if(hasChildren(obj[2].toString(),i)){
                    res += "{id:\""+obj[0].toString()+"\",name:\""+obj[1].toString()+"\",type:\""+obj[3].toString()+"\",checked:"+(Integer.parseInt(obj[4].toString()) == 1)+",children:[";
                    res = getZtreePlusChecked(i + 1, obj[2].toString(), res);
                    res += "]}";
                }else if(hasBrothers(obj[2].toString(),i)){
                    res += "{id:\""+obj[0].toString()+"\",name:\""+obj[1].toString()+"\",type:\""+obj[3].toString()+"\",checked:"+(Integer.parseInt(obj[4].toString()) == 1)+"},";
                }else{
                    res += "{id:\""+obj[0].toString()+"\",name:\""+obj[1].toString()+"\",type:\""+obj[3].toString()+"\",checked:"+(Integer.parseInt(obj[4].toString()) == 1)+"}";
                    for(int j=0;j<getLevel(obj[2].toString(),i);j++){
                        count++;
                        res += "]}";
                    }
                    return res;
                }
            }
        }
        return res;
    }
    public static int getLevel(String root,int index){
        if(index == orgUnitList.size()-1){
            return 0;
        }else{
            Object []obj = (Object[])orgUnitList.get(index+1);
            int len1 = root.length();
            int nextLen = obj[2].toString().length();
            int res = (len1 - nextLen)/4-1;
            res = res < 0? 0:res;
            return res;
        }
    }
    public static Boolean hasBrothers(String root,int index){
        if(index == orgUnitList.size()-1){
            return false;
        }else {
            Object []obj = (Object[])orgUnitList.get(index+1);
            if(obj[2].toString().length() == root.length()){
                return true;
            }
            else{
                return false;
            }
        }
    }
    public static Boolean hasChildren(String root,int index){
        if(index == orgUnitList.size()-1){
            return false;
        }else {
            Object []obj = (Object[])orgUnitList.get(index+1);
            if(obj[2].toString().startsWith(root)){
                return true;
            }
            else{
                return false;
            }
        }
    }


}
