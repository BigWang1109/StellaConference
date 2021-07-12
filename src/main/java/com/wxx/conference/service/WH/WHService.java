package com.wxx.conference.service.WH;

import com.wxx.conference.model.HR.*;
import com.wxx.conference.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by thinkpad on 2021-4-8.
 */
@Service
public class WHService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WHService.class);
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
    public HashSet getPsncodeList(String searchVal,List roleList){
        HashSet<String> codeSet = new HashSet<String>();
        try{
            String []vals = searchVal.split(" ");
            for(String val : vals){
                if(val!=null && !"".equals(val)){
                    HashSet<String> tmpSet = new HashSet<String>();
                    String search_psndoc_sql = "select PSNCODE from oabi.zgfh_bi_psndoc2020 where corpcode in ('01020192','010217','010261','01021307','01020193') and ( CORPNAME like:PSNNAME or GW like:PSNNAME or " +
                            "ZW like:PSNNAME or SY like:PSNNAME or HY like:PSNNAME or MZ like:PSNNAME or SEX like:PSNNAME or XL like:PSNNAME or SCHOOL like:PSNNAME" +
                            " or BIRTHDAY like:PSNNAME or PSNCODE like:PSNNAME or PSNNAME like:PSNNAME or RSDATE like:PSNNAME or JG like:PSNNAME or HK like:PSNNAME or " +
                            "RDDATE like:PSNNAME or ZZMM like:PSNNAME or WORKDATE like:PSNNAME or ZY like:PSNNAME or PY like LOWER(:PSNNAME) or QPY like LOWER(:PSNNAME) or DEPTNAME like:PSNNAME or psnclassname like:PSNNAME) ";
                    String search_work_sql = "select PSNCODE from oabi.zgfh_bi_psndoc_work where corpcode in ('01020192','010217','010261','01021307','01020193') and (  BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or WORKCORP like:PSNNAME or WORKPOST like:PSNNAME)";
                    String search_edu_sql = "select PSNCODE from oabi.zgfh_bi_edu where corpcode in ('01020192','010217','010261','01021307','01020193') and (  BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or SCHOOL like:PSNNAME or MAJOR like:PSNNAME or EDUCATION like:PSNNAME or DEGREE like:PSNNAME)";
                    String search_kpi_sql = "select PSNCODE from oabi.zgfh_bi_kpi where corpcode in ('01020192','010217','010261','01021307','01020193') and (  KPI_YEAR like:PSNNAME or DEGREE like:PSNNAME or REWARD like:PSNNAME)";
                    String search_cont_sql = "select PSNCODE from oabi.zgfh_bi_cont where corpcode in ('01020192','010217','010261','01021307','01020193') and (  VCONTRACTNUM like:PSNNAME or TERMNAME like:PSNNAME or BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME)";
                    String search_train_sql = "select PSNCODE from oabi.zgfh_bi_train where  corpcode in ('01020192','010217','010261','01021307','01020193') and ( BEGINDATE like:PSNNAME or ENDDATE like:PSNNAME or VTRA_ACT like:PSNNAME or TRM_CLASS_NAMES like:PSNNAME)";
                    String search_title_sql = "select PSNCODE from oabi.zgfh_bi_title where corpcode in ('01020192','010217','010261','01021307','01020193') and (  P_NAME like:PSNNAME or DOCNAME like:PSNNAME or P_ORG like:PSNNAME or P_DATE like:PSNNAME)";
                    String search_ncfile_sql = "select PSNCODE from oabi.zgfh_bi_ncfile where corpcode in ('01020192','010217','010261','01021307','01020193') and (  CONTENT like:PSNNAME)";
//                    String corpSql = "";
//                    String tmpSql = "";
//                    if(roleList.size() > 0){
//                        if(!roleList.get(0).toString().equals("01")){
//                            for(int i=0;i<roleList.size();i++){
//                                String corpCode = roleList.get(i).toString();
//                                tmpSql += " corpcode like ('"+corpCode+"%') or";
//                            }
//                            tmpSql = tmpSql.substring(0,tmpSql.lastIndexOf("or"));
//                            corpSql += " and ("+tmpSql+")";
//                            search_psndoc_sql += corpSql;
//                            search_work_sql += corpSql;
//                            search_edu_sql += corpSql;
//                            search_kpi_sql += corpSql;
//                            search_cont_sql += corpSql;
//                            search_train_sql += corpSql;
//                            search_title_sql += corpSql;
//                            search_ncfile_sql += corpSql;
//                        }
//                    }
                    String []sqls = {search_psndoc_sql,search_work_sql,search_edu_sql,search_kpi_sql,search_cont_sql,search_train_sql,search_title_sql,search_ncfile_sql};
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
    public ArrayList searchByPsncodeByPage(HashSet codeSet,int index,List roleList){
        Session session = null;
        ArrayList<zgfh_bi_psndoc> list = new ArrayList<zgfh_bi_psndoc>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select CORPNAME,PSNNAME,PSNCODE from oabi.zgfh_bi_psndoc2020 where  PSNCODE in(:codes) ";
//            String corpSql = "";
//            String tmpSql = "";
//            if(roleList.size() > 0){
//                if(!roleList.get(0).toString().equals("01")){
//                    for(int i=0;i<roleList.size();i++){
//                        String corpCode = roleList.get(i).toString();
//                        tmpSql += " corpcode like '"+corpCode+"%' or";
//                    }
//                    tmpSql = tmpSql.substring(0,tmpSql.lastIndexOf("or"));
//                    corpSql += " and ("+tmpSql+")";
//                    sql += corpSql;
//                }
//            }
            sql += " order by CORPCODE,PSNCODE";
            Query query = session.createSQLQuery(sql);
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
                        Blob blob = (Blob) obj[1];
                        long size = blob.length();
                        data = blob.getBytes(1,(int)size);
                        String res = new sun.misc.BASE64Encoder().encode(data);
                        int start = res.indexOf("9j")-1;
                        res = res.substring(start,res.length());
                        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
                        data = dec.decodeBuffer(res);
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
                String content = PDFUtil.READPDF(rootPath + separator + PK_ATTACHMENT + ".pdf", PK_ATTACHMENT + ".pdf");
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
    public int getCount(String authCode){
        Session session = null;
        int count = 0;
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select count(*) from oabi.zgfh_bi_psndoc2020 where corpcode in ('01020192','010217','010261','01021307','01020193')";
//            List roleList = getRoleList(authCode);
//            String corpSql = "";
//            String tmpSql = "";
//            if(roleList.size() > 0){
//                if(!roleList.get(0).toString().equals("01")){
//                    for(int i=0;i<roleList.size();i++){
//                        String corpCode = roleList.get(i).toString();
//                        tmpSql += " corpcode like ('"+corpCode+"%') or";
//                    }
//                    tmpSql = tmpSql.substring(0,tmpSql.lastIndexOf("or"));
//                    corpSql += " and ("+tmpSql+")";
//                    sql += corpSql;
//                }
//            }
            Query query = session.createSQLQuery(sql);
            count = Integer.parseInt(query.list().get(0).toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null)session.close();
        }
        return count;
    }

    public boolean checkAccessRole(String authCode){
        Session session = null;
        boolean flag = false;
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select * from oabi.zgfh_bi_fhkg_user_role where psncode =:psncode";
            Query query = session.createSQLQuery(sql);
            query.setString("psncode",authCode);
            List list = query.list();
            if(list.size() > 0){
                flag = true;
            }else {
                flag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return  flag;
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

    public byte[] getPdf(){
        String path = "/soft/testPdf/default.pdf";
        byte []data =  FileUtil.getImgFromLocal(path);
        return data;
    }
    /**
     * 根据用户编码获取用户查询权限
     * */
    public ArrayList<String> getRoleList(String psncode){
        Session session = null;
        ArrayList roleList = new ArrayList();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select corpcode from oabi.zgfh_bi_fhkg_user_role where psncode =:psncode order by corpcode asc";
            Query query = session.createSQLQuery(sql);
            query.setString("psncode",psncode);
            List list = query.list();
            if(list.size() > 0){
                if(list.get(0).toString().equals("01")){
                    roleList.add(list.get(0).toString());
                }else{
                    for(int i=0;i<list.size();i++){
                        roleList.add(list.get(i).toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return  roleList;
    }
}
