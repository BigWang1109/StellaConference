package com.wxx.conference.common;

import com.wxx.conference.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by wxx on 16-11-24.
 */
public class BaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * 根据主键获取一个对象
     *
     * @param c 对象类
     * @param id 主键
     * @return
     */
    //@SuppressWarnings("unchecked")
    public static Object get(Class c, String id) {
        Session session = null;
        Object object = null;
        try
        {
            session = HibernateUtil.getSession();
            object = session.get(c, id);
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        }
        finally {
            if(session!=null) {
                session.close();
            }
        }
        return object;
    }

    /**
     * 新增数据
     *
     * @param object 要新增的对象
     * @return
     */
    public static boolean add(Object object) {
        Session session = null;
        Transaction ts = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            session.save(object);
            ts.commit();
            result = true;
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            if(ts != null) {
                ts.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            if(session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param object 要更新的对象
     * @return
     */
    public static boolean update(Object object){
        Session session = null;
        Transaction ts = null;
        boolean result = false;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            session.update(object);
            ts.commit();
            result = true;
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            if(ts != null) {
                ts.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            if(session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * 删除对象
     *
     * @param object 要删除的对象
     * @return
     */
    public static boolean delete(Object object) {
        Session session = null;
        Transaction ts = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            session.delete(object);
            ts.commit();
            result = true;
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            if(ts != null) {
                ts.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            if(session!=null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * 根据主键删除数据
     *
     * @param c 对象类
     * @param id 要删除数据的主键
     * @return
     */
    public static boolean delete(Class c, String id) {
        Session session = null;
        Transaction ts = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            Object object = session.get(c, id);
            session.delete(object);
            ts.commit();
            result = true;
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            if(ts != null) {
                ts.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            if(session!=null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * 查询多条记录
     *
     * @param <T>
     * @param hql  hql语句
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @return
     */
    public static <T> List<T> queryByHql(String hql, HashMap<String, Object> param) {

        List<T> list=new ArrayList<T>();
        Session session = null;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);

            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                query.setParameter(key, param.get(key));
            }
            list=query.list();
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        }
        finally {
            if(session!=null)
            {
                session.close();
            }
        }
        return list;
    }

    /**
     * 查询多条记录
     *
     * @param sql  hql语句
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @return
     */
    public static List<Object[]> queryBySql(String sql, HashMap<String, Object> param) {

        List<Object[]> list = new ArrayList<Object[]>();
        Session session = null;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createSQLQuery(sql);

            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                query.setParameter(key, param.get(key));
            }
            list=query.list();
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        }
        finally {
            if(session!=null)
            {
                session.close();
            }
        }
        return list;
    }

    /**
     * 查询单条记录
     *
     * @param hql
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @return
     */
    public static Object queryOne(String hql, HashMap<String, Object> param)
    {
        Object object = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);

            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                query.setParameter(key, param.get(key));
            }
            object = query.uniqueResult();
        }
        catch (Exception ex){
            logger.error(ex.toString());
            ex.printStackTrace();
        }
        finally {
            if(session!=null) {
                session.close();
            }
        }
        return object;
    }

    /**
     * 查询对象的数量
     *
     * @param hql
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @return 返回对象个数
     */
    public static int getCountByHql(String hql, HashMap<String, Object> param) {
        int count = 0;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);

            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                query.setParameter(key, param.get(key));
            }
            count = Integer.valueOf(query.iterate().next().toString());
        } catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return count;
    }

    /**
     * 查询对象的数量
     *
     * @param sql
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @return 返回对象个数
     */
    public static int getCountBySql(String sql, HashMap<String, Object> param) {
        int count = 0;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createSQLQuery(sql);

            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                query.setParameter(key, param.get(key));
            }
            count = Integer.valueOf(query.iterate().next().toString());
        } catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return count;
    }


    /**
     * 执行sql语句
     *
     * @param sql
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @return 返回成功操作后的影响记录数量
     */
    public int executeUpdate(String sql, HashMap<String, Object> param) {
        int count = 0;

        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            Query query = session.createSQLQuery(sql);

            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                query.setParameter(key, param.get(key));
            }
            count = query.executeUpdate();
            ts.commit();
        }
        catch (Exception ex) {
            logger.error(ex.toString());
            if(ts != null) {
                ts.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            if(session != null) {
                session.close();
            }
        }
        return count;
    }

    /**
     * 分页查询
     *
     * @param hqlCount  计算总页数hql
     * @param hqlSelect 查询数据hql
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @param pagination 分页对象
     * @return
     */
    public static Map<String, Object> queryPageByHql(String hqlCount, String hqlSelect, HashMap<String, Object> param, Pagination pagination) {
        Map<String, Object> map = new Hashtable<String, Object>(3);
        List list = null;
        int totalCount = 0;
        Session session = null;
        try {
            session = HibernateUtil.getSession();

            Query queryCount = session.createQuery(hqlCount);
            Query querySelect = session.createQuery(hqlSelect);

            //为 where 条件中的参数赋值
            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                queryCount.setParameter(key, param.get(key));
                querySelect.setParameter(key, param.get(key));
            }

            //计算总页数
            totalCount = pagination.getClientPageCount();
            //避免每次都查询总页数
            if (totalCount == 0)
            {
                totalCount = ((Number) queryCount.iterate().next()).intValue();
            }

            //获取分页处理
            querySelect.setFirstResult((pagination.getPage() - 1) * Constants.PAGE_ROW_COUNT);
            querySelect.setMaxResults(pagination.getRows());
            list = querySelect.list();

        } catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

        map.put("total", totalCount);
        map.put("rows", list);
        map.put("QueryKey", pagination.getQueryKey());
        return map;
    }


    /**
     * 分页查询
     *
     * @param sqlCount  计算总页数hql
     * @param sqlSelect 查询数据hql
     * @param param 参数 String 为参数名 Object 为参数值, 参数值必须转换为对象属性匹配的数据类型
     * @param pagination 分页对象
     * @return 注意返回值的 rows 为List<Object[]>
     */
    public static Map<String, Object> queryPageBySql(String sqlCount, String sqlSelect, HashMap<String, Object> param, Pagination pagination) {
        Map<String, Object> map = new Hashtable<String, Object>(3);
        List list = null;
        int totalCount = 0;
        Session session = null;
        try {
            session = HibernateUtil.getSession();

            Query queryCount = session.createSQLQuery(sqlCount);
            Query querySelect = session.createSQLQuery(sqlSelect);

            //为 where 条件中的参数赋值
            Iterator iterator = param.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                queryCount.setParameter(key, param.get(key));
                querySelect.setParameter(key, param.get(key));
            }

            //计算总页数
            totalCount = pagination.getClientPageCount();
            //避免每次都查询总页数
            if (totalCount == 0)
            {
                totalCount = ((Number) queryCount.iterate().next()).intValue();
            }

            //获取分页处理
            querySelect.setFirstResult((pagination.getPage() - 1) * Constants.PAGE_ROW_COUNT);
            querySelect.setMaxResults(pagination.getRows());
            list = querySelect.list();

        } catch (Exception ex) {
            logger.error(ex.toString());
            ex.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

        map.put("total", totalCount);
        map.put("rows", list);
        map.put("QueryKey", pagination.getQueryKey());
        return map;
    }

}
