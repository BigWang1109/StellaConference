package com.wxx.conference.service.booking;


import com.wxx.conference.model.booking.bookingRegion;
import com.wxx.conference.model.booking.user;
import com.wxx.conference.util.DateTimeUtil;
import com.wxx.conference.util.HibernateUtil;
import com.wxx.conference.util.UUIDUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017-8-15.
 */
@Service
public class BKService {
    //初始化会议室
    public List getRegionList(String floor){
        Session session = null;
        List regionList = new ArrayList();
        try{
            session = HibernateUtil.getSession();
            String hql = "from region where regionFloor=:floor order by regionName asc";
            Query query = session.createQuery(hql);
            query.setString("floor",floor);
            regionList = query.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return regionList;
    }
    //初始化会议室预定情况
    public List getDualList(String date,String regionId){
        Session session = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dualList = new ArrayList();
        try{
            session = HibernateUtil.getSession();
//            String hql = "from bookingRegion where bookingDate=:bookingDate and regionId=:regionId order by bookingDual asc";
            String sql = "select * from bookingregion b where b.bookingDate=:bookingDate and regionId=:regionId and flag='0' order by  bookingDual asc";
            Date bookingDate = sdf.parse(date);
//            Query query = session.createQuery(hql);
            SQLQuery squery = session.createSQLQuery(sql);
//            query.setDate("bookingDate",bookingDate);
//            query.setString("regionId", regionId);

            squery.setDate("bookingDate", bookingDate);
            squery.setString("regionId", regionId);

            List array = squery.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[])array.get(i);
                bookingRegion bregion = new bookingRegion();
                bregion.setBookingRegionId(obj[0].toString());
                bregion.setBookingId(obj[1].toString());
                bregion.setRegionId(obj[2].toString());
                bregion.setUserId(obj[3].toString());
                bregion.setBookingDate(DateTimeUtil.getDateFShort(obj[4].toString()));
                bregion.setBookingDual(obj[5].toString());
                bregion.setBookingTitle(obj[6].toString());
                bregion.setIsManager(new Boolean(obj[7].toString()));
                bregion.setFlag(obj[8].toString());
                dualList.add(bregion);
            }
//            dualList = query.list();
//            dualList = squery.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }
        return dualList;
    }
    //初始化时间段
    public List loadDual(){
        Session session = null;
        List dualList = new ArrayList();
        try{
            session = HibernateUtil.getSession();
            String hql = "from regionDual order by dualId asc";
            Query query = session.createQuery(hql);
            dualList = query.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return dualList;
    }
    //预定
    public boolean booking(List<bookingRegion> bookingList){
        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            bookingRegion booking = bookingList.get(0);
            String bookingId = UUIDUtil.getUUID();
            String []dualList = booking.getBookingDual().split(",");
            String regionId = booking.getRegionId();
            Date bookingDate = booking.getBookingDate();
            String bookingTitle = booking.getBookingTitle();
            Boolean isManger = booking.isManager();
            for(int i=0;i<dualList.length;i++){
                bookingRegion bookingRegion = new bookingRegion();
                bookingRegion.setBookingId(bookingId);
                bookingRegion.setBookingRegionId(UUIDUtil.getUUID());
                bookingRegion.setRegionId(regionId);
                bookingRegion.setBookingDate(bookingDate);
                bookingRegion.setBookingTitle(bookingTitle);
                bookingRegion.setBookingDual(dualList[i]);
                bookingRegion.setIsManager(isManger);
                bookingRegion.setFlag("0");
                bookingRegion.setUserId("00025766");
//                session.saveOrUpdate(bookingRegion);
                session.save(bookingRegion);
            }
            ts.commit();
        }catch(Exception e){
            if(ts != null){
                ts.rollback();
            }
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }
    //预定前先检查该时间段是否已被预定
    public String checkBookingRegion(List<bookingRegion> bookingList){
        Session session = null;
        String str = "";
        try{
            session = HibernateUtil.getSession();
            StringBuffer dual = new StringBuffer();
            bookingRegion booking = bookingList.get(0);
            String sql = "select r.dualName from bookingregion b left join regiondual r on b.bookingDual = r.dualId where b.regionId=:regionId and b.bookingDate=:bookingDate and b.bookingDual in(:dualList) and b.flag = '0'";
            SQLQuery query = session.createSQLQuery(sql);
            String []dualList = booking.getBookingDual().split(",");
            query.setString("regionId",booking.getRegionId());
            query.setDate("bookingDate",booking.getBookingDate());
            query.setParameterList("dualList", dualList);
            List array = query.list();
            for(int i=0;i<array.size();i++){
//                Object[] obj = (Object[])array.get(i);
                dual.append(array.get(i).toString());
                dual.append(",");
            }
            str = dual.toString();
            if(str.length()>0){
                str = str.substring(0,str.length()-1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return str;
    }
    //每天凌晨定时更新会议室预定状态,将预定日期为前一天的记录置为已使用
    public void resetBookingResigon(){
        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            Date date = new Date();
            String sql = "update bookingregion set flag = '1' where bookingDate<:date and flag = '0'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setDate("date", date);
            int num = query.executeUpdate();
            ts.commit();
        }catch(Exception e){
            if(ts!=null){
                ts.rollback();
            }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }
    }
    public List getBookingList(){
        Session session = null;
        List array = new ArrayList();
        try{
            session = HibernateUtil.getSession();
//            String hql = "from bookingRegion  where userId=:userId order by bookingDate,regionId,bookingDual";
//            String sql = "select b.bookingId from bookingRegion b where b.userId=:userId group by b.bookingId order by b.bookingDate";
            String hql = "from bookingRegion  where userId=:userId group by bookingId order by flag,bookingDate,bookingTitle desc";
            Query query = session.createQuery(hql);
//            SQLQuery squery = session.createSQLQuery(sql);
            query.setString("userId","00025766");
//            squery.setString("userId","00025766");
            array = query.list();
//            List ids = squery.list();
//            List array = query.list();
//            List<bookingRegion> res = new ArrayList<bookingRegion>();
//            List<bookingRegion> ids = new ArrayList<bookingRegion>();
//            res = query.list();
//            ids = squery.list();
//            for(int i=0;i<ids.size();i++){
//                bookingRegion booking = ids.get(i);
//                String bookingId = booking.getBookingId();
//                String dualList = "";
//                for(int j=0;j<res.size();j++){
//                    bookingRegion bookingRegion = res.get(j);
//                    if(bookingRegion.getBookingId().equals(bookingId)){
//
//                    }
//                }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return array;
    }
    //取消预定
    public boolean cancelBooking(String bookingId){

        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            String sql = "update bookingregion set flag = '2' where bookingId=:bookingId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("bookingId", bookingId);
            int num = query.executeUpdate();
            if(num<=0){
                return false;
            }
            ts.commit();
        }catch(Exception e){
            if(ts!=null){
                ts.rollback();
            }
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }
    //获取会议详细信息
    public bookingRegion getBookingRegion(String bookingId){

        Session session = null;
        bookingRegion bookingregion = new bookingRegion();
        try{
            session = HibernateUtil.getSession();
            String sql = " select b.bookingId,b.bookingDate,f.floorName,r.regionName,d.dualName,b.bookingTitle,b.isManager " +
                    "from bookingregion b left join region r on b.regionId = r.regionId left join floor f on r.regionFloor = f.floorId " +
                    "left join regiondual d on b.bookingDual = d.dualId where b.bookingId=:bookingId order by b.bookingDual";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("bookingId",bookingId);
            List array = query.list();
            List dualList = new ArrayList();
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[])array.get(i);
                bookingregion.setBookingId(obj[0].toString());
                bookingregion.setBookingDate(DateTimeUtil.getDateFShort(obj[1].toString()));
                bookingregion.setBookingRegionId(obj[2].toString());//存储楼层
                bookingregion.setRegionId(obj[3].toString());
                dualList.add(obj[4].toString());
//                sb.append(obj[4].toString());
//                sb.append(",");
                bookingregion.setBookingTitle(obj[5].toString());
                bookingregion.setIsManager(new Boolean(obj[6].toString()));
            }
//            bookingregion.setBookingDual(sb.toString().substring(0,sb.toString().length()-1));
            List list = generateDual(dualList);
            int len = list.size();
            for(int i=0;i<list.size();i++){
                sb.append(list.get(i));
                sb.append(",");
            }
            bookingregion.setBookingDual(sb.toString().substring(0, sb.toString().length() - 1));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }

        return bookingregion;
    }
    //生成会议区间
    public List generateDual(List dualList){

        int len = dualList.size();
        List list = new ArrayList();
        String begin = dualList.get(0).toString().split("-")[0];
        String end = dualList.get(0).toString().split("-")[1];
        for(int i=0;i<len;i++){
            if(i!=len-1){
                String []pre = dualList.get(i).toString().split("-");
                String []next = dualList.get(i+1).toString().split("-");
                if(pre[1].equals(next[0])){
                    end = next[1];
                }else{
                    list.add(begin+"-"+end);
                    begin =next[0];
                    end = next[1];
                }
            }
            if(i==len-1){
                String []fin = dualList.get(i).toString().split("-");
                if(fin[0].equals(end)){
                    end = fin[1];
                    list.add(begin+"-"+end);
                }else if(end.equals(fin[1])){
                    list.add(begin+"-"+end);
                }else{
                    list.add(begin+"-"+end);
                    list.add(dualList.get(i).toString());
                }
            }
        }

        return list;
    }

    public String adminLogin(String account,String password){
        Session session = null;
        String mess = "";
        try{
            session = HibernateUtil.getSession();
            String hql = "from user where userId=:userId";
            Query query = session.createQuery(hql);
            user user = (user)query.list().get(0);
            if(user.getType().equals("2")){
                mess = "对不起，您不是管理员";
            }else{
                if(password!=null && !password.equals("") && password.equals(user.getPassword())){
                    mess = "对不起，密码有误，请重新输入";
                }
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
}
