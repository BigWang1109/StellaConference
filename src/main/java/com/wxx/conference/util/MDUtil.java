package com.wxx.conference.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by thinkpad on 2020-5-11.
 */
public class MDUtil {
    public static String md5(String plainText){
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainText.getBytes());
            return new BigInteger(1,md5.digest()).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
