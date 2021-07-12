package com.wxx.conference.service.portal;

import com.wxx.conference.common.Constants;
import com.wxx.conference.common.Pagination;
import com.wxx.conference.common.QueryParams;
import com.wxx.conference.model.portal.OrgMember;
import com.wxx.conference.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by thinkpad on 2019-11-6.
 */
@Service
public class PortalService {
    public String adminLogin(String account,String password){
        Session session = null;
        String mess = "";
//        try{
//            session = HibernateUtil.getSession();
//            String hql = "from user where userId=:userId";
//            Query query = session.createQuery(hql);
//            user user = (user)query.list().get(0);
//            if(user.getType().equals("2")){
//                mess = "对不起，您不是管理员";
//            }else{
//                if(password!=null && !password.equals("") && password.equals(user.getPassword())){
//                    mess = "对不起，密码有误，请重新输入";
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            if(session!=null){
//                session.close();
//            }
//        }
        return mess;
    }
    public String Login(String account,String password){
        Session session = null;
        String mess = "";
        int isAdmin = 0;
        try{
            session = HibernateUtil.getSession();
            String hql = "from OrgMember where CODE=:account";
            Query query = session.createQuery(hql);
            query.setString("account", account);
            if(query.list().size() > 0){
                OrgMember member = (OrgMember)query.list().get(0);
                String name = member.getNAME();
                isAdmin = member.getIS_ADMIN();
                mess = name;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return mess;
    }

    public OrgMember getOrgMember(String account){
        Session session = null;
        OrgMember member = new OrgMember();
        try{
            session = HibernateUtil.getSession();
            String hql = "from OrgMember where CODE=:account";
            Query query = session.createQuery(hql);
            query.setString("account", account);
            if(query.list().size() > 0){
                member = (OrgMember)query.list().get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return member;
    }
    public int getMaxPathLengthFromOrgUnit(){
        Session session = null;
        int maxLength = 0;
        try{
            session = HibernateUtil.getSession();
            String sql = "select max(LENGTH(path)) from org_unit";
            SQLQuery query = session.createSQLQuery(sql);
            maxLength = Integer.parseInt(String.valueOf(query.list().get(0)));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return maxLength;
    }
    public List getOrgUnitList(){
        Session session = null;
        List orgUnitList = new ArrayList();
        try{
            session = HibernateUtil.getSession();
//            String sql = "select id,name,path from org_unit order by path limit 33";
            String sql = "select id,name,path,type from org_unit order by path";
            SQLQuery query = session.createSQLQuery(sql);
            orgUnitList = query.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return orgUnitList;
    }
    public List getOrgUnitListPlusSystemID(String system_id){
        Session session = null;
        List orgUnitList = new ArrayList();
        try{
            session = HibernateUtil.getSession();
//            String sql = "select id,name,path from org_unit order by path limit 33";
            String sql = "select t1.id,t1.name,t1.PATH,t1.type,if(ISNULL(t2.id),0,1) sys_flag FROM org_unit t1 LEFT JOIN (select * from unit_system_authorization t3 where t3.system_id = "+system_id+") t2 ON t1.ID = t2.unit_id order by path";
            SQLQuery query = session.createSQLQuery(sql);
            orgUnitList = query.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return orgUnitList;
    }

    public static Map<String,Object> loadOrgMemberByDepartmentID(Pagination pagination,String ORG_DEPARTMENT_ID){
        Map<String, Object> map = new Hashtable<String, Object>(3);
        List list = new ArrayList();
        int totalCount = 0;
        Session session = null;
        try{
            session = HibernateUtil.getSession();

            String hql_count = "select count(*) from org_member where ORG_DEPARTMENT_ID = '"+ORG_DEPARTMENT_ID+"' and  1=1 ";
            String hql_select = "select id,name,code from org_member where ORG_DEPARTMENT_ID = '"+ORG_DEPARTMENT_ID+"' and 1=1 ";

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("code") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    hql_select += " and code like:code ";
                    hql_count += " and code like:code ";
                }
                if(query.getName().equals("name") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    hql_select += " and name like:name ";
                    hql_count += " and name like:name ";
                }
            }

            hql_select += "order by code asc";

            SQLQuery queryCount = session.createSQLQuery(hql_count);
            SQLQuery querySelect = session.createSQLQuery(hql_select);

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("code") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    queryCount.setString("code", "%"+query.getValue().toString()+"%");
                    querySelect.setString("code", "%"+query.getValue().toString()+"%");
                }
                if(query.getName().equals("name") && !StringUtils.isEmpty(query.getValue().toString())&& !query.getValue().toString().equals("0"))
                {
                    queryCount.setString("name", "%"+query.getValue().toString()+"%");
                    querySelect.setString("name", "%"+query.getValue().toString()+"%");
                }
            }
            //计算总页数
            totalCount = pagination.getClientPageCount();
            totalCount = ((Number) queryCount.list().get(0)).intValue();
            //获取分页处理
            querySelect.setFirstResult((pagination.getPage() - 1) * Constants.PAGE_ROW_COUNT);
            querySelect.setMaxResults(pagination.getRows());
            List array = querySelect.list();
            for(int i=0;i<array.size();i++){
                Object []obj = (Object[])array.get(i);
                Map mapt = new HashMap();
                mapt.put("id",obj[0]);
                mapt.put("name",obj[1]);
                mapt.put("code",obj[2]);

                list.add(mapt);
            }


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session !=null){
                session.close();
            }
        }
        map.put("total",totalCount);
        map.put("rows", list);
        map.put("QueryKey", pagination.getQueryKey());

        return map;
    }
    public static Map<String,Object> loadOrgMemberByAccountId(Pagination pagination,String ORG_ACCOUNT_ID){
        Map<String, Object> map = new Hashtable<String, Object>(3);
        List list = new ArrayList();
        int totalCount = 0;
        Session session = null;
        try{
            session = HibernateUtil.getSession();

            String hql_count = "select count(*) from org_member where ORG_ACCOUNT_ID = '"+ORG_ACCOUNT_ID+"' and  1=1 ";
            String hql_select = "select id,name,code from org_member where ORG_ACCOUNT_ID = '"+ORG_ACCOUNT_ID+"' and 1=1 ";

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("code") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    hql_select += " and code like:code ";
                    hql_count += " and code like:code ";
                }
                if(query.getName().equals("name") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    hql_select += " and name like:name ";
                    hql_count += " and name like:name ";
                }
            }

            hql_select += "order by code asc";

            SQLQuery queryCount = session.createSQLQuery(hql_count);
            SQLQuery querySelect = session.createSQLQuery(hql_select);

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("code") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    queryCount.setString("code", "%"+query.getValue().toString()+"%");
                    querySelect.setString("code", "%"+query.getValue().toString()+"%");
                }
                if(query.getName().equals("name") && !StringUtils.isEmpty(query.getValue().toString())&& !query.getValue().toString().equals("0"))
                {
                    queryCount.setString("name", "%"+query.getValue().toString()+"%");
                    querySelect.setString("name", "%"+query.getValue().toString()+"%");
                }
            }
            //计算总页数
            totalCount = pagination.getClientPageCount();
            totalCount = ((Number) queryCount.list().get(0)).intValue();
            //获取分页处理
            querySelect.setFirstResult((pagination.getPage() - 1) * Constants.PAGE_ROW_COUNT);
            querySelect.setMaxResults(pagination.getRows());
            List array = querySelect.list();
            for(int i=0;i<array.size();i++){
                Object []obj = (Object[])array.get(i);
                Map mapt = new HashMap();
                mapt.put("id",obj[0]);
                mapt.put("name",obj[1]);
                mapt.put("code",obj[2]);

                list.add(mapt);
            }


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session !=null){
                session.close();
            }
        }
        map.put("total",totalCount);
        map.put("rows", list);
        map.put("QueryKey", pagination.getQueryKey());

        return map;
    }
    public static Map<String,Object> loadSystemListByPage(Pagination pagination){
        Map<String, Object> map = new Hashtable<String, Object>(3);
        List list = new ArrayList();
        int totalCount = 0;
        Session session = null;
        try{
            session = HibernateUtil.getSession();

            String hql_count = "select count(*) from portal_system where 1=1 ";
            String hql_select = "select id,name,url from portal_system where 1=1 ";

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("name") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    hql_select += " and name like:name ";
                    hql_count += " and name like:name ";
                }
            }

            hql_select += "order by name asc";

            SQLQuery queryCount = session.createSQLQuery(hql_count);
            SQLQuery querySelect = session.createSQLQuery(hql_select);

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("name") && !StringUtils.isEmpty(query.getValue().toString())&& !query.getValue().toString().equals("0"))
                {
                    queryCount.setString("name", "%"+query.getValue().toString()+"%");
                    querySelect.setString("name", "%"+query.getValue().toString()+"%");
                }
            }
            //计算总页数
            totalCount = pagination.getClientPageCount();
            totalCount = ((Number) queryCount.list().get(0)).intValue();
            //获取分页处理
            querySelect.setFirstResult((pagination.getPage() - 1) * Constants.PAGE_ROW_COUNT);
            querySelect.setMaxResults(pagination.getRows());
            List array = querySelect.list();
            for(int i=0;i<array.size();i++){
                Object []obj = (Object[])array.get(i);
                Map mapt = new HashMap();
                mapt.put("id",obj[0]);
                mapt.put("name",obj[1]);
                mapt.put("url",obj[2]);

                list.add(mapt);
            }


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session !=null){
                session.close();
            }
        }
        map.put("total",totalCount);
        map.put("rows", list);
        map.put("QueryKey", pagination.getQueryKey());

        return map;
    }
}
