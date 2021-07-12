package com.wxx.conference.test;

import java.util.List;

/**
 * Created by thinkpad on 2017-11-6.
 */
public class GenerateWords {
    public static int total = 0;

    public static void swap(String[] str, int i, int j)
    {
        String temp = new String();
        temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }
    public static void arrange (String[] str, int st, int len)
    {
        String word = "";
        if (st == len - 1)
        {
            for (int i = 0; i < len; i ++)
            {
//                System.out.print(checkword(str[i]));
//                System.out.print(str[i]);
                word +=str[i];
            }
            String res = checkword(word);
            if(res!=""){
                System.out.println(res);
            }else{
                System.out.println("...");
            }
            total++;
        } else
        {
            for (int i = st; i < len; i ++)
            {
                swap(str, st, i);
                arrange(str, st + 1, len);
                swap(str, st, i);
            }
        }

    }
    public static String checkword(String str){
        String result = "";
        try{
            result = GetHtmlContentUtils.getTranslateResult(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(result!=null && !result.equals("")){
            return str+":"+result;
        }
        return "";
    }
    public static void main(String args[]){
        String str[] = {"e","d","h","m","r","s","t","u"};
//        String str[] = {"a","c","g","n","i","t"};
//        String str[] = {"c","e","s","r","o","c"};
        arrange(str, 0, str.length);
        System.out.println(total);
    }
}
