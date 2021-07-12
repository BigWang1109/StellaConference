package com.wxx.conference.util;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/22 0022.
 */
public class JsonUtils {
    /**
     * 将传入的json字符串转化为list
     * @param str_json json字符串
     * @param className 对应的类：Person.class
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String str_json, Class className){
        JSONArray jsonArray = JSONArray.fromObject(str_json);
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { DateTimeUtil.DATE_TIME_FORMART_ALL,DateTimeUtil.DATE_TIME_FORMART_HIGH,DateTimeUtil.DATE_TIME_FORMART_HIGH_M,DateTimeUtil.DATE_TIME_FORMART_HIGH_H,DateTimeUtil.DATE_TIME_FORMART_SHORT }));
        List<T> list = (List<T>) JSONArray.toCollection(jsonArray,className);
        return list;
    }
//    public static void main(String[] args){
//        String str ="[{\"examTypeProtocolopen\":\"1\",\"languageType\":\"2\",\"examPersonNum\":\"12\",\"protocolPersonNum\":\"8\",\"openPersonNum\":\"4\",\"invigilatorOne\":\"11\",\"invigilatorTwo\":\"22\",\"examDate\":\"2016-04-01\",\"examDateBegin\":\"2016-04-01\",\"examDateEnd\":\"2016-04-03\"},{\"examTypeProtocolopen\":\"1,2\",\"languageType\":\"1,2\",\"examPersonNum\":\"23\",\"protocolPersonNum\":\"14\",\"openPersonNum\":\"9\",\"invigilatorOne\":\"33\",\"invigilatorTwo\":\"44\",\"examDate\":\"2016-4-2\",\"examDateBegin\":\"2016-04-01\",\"examDateEnd\":\"2016-04-03\"},{\"examTypeProtocolopen\":\"2\",\"languageType\":\"1\",\"examPersonNum\":\"34\",\"protocolPersonNum\":\"21\",\"openPersonNum\":\"13\",\"invigilatorOne\":\"55\",\"invigilatorTwo\":\"66\",\"examDate\":\"2016-04-03\",\"examDateBegin\":\"2016-04-01\",\"examDateEnd\":\"2016-04-03\"}]";
//        String str1 = "[{\"examDate\":\"2016-04-02\"}]";
//        List<PEMSession> list = json2List(str,PEMSession.class);
//        for(int i=0;i<list.size();i++){
//           System.out.println(list.get(i).getExamDate());
//        }
//    }



}
