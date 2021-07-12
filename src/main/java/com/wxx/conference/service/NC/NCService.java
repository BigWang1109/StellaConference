package com.wxx.conference.service.NC;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxx.conference.common.Constants;
import com.wxx.conference.common.Pagination;
import com.wxx.conference.common.QueryParams;
import com.wxx.conference.model.common.AuthApp;
import com.wxx.conference.util.*;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by thinkpad on 2020-4-30.
 */
@Service
public class NCService {

//    @Autowired
//    private JedisUtil JedisUtil;
    private static final Logger logger = LoggerFactory.getLogger(NCService.class);
//    private String requestIp;

    public boolean customerExists(String phoneNumber){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        int count = 0;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("开始尝试连接NC数据库！");
//            String url = "jdbc:oracle:" + "thin:@10.0.125.200:1521:orcl";
            String url = "jdbc:oracle:thin:@"+PropertiesUtil.getProperty("NCURL");
//            String user = "ps_wh";
            String user = PropertiesUtil.getProperty("NCUser");
//            String password = "1";
            String password = PropertiesUtil.getProperty("NCPass");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("NC连接成功");
            String sql = "select count(*) from fhjszs.view_ps_customer_wh where vpreferredtel like ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, "%"+phoneNumber+"%");
            result = pre.executeQuery();
            while(result.next()){
                count = result.getInt(1);
                System.out.println(count);
                logger.info("查询手机号"+phoneNumber+",查询结果为："+(count == 0?"不存在":"已存在"));
            }

        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
        return count == 0?false:true;
    }
    //根据手机号判断客户是否存在，如果不存在返回0否则返回1，支持多个、前三+后四的模糊匹配
    public int customerExistsNew(String checkValue,String checkType){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        boolean flag = false;
        try{
            conn = GetDBConnUtil.getOracleDBConn(conn,PropertiesUtil.getProperty("NCURL"),PropertiesUtil.getProperty("NCUser"),PropertiesUtil.getProperty("NCPass"));
            String []tels = checkValue.split(",");
            here:
            if(checkType.equals("tel_match")){
                for(int i=0;i<tels.length;i++){
                    String sql = "select count(*) from fhjszs.view_ps_customer_wh where vpreferredtel like ?";
                    pre = conn.prepareStatement(sql);
                    pre.setString(1, "%"+tels[i]+"%");
                    result = pre.executeQuery();
                    while(result.next()){
                        int count = result.getInt(1);
                        flag = (count == 0?false:true);
//                        System.out.println(count);
                        logger.info("判客查询(精确匹配)：查询手机号"+tels[i]+",查询结果为："+(count == 0?"不存在":"已存在"));
                        if(flag) break here;
                    }
                }
            }else if(checkType.equals("tel")){
                for(int i=0;i<tels.length;i++){
//                    String sql = "select count(*) from nc57_0109.view_ps_customer_wh where vpreferredtel like ?";
                    String sql = "select count(*) from fhjszs.view_ps_customer_wh where REGEXP_LIKE(vpreferredtel,?)";
                    pre = conn.prepareStatement(sql);
                    String preTel = tels[i].substring(0,3);
                    String sufTel = tels[i].substring(7,tels[i].length());
                    String reg = preTel+"[[:digit:]]{4}"+sufTel;
//                    System.out.println(reg);
                    pre.setString(1, reg);
                    result = pre.executeQuery();
                    while(result.next()){
                        int count = result.getInt(1);
                        flag = (count == 0?false:true);
//                        System.out.println(count);
                        logger.info("判客查询(模糊匹配)：查询手机号"+tels[i]+",查询结果为："+(count == 0?"不存在":"已存在"));
                        if(flag) break here;
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return 2;
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
        return (flag?1:0);
    }
    //根据手机号判断客户最近跟进日期，如果30天内有跟踪记录返回0否则返回1，支持多个、前三+后四的模糊匹配
    public int customerFollow(String checkValue,String checkType){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        boolean flag = false;
        try{
            conn = GetDBConnUtil.getOracleDBConn(conn,PropertiesUtil.getProperty("NCURL"),PropertiesUtil.getProperty("NCUser"),PropertiesUtil.getProperty("NCPass"));
            String []tels = checkValue.split(",");
            here:
            if(checkType.equals("tel_match")){
                for(int i=0;i<tels.length;i++){
                    String sql = "select trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd') as res,contactdate from fhjszs.view_ps_customer_wh where vpreferredtel like ?";
                    pre = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    pre.setString(1, "%"+tels[i]+"%");
                    result = pre.executeQuery();
                    if(result.next()){
                        result.previous();
                        while(result.next()){
                            Object obj = result.getObject(1);
                            Object obj2 = result.getObject(2);
                            if(obj == null || "" == obj){
                                continue;
                            }else{
                                int num = Integer.parseInt(obj.toString());
                                if(num >=0 && num <= 30){
                                    flag = true;
                                }
                            }
//                        flag = (count == 0?false:true);
//                        System.out.println(count);
                            logger.info("客户跟进状态查询(精确匹配)：查询手机号"+tels[i]+",跟进日期为:"+obj2+",未跟进天数为："+obj);
                            if(flag) break here;
                        }
                    }else{
                        logger.info("客户跟进状态查询(精确匹配)：查询手机号" + tels[i] + ",该手机号不存在");
                    }
                }
            }else if(checkType.equals("tel")){
                for(int i=0;i<tels.length;i++){
//                    String sql = "select count(*) from nc57_0109.view_ps_customer_wh where vpreferredtel like ?";
                    String sql = "select trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd') as res,contactdate from fhjszs.view_ps_customer_wh where REGEXP_LIKE(vpreferredtel,?)";
                    pre = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    String preTel = tels[i].substring(0,3);
                    String sufTel = tels[i].substring(7,tels[i].length());
                    String reg = preTel+"[[:digit:]]{4}"+sufTel;
//                    System.out.println(reg);
                    pre.setString(1, reg);
                    result = pre.executeQuery();
                    if(result.next()){
                        result.previous();
                        while(result.next()){
                            Object obj = result.getObject(1);
                            Object obj2 = result.getObject(2);
                            if(obj == null || "" == obj){
                                continue;
                            }else{
                                int num = Integer.parseInt(obj.toString());
                                if(num >=0 && num <= 30){
                                    flag = true;
                                }
                            }
//                        flag = (count == 0?false:true);
//                        System.out.println(count);
                            logger.info("客户跟进状态查询(模糊匹配)：查询手机号"+tels[i]+",跟进日期为:"+obj2+",未跟进天数为："+obj);
                            if(flag) break here;
                        }
                    }else{
                        logger.info("客户跟进状态查询(模糊匹配)：查询手机号"+preTel+"****"+sufTel+",该手机号不存在");
                    }
                }
            }

        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return 2;
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
//        return "{\"content\":{\"IsOldCst\":"+(flag?0:1)+"}";
        return (flag?0:1);
    }
    /**
     * 根据手机号判断客户是否允许被推荐，支持多个、前三+后四的模糊匹配
     * ------------单号码判客逻辑-------------
     * 如果手机号不存在则是新客户允许被推荐返回0
     * 如果手机号存在并且30天内无跟踪记录允许被推荐返回0
     * 如果手机号存在并且30天内有跟踪记录不允许被推荐返回1
     * ------------多号码判断逻辑------------
     * 多个手机号若存在不符合推荐条件的不允许被推荐
     * ------------异常信息-------------
     * 数据库连接异常返回2
     * */
    public int customerIsRecom(String checkValue,String checkType){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        boolean flag = true;
        try{
            conn = GetDBConnUtil.getOracleDBConn(conn,PropertiesUtil.getProperty("NCURL"),PropertiesUtil.getProperty("NCUser"),PropertiesUtil.getProperty("NCPass"));
            String []tels = checkValue.split(",");
            here:
            if(checkType.equals("tel_match")){
                for(int i=0;i<tels.length;i++){
                    String sql = "select trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd') as res,contactdate from fhjszs.view_ps_customer_wh where vpreferredtel like ? order by contactdate desc";
                    pre = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    pre.setString(1, "%"+tels[i]+"%");
                    result = pre.executeQuery();
                    if(result.next()){
                        result.previous();
                        while(result.next()){
                            Object obj = result.getObject(1);
                            Object obj2 = result.getObject(2);
                            if(obj == null || "" == obj){
                                continue;
                            }else{
                                int num = Integer.parseInt(obj.toString());
                                logger.info("客户跟进状态查询(精确匹配)：查询手机号"+tels[i]+",跟进日期为:"+obj2+",未跟进天数为："+obj);
                                if(num > 0 && num <= 30){
                                    flag = false;
                                }else{
                                    break ;
                                }
                                if(!flag) break here;
                            }
                        }
                    }else{
                        //新客户
                        logger.info("客户跟进状态查询(精确匹配)：查询手机号" + tels[i] + ",该手机号不存在");
                    }
                }
            }else if(checkType.equals("tel")){
                for(int i=0;i<tels.length;i++){
                    String sql = "select trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd') as res,contactdate from fhjszs.view_ps_customer_wh where REGEXP_LIKE(vpreferredtel,?) order by contactdate desc";
                    pre = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    String preTel = tels[i].substring(0,3);
                    String sufTel = tels[i].substring(7,tels[i].length());
                    String reg = preTel+"[[:digit:]]{4}"+sufTel;
                    pre.setString(1, reg);
                    result = pre.executeQuery();
                    if(result.next()){
                        result.previous();
                        while(result.next()){
                            Object obj = result.getObject(1);
                            Object obj2 = result.getObject(2);
                            if(obj == null || "" == obj){
                                continue;
                            }else{
                                int num = Integer.parseInt(obj.toString());
                                logger.info("客户跟进状态查询(模糊匹配)：查询手机号"+tels[i]+",跟进日期为:"+obj2+",未跟进天数为："+obj);
                                if(num > 0 && num <= 30){
                                    flag = false;
                                }else{
                                    break ;
                                }
                                if(!flag) break here;
                            }
                        }
                    }else{
                        //新客户
                        logger.info("客户跟进状态查询(模糊匹配)：查询手机号" + tels[i] + ",该手机号不存在");
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return 2;
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
        return (flag?0:1);
    }

    /**
     * 判断单个手机号是否允许被推荐，支持前三+后四的模糊匹配
     * 如果手机号不存在则是新客户允许被推荐返回0
     * 如果手机号存在并且30天内无跟踪记录允许被推荐返回0
     * 如果手机号存在并且30天内有跟踪记录不允许被推荐返回1
     * 数据库连接异常返回2
     * */
    public int checkSingleCustomerIsRecom(String tel,String checkType){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        boolean flag = false;
        int res = 1;
        try{
            conn = GetDBConnUtil.getOracleDBConn(conn,PropertiesUtil.getProperty("NCURL"),PropertiesUtil.getProperty("NCUser"),PropertiesUtil.getProperty("NCPass"));
            if(checkType.equals("tel_match")){
                String sql = "select trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd') as res,contactdate from fhjszs.view_ps_customer_wh where vpreferredtel like ? order by contactdate desc";
                pre = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pre.setString(1, "%" + tel + "%");
                result = pre.executeQuery();
                if (result.next()) {
                    result.previous();
                    while (result.next()) {
                        Object obj = result.getObject(1);
                        Object obj2 = result.getObject(2);
                        if (obj == null || "" == obj) {
                            continue;
                        } else {
                            int num = Integer.parseInt(obj.toString());
                            logger.info("客户跟进状态查询(精确匹配)：查询手机号" + tel + ",跟进日期为:" + obj2 + ",未跟进天数为：" + obj);
//                                if(num >=0 && num <= 30){
                            if (num > 30) {
                                res = 0;
                                break;
                            } else {
                                res = 1;
                            }

                        }
                    }
                } else {
                    //新客户
                    res = 0;
                    logger.info("客户跟进状态查询(精确匹配)：查询手机号" + tel + ",该手机号不存在");
                }
            }else if(checkType.equals("tel")){
//                    String sql = "select count(*) from nc57_0109.view_ps_customer_wh where vpreferredtel like ?";
                String sql = "select trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd') as res,contactdate from fhjszs.view_ps_customer_wh where REGEXP_LIKE(vpreferredtel,?) order by contactdate desc";
                pre = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String preTel = tel.substring(0, 3);
                String sufTel = tel.substring(7, tel.length());
                String reg = preTel + "[[:digit:]]{4}" + sufTel;
//                    System.out.println(reg);
                pre.setString(1, reg);
                result = pre.executeQuery();
                if (result.next()) {
                    result.previous();
                    while (result.next()) {
                        Object obj = result.getObject(1);
                        Object obj2 = result.getObject(2);
                        if (obj == null || "" == obj) {
                            continue;
                        } else {
                            int num = Integer.parseInt(obj.toString());
                            if (num >= 0 && num <= 30) {
                                flag = true;
                                res = 0;
                            }
                        }
                        logger.info("客户跟进状态查询(模糊匹配)：查询手机号" + tel + ",跟进日期为:" + obj2 + ",未跟进天数为：" + obj);
                        if (flag) break;
                    }
                } else {
                    res = 0;
                    logger.info("客户跟进状态查询(模糊匹配)：查询手机号" + preTel + "****" + sufTel + ",该手机号不存在");
                }
            }

        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return 2;
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
        return res;
    }
    //校验手机号是否包含特殊符号，长度是否是11位
    public boolean validTel(String checkValue,Map<String,Object> responseMap,Map<String,Object> contentMap,Map<String,Object> dataMap){
//        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        String regEx = "[ _`~!@#$%^&()\\-+=|{}':;'\\[\\].<>/?~！@#￥%……&（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        boolean flag = true;
        try{
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(checkValue);
            if(m.find()){
//            System.out.println(m.group(0));
                dataMap.put("errcode",2);
                dataMap.put("errmsg","电话号码中含有特殊符号"+m.group(0));
                dataMap.put("content","");
                responseMap.put("data", dataMap);
                logger.info("电话号码中含有特殊符号"+m.group(0));
                return false;
            }
            String []tels = checkValue.split(",");
            for(int i=0;i<tels.length;i++){
                if(tels[i].length() != 11){
                    dataMap.put("errcode",3);
                    dataMap.put("errmsg","电话号码"+tels[i]+"长度不合规");
                    dataMap.put("content","");
                    responseMap.put("data",dataMap);
                    flag = false;
                    logger.info("电话号码"+tels[i]+"长度不合规");
                    break;
                }
            }
        }catch(Exception e){
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
    public String getCorp(HttpServletRequest request){
        String corp = "";
        try{
            corp = request.getParameter("corp");
        }catch (Exception e){
            e.printStackTrace();
        }
        return corp;
    }
    //验证http请求中的token是否有效，token为md5加密的(appid+timestamp+appkey),为提高接口响应速度，其中appid、appkey从配置文件中读取。
    public boolean checkToken(JSONObject jo){
        testRedis();
        boolean flag = false;
        String appid = PropertiesUtil.getProperty("Myappid");
        String appKey = PropertiesUtil.getProperty("MYappKey");
        try{
            String header = jo.get("header").toString();
            JSONObject jheader = new JSONObject(header);
//            String appid = jheader.get("appid").toString();
//            String appKey = getAppKey(appid);
            String timestamp = jheader.get("timestamp").toString();
            String myToken = jheader.get("token").toString();
            String token = MDUtil.md5(appid + timestamp + appKey);
            flag = myToken.equals(token);
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
        }
        return flag;
    }
    //根据http请求中的appid从数据库中读取appkey，速度慢，影响接口响应
    public String getAppKey(String appId){
        Session session = null;
        String appKey = "";
        try{
            session = HibernateUtil.getSession();
            String hql = "from AuthApp where appid =:appId";
            Query query = session.createQuery(hql);
            query.setString("appId",appId);
            Object obj = query.list().get(0);

            appKey = ((AuthApp)query.list().get(0)).getAppkey();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return appKey;
    }

    public Map<String,Object> formResponseMap(String valueStr,Map<String,Object> responseMap,int type){
        Map<String,Object> contentMap = new HashMap<String, Object>();
        Map<String,Object> dataMap = new HashMap<String, Object>();
        try{
            JSONObject jo = new JSONObject(valueStr);
            if(checkToken(jo)){
                String data = jo.get("data").toString();
                JSONObject jdata = new JSONObject(data);
                String checkType = jdata.get("checkType").toString();
                String checkValue = jdata.get("checkValue").toString();
                if(validTel(checkValue,responseMap,contentMap,dataMap)){
                    //type=0 判客接口，type=1客户跟踪接口
                    if(type == 0){
                        int res = customerExistsNew(checkValue, checkType);
                        if(res != 2){
                            contentMap.put("IsOldCst",res);
                            dataMap.put("errcode",0);
                            dataMap.put("errmsg","");
                            dataMap.put("content",contentMap);
                            responseMap.put("data",dataMap);
                        }else{
                            dataMap.put("errcode",4);
                            dataMap.put("errmsg","NC数据库异常");
                            dataMap.put("content","");
                            responseMap.put("data",dataMap);
                            logger.info("NC数据库异常");
                        }

                    } else if (type == 1){
                        int res = customerFollow(checkValue, checkType);
                        if(res != 2){
                            contentMap.put("IsFollowed",res);
                            dataMap.put("errcode",0);
                            dataMap.put("errmsg","");
                            dataMap.put("content",contentMap);
                            responseMap.put("data",dataMap);
                        }else{
                            dataMap.put("errcode",4);
                            dataMap.put("errmsg","NC数据库异常");
                            dataMap.put("content","");
                            responseMap.put("data",dataMap);
                            logger.info("NC数据库异常");
                        }
                    }

                }
            }else{
                dataMap.put("errcode",1);
                dataMap.put("errmsg","token验证失败");
                dataMap.put("content","");
                responseMap.put("data",dataMap);
                logger.info("token验证失败");
            }
        }catch (Exception e){
            dataMap.put("errcode",5);
            dataMap.put("errmsg","JSON格式有误");
            dataMap.put("content","");
            responseMap.put("data",dataMap);
            logger.error(e.toString());
            e.printStackTrace();
        }
        return responseMap;
    }
    public Map<String,Object> formResponseMapNew(String valueStr,Map<String,Object> responseMap,int type){
        Map<String,Object> contentMap = new HashMap<String, Object>();
        Map<String,Object> dataMap = new HashMap<String, Object>();
        try{
            JSONObject jo = new JSONObject(valueStr);
            if(checkToken(jo)){
                String data = jo.get("data").toString();
                JSONObject jdata = new JSONObject(data);
                String checkType = jdata.get("checkType").toString();
                String checkValue = jdata.get("checkValue").toString();
                if(validTel(checkValue,responseMap,contentMap,dataMap)){
//                    int oldRes = customerExistsNew(checkValue, checkType);
                    int IsRecom = customerIsRecom(checkValue, checkType);
                    //是新客户或者是老客户且一个月内无跟进(IsRecom=0)可以被推介
                    if(IsRecom == 0){
                        contentMap.put("IsRecom",0);
                        dataMap.put("errcode",0);
                        dataMap.put("errmsg","");
                        dataMap.put("content",contentMap);
                        responseMap.put("data",dataMap);
                    //返回2说明数据库异常
                    }else if(IsRecom == 2){
                        dataMap.put("errcode",4);
                        dataMap.put("errmsg","NC数据库异常");
                        dataMap.put("content","");
                        responseMap.put("data",dataMap);
                        logger.info("NC数据库异常");
                    //是老客户并且一个月内有跟进不可以被推介
                    }else if(IsRecom == 1){
                        contentMap.put("IsRecom",1);
                        dataMap.put("errcode",0);
                        dataMap.put("errmsg","");
                        dataMap.put("content",contentMap);
                        responseMap.put("data",dataMap);
                    }

                }
            }else{
                dataMap.put("errcode",1);
                dataMap.put("errmsg","token验证失败");
                dataMap.put("content","");
                responseMap.put("data",dataMap);
                logger.info("token验证失败");
            }
        }catch (Exception e){
            dataMap.put("errcode",5);
            dataMap.put("errmsg","JSON格式有误");
            dataMap.put("content","");
            responseMap.put("data",dataMap);
            logger.error(e.toString());
            e.printStackTrace();
        }
        return responseMap;
    }

    public void testRedis(){
//        Jedis jedis=JedisUtil.getJedis();
//        String str = jedis.get("bill");
//        System.out.println(str);

    }

    public int checkRedisOldCus(String checkValue){
//        Jedis jedis=JedisUtil.getJedis();
//        String []tels = checkValue.split(",");
//        for(int i=0;i<tels.length;i++){
//            String res = jedis.get(tels[i]);
//            if(res == null){
//
//            }
//        }
        return 0;
    }
    //根据手机号查询客户跟进信息，支持多个手机号、前三后四模糊查询
    public ArrayList searchCustomer(String checkValue,String checkType,String requestIp,String corp){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        ArrayList list = new ArrayList();
        try{
            conn = GetDBConnUtil.getOracleDBConn(conn,PropertiesUtil.getProperty("NCURL"),PropertiesUtil.getProperty("NCUser"),PropertiesUtil.getProperty("NCPass"));
            String []tels = checkValue.split(",");
            here:
            if(checkType.equals("tel_match")){
                for(int i=0;i<tels.length;i++){
//                    String sql = "select * from fhjszs.view_ps_customer_wh2020 where vpreferredtel like ? order by contactdate desc";
                    String sql = "select vpreferredtel,vcname,vcode,vname,contactdate,trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd'),fcustype from fhjszs.view_ps_customer_wh2020 where corp = ? and vpreferredtel like ? order by contactdate desc";
                    pre = conn.prepareStatement(sql);
                    pre.setString(1, corp);
                    pre.setString(2, "%"+tels[i]+"%");
                    result = pre.executeQuery();
                    while(result.next()){
                        Map map = new HashMap();
//                        map.put("vpreferredtel",encryTel(result.getObject(1)));
                        map.put("vpreferredtel",result.getObject(1));
                        map.put("vcname",result.getObject(2));
                        map.put("vcode",result.getObject(3));
                        map.put("vname",result.getObject(4));
                        map.put("contactdate", result.getObject(5));
                        map.put("fcustype", result.getObject(7));
                        Object obj = result.getObject(6);
                        boolean flag = false;
                        if(obj == null || "" == obj){
                            continue;
                        }else{
                            int num = Integer.parseInt(obj.toString());
                            if(num >=0 && num <= 30){
                                flag = true;
                            }
                        }
                        map.put("isFollowed",flag);
                        list.add(map);
                    }
                }
                logger.info("IP:"+requestIp+"执行客户信息查询(精确匹配)：查询手机号" + checkValue + "");
            }else if(checkType.equals("tel")){
                for(int i=0;i<tels.length;i++){
//                    String sql = "select count(*) from nc57_0109.view_ps_customer_wh where vpreferredtel like ?";
//                    String sql = "select * from fhjszs.view_ps_customer_wh2020 where REGEXP_LIKE(vpreferredtel,?) order by contactdate desc";
                    String sql = "select vpreferredtel,vcname,vcode,vname,contactdate,trunc(sysdate)-to_date(contactdate,'yyyy-MM-dd'),fcustype from fhjszs.view_ps_customer_wh2020 where corp = ? and  REGEXP_LIKE(vpreferredtel,?) order by contactdate desc";
                    pre = conn.prepareStatement(sql);
                    String preTel = tels[i].substring(0,3);
                    String sufTel = tels[i].substring(7,tels[i].length());
                    String reg = preTel+"[[:digit:]]{4}"+sufTel;
                    pre.setString(1, corp);
                    pre.setString(2, reg);
                    result = pre.executeQuery();
                    while(result.next()){
                        Map map = new HashMap();
//                        map.put("vpreferredtel",encryTel(result.getObject(1)));
                        map.put("vpreferredtel",result.getObject(1));
                        map.put("vcname",result.getObject(2));
                        map.put("vcode",result.getObject(3));
                        map.put("vname",result.getObject(4));
                        map.put("contactdate", result.getObject(5));
                        map.put("fcustype", result.getObject(7));
                        Object obj = result.getObject(6);
                        boolean flag = false;
                        if(obj == null || "" == obj){
                            continue;
                        }else{
                            int num = Integer.parseInt(obj.toString());
                            if(num >=0 && num <= 30){
                                flag = true;
                            }
                        }
                        map.put("isFollowed",flag);
                        list.add(map);
                    }
                }
                logger.info("IP:"+requestIp+"执行客户信息查询(模糊匹配)：查询手机号"+checkValue+"");
            }
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
//            return 2;
        }finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (conn != null)
                    conn.close();
                System.out.println("NC数据库连接已关闭！");
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
        return list;
    }
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
    public String encryTel(Object obj){
        String originalTel = obj.toString();
        StringBuffer sb = new StringBuffer();
        if(originalTel.length() >= 11){
            sb.append(originalTel.substring(0,3));
            sb.append("****");
            sb.append(originalTel.substring(7,originalTel.length()));
        }
        return sb.toString();
    }
    public static Map<String,Object> loadCustomerInfo(Pagination pagination,String requestIp,String corp){
        Map<String, Object> map = new Hashtable<String, Object>(3);
        List list = new ArrayList();
        int totalCount = 0;
        Session session = null;
        StringBuffer searchTerm = new StringBuffer();
        try{
            session = HibernateNCDBUtil.getSession();

            String hql_count = "select count(*) from fhjszs.view_ps_customer_afterrecord where corp = '"+corp+"' ";
            String hql_select = "select vpreferredtel,vcname,vcode,vname,contactdate,fcustype,dmakedate,dproceedingdate,fproceedingtype,vproceedingdesc from fhjszs.view_ps_customer_afterrecord where corp = '"+corp+"' ";

//            if(pagination.getQuerys().size() == 0){
//                hql_select += " and vpreferredtel =:vpreferredtel ";
//                hql_count += " and vpreferredtel =:vpreferredtel ";
//            }
            StringBuffer vnameString = new StringBuffer("");
            StringBuffer fcustypeString = new StringBuffer("");
            StringBuffer fproceedingtypeString = new StringBuffer("");
            Boolean vnameFlag = true;
            Boolean fcustypeFlag = true;
            Boolean fproceedingtypeFlag = true;
            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("vpreferredtel") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    hql_select += " and vpreferredtel like:vpreferredtel ";
                    hql_count += " and vpreferredtel like:vpreferredtel ";
                }
                if(query.getName().equals("vname") && !StringUtils.isEmpty(query.getValue().toString()))
                {
//                    hql_select += " and vname =:vname ";
//                    hql_count += " and vname =:vname ";
//                    vnameString.append("vname =:vname");
                    if(vnameFlag){
                        hql_select += " and vname in(:vname) ";
                        hql_count += " and vname in(:vname) ";
                        vnameFlag = false;
                    }
                }
                if(query.getName().equals("fcustype") && !StringUtils.isEmpty(query.getValue().toString()))
                {
//                    hql_select += " and fcustype =:fcustype ";
//                    hql_count += " and fcustype =:fcustype ";
                    if(fcustypeFlag){
                        hql_select += " and fcustype in(:fcustype) ";
                        hql_count += " and fcustype in(:fcustype) ";
                        vnameFlag = false;
                    }
                }
                if(query.getName().equals("fproceedingtype") && !StringUtils.isEmpty(query.getValue().toString()))
                {
//                    hql_select += " and fproceedingtype =:fproceedingtype ";
//                    hql_count += " and fproceedingtype =:fproceedingtype ";
                    if(fproceedingtypeFlag){
                        hql_select += " and fproceedingtype in(:fproceedingtype) ";
                        hql_count += " and fproceedingtype in(:fproceedingtype) ";
                        vnameFlag = false;
                    }
                }
            }
            hql_select += "order by vname,dproceedingdate asc";

            SQLQuery queryCount = session.createSQLQuery(hql_count);
            SQLQuery querySelect = session.createSQLQuery(hql_select);

            for(QueryParams query : pagination.getQuerys()){
                if(query.getName().equals("vpreferredtel") && !StringUtils.isEmpty(query.getValue().toString()))
                {
                    queryCount.setString("vpreferredtel", "%"+query.getValue().toString()+"%");
                    querySelect.setString("vpreferredtel", "%"+query.getValue().toString()+"%");
                    searchTerm.append("电话："+query.getValue().toString()+";");
                }
                if(query.getName().equals("vname") && !StringUtils.isEmpty(query.getValue().toString()))
                {
//                    queryCount.setString("vname", query.getValue().toString());
//                    querySelect.setString("vname", query.getValue().toString());
                    searchTerm.append("项目名称：" + query.getValue().toString()+";");
                    vnameString.append(query.getValue().toString()+",");
                }
                if(query.getName().equals("fcustype") && !StringUtils.isEmpty(query.getValue().toString()))
                {
//                    queryCount.setString("fcustype", query.getValue().toString());
//                    querySelect.setString("fcustype", query.getValue().toString());
                    fcustypeString.append(query.getValue().toString()+",");
                    searchTerm.append("客户类型：" + query.getValue().toString()+";");
                }
                if(query.getName().equals("fproceedingtype") && !StringUtils.isEmpty(query.getValue().toString()))
                {
//                    queryCount.setString("fproceedingtype", query.getValue().toString());
//                    querySelect.setString("fproceedingtype", query.getValue().toString());
                    fproceedingtypeString.append(query.getValue().toString()+",");
                    searchTerm.append("事件类型：" + query.getValue().toString()+";");
                }
            }
//            if(pagination.getQuerys().size() == 0){
//                queryCount.setString("vpreferredtel", "");
//                querySelect.setString("vpreferredtel", "");
//            }

            if(vnameString.length()>0){
                String[] str = vnameString.toString().substring(0,vnameString.length()-1).split(",");
                querySelect.setParameterList("vname", str);
                queryCount.setParameterList("vname", str);
            }
            if(fcustypeString.length()>0){
                String[] str = fcustypeString.toString().substring(0,fcustypeString.length()-1).split(",");
                querySelect.setParameterList("fcustype", str);
                queryCount.setParameterList("fcustype", str);
            }
            if(fproceedingtypeString.length()>0){
                String[] str = fproceedingtypeString.toString().substring(0,fproceedingtypeString.length()-1).split(",");
                querySelect.setParameterList("fproceedingtype", str);
                queryCount.setParameterList("fproceedingtype", str);
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
                mapt.put("vpreferredtel",obj[0]);
                mapt.put("vcname",obj[1]);
                mapt.put("vcode",obj[2]);
                mapt.put("vname",obj[3]);
                mapt.put("contactdate", obj[4]);
                mapt.put("fcustype", obj[5]);
                mapt.put("dmakedate",obj[6]);
                mapt.put("dproceedingdate",obj[7]);
                mapt.put("fproceedingtype",obj[8]);
                mapt.put("vproceedingdesc",obj[9]);
                list.add(mapt);
            }
            logger.info("IP:"+requestIp+"-执行客户跟进信息查询，查询条件为：" + searchTerm + "");

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }
        map.put("total",totalCount);
        map.put("rows", list);
        map.put("QueryKey", pagination.getQueryKey());

        return map;
    }
    //初始化客户跟进信息查询下拉框
    public static List initializeOption(String type,String corp){
        Session session = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try{
            session = HibernateNCDBUtil.getSession();
            String sql = "select "+type+" from fhjszs.view_ps_customer_afterrecord f where corp = '"+corp+"' ";
//            if(vpreferredtel == null || "".equals(vpreferredtel)){
//                sql += " group by "+type+" order by "+type;
//            }else{
//                sql += " and f.vpreferredtel like:vpreferredtel group by "+type+" order by"+type;
//            }
            sql += " group by "+type+" order by "+type;
            SQLQuery query = session.createSQLQuery(sql);
//            if(vpreferredtel != null || !"".equals(vpreferredtel)){
//                query.setString("vpreferredtel","%"+vpreferredtel+"%");
//            }
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Map map = new HashMap();
                map.put(type,array.get(i));
                list.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session != null) session.close();
        }
        return list;
    }
    //初始化客户跟进信息查询下拉框
    public static List initializeOptionNew(String type,String vpreferredtel,String corp){
        Session session = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try{
            session = HibernateNCDBUtil.getSession();
            String sql = "select "+type+" from fhjszs.view_ps_customer_afterrecord f where corp = '"+corp+"'";
            if(vpreferredtel == null || "".equals(vpreferredtel)){
                sql += " group by "+type+" order by "+type;
            }else{
                sql += " and f.vpreferredtel like:vpreferredtel group by "+type+" order by "+type;
            }
//            sql += " group by "+type+" order by "+type;
            SQLQuery query = session.createSQLQuery(sql);
            if(vpreferredtel != null || !"".equals(vpreferredtel)){
                query.setString("vpreferredtel","%"+vpreferredtel+"%");
            }
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Map map = new HashMap();
                map.put(type,array.get(i));
                list.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session != null) session.close();
        }
        return list;
    }
}
