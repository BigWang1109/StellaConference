package com.wxx.conference.test;

import com.wxx.conference.common.ImageConstants;
import com.wxx.conference.util.HexConverter;
import com.wxx.conference.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;

/**
 * Created by thinkpad on 2021-2-3.
 */
public class testSQLServer {
    public static final String separator = File.separator;
    private static final Logger logger = LoggerFactory.getLogger(testSQLServer.class);
    private static final String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // private static final String POSTGRESQL_DRIVER="org.postgresql.Driver";
    private static final String SQLSERVER_URL = "jdbc:sqlserver://10.0.59.10:1433;DatabaseName=mszq";
//    jdbc:microsoft.sqlserver://10.0.59.10:1433;DatabaseName=hrtest

    // private static final String POSTGRESQL_URL=
    // "jdbc:postgresql://localhost:5432/testdb";

    public static void main(String[] args) {
        try {
            Class.forName(SQLSERVER_DRIVER).newInstance();
            String url = SQLSERVER_URL;
            Connection con = DriverManager.getConnection(url, "sa", "sa");
            Statement st = con.createStatement();
            String sql = "select photo from ePhoto where eid=(select eid from eemployee where badge='"+110788+"')";
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    convertPhotot(rs.getString(i),"110788");
                }
                System.out.println();
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertPhotot(String hexString,String code){
        if (hexString == null) {
            return "";
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
            HexConverter.hex2File(photoString, fileWithExtension);

            //update photo file name with extension
            logger.info("Created photo file: " + fileWithExtension);
            return code + extension;

        }
        logger.error("Can't create photo file with code " + code);
        return ImageConstants.PNG_UNKNOWN;
    }
}
