package com.wxx.conference.service.HR;


import com.wxx.conference.common.ImageConstants;
import com.wxx.conference.model.HR.*;
import com.wxx.conference.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by thinkpad on 2020-8-14.
 */
@Service
public class CVService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CVService.class);
    public static final String separator = File.separator;
    /**
     * 根据员工姓名、拼音首字母、全拼搜索匹配到的员工
     * */
    public ArrayList CVSearchByName(String searchVal) {
        Session session = null;
        ArrayList<zgfh_bi_psndoc> list = new ArrayList<zgfh_bi_psndoc>();
        try {
//            ArrayList<String> codeList = new ArrayList<String>();
            HashSet codeSet = new HashSet();
//            StringBuffer sb = new StringBuffer();
            session = HibernateHRDBUtil.getSession();
//            String sql = "select CORPNAME,PSNNAME,PSNCODE from zgfh_bi_psndoc2020 where PSNNAME like:PSNNAME or PY like LOWER(:PSNNAME) or CORPNAME like:PSNNAME order by CORPCODE,PSNCODE";
            String sql = "select CORPNAME,PSNNAME,PSNCODE from oabi.zgfh_bi_psndoc2020 where PSNCODE in(:codes) order by CORPCODE,PSNCODE";
            String search_psndoc_sql = "select PSNCODE from oabi.zgfh_bi_psndoc2020 where CORPNAME like:PSNNAME or GW like:PSNNAME or " +
                    "ZW like:PSNNAME or SY like:PSNNAME or HY like:PSNNAME or MZ like:PSNNAME or SEX like:PSNNAME or XL like:PSNNAME or SCHOOL like:PSNNAME" +
                    " or BIRTHDAY like:PSNNAME or PSNCODE like:PSNNAME or PSNNAME like:PSNNAME or RSDATE like:PSNNAME or JG like:PSNNAME or HK like:PSNNAME or " +
                    "RDDATE like:PSNNAME or ZZMM like:PSNNAME or WORKDATE like:PSNNAME or ZY like:PSNNAME or PY like LOWER(:PSNNAME) or QPY like LOWER(:PSNNAME) order by CORPCODE,PSNCODE";
            String search_work_sql = "select PSNCODE from oabi.zgfh_bi_psndoc_work where BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or WORKCORP like:PSNNAME or WORKPOST like:PSNNAME";
            String search_edu_sql = "select PSNCODE from oabi.zgfh_bi_edu where BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or SCHOOL like:PSNNAME or MAJOR like:PSNNAME or EDUCATION like:PSNNAME or DEGREE like:PSNNAME";
            String search_kpi_sql = "select PSNCODE from oabi.zgfh_bi_kpi where KPI_YEAR like:PSNNAME or DEGREE like:PSNNAME or REWARD like:PSNNAME";
            String search_cont_sql = "select PSNCODE from oabi.zgfh_bi_cont where VCONTRACTNUM like:PSNNAME or TERMNAME like:PSNNAME or BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME";
            String search_train_sql = "select PSNCODE from oabi.zgfh_bi_train where BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or VTRA_ACT like:PSNNAME or TRM_CLASS_NAMES like:PSNNAME";
            String search_title_sql = "select PSNCODE from oabi.zgfh_bi_title where P_NAME like:PSNNAME or DOCNAME like:PSNNAME or P_ORG like:PSNNAME or P_DATE like:PSNNAME";
            String []sqls = {search_psndoc_sql,search_work_sql,search_edu_sql,search_kpi_sql,search_cont_sql,search_train_sql,search_title_sql};
            searchVal = searchVal.replaceAll(" ","");
            for(String s : sqls){
                generateCodeList(s,searchVal,codeSet);
            }
            Query query = session.createSQLQuery(sql);
//            String codeStr = sb.substring(0,sb.length()-1);
            query.setParameterList("codes", codeSet);
            List array = query.list();
            for (int i = 0; i < array.size(); i++) {
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setPSNNAME(checkIsNull(obj[1]));
                psndoc.setPSNCODE(checkIsNull(obj[2]));
                psndoc.setSearchVal(searchVal);
                list.add(psndoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 获取符合查询条件的psncode
     * */
    public HashSet getPsncodeList(String searchVal){
        HashSet<String> codeSet = new HashSet<String>();
        try{
            String []vals = searchVal.split(" ");
            for(String val : vals){
                if(val!=null && !"".equals(val)){
                    HashSet<String> tmpSet = new HashSet<String>();
                    String search_psndoc_sql = "select PSNCODE from oabi.zgfh_bi_psndoc2020 where CORPNAME like:PSNNAME or GW like:PSNNAME or " +
                            "ZW like:PSNNAME or SY like:PSNNAME or HY like:PSNNAME or MZ like:PSNNAME or SEX like:PSNNAME or XL like:PSNNAME or SCHOOL like:PSNNAME" +
                            " or BIRTHDAY like:PSNNAME or PSNCODE like:PSNNAME or PSNNAME like:PSNNAME or RSDATE like:PSNNAME or JG like:PSNNAME or HK like:PSNNAME or " +
                            "RDDATE like:PSNNAME or ZZMM like:PSNNAME or WORKDATE like:PSNNAME or ZY like:PSNNAME or PY like LOWER(:PSNNAME) or QPY like LOWER(:PSNNAME) or DEPTNAME like:PSNNAME or psnclassname like:PSNNAME ";
                    String search_work_sql = "select PSNCODE from oabi.zgfh_bi_psndoc_work where BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or WORKCORP like:PSNNAME or WORKPOST like:PSNNAME";
                    String search_edu_sql = "select PSNCODE from oabi.zgfh_bi_edu where BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or SCHOOL like:PSNNAME or MAJOR like:PSNNAME or EDUCATION like:PSNNAME or DEGREE like:PSNNAME";
                    String search_kpi_sql = "select PSNCODE from oabi.zgfh_bi_kpi where KPI_YEAR like:PSNNAME or DEGREE like:PSNNAME or REWARD like:PSNNAME";
                    String search_cont_sql = "select PSNCODE from oabi.zgfh_bi_cont where VCONTRACTNUM like:PSNNAME or TERMNAME like:PSNNAME or BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME";
                    String search_train_sql = "select PSNCODE from oabi.zgfh_bi_train where BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or VTRA_ACT like:PSNNAME or TRM_CLASS_NAMES like:PSNNAME";
                    String search_title_sql = "select PSNCODE from oabi.zgfh_bi_title where P_NAME like:PSNNAME or DOCNAME like:PSNNAME or P_ORG like:PSNNAME or P_DATE like:PSNNAME";
                    String search_ncfile_sql = "select PSNCODE from oabi.zgfh_bi_ncfile where CONTENT like:PSNNAME";
                    String []sqls = {search_psndoc_sql,search_work_sql,search_edu_sql,search_kpi_sql,search_cont_sql,search_train_sql,search_title_sql,search_ncfile_sql};
//                    String []sqls = {search_psndoc_sql};
                    for(String s : sqls){
                        generateCodeList(s,val,tmpSet);
                    }
                    if(codeSet.isEmpty()){
                        codeSet.addAll(tmpSet);
                    }else{
                        codeSet.retainAll(tmpSet);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return codeSet;
    }
    /**
     * 根据匹配到的psncodeList查询用户列表
     * */
    public ArrayList searchByPsncode(HashSet codeSet){
        Session session = null;
        ArrayList<zgfh_bi_psndoc> list = new ArrayList<zgfh_bi_psndoc>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select CORPNAME,PSNNAME,PSNCODE from oabi.zgfh_bi_psndoc2020 where PSNCODE in(:codes) order by CORPCODE,PSNCODE";
            Query query = session.createSQLQuery(sql);
            if(codeSet.size() > 100){
                List<String> codeList = new ArrayList<String>(codeSet);
                codeList = codeList.subList(0,100);
//                Collections.sort(codeList);
//                StringBuffer sb = new StringBuffer();
//                for(int j=0;j<codeList.size();j++){
//                    sb.append("'");
//                    sb.append(codeList.get(j));
//                    sb.append("',");
//                }
//                String str = sb.substring(0,sb.length()-1);
//                System.out.println(str);
                query.setParameterList("codes", codeList);
            }else if(codeSet.size() > 0 && codeSet.size() <= 100){
                query.setParameterList("codes", codeSet);
            }else if(codeSet.size() == 0){
                codeSet.add("0000000000");
                query.setParameterList("codes", codeSet);
            }
            List array = query.list();
            for (int i = 0; i < array.size(); i++) {
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setPSNNAME(checkIsNull(obj[1]));
                psndoc.setPSNCODE(checkIsNull(obj[2]));
                list.add(psndoc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return list;
    }
    /**
     * 根据匹配到的psncodeList查询用户列表
     * */
    public ArrayList searchByPsncodeByPage(HashSet codeSet,int index){
        Session session = null;
        ArrayList<zgfh_bi_psndoc> list = new ArrayList<zgfh_bi_psndoc>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select CORPNAME,PSNNAME,PSNCODE from oabi.zgfh_bi_psndoc2020 where PSNCODE in(:codes) order by CORPCODE,PSNCODE";
            Query query = session.createSQLQuery(sql);
//            if(codeSet.size() > 100){
//                List<String> codeList = new ArrayList<String>(codeSet);
//                Collections.sort(codeList);
//                codeList = codeList.subList(index,20);
////                Collections.sort(codeList);
////                StringBuffer sb = new StringBuffer();
////                for(int j=0;j<codeList.size();j++){
////                    sb.append("'");
////                    sb.append(codeList.get(j));
////                    sb.append("',");
////                }
////                String str = sb.substring(0,sb.length()-1);
////                System.out.println(str);
//                query.setParameterList("codes", codeList);
//            }else if(codeSet.size() > 0 && codeSet.size() <= 100){
//                query.setParameterList("codes", codeSet);
//            }else if(codeSet.size() == 0){
//                codeSet.add("0000000000");
//                query.setParameterList("codes", codeSet);
//            }
            List<String> codeList = new ArrayList<String>(codeSet);
//            Collections.sort(codeList);
            if(codeList.size() > (index+1)*100){
                codeList = codeList.subList(index*100,(index+1)*100);
            }else if(codeList.size() > index*100 && codeList.size() <= (index+1)*100){
                codeList = codeList.subList(index*100,codeList.size());
            }else if(codeSet.size() == 0){
                codeList.add("0000000000");
            }
            query.setParameterList("codes", codeList);
            List array = query.list();
            for (int i = 0; i < array.size(); i++) {
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setPSNNAME(checkIsNull(obj[1]));
                psndoc.setPSNCODE(checkIsNull(obj[2]));
                list.add(psndoc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return list;
    }

    /**
     * 根据匹配到的psncodeList查询用户列表
     * */
    public ArrayList searchByPsncodeByPageFHKG(HashSet codeSet,int index){
        Session session = null;
        ArrayList<zgfh_bi_psndoc> list = new ArrayList<zgfh_bi_psndoc>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select CORPNAME,PSNNAME,PSNCODE from oabi.zgfh_bi_psndoc2020 where corp_system != 'zgfh' and PSNCODE in(:codes) order by CORPCODE,PSNCODE";
            Query query = session.createSQLQuery(sql);
//            if(codeSet.size() > 100){
//                List<String> codeList = new ArrayList<String>(codeSet);
//                Collections.sort(codeList);
//                codeList = codeList.subList(index,20);
////                Collections.sort(codeList);
////                StringBuffer sb = new StringBuffer();
////                for(int j=0;j<codeList.size();j++){
////                    sb.append("'");
////                    sb.append(codeList.get(j));
////                    sb.append("',");
////                }
////                String str = sb.substring(0,sb.length()-1);
////                System.out.println(str);
//                query.setParameterList("codes", codeList);
//            }else if(codeSet.size() > 0 && codeSet.size() <= 100){
//                query.setParameterList("codes", codeSet);
//            }else if(codeSet.size() == 0){
//                codeSet.add("0000000000");
//                query.setParameterList("codes", codeSet);
//            }
            List<String> codeList = new ArrayList<String>(codeSet);
//            Collections.sort(codeList);
            if(codeList.size() > (index+1)*100){
                codeList = codeList.subList(index*100,(index+1)*100);
            }else if(codeList.size() > index*100 && codeList.size() <= (index+1)*100){
                codeList = codeList.subList(index*100,codeList.size());
            }else if(codeSet.size() == 0){
                codeList.add("0000000000");
            }
            query.setParameterList("codes", codeList);
            List array = query.list();
            for (int i = 0; i < array.size(); i++) {
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setPSNNAME(checkIsNull(obj[1]));
                psndoc.setPSNCODE(checkIsNull(obj[2]));
                list.add(psndoc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return list;
    }
    /**
     * 根据员工编号获取员工基本信息
     * */
    public zgfh_bi_psndoc userSearchByCode(String PSNCODE) {
        Session session = null;
        zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select CORPNAME,GW,ZW,SY,HY,MZ,SEX,XL,SCHOOL,PSNCODE,PSNNAME,JG,HK,ZZMM,ZY,BIRTHDAY,RSDATE,RDDATE,WORKDATE,to_char(sysdate,'yyyy') - to_char(to_date(BIRTHDAY,'yyyy-MM-dd'),'yyyy'),DEPTNAME,CORP_SYSTEM,to_char(sysdate,'yyyy') - to_char(to_date(WORKDATE,'yyyy-MM-dd'),'yyyy'),percentage,psnclassname from oabi.zgfh_bi_psndoc2020 where PSNCODE=:PSNCODE";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                Object[] obj = (Object[]) array.get(0);
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setGW(checkIsNull(obj[1]));
                psndoc.setZW(checkIsNull(obj[2]));
                psndoc.setSY(checkIsNull(obj[3]));
                psndoc.setHY(checkIsNull(obj[4]));
                psndoc.setMZ(checkIsNull(obj[5]));
                psndoc.setSEX(checkIsNull(obj[6]));
                psndoc.setXL(checkIsNull(obj[7]));
                psndoc.setSCHOOL(checkIsNull(obj[8]));
                psndoc.setPSNCODE(checkIsNull(obj[9]));
                psndoc.setPSNNAME(checkIsNull(obj[10]));
                psndoc.setJG(checkIsNull(obj[11]));
                psndoc.setHK(checkIsNull(obj[12]));
                psndoc.setZZMM(checkIsNull(obj[13]));
                psndoc.setZY(checkIsNull(obj[14]));
                psndoc.setBIRTHDAY(checkIsNull(obj[15]));
                psndoc.setRSDATE(checkIsNull(obj[16]));
                psndoc.setRDDATE(checkIsNull(obj[17]));
                psndoc.setWORKDATE(checkIsNull(obj[18]));
                psndoc.setAGE(checkIsNull(obj[19]));
                psndoc.setDEPTNAME(checkIsNull(obj[20]));
                psndoc.setCORP_SYSTEM(checkIsNull(obj[21]));
                psndoc.setGL(checkIsNull(obj[22]));
                psndoc.setPercentage(checkIsNull(obj[23])+"%");
                psndoc.setPsnclassname(checkIsNull(obj[24]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return psndoc;
    }
    /**
     * 根据员工编号获取工作经历
     * */
    public ArrayList getWorkExprienceByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_psndoc_work> list = new ArrayList<zgfh_bi_psndoc_work>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select BEGINDATE,ENDDATE,WORKCORP,WORKPOST from oabi.zgfh_bi_psndoc_work where PSNCODE=:PSNCODE order by BEGINDATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_psndoc_work psndoc = new zgfh_bi_psndoc_work();
                    psndoc.setBEGINDATE(checkIsNull(obj[0]));
                    psndoc.setENDDATE(checkIsNull(obj[1]));
                    psndoc.setWORKCORP(checkIsNull(obj[2]));
                    psndoc.setWORKPOST(checkIsNull(obj[3]));
                    list.add(psndoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编号获取教育经历
     * */
    public ArrayList getEduExprienceByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_edu> list = new ArrayList<zgfh_bi_edu>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select BEGINDATE,ENDDATE,SCHOOL,MAJOR,EDUCATION,DEGREE from oabi.zgfh_bi_edu where PSNCODE=:PSNCODE order by BEGINDATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_edu psndoc = new zgfh_bi_edu();
                    psndoc.setBEGINDATE(checkIsNull(obj[0]));
                    psndoc.setENDDATE(checkIsNull(obj[1]));
                    psndoc.setSCHOOL(checkIsNull(obj[2]));
                    psndoc.setMAJOR(checkIsNull(obj[3]));
                    psndoc.setEDUCATION(checkIsNull(obj[4]));
                    psndoc.setDEGREE(checkIsNull(obj[5]));
                    list.add(psndoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编号获取考核信息
     * */
    public ArrayList getKPIByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_kpi> list = new ArrayList<zgfh_bi_kpi>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select KPI_YEAR,DEGREE,REWARD from oabi.zgfh_bi_kpi where PSNCODE=:PSNCODE order by KPI_YEAR desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_kpi psndoc = new zgfh_bi_kpi();
                    psndoc.setKPI_YEAR(checkIsNull(obj[0]));
                    psndoc.setDEGREE(checkIsNull(obj[1]));
                    psndoc.setREWARD(checkIsNull(obj[2]));
                    list.add(psndoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编号获取培训经历
     * */
    public ArrayList getTrainByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_train> list = new ArrayList<zgfh_bi_train>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select VTRA_ACT,TRM_CLASS_NAMES,BEGINDATE,ENDDATE from oabi.zgfh_bi_train where PSNCODE=:PSNCODE order by BEGINDATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_train psndoc = new zgfh_bi_train();
                    psndoc.setVTRA_ACT(checkIsNull(obj[0]));
                    psndoc.setTRM_CLASS_NAMES(checkIsNull(obj[1]));
                    psndoc.setBEGINDATE(checkIsNull(obj[2]));
                    psndoc.setENDDATE(checkIsNull(obj[3]));
                    list.add(psndoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编号获取合同信息
     * */
    public ArrayList getContByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_cont> list = new ArrayList<zgfh_bi_cont>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select VCONTRACTNUM,TERMNAME,BEGINDATE,ENDDATE from oabi.zgfh_bi_cont where PSNCODE=:PSNCODE order by BEGINDATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_cont psndoc = new zgfh_bi_cont();
                    psndoc.setVCONTRACTNUM(checkIsNull(obj[0]));
                    psndoc.setTERMNAME(checkIsNull(obj[1]));
                    psndoc.setBEGINDATE(checkIsNull(obj[2]));
                    psndoc.setENDDATE(checkIsNull(obj[3]));
                    list.add(psndoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编号获取职称、执业资格、技术等级
     * */
    public ArrayList getTitleByCode(String PSNCODE) {
        Session session = null;
        ArrayList<zgfh_bi_title> list = new ArrayList<zgfh_bi_title>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select P_NAME,DOCNAME,P_ORG,P_DATE from oabi.zgfh_bi_title where PSNCODE=:PSNCODE order by P_DATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_title psndoc = new zgfh_bi_title();
                    psndoc.setP_NAME(checkIsNull(obj[0]));
                    psndoc.setDOCNAME(checkIsNull(obj[1]));
                    psndoc.setP_ORG(checkIsNull(obj[2]));
                    psndoc.setP_DATE(checkIsNull(obj[3]));
                    list.add(psndoc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据员工编码获取附件列表
     * @param PSNCODE 员工编码
     * */
    public ArrayList getFileListByCode(String PSNCODE){
        Session session = null;
        ArrayList<zgfh_bi_ncfile> list = new ArrayList<zgfh_bi_ncfile>();
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select PSNCODE,ATTACHMENT_NAME,PK_ATTACHMENT,UPLOAD_DATE from oabi.zgfh_bi_ncfile where PSNCODE=:PSNCODE order by UPLOAD_DATE desc";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    zgfh_bi_ncfile psndoc = new zgfh_bi_ncfile();
                    psndoc.setPSNCODE(checkIsNull(obj[0]));
                    psndoc.setATTACHMENT_NAME(checkIsNull(obj[1]));
                    psndoc.setPK_ATTACHMENT(checkIsNull(obj[2]));
                    psndoc.setUPLOAD_DATE(checkIsNull(obj[3]));
                    list.add(psndoc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 根据文件ID获取文件属性
     * */
    public zgfh_bi_ncfile getFileById(String PK_ATTACHMENT){
        Session session = null;
        zgfh_bi_ncfile ncfile = new zgfh_bi_ncfile();
        try{
            session = HibernateHRDBUtil.getSession();
            String hql = "select ATTACHMENT_NAME from oabi.zgfh_bi_ncfile where PK_ATTACHMENT=:PK_ATTACHMENT";
            Query query = session.createSQLQuery(hql);
            query.setString("PK_ATTACHMENT", PK_ATTACHMENT);
            List array = query.list();
            if(array.size() > 0){
                Object obj = array.get(0);
                ncfile.setATTACHMENT_NAME(checkIsNull(obj));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return ncfile;
    }
    /**
     * 根据员工编码获取头像
     * */
    public byte[] getImg(String PSNCODE){
        Session session = null;
        byte[] data = null;
        try {
            session = HibernateHRDBUtil.getSession();
            String hql = "select PHOTO from oabi.zgfh_bi_psndoc2020 where PSNCODE=:PSNCODE";
            Query query = session.createSQLQuery(hql);
            query.setString("PSNCODE", PSNCODE);
            List array = query.list();
            if (array.size() > 0) {
                Object obj =  array.get(0);
                if (obj != null){
                    Blob blob = (Blob) obj;
                    long size = blob.length();
                    data = blob.getBytes(1,(int)size);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    /**
     * 根据员工编码获取头像,先检查本地，若本地存在直接读取本地图片，否则先保存至本地。无照片的人员读取default.jpg
     * */
    public byte[] getImgNew(String PSNCODE){
        Session session = null;
        byte[] data = null;
//        String rootPath = "E:"+separator+"QRCode"+separator+"headImg";
        String rootPath = PropertiesUtil.getProperty("imgPath");
//        String rootPath = separator +"soft"+separator+"module"+separator+"apache-tomcat-7.0.105"+separator+"webapps"+separator+"headImg";
        String originalImgPath = rootPath + separator + PSNCODE+".JPG";
        String compressImgPath = rootPath + separator + "compress" + separator + PSNCODE +".JPG";
        String compressPath = rootPath + separator + "compress";
        String defaultImg = rootPath + separator + "default.JPG";

        try {
            File file = new File(originalImgPath);
            if(!file.exists()){
                session = HibernateHRDBUtil.getSession();
                String hql = "select PHOTO from oabi.zgfh_bi_psndoc2020 where PSNCODE=:PSNCODE";
//                String hql = "select previewphoto from oabi.ZGFH_BI_TEST_PHOTO where CODE=:PSNCODE";
                Query query = session.createSQLQuery(hql);
                query.setString("PSNCODE", PSNCODE);
                List array = query.list();
                if (array.size() > 0) {
                    Object obj =  array.get(0);
                    if (obj != null){
                        Blob blob = (Blob) obj;
                        long size = blob.length();
                        data = blob.getBytes(1,(int)size);
//                        ByteArrayOutputStream out = new ByteArrayOutputStream();
//                        ObjectOutputStream sout = new ObjectOutputStream(out);
//                        sout.writeObject(obj);
//                        data = out.toByteArray();
//                        String encoded = Base64.getEncoder().encodeToString(data);
//                        String encoded = new BASE64Encoder().encode(data);
//                        System.out.println(encoded);
//                        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
//                        data = dec.decodeBuffer(encoded);
//                        encoded = new BASE64Encoder().encode(data);
//                        System.out.println(encoded);
                    }
                }
                if(data!=null && data.length > 0){
                    FileUtil.savePhotoToLocal(data, originalImgPath);
                    logger.info("图片成功存储到本地，路径为：" + originalImgPath);
                    FileUtil.imageCompress(originalImgPath,compressPath);
                    logger.info("图片压缩成功，路径为：" + compressImgPath);
                    data = FileUtil.getImgFromLocal(compressImgPath);
                    logger.info("从本地加载图片，路径为:"+compressImgPath);
                }else{
                    data = FileUtil.getImgFromLocal(defaultImg);
                    logger.info("从本地加载默认图片，路径为:"+defaultImg);
                }
            }else{
                data = FileUtil.getImgFromLocal(compressImgPath);
                logger.info("从本地加载图片，路径为:" + compressImgPath);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    /**
     * 根据员工编码获取头像,先检查本地，若本地存在直接读取本地图片，否则先保存至本地。无照片的人员读取default.jpg
     * */
    public byte[] getImgPro(String PSNCODE){
        Session session = null;
        byte[] data = null;
//        String rootPath = "E:"+separator+"QRCode"+separator+"headImg";
        String rootPath = PropertiesUtil.getProperty("imgPath");
//        String rootPath = separator +"soft"+separator+"module"+separator+"apache-tomcat-7.0.105"+separator+"webapps"+separator+"headImg";
        String originalImgPath = rootPath + separator + PSNCODE+".JPG";
        String compressImgPath = rootPath + separator + "compress" + separator + PSNCODE +".JPG";
        String compressPath = rootPath + separator + "compress";
        String defaultImg = rootPath + separator + "default.JPG";

        try {
            File file = new File(originalImgPath);
            if(!file.exists()){
                session = HibernateHRDBUtil.getSession();
                String hql = "select corp_system,PHOTO from oabi.zgfh_bi_psndoc2020 where PSNCODE=:PSNCODE";
//                String hql = "select previewphoto from oabi.ZGFH_BI_TEST_PHOTO where CODE=:PSNCODE";
                Query query = session.createSQLQuery(hql);
                query.setString("PSNCODE", PSNCODE);
                List array = query.list();
                if (array.size() > 0) {
                    Object []obj =  (Object[])array.get(0);
                    if (obj[1] != null){
                        String corp_system = String.valueOf(obj[0]);
                        if(corp_system.equals("mszq")){
                            data = downloadMSZQ(PSNCODE);
                        }else{
                            Blob blob = (Blob) obj[1];
                            long size = blob.length();
                            data = blob.getBytes(1,(int)size);
                            //亚太财险
                            if(corp_system.equals("api")){
                                String res = new sun.misc.BASE64Encoder().encode(data);
                                int start = res.indexOf("9j")-1;
                                res = res.substring(start,res.length());
                                sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
                                data = dec.decodeBuffer(res);
                                //民生信托暂时不处理，显示默认图片
                            }else if(corp_system.equals("msxt")){
                                data = null;
                            }
                        }
                    }
                }
                if(data!=null && data.length > 0){
                    FileUtil.savePhotoToLocal(data, originalImgPath);
                    logger.info("图片成功存储到本地，路径为：" + originalImgPath);
                    FileUtil.imageCompress(originalImgPath,compressPath);
                    logger.info("图片压缩成功，路径为：" + compressImgPath);
                    data = FileUtil.getImgFromLocal(compressImgPath);
                    logger.info("从本地加载图片，路径为:"+compressImgPath);
                }else{
                    data = FileUtil.getImgFromLocal(defaultImg);
                    logger.info("从本地加载默认图片，路径为:"+defaultImg);
                }
            }else{
                data = FileUtil.getImgFromLocal(compressImgPath);
                logger.info("从本地加载图片，路径为:"+compressImgPath);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    /**
     * 按原文件类型下载附件
     * @param PK_ATTACHMENT
     * @param PSNCODE
     * @param realname 真实文件名
     * */
    public byte[] downloadFile(String PK_ATTACHMENT, String PSNCODE,String realname) {
        byte[] data = null;
        Session session;
//        String rootPath = "E:"+separator+"QRCode"+separator+"testDoc"+separator+PSNCODE;
        String rootPath = PropertiesUtil.getProperty("filePath")+separator+PSNCODE;
//        String rootPath = separator +"soft"+separator+"module"+separator+"apache-tomcat-7.0.105"+separator+"webapps"+separator+"headImg";;
        try {
            // 得到要下载的文件名
            String pdfFileName = PK_ATTACHMENT + ".pdf";
            pdfFileName = new String(pdfFileName.getBytes("iso8859-1"), "UTF-8");
            // 得到要下载的文件
            File file = new File(rootPath + separator + pdfFileName);
            // 如果文件不存在则从数据库中读取
            if (!file.exists()) {
                session = HibernateHRDBUtil.getSession();
                String hql = "select ATTACHMENT,ATTACHMENT_NAME from oabi.zgfh_bi_ncfile where PK_ATTACHMENT=:PK_ATTACHMENT";
                Query query = session.createSQLQuery(hql);
                query.setString("PK_ATTACHMENT", PK_ATTACHMENT);
                String attachment_name = "";
                String fileType = "";
                String fileName = "";
                List array = query.list();
                if (array.size() > 0) {
                    Object[] obj =  (Object[])array.get(0);
                    Blob blob = (Blob) obj[0];
                    long size = blob.length();
                    data = blob.getBytes(1, (int) size);
                    attachment_name = obj[1].toString();
                    //转换成64编码后头部会多出36个字符，不清楚什么原因，截掉头部字符以后即可正常保存
                    String res = new sun.misc.BASE64Encoder().encode(data);
                    res = res.substring(36, res.length());
                    data = new sun.misc.BASE64Decoder().decodeBuffer(res);
                }
                if(data!=null && data.length > 0){
                    fileType = attachment_name.substring(attachment_name.lastIndexOf("."), attachment_name.length());
                    fileName = attachment_name.substring(0, attachment_name.lastIndexOf("."));
                    FileUtil.saveFileToLocal(data, rootPath, PK_ATTACHMENT + fileType);
                    //目前office文件保存到本地以后会存在乱码以及数据丢失的问题（可能是编码的原因，待解决）
//                    updateFileContent(PK_ATTACHMENT,PSNCODE);
                    //如果是不pdf文件需先转换为pdf，目前只能转换.doc文件
                    if(!fileType.equals(".pdf")){
//                        FileUtil.wordToPdf(rootPath+separator+PK_ATTACHMENT+".doc",rootPath+separator+PK_ATTACHMENT+".pdf");
//                        FileConverter fileConverter = new FileConverter(rootPath+separator+PK_ATTACHMENT+fileType);
//                        fileConverter.office2pdf();
                    }
                    data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+fileType);
//                    data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+".pdf");
                    logger.info("从本地加载加载，路径为:" + rootPath+separator+fileName+fileType);
                }
            }else{
                String fileType = realname.substring(realname.lastIndexOf("."), realname.length());
                data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+fileType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 下载转换为PDF格式的附件
     * @param PK_ATTACHMENT
     * @param PSNCODE
     * @param realname 真实文件名
     * */
    public byte[] downloadFileFormat(String PK_ATTACHMENT, String PSNCODE,String realname) {
        byte[] data = null;
        Session session;
        String rootPath = PropertiesUtil.getProperty("filePath")+separator+PSNCODE;
        try {
            // 得到要下载的文件名
            String pdfFileName = PK_ATTACHMENT + ".pdf";
            pdfFileName = new String(pdfFileName.getBytes("iso8859-1"), "UTF-8");
            // 得到要下载的文件
            File file = new File(rootPath + separator + pdfFileName);
            // 如果文件不存在则从数据库中读取
            if (!file.exists()) {
                session = HibernateHRDBUtil.getSession();
                String hql = "select ATTACHMENT,ATTACHMENT_NAME from oabi.zgfh_bi_ncfile where PK_ATTACHMENT=:PK_ATTACHMENT";
                Query query = session.createSQLQuery(hql);
                query.setString("PK_ATTACHMENT", PK_ATTACHMENT);
                String attachment_name = "";
                String fileType = "";
                String fileName = "";
                List array = query.list();
                if (array.size() > 0) {
                    Object[] obj =  (Object[])array.get(0);
                    Blob blob = (Blob) obj[0];
                    long size = blob.length();
                    data = blob.getBytes(1, (int) size);
                    attachment_name = obj[1].toString();
                    //转换成64编码后头部会多出36个字符，不清楚什么原因，截掉头部字符以后即可正常保存
//                    String res = new sun.misc.BASE64Encoder().encode(data);
                    String res = new BASE64Encoder().encode(data);
//                    System.out.println(res);
                    res = res.substring(36, res.length());
                    data = new BASE64Decoder().decodeBuffer(res);
//                    data = new sun.misc.BASE64Decoder().decodeBuffer(res);
                }
                if(data!=null && data.length > 0){
                    fileType = attachment_name.substring(attachment_name.lastIndexOf("."), attachment_name.length());
                    fileName = attachment_name.substring(0, attachment_name.lastIndexOf("."));
                    FileUtil.saveFileToLocal(data, rootPath, PK_ATTACHMENT + fileType);

                    //如果是不pdf文件需先转换为pdf，目前只能转换.doc文件
                    if(!fileType.equals(".pdf")){
//                        FileUtil.wordToPdf(rootPath+separator+PK_ATTACHMENT+".doc",rootPath+separator+PK_ATTACHMENT+".pdf");
                        FileConverter fileConverter = new FileConverter(rootPath+separator+PK_ATTACHMENT+fileType);
                        fileConverter.office2pdf();
                    }
                    //目前office文件保存到本地以后会存在乱码以及数据丢失的问题（可能是编码的原因，待解决）
                    updateFileContent(PK_ATTACHMENT,PSNCODE);
//                    data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+fileType);
                    data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+".pdf");
                    logger.info("从本地加载加载，路径为:" + rootPath+separator+fileName+fileType);
                }
            }else{
//                String fileType = realname.substring(realname.lastIndexOf("."), realname.length());
                data = FileUtil.getImgFromLocal(rootPath+separator+PK_ATTACHMENT+".pdf");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 定时更新附件文字内容,查询附件表中CONTENT字段为空的记录，将文件保存至服务器端后，抽取其中的文字内容存入数据库
     * */
    public static void updateContent(){
        Session session = null;
        Transaction ts = null;
        try {
            session = HibernateHRDBUtil.getSession();
            ts = session.beginTransaction();
            String hql = "select PSNCODE,PK_ATTACHMENT,ATTACHMENT_NAME,ATTACHMENT from oabi.zgfh_bi_ncfile where content is null";
            Query query = session.createSQLQuery(hql);
            List array = query.list();
            int res = 0;
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    String psncode = obj[0].toString();
                    String pk_attachment = obj[1].toString();
                    String attachment_name = obj[2].toString();
                    String file_type = attachment_name.substring(attachment_name.lastIndexOf("."), attachment_name.length());
                    String rootPath = PropertiesUtil.getProperty("filePath")+separator+psncode;

                    String filePath = rootPath+separator+pk_attachment+file_type;
                    String fileName = pk_attachment + file_type;
                    //获取数据库中二进制形式附件
                    Blob blob = (Blob) obj[3];
                    long size = blob.length();
                    byte []data = blob.getBytes(1, (int) size);
                    //根据附件格式抽取文字内容，目前仅处理doc、docx、pdf
                    String content = "";
                    if(data!=null && data.length > 0){
                        //将附件存储至服务器，以文件pk值为文件名
                        FileUtil.saveFileToLocal(data, rootPath, pk_attachment + file_type);
                        FileConverter fileConverter = new FileConverter(filePath);
                        if(file_type.equals(".pdf")){
                            content = PDFUtil.READPDF(filePath,fileName);
                        }else if(file_type.equals(".doc")){
                            fileConverter.office2pdf();
//                            FileUtil.wordToPdf(rootPath+separator+pk_attachment+file_type,rootPath+separator+pk_attachment+".pdf");
                            content = POIUtil.readDoc(filePath,fileName);
                        }else if(file_type.equals(".docx")){
                            content = POIUtil.readDocx(filePath,fileName);
                        }
                        String updateSql = "update oabi.zgfh_bi_ncfile set content=:content where PK_ATTACHMENT=:PK_ATTACHMENT";
                        Query updateQuery = session.createSQLQuery(updateSql);
                        updateQuery.setString("content",content);
                        updateQuery.setString("PK_ATTACHMENT",pk_attachment);
                        res+=updateQuery.executeUpdate();
                    }
                }
            }
            System.out.println("共更新" + res + "条数据");
            ts.commit();
        } catch (Exception e) {
            if(ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 根据PK_ATTACHMENT更新附件表中对应记录的content字段
     * */
    public void updateFileContent(String PK_ATTACHMENT,String PSNCODE){
        String rootPath = PropertiesUtil.getProperty("filePath")+separator+PSNCODE;
        Session session = null;
        Transaction ts = null;
        try{
            File file = new File(rootPath+separator+PK_ATTACHMENT+".pdf");
            if(file.exists()){
                session = HibernateHRDBUtil.getSession();
                ts = session.beginTransaction();
                String content = PDFUtil.READPDF(rootPath+separator+PK_ATTACHMENT+".pdf",PK_ATTACHMENT+".pdf");
                String sql = "update oabi.zgfh_bi_ncfile set content=:content where PK_ATTACHMENT=:PK_ATTACHMENT";
                Query query = session.createSQLQuery(sql);
                query.setString("content", content);
                query.setString("PK_ATTACHMENT",PK_ATTACHMENT);
                query.executeUpdate();
            }else{
                logger.error("文件不存在，附件内容存储失败");
            }
            ts.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 判断数据库中获取的字段数据是否为空，为空返回“---”
     * */
    public String checkIsNull(Object obj){
        if(obj != null){
            return obj.toString();
        }else{
            return "---";
        }
    }
    /**
     * 定时更新拼音、全拼字段为空的用户信息
     * */
    public static void updatePY() {
        Session session = null;
        Transaction ts = null;
        try {
            session = HibernateHRDBUtil.getSession();
            ts = session.beginTransaction();
            String hql = "select PSNCODE,PSNNAME from oabi.zgfh_bi_psndoc2020 where PY is null or QPY is null order by CORPCODE,PSNCODE";
            Query query = session.createSQLQuery(hql);
            List array = query.list();
            int res = 0;
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    String psncode = obj[0].toString();
                    System.out.println(psncode);
                    String py = PinYinUtil.ToFirstChar(obj[1].toString());
                    String qpy = PinYinUtil.ToPinyin(obj[1].toString());
                    String updateSql = "update oabi.zgfh_bi_psndoc2020 set PY=:PY,QPY=:QPY where PSNCODE=:PSNCODE";
                    Query updateQuery = session.createSQLQuery(updateSql);
                    updateQuery.setString("PY",py);
                    updateQuery.setString("QPY",qpy);
                    updateQuery.setString("PSNCODE", psncode);
                    res+=updateQuery.executeUpdate();
                }
            }
            System.out.println("共更新" + res + "条数据");
            ts.commit();
        } catch (Exception e) {
            if(ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 缩小或放大图片
     *
     * @param data 图片的byte数据
     * @param nw    需要缩到的宽度
     * @param nh    需要缩到高度
     * @return 缩放后的图片的byte数据
     */
    private byte[] ChangeImgSize(byte[] data, int nw, int nh) {
        byte[] newdata = null;
        try {
            BufferedImage bis = ImageIO.read(new ByteArrayInputStream(data));
            int w = bis.getWidth();
            int h = bis.getHeight();
            double sx = (double) nw / w;
            double sy = (double) nh / h;
            AffineTransform transform = new AffineTransform();
            transform.setToScale(sx, sy);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            //原始颜色
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);

            //转换成byte字节
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bid, "jpeg", baos);
            newdata = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newdata;
    }

    public static void gengerateQRCode(String PSNCODE) throws Exception{
        String text = "http://10.0.6.169:8060/CV/userSearchByCode/"+PSNCODE;
        String logoPath = "E:\\QRCode\\logo.jpg";
        String destPath = "E:\\QRCode\\result\\";
        QRCodeUtils.encode(text,PSNCODE, logoPath, destPath, true);
    }
    /**
     * 根据员工编码生成二维码
     * */
    public byte[] getQRCode(String PSNCODE){
        InputStream ins = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        String path = "E:\\QRCode\\result\\"+PSNCODE+".jpg";
//        File file = new File(path);
        try{
            ins = new FileInputStream(path);
            byte[] b = new byte[1024];
            int len = -1;
            while((len = ins.read(b)) != -1 ){
                bs.write(b,0,len);
            }
            return bs.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                ins.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void generateCodeList(String sql,String searchVal,HashSet codeSet){
        Session session = null;
        try{
            session = HibernateHRDBUtil.getSession();
            Query query = session.createSQLQuery(sql);
            query.setString("PSNNAME", "%" + searchVal + "%");
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object obj =  array.get(i);
//                if(codeSet.size() < 100){
//                if(!codeList.contains(obj) && codeList.size() < 100){
//                if(!codeSet.contains(obj)){
//                    codeList.add(checkIsNull(obj));
                if(obj != null && !"".equals(obj))
                    codeSet.add(obj.toString());

//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
    }
    /**
     * 获取简历总数
     * */
    public int getCount(){
        Session session = null;
        int count = 0;
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select count(*) from oabi.zgfh_bi_psndoc2020";
            Query query = session.createSQLQuery(sql);
            count = Integer.parseInt(query.list().get(0).toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null)session.close();
        }
        return count;
    }
    /**
     * 获取fhkg简历总数
     * */
    public int getFHKGCount(){
        Session session = null;
        int count = 0;
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select count(*) from oabi.zgfh_bi_psndoc2020 where corp_system != 'zgfh'";
            Query query = session.createSQLQuery(sql);
            count = Integer.parseInt(query.list().get(0).toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null)session.close();
        }
        return count;
    }
    /**
     * PC端OA校验token
     * */
    public boolean checkOAToken(HttpServletRequest request){
        boolean flag = false;
        try{
            String requestKey = request.getParameter("OAKey");
            String requestValue = request.getParameter("OAValue");
            String OAKey = PropertiesUtil.getProperty("OAKey");
            String OAValue = PropertiesUtil.getProperty("OAValue");
            String requestIp = getRequestIP(request);
            if(requestKey == null || requestValue == null){
                flag = false;
                logger.info("IP:"+requestIp+"校验失败，禁止访问");
            }else if(requestKey.equals(OAKey) && requestValue.equals(OAValue)){
                flag = true;
                logger.info("IP:"+requestIp+"校验成功，允许访问");
            }else{
                flag = false;
                logger.info("IP:"+requestIp+"校验失败，禁止访问");
            }
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
        }
        return flag;
    }
    //打印http请求头
    public String getRequestIP(HttpServletRequest request){
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println("===> " + key + " --- " + value);
//        }
        String ip = "";
        try{
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if(ip == null || ip.length() == 0){
                logger.error("ip地址获取失败");
                ip = "0.0.0.0";
            }
//            requestIp = ip;
        }catch(Exception e){
            logger.error(e.toString());
            e.printStackTrace();
        }
//        logger.info("访问源IP地址为："+ip);
        return ip;
    }
    /**
     * 查询信息表所有数据
     * */
    public List<zgfh_bi_psndoc> queryPsndoc(){
        Session session = null;
        List<zgfh_bi_psndoc> res = new ArrayList<zgfh_bi_psndoc>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select CORPNAME,GW,ZW,SY,HY,MZ,SEX,XL,SCHOOL,PSNCODE,PSNNAME,JG,HK,ZZMM,ZY,BIRTHDAY,RSDATE,RDDATE,WORKDATE,to_char(sysdate,'yyyy') - to_char(to_date(BIRTHDAY,'yyyy-MM-dd'),'yyyy') from oabi.zgfh_bi_psndoc2020 order by corpname,psncode";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setGW(checkIsNull(obj[1]));
                psndoc.setZW(checkIsNull(obj[2]));
                psndoc.setSY(checkIsNull(obj[3]));
                psndoc.setHY(checkIsNull(obj[4]));
                psndoc.setMZ(checkIsNull(obj[5]));
                psndoc.setSEX(checkIsNull(obj[6]));
                psndoc.setXL(checkIsNull(obj[7]));
                psndoc.setSCHOOL(checkIsNull(obj[8]));
                psndoc.setPSNCODE(checkIsNull(obj[9]));
                psndoc.setPSNNAME(checkIsNull(obj[10]));
                psndoc.setJG(checkIsNull(obj[11]));
                psndoc.setHK(checkIsNull(obj[12]));
                psndoc.setZZMM(checkIsNull(obj[13]));
                psndoc.setZY(checkIsNull(obj[14]));
                psndoc.setBIRTHDAY(checkIsNull(obj[15]));
                psndoc.setRSDATE(checkIsNull(obj[16]));
                psndoc.setRDDATE(checkIsNull(obj[17]));
                psndoc.setWORKDATE(checkIsNull(obj[18]));
                psndoc.setAGE(checkIsNull(obj[19]));
                res.add(psndoc);
            }
            logger.info("基本信息表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询工作履历表所有数据
     * */
    public List<zgfh_bi_psndoc_work> queryWork(){
        Session session = null;
        List<zgfh_bi_psndoc_work> res = new ArrayList<zgfh_bi_psndoc_work>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,WORKCORP,WORKPOST from oabi.zgfh_bi_psndoc_work";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc_work work = new zgfh_bi_psndoc_work();
                work.setPSNCODE(checkIsNull(obj[0]));
                work.setBEGINDATE(checkIsNull(obj[1]));
                work.setENDDATE(checkIsNull(obj[2]));
                work.setWORKCORP(checkIsNull(obj[3]));
                work.setWORKPOST(checkIsNull(obj[4]));
                res.add(work);
            }
            logger.info("工作履历表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询学历信息表所有数据
     * */
    public List<zgfh_bi_edu> queryEdu(){
        Session session = null;
        List<zgfh_bi_edu> res = new ArrayList<zgfh_bi_edu>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,SCHOOL,MAJOR,EDUCATION,DEGREE from oabi.zgfh_bi_edu";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                zgfh_bi_edu edu = new zgfh_bi_edu();
                Object[] obj = (Object[]) array.get(i);
                edu.setPSNCODE(checkIsNull(obj[0]));
                edu.setBEGINDATE(checkIsNull(obj[1]));
                edu.setENDDATE(checkIsNull(obj[2]));
                edu.setSCHOOL(checkIsNull(obj[3]));
                edu.setMAJOR(checkIsNull(obj[4]));
                edu.setEDUCATION(checkIsNull(obj[5]));
                edu.setDEGREE(checkIsNull(obj[6]));

                res.add(edu);
            }
            logger.info("学历信息表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询考核表所有数据
     * */
    public List<zgfh_bi_kpi> queryKpi(){
        Session session = null;
        List<zgfh_bi_kpi> res = new ArrayList<zgfh_bi_kpi>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,KPI_YEAR,DEGREE,REWARD from oabi.zgfh_bi_kpi";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_kpi kpi = new zgfh_bi_kpi();
                kpi.setPSNCODE(checkIsNull(obj[0]));
                kpi.setKPI_YEAR(checkIsNull(obj[1]));
                kpi.setDEGREE(checkIsNull(obj[2]));
                kpi.setREWARD(checkIsNull(obj[3]));

                res.add(kpi);
            }
            logger.info("考核表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询合同信息表所有数据
     * */
    public List<zgfh_bi_cont> queryCont(){
        Session session = null;
        List<zgfh_bi_cont> res = new ArrayList<zgfh_bi_cont>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,VCONTRACTNUM,TERMNAME from oabi.zgfh_bi_cont";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(0);
                zgfh_bi_cont cont = new zgfh_bi_cont();
                cont.setPSNCODE(checkIsNull(obj[0]));
                cont.setBEGINDATE(checkIsNull(obj[1]));
                cont.setENDDATE(checkIsNull(obj[2]));
                cont.setVCONTRACTNUM(checkIsNull(obj[3]));
                cont.setTERMNAME(checkIsNull(obj[4]));

                res.add(cont);
            }
            logger.info("合同信息表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询教育培训表所有数据
     * */
    public List<zgfh_bi_train> queryTrain(){
        Session session = null;
        List<zgfh_bi_train> res = new ArrayList<zgfh_bi_train>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,VTRA_ACT,TRM_CLASS_NAMES from oabi.zgfh_bi_train";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_train train = new zgfh_bi_train();
                train.setPSNCODE(checkIsNull(obj[0]));
                train.setBEGINDATE(checkIsNull(obj[1]));
                train.setENDDATE(checkIsNull(obj[2]));
                train.setVTRA_ACT(checkIsNull(obj[3]));
                train.setTRM_CLASS_NAMES(checkIsNull(obj[4]));
                res.add(train);
            }
            logger.info("教育培训表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询职称表所有数据
     * */
    public List<zgfh_bi_title> queryTitle(){
        Session session = null;
        List<zgfh_bi_title> res = new ArrayList<zgfh_bi_title>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,P_NAME,DOCNAME,P_ORG,P_DATE from oabi.zgfh_bi_title";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_title title = new zgfh_bi_title();
                title.setPSNCODE(checkIsNull(obj[0]));
                title.setP_NAME(checkIsNull(obj[1]));
                title.setDOCNAME(checkIsNull(obj[2]));
                title.setP_ORG(checkIsNull(obj[3]));
                title.setP_DATE(checkIsNull(obj[4]));

                res.add(title);
            }
            logger.info("职称表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
     /**
     * 定时创建信息表索引
     * */
    public void createPsndocIndex(){
        List<zgfh_bi_psndoc> array = queryPsndoc();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_psndoc psndoc : array){
                Document document = new Document();

                Field CORPNAME = new TextField("CORPNAME",psndoc.getCORPNAME(),Field.Store.YES);
                Field GW = new TextField("GW",psndoc.getGW(),Field.Store.YES);
                Field ZW = new TextField("ZW",psndoc.getZW(),Field.Store.YES);
                Field SY = new TextField("SY",psndoc.getSY(),Field.Store.YES);
                Field HY = new TextField("HY",psndoc.getHY(),Field.Store.YES);
                Field MZ = new TextField("MZ",psndoc.getMZ(),Field.Store.YES);
                Field SEX = new TextField("SEX",psndoc.getSEX(),Field.Store.YES);
                Field XL = new TextField("XL",psndoc.getXL(),Field.Store.YES);
                Field SCHOOL = new TextField("SCHOOL",psndoc.getSCHOOL(),Field.Store.YES);
                Field PSNCODE = new TextField("PSNCODE",psndoc.getPSNCODE(),Field.Store.YES);
                Field PSNNAME = new TextField("PSNNAME",psndoc.getPSNNAME(),Field.Store.YES);
                Field JG = new TextField("JG",psndoc.getJG(),Field.Store.YES);
                Field HK = new TextField("HK",psndoc.getHK(),Field.Store.YES);
                Field ZZMM = new TextField("ZZMM",psndoc.getZZMM(),Field.Store.YES);
                Field ZY = new TextField("ZY",psndoc.getZY(),Field.Store.YES);
                Field BIRTHDAY = new TextField("BIRTHDAY",psndoc.getBIRTHDAY(),Field.Store.YES);
                Field RSDATE = new TextField("RSDATE",psndoc.getRSDATE(),Field.Store.YES);
                Field WORKDATE = new TextField("WORKDATE",psndoc.getWORKDATE(),Field.Store.YES);
                Field AGE = new TextField("AGE",psndoc.getAGE(),Field.Store.YES);

                document.add(CORPNAME);
                document.add(GW);
                document.add(ZW);
                document.add(SY);
                document.add(HY);
                document.add(MZ);
                document.add(SEX);
                document.add(XL);
                document.add(SCHOOL);
                document.add(PSNCODE);
                document.add(PSNNAME);
                document.add(JG);
                document.add(HK);
                document.add(ZZMM);
                document.add(ZY);
                document.add(BIRTHDAY);
                document.add(RSDATE);
                document.add(WORKDATE);
                document.add(AGE);

                docList.add(document);
            }
            Analyzer analyzer = new IKAnalyzer();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("信息表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("信息表索引创建失败:"+e.toString());
            e.printStackTrace();
        }

    }
    /**
     * 定时创建工作履历表索引
     * */
    public void createWorkIndex(){
        List<zgfh_bi_psndoc_work> array = queryWork();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_psndoc_work work : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",work.getPSNCODE(),Field.Store.YES);
                Field WORKCORP = new TextField("WORKCORP",work.getWORKCORP(),Field.Store.YES);
                Field WORKPOST = new TextField("WORKPOST",work.getWORKPOST(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(WORKCORP);
                document.add(WORKPOST);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc_work");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("工作履历表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("工作履历表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建学历信息表索引
     * */
    public void createEduIndex(){
        List<zgfh_bi_edu> array = queryEdu();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_edu edu : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",edu.getPSNCODE(),Field.Store.YES);
                Field SCHOOL = new TextField("SCHOOL",edu.getSCHOOL(), Field.Store.YES);
                Field MAJOR = new TextField("MAJOR",edu.getMAJOR(),Field.Store.YES);
                Field EDUCATION = new TextField("EDUCATION",edu.getEDUCATION(),Field.Store.YES);
                Field DEGREE = new TextField("DEGREE",edu.getDEGREE(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(SCHOOL);
                document.add(MAJOR);
                document.add(EDUCATION);
                document.add(DEGREE);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_edu");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("学历信息表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("学历信息表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建考核表索引
     * */
    public void createKpiIndex(){
        List<zgfh_bi_kpi> array = queryKpi();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_kpi kpi : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",kpi.getPSNCODE(),Field.Store.YES);
                Field MAJOR = new TextField("MAJOR",kpi.getMAJOR(),Field.Store.YES);
                Field REWARD = new TextField("REWARD",kpi.getREWARD(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(MAJOR);
                document.add(REWARD);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_kpi");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("考核表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("考核表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建合同信息表索引
     * */
    public void createContIndex(){
        List<zgfh_bi_cont> array = queryCont();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_cont cont : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",cont.getPSNCODE(),Field.Store.YES);
                Field VCONTRACTNUM = new TextField("VCONTRACTNUM",cont.getVCONTRACTNUM(),Field.Store.YES);
                Field TERMNAME = new TextField("TERMNAME",cont.getTERMNAME(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(VCONTRACTNUM);
                document.add(TERMNAME);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_cont");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("合同信息表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("合同信息表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建教育培训表索引
     * */
    public void createTrainIndex(){
        List<zgfh_bi_train> array = queryTrain();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_train train : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",train.getPSNCODE(),Field.Store.YES);
                Field VTRA_ACT = new TextField("VTRA_ACT",train.getVTRA_ACT(),Field.Store.YES);
                Field TRM_CLASS_NAMES = new TextField("TRM_CLASS_NAMES",train.getTRM_CLASS_NAMES(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(VTRA_ACT);
                document.add(TRM_CLASS_NAMES);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_train");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("教育培训表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("教育培训表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建职称表索引
     * */
    public void createTitleIndex(){
        List<zgfh_bi_title> array = queryTitle();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_title title : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",title.getPSNCODE(),Field.Store.YES);
                Field P_NAME = new TextField("P_NAME",title.getP_NAME(),Field.Store.YES);
                Field DOCNAME = new TextField("DOCNAME",title.getDOCNAME(),Field.Store.YES);
                Field P_ORG = new TextField("P_ORG",title.getP_ORG(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(P_NAME);
                document.add(DOCNAME);
                document.add(P_ORG);

                docList.add(document);
            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_title");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("职称表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("职称表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }

    public byte[] getPdf(){
        String path = "/soft/testPdf/default.pdf";
        byte []data =  FileUtil.getImgFromLocal(path);
        return data;
    }

    public static byte[] downloadMSZQ(String PSNCODE){
        String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String SQLSERVER_URL = "jdbc:sqlserver://10.0.59.10:1433;DatabaseName=mszq";
        byte[] data = null;
        try {
            Class.forName(SQLSERVER_DRIVER).newInstance();
            String url = SQLSERVER_URL;
            Connection con = DriverManager.getConnection(url, "sa", "sa");
            Statement st = con.createStatement();
            String sql = "select photo from ePhoto where eid=(select eid from eemployee where badge='"+PSNCODE+"')";
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
//                    System.out.print(rs.getString(i) + "\t");
                    data = convertPhoto(rs.getString(i),PSNCODE);
                }
                System.out.println();
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static byte[] convertPhoto(String hexString,String code){
        byte[] data = null;
        try{
            if (hexString == null) {
                return null;
            }
            String extension = null;

            int jpgStartIndex = hexString.indexOf(ImageConstants.JPG_START);
            int pngStartIndex = hexString.indexOf(ImageConstants.PNG_START);

            int photoStartIndex = 0;

            if (jpgStartIndex == 0) {
                //A jpg file
                extension = ImageConstants.IMG_SUFFIX_JPG;
                photoStartIndex = jpgStartIndex;

            }else if(pngStartIndex == 0) {
                //here should be a png photo file
                extension = ImageConstants.IMG_SUFFIX_PNG;
                photoStartIndex = pngStartIndex;
            }

            if (photoStartIndex >= 0) {
                String fileWithExtension = PropertiesUtil.getProperty("filePath")+separator + code + separator + code + extension;
                logger.info("Creating photo file: " + fileWithExtension + " " + photoStartIndex);
                String photoString = hexString.substring(photoStartIndex);
                data = HexConverter.hex2File(photoString);
//                FileUtil.imageCompress(originalImgPath,compressPath);
                //update photo file name with extensions
                logger.info("Created photo file: " + fileWithExtension);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.error("Can't create photo file with code " + code);
        return data;
    }

    public List initEdu(){
        Session session = null;
        List<zgfh_bi_edu> res = new ArrayList();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select t.Xl from oabi.zgfh_bi_psndoc2020 t group by t.XL";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                zgfh_bi_edu edu = new zgfh_bi_edu();
                Object obj = array.get(i);
                if(obj == null || obj.toString().contains("/")){
//                    edu.setDEGREE("学历未知");
                }else{
                    edu.setDEGREE(obj.toString());
                    res.add(edu);
                }
            }
            logger.info("共获取："+res.size()+"种学历类型");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    public List initSex(){
        Session session = null;
        List<zgfh_bi_psndoc> res = new ArrayList();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select t.sex from oabi.zgfh_bi_psndoc2020 t group by t.sex";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                Object obj = array.get(i);
                if(obj == null){
//                    psndoc.setSEX("性别未知");
                }else{
                    psndoc.setSEX(obj.toString());
                    res.add(psndoc);
                }
            }
            logger.info("共获取："+res.size()+"种性别类型");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }

    public List initZzmm(){
        Session session = null;
        List<zgfh_bi_psndoc> res = new ArrayList();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select t.zzmm from oabi.zgfh_bi_psndoc2020 t group by t.zzmm";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                Object obj = array.get(i);
                if(obj == null){
//                    psndoc.setSEX("性别未知");
                }else{
                    psndoc.setZZMM(obj.toString());
                    res.add(psndoc);
                }
            }
            logger.info("共获取："+res.size()+"种性别类型");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }

    public ArrayList<zgfh_bi_psndoc> conditionQueryList(List<condition_obj> condition_list){
        Session session = null;
        ArrayList<zgfh_bi_psndoc> res = new ArrayList();
        List<String> sexList = new ArrayList<String>();
        List<String> eduList = new ArrayList<String>();
        List<String> zzmmList = new ArrayList<String>();
        List<String> docList = new ArrayList<String>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select * from(select t1.CORPNAME,t1.PSNNAME,t1.PSNCODE,to_char(sysdate,'yyyy') - to_char(to_date(t1.BIRTHDAY,'yyyy-MM-dd'),'yyyy') as age,t1.CORPCODE,t1.ZZMM,t1.SEX,t1.XL,t2.p_name,t3.major from oabi.zgfh_bi_psndoc2020 t1 left join oabi.zgfh_bi_title t2 on t1.psncode = t2.psncode left join oabi.zgfh_bi_edu t3 on t3.psncode = t1.psncode ) where 1=1 ";
            for(int i=0;i<condition_list.size();i++){
                condition_obj con = condition_list.get(i);
                String type = con.getType();
                if(type.equals("sex")){
                    sql += " and sex in(:sexList)";
                    sexList = Arrays.asList(con.getConditions().split(","));
//                    Collections.replaceAll(sexList,"性别未知","");
                }
                if(type.equals("edu")){
                    sql += " and XL in(:eduList)";
                    eduList = Arrays.asList(con.getConditions().split(","));
//                    Collections.replaceAll(eduList,"学历未知","");
                }
                if(type.equals("zzmm")){
                    sql += " and ZZMM in(:zzmmList)";
                    zzmmList = Arrays.asList(con.getConditions().split(","));
//                    Collections.replaceAll(eduList,"学历未知","");
                }
                if(type.equals("doc")){
                    sql += " and p_name in(:docList)";
                    docList = Arrays.asList(con.getConditions().split(","));
                }
                if(type.equals("lan")){
                    List<String> lanList = Arrays.asList(con.getConditions().split(","));
                    sql += "and (";
                    for(int j=0;j<lanList.size();j++){
                        if(lanList.get(j).equals("lan0")){
                            sql += " major LIKE '%英语%' or";
                        }
                        if(lanList.get(j).equals("lan1")){
                            sql += " major LIKE '%西班牙语%' or";
                        }
                        if(lanList.get(j).equals("lan2")){
                            sql += " major LIKE '%俄语%' or";
                        }
                        if(lanList.get(j).equals("lan3")){
                            sql += " major LIKE '%法语%' or";
                        }
                        if(lanList.get(j).equals("lan4")){
                            sql += " major LIKE '%德语%' or";
                        }
                        if(lanList.get(j).equals("lan5")){
                            sql += " major LIKE '%日语%' or";
                        }
                    }
                    sql = sql.substring(0,sql.lastIndexOf(" or")+1);
                    sql += ")";
                }
                if(type.equals("age")){
//                    sql += " and age in(:ageList)";
                    List<String> ageList = Arrays.asList(con.getConditions().split(","));
                    sql += "and (";
                    for(int j=0;j<ageList.size();j++){
                        if(ageList.get(j).equals("age1")){
                            sql += " age < 25 or";
                        }
                        if(ageList.get(j).equals("age2")){
                            sql += " (age >= 25 and age < 35) or";
                        }
                        if(ageList.get(j).equals("age3")){
                            sql += "(age >= 35 and age < 45) or";
                        }
                        if(ageList.get(j).equals("age4")){
                            sql += " (age >= 45 and age < 55) or";
                        }
                        if(ageList.get(j).equals("age5")){
                            sql += " age >= 55 or";
                        }
                    }
                    sql = sql.substring(0,sql.lastIndexOf(" or")+1);
                    sql += ")";
                }
            }
            sql += " order by CORPCODE,PSNCODE ";
            Query query = session.createSQLQuery(sql);
            if(sexList.size() > 0){
                query.setParameterList("sexList", sexList);
            }
            if(eduList.size() > 0){
                query.setParameterList("eduList", eduList);
            }
            if(zzmmList.size() > 0){
                query.setParameterList("zzmmList", zzmmList);
            }
            if(docList.size() > 0){
                query.setParameterList("docList", docList);
            }
            List array = query.list();
            for (int i = 0; i < array.size(); i++) {
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setPSNNAME(checkIsNull(obj[1]));
                psndoc.setPSNCODE(checkIsNull(obj[2]));
                if(!res.contains(psndoc)){
                    res.add(psndoc);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return res;
    }

    public int conditionQueryListCount(List<condition_obj> condition_list){
        Session session = null;
        ArrayList<String> res = new ArrayList();
        List<String> sexList = new ArrayList<String>();
        List<String> eduList = new ArrayList<String>();
        List<String> zzmmList = new ArrayList<String>();
        List<String> docList = new ArrayList<String>();
        int count = 0;
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select distinct PSNCODE from(select t1.CORPNAME,t1.PSNNAME,t1.PSNCODE,to_char(sysdate,'yyyy') - to_char(to_date(t1.BIRTHDAY,'yyyy-MM-dd'),'yyyy') as age,t1.CORPCODE,t1.ZZMM,t1.SEX,t1.XL,t2.p_name,t3.major from oabi.zgfh_bi_psndoc2020 t1 left join oabi.zgfh_bi_title t2 on t1.psncode = t2.psncode left join oabi.zgfh_bi_edu t3 on t3.psncode = t1.psncode ) where 1=1 ";
            for(int i=0;i<condition_list.size();i++){
                condition_obj con = condition_list.get(i);
                String type = con.getType();
                if(type.equals("sex")){
                    sql += " and sex in(:sexList)";
                    sexList = Arrays.asList(con.getConditions().split(","));
//                    Collections.replaceAll(sexList,"性别未知","");
                }
                if(type.equals("edu")){
                    sql += " and XL in(:eduList)";
                    eduList = Arrays.asList(con.getConditions().split(","));
//                    Collections.replaceAll(eduList,"学历未知","");
                }
                if(type.equals("zzmm")){
                    sql += " and ZZMM in(:zzmmList)";
                    zzmmList = Arrays.asList(con.getConditions().split(","));
//                    Collections.replaceAll(eduList,"学历未知","");
                }
                if(type.equals("doc")){
                    sql += " and p_name in(:docList)";
                    docList = Arrays.asList(con.getConditions().split(","));
                }
                if(type.equals("lan")){
                    List<String> lanList = Arrays.asList(con.getConditions().split(","));
                    sql += "and (";
                    for(int j=0;j<lanList.size();j++){
                        if(lanList.get(j).equals("lan0")){
                            sql += " major LIKE '%英语%' or";
                        }
                        if(lanList.get(j).equals("lan1")){
                            sql += " major LIKE '%西班牙语%' or";
                        }
                        if(lanList.get(j).equals("lan2")){
                            sql += " major LIKE '%俄语%' or";
                        }
                        if(lanList.get(j).equals("lan3")){
                            sql += " major LIKE '%法语%' or";
                        }
                        if(lanList.get(j).equals("lan4")){
                            sql += " major LIKE '%德语%' or";
                        }
                        if(lanList.get(j).equals("lan5")){
                            sql += " major LIKE '%日语%' or";
                        }
                    }
                    sql = sql.substring(0,sql.lastIndexOf(" or")+1);
                    sql += ")";
                }
                if(type.equals("age")){
//                    sql += " and age in(:ageList)";
                    List<String> ageList = Arrays.asList(con.getConditions().split(","));
                    sql += "and (";
                    for(int j=0;j<ageList.size();j++){
                        if(ageList.get(j).equals("age1")){
                            sql += " age < 25 or";
                        }
                        if(ageList.get(j).equals("age2")){
                            sql += " (age >= 25 and age < 35) or";
                        }
                        if(ageList.get(j).equals("age3")){
                            sql += "(age >= 35 and age < 45) or";
                        }
                        if(ageList.get(j).equals("age4")){
                            sql += " (age >= 45 and age < 55) or";
                        }
                        if(ageList.get(j).equals("age5")){
                            sql += " age >= 55 or";
                        }
                    }
                    sql = sql.substring(0,sql.lastIndexOf(" or")+1);
                    sql += ")";
                }
            }
//            sql += " order by CORPCODE,PSNCODE ";
            Query query = session.createSQLQuery(sql);
            if(sexList.size() > 0){
                query.setParameterList("sexList", sexList);
            }
            if(eduList.size() > 0){
                query.setParameterList("eduList", eduList);
            }
            if(zzmmList.size() > 0){
                query.setParameterList("zzmmList", zzmmList);
            }
            if(docList.size() > 0){
                query.setParameterList("docList", docList);
            }
//            List array = query.list();
            count = query.list().size();
//            Set<String> set = new HashSet<String>(array);
//            count = set.size();
//            count = Integer.parseInt(array.get(0).toString());
//            count = array.size();
//            for (int i = 0; i < array.size(); i++) {
//                Object[] obj = (Object[]) array.get(i);
////                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
//////                psndoc.setCORPNAME(checkIsNull(obj[0]));
//////                psndoc.setPSNNAME(checkIsNull(obj[1]));
////                psndoc.setPSNCODE(checkIsNull(obj[2]));
//                if(!res.contains(checkIsNull(obj[2]))){
//                    res.add(checkIsNull(obj[2]));
//                }
//            }
//            count = res.size();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return count;
    }

 }