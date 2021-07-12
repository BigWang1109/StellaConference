package com.wxx.conference.util;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2021-6-3.
 */
public class YTCXZtreeUtil {

    public static void main(String[] args) {
        toJSON();
    }

    public static String toJSON(){

        String treeNode = "[";
        List orgList = getOrgList();
        for(int i=0;i<orgList.size();i++){
            Map map = (HashMap)orgList.get(i);
            treeNode += "{id:'"+map.get("id")+"',pId:'"+map.get("pid")+"',name:'"+map.get("name")+"'},";
        }
        treeNode = treeNode.substring(0,treeNode.length()-1);
        treeNode += "]";
        System.out.println(treeNode);
        return treeNode;
    }

    public static String toJSONBycode(String psncode){

        String treeNode = "[";
        List orgList = getOrgListByCode(psncode);
        for(int i=0;i<orgList.size();i++){
            Map map = (HashMap)orgList.get(i);
            treeNode += "{id:'"+map.get("id")+"',pId:'"+map.get("pid")+"',name:'"+map.get("name")+"',checked:"+map.get("checked")+"},";
        }
        treeNode = treeNode.substring(0,treeNode.length()-1);
        treeNode += "]";
        System.out.println(treeNode);
        return treeNode;
    }

    public static List getOrgList(){
        Session session = null;
        List list = new ArrayList();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select pk_org,code,name,pk_fatherorg from oabi.zgfh_api_org order by code";
            SQLQuery query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object []obj = (Object[])array.get(i);
                Map mapt = new HashMap();
//                if(obj[0].toString().equals("~")){
//                    mapt.put("id","0");
//                }else{
//                    mapt.put("id",obj[0]);
//                }
                mapt.put("id",obj[0]);
                mapt.put("code",obj[1]);
                mapt.put("name",obj[2]);
                mapt.put("pid",obj[3]);

                list.add(mapt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return list;
    }

    public static List getOrgListByCode(String psncode){
        Session session = null;
        List list = new ArrayList();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select t1.pk_org,code,name,pk_fatherorg,psncode from oabi.zgfh_api_org t1 left join (select psncode,corpcode,pk_org from oabi.zgfh_api_user_role_b where psncode=:psncode) t2 on t1.pk_org = t2.pk_org order by t1.code";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("psncode",psncode);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object []obj = (Object[])array.get(i);
                Map mapt = new HashMap();
//                if(obj[0].toString().equals("~")){
//                    mapt.put("id","0");
//                }else{
//                    mapt.put("id",obj[0]);
//                }
                mapt.put("id",obj[0]);
                mapt.put("code",obj[1]);
                mapt.put("name",obj[2]);
                mapt.put("pid",obj[3]);
                if(obj[4]!=null){
                    mapt.put("checked",true);
                }else{
                    mapt.put("checked",false);
                }
                list.add(mapt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return list;
    }

}
