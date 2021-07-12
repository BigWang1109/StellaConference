package com.wxx.conference.util;

import java.util.UUID;

/**
 * Created by thinkpad on 2016/11/30.
 */
public class UUIDUtil {
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
         return uuid.toString();
    }

}
