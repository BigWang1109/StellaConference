package com.wxx.conference.service.portal;

import com.wxx.conference.model.portal.OrgMember;
import com.wxx.conference.model.portal.OrgUnit;
import com.wxx.conference.util.HibernateUtil;
import com.wxx.conference.util.HttpClientUtil;
import com.wxx.conference.util.PropertiesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by thinkpad on 2019-11-6.
 */
@Service
public class OAService {

    public static void main(String[] args) {
        String UnitJSON = getUnitFromOA();
        ArrayList<OrgUnit> unitList = FormUnit(UnitJSON);
        ArrayList<OrgUnit> departmentList = new ArrayList<OrgUnit>();
        ArrayList<OrgMember> memberList = new ArrayList<OrgMember>();
        SaveUnitToDB(unitList);

        String MemberJOSN = "";
        String DepartmentJSON = "";
        //循环公司获取部门
        for(int i=0;i<unitList.size();i++){
            OrgUnit orgUnit = unitList.get(i);
//            MemberJOSN = getMemberFromOA(orgUnit.getORG_ACCOUNT_ID());
            DepartmentJSON = getDepartmentFromOA(orgUnit.getORG_ACCOUNT_ID());
//            memberList = FormMember(MemberJOSN);
            departmentList = FormDepartment(DepartmentJSON);
            //循环部门获取人员
            for(int j=0;j<departmentList.size();j++){
                OrgUnit department = departmentList.get(j);
                MemberJOSN = getMemberFromOABydepartmentID(department.getID());
                memberList = FormMember(MemberJOSN, department.getID());
                SaveMemberToDB(memberList);
            }
//            SaveMemberToDB(memberList);
//            SaveUnitToDB(departmentList);
        }


    }
    //从OA获取公司信息，返回json格式
    public static String getUnitFromOA(){
        String result = "";
        String oaIP = PropertiesUtil.getProperty("OAIP");
        String restUserName = PropertiesUtil.getProperty("RestUserName");
        String restUserPass = PropertiesUtil.getProperty("RestUserPass");
        String tokenAddress = PropertiesUtil.getProperty("tokenAddress");
        String orgAccountURL = PropertiesUtil.getProperty("OrgAccountURL");
        HttpClientUtil HCUtil = new HttpClientUtil();
        result = HCUtil.requireJsonData(oaIP+tokenAddress,restUserName,restUserPass,oaIP+orgAccountURL);
        return result;
    }
    //根据公司id获取部门
    public static String getDepartmentFromOA(String accountID){
        String result = "";
        String oaIP = PropertiesUtil.getProperty("OAIP");
        String restUserName = PropertiesUtil.getProperty("RestUserName");
        String restUserPass = PropertiesUtil.getProperty("RestUserPass");
        String tokenAddress = PropertiesUtil.getProperty("tokenAddress");
        String orgDepartmentsURL = PropertiesUtil.getProperty("OrgDepartmentsURL");
        orgDepartmentsURL = orgDepartmentsURL.replaceAll("\\{accountId\\}", accountID);
        HttpClientUtil HCUtil = new HttpClientUtil();
        result = HCUtil.requireJsonData(oaIP+tokenAddress,restUserName,restUserPass,oaIP+orgDepartmentsURL);
//        System.out.println(result);
        return result;
    }
    //根据orgAccountId获取人员信息，返回JSON格式
    public static String getMemberFromOA(String accountID){
        String result = "";
        String oaIP = PropertiesUtil.getProperty("OAIP");
        String restUserName = PropertiesUtil.getProperty("RestUserName");
        String restUserPass = PropertiesUtil.getProperty("RestUserPass");
        String tokenAddress = PropertiesUtil.getProperty("tokenAddress");
        String OrgMembersURL = PropertiesUtil.getProperty("OrgMembersURL");
        OrgMembersURL = OrgMembersURL.replaceAll("\\{accountId\\}", accountID);
        HttpClientUtil HCUtil = new HttpClientUtil();
        result = HCUtil.requireJsonData(oaIP+tokenAddress,restUserName,restUserPass,oaIP+OrgMembersURL);
        return result;
    }
    //根据DepartmentId获取人员信息，返回JSON格式
    public static String getMemberFromOABydepartmentID(String departmentId){
        String result = "";
        String oaIP = PropertiesUtil.getProperty("OAIP");
        String restUserName = PropertiesUtil.getProperty("RestUserName");
        String restUserPass = PropertiesUtil.getProperty("RestUserPass");
        String tokenAddress = PropertiesUtil.getProperty("tokenAddress");
        String OrgMembersByDepartmentURL = PropertiesUtil.getProperty("OrgMembersByDepartmentURL");
        OrgMembersByDepartmentURL = OrgMembersByDepartmentURL.replaceAll("\\{departmentId\\}", departmentId);
        HttpClientUtil HCUtil = new HttpClientUtil();
        result = HCUtil.requireJsonData(oaIP+tokenAddress,restUserName,restUserPass,oaIP+OrgMembersByDepartmentURL);
        return result;
    }
    //整理从OA获取的JSON格式的人员信息，返回结果为人员实体类OrgMember的List
    public static ArrayList FormMember(String MemberJOSN,String ORG_DEPARTMENT_ID){
        JSONArray jsonArray = JSONArray.fromObject(MemberJOSN);
        System.out.println("获取到的人员数为:"+jsonArray.size());
        ArrayList <OrgMember>memberList = new ArrayList<OrgMember>();
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jb = jsonArray.getJSONObject(i);
            String ORG_ACCOUNT_ID = String.valueOf(jb.get("orgAccountId"));
            String ID = String.valueOf(jb.get("id"));
            String NAME = String.valueOf(jb.get("name"));
            String CODE = String.valueOf(jb.get("loginName"));
            OrgMember orgMember = new OrgMember();
            orgMember.setORG_ACCOUNT_ID(ORG_ACCOUNT_ID);
            orgMember.setID(ID);
            orgMember.setNAME(NAME);
            orgMember.setCODE(CODE);
            orgMember.setORG_DEPARTMENT_ID(ORG_DEPARTMENT_ID);
            orgMember.setIS_ADMIN(0);
            memberList.add(orgMember);
        }
        return memberList;
    }
    //整理从OA获取的JSON格式的部门信息，返回结果为公司实体类orgUnit的List
    public static ArrayList FormDepartment(String DepertmentJSON){
        JSONArray jsonArray = JSONArray.fromObject(DepertmentJSON);
        System.out.println("获取到的部门数为:"+jsonArray.size());
        ArrayList unitList = new ArrayList();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jb = jsonArray.getJSONObject(i);
            String ID = String.valueOf(jb.get("id"));
            String NAME = String.valueOf(jb.get("name"));
            String CODE = String.valueOf(jb.get("code"));
            String TYPE = String.valueOf(jb.get("type"));
            String IS_GROUP = String.valueOf(jb.get("isGroup"));
            String PATH = String.valueOf(jb.get("path"));
            String IS_INTERNAL = String.valueOf(jb.get("isInternal"));
            Integer SORT_ID = Integer.parseInt(String.valueOf(jb.get("sortId")));
            String IS_ENABLE = String.valueOf(jb.get("enabled"));
            String IS_DELETED = String.valueOf(jb.get("isDeleted"));
            Integer STATUS = Integer.parseInt(String.valueOf(jb.get("status")));
            String LEVEL_SCOPE = String.valueOf(jb.get("levelScope"));
            String ORG_ACCOUNT_ID = String.valueOf(jb.get("orgAccountId"));
            OrgUnit orgUnit = new OrgUnit();
            orgUnit.setID(ID);
            orgUnit.setNAME(NAME);
            orgUnit.setCODE(CODE);
            orgUnit.setTYPE(TYPE);
            orgUnit.setIS_GROUP(IS_GROUP.equals("true") ? 1 : 0);
            orgUnit.setPATH(PATH);
            orgUnit.setIS_INTERNAL(IS_INTERNAL.equals("true") ? 1 : 0);
            orgUnit.setSORT_ID(SORT_ID);
            orgUnit.setIS_ENABLE(IS_ENABLE.equals("true") ? 1 : 0);
            orgUnit.setIS_DELETED(IS_DELETED.equals("true") ? 1 : 0);
            orgUnit.setSTATUS(STATUS);
            orgUnit.setLEVEL_SCOPE(LEVEL_SCOPE);
            orgUnit.setORG_ACCOUNT_ID(ORG_ACCOUNT_ID);
            unitList.add(orgUnit);
        }
        return unitList;
    }
    //整理从OA获取的JSON格式的公司信息，返回结果为公司实体类orgUnit的List
    public static ArrayList FormUnit(String UnitJSON){
        JSONArray jsonArray = JSONArray.fromObject(UnitJSON);
        System.out.println("获取到的公司数为:"+jsonArray.size());
        ArrayList unitList = new ArrayList();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jb = jsonArray.getJSONObject(i);
            String ID = String.valueOf(jb.get("id"));
            String NAME = String.valueOf(jb.get("name"));
            String CODE = String.valueOf(jb.get("code"));
            String TYPE = String.valueOf(jb.get("type"));
            String IS_GROUP = String.valueOf(jb.get("isGroup"));
            String PATH = String.valueOf(jb.get("path"));
            String IS_INTERNAL = String.valueOf(jb.get("isInternal"));
            Integer SORT_ID = Integer.parseInt(String.valueOf(jb.get("sortId")));
            String IS_ENABLE = String.valueOf(jb.get("enabled"));
            String IS_DELETED = String.valueOf(jb.get("isDeleted"));
            Integer STATUS = Integer.parseInt(String.valueOf(jb.get("status")));
            String LEVEL_SCOPE = String.valueOf(jb.get("levelScope"));
            String ORG_ACCOUNT_ID = String.valueOf(jb.get("orgAccountId"));
            OrgUnit orgUnit = new OrgUnit();
            orgUnit.setID(ID);
            orgUnit.setNAME(NAME);
            orgUnit.setCODE(CODE);
            orgUnit.setTYPE(TYPE);
            orgUnit.setIS_GROUP(IS_GROUP.equals("true") ? 1 : 0);
            orgUnit.setPATH(PATH);
            orgUnit.setIS_INTERNAL(IS_INTERNAL.equals("true") ? 1 : 0);
            orgUnit.setSORT_ID(SORT_ID);
            orgUnit.setIS_ENABLE(IS_ENABLE.equals("true") ? 1 : 0);
            orgUnit.setIS_DELETED(IS_DELETED.equals("true") ? 1 : 0);
            orgUnit.setSTATUS(STATUS);
            orgUnit.setLEVEL_SCOPE(LEVEL_SCOPE);
            orgUnit.setORG_ACCOUNT_ID(ORG_ACCOUNT_ID);
            unitList.add(orgUnit);
        }
        return unitList;
    }
    //将公司信息存入数据库
    public static Boolean SaveUnitToDB(ArrayList<OrgUnit> unitList){
        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            for(int i=0;i<unitList.size();i++){
                OrgUnit orgUnit = unitList.get(i);
                session.saveOrUpdate(orgUnit);
            }
            ts.commit();
        }catch (Exception e){
            if(ts != null){
                ts.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }
    //将人员信息存入数据库
    public static Boolean SaveMemberToDB(ArrayList<OrgMember> memberList){
        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            for(int i=0;i<memberList.size();i++){
                OrgMember orgMember = memberList.get(i);
                session.merge(orgMember);
            }
            ts.commit();
        }catch (Exception e){
            if(ts != null){
                ts.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }
}
