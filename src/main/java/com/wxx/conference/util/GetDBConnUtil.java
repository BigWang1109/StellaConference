package com.wxx.conference.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by thinkpad on 2020-5-14.
 */
public class GetDBConnUtil {

    public static Connection getOracleDBConn(Connection conn,String DBUrl,String user,String password){
//        Connection conn = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("开始尝试连接目标数据库！");
            String url = "jdbc:oracle:thin:@"+DBUrl;
//            String user = PropertiesUtil.getProperty("NCUser");
//            String password = PropertiesUtil.getProperty("NCPass");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("目标数据库连接成功");
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

}
