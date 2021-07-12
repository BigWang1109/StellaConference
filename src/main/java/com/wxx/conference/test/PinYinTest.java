package com.wxx.conference.test;

import com.wxx.conference.util.HibernateHRDBUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by thinkpad on 2020-8-20.
 */
public class PinYinTest {
    public static void main(String[] args) {
//        System.out.println(ToFirstChar("王晓旭").toUpperCase()); //转为首字母大写
//        System.out.println(ToPinyin("王晓旭"));
        updatePY();
    }
    /**
     * 获取字符串拼音的第一个字母
     * @param chinese
     * @return
     */
    public static String ToFirstChar(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();  //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try{
            for (int i = 0; i < newChar.length; i++) {
                if (newChar[i] > 128) {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                }else{
                    pinyinStr += newChar[i];
                }
            }
        }catch(Exception e){
            return chinese;
        }
        return pinyinStr;
    }

    /**
     * 汉字转为拼音
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try{
            for (int i = 0; i < newChar.length; i++) {
                if (newChar[i] > 128) {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                }else{
                    pinyinStr += newChar[i];
                }
            }
        }catch(Exception e){
            return chinese;
        }

        return pinyinStr;
    }

    public static void updatePY() {
        Session session = null;
        Transaction ts = null;
        try {
            session = HibernateHRDBUtil.getSession();
            ts = session.beginTransaction();
            String hql = "select PSNCODE,PSNNAME from oabi.zgfh_bi_psndoc2020 where PY is null or QPY is null order by CORPCODE,PSNCODE";
            Query query = session.createSQLQuery(hql);
            List array = query.list();
            int res = 0;
            if (array.size() > 0) {
                for(int i=0;i<array.size();i++){
                    Object[] obj = (Object[]) array.get(i);
                    String psncode = obj[0].toString();
                    System.out.println(psncode);
                    String py = ToFirstChar(obj[1].toString());
                    String qpy = ToPinyin(obj[1].toString());
                    String updateSql = "update oabi.zgfh_bi_psndoc2020 set PY=:PY,QPY=:QPY where PSNCODE=:PSNCODE";
                    Query updateQuery = session.createSQLQuery(updateSql);
                    updateQuery.setString("PY",py);
                    updateQuery.setString("QPY",qpy);
                    updateQuery.setString("PSNCODE", psncode);
                    res+=updateQuery.executeUpdate();
                }
            }
            System.out.println("共更新" + res + "条数据");
            ts.commit();
        } catch (Exception e) {
            if(ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null)
                    session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
