package com.wxx.conference.service.boardRoomManagement;

import com.wxx.conference.model.boardRoomManagement.boardRoomDevice;
import com.wxx.conference.util.HibernateUtil;
import com.wxx.conference.util.UUIDUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;


import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017-9-8.
 */
@Service
public class BRMService {

    public boolean addDevice(String deviceName){
        Session session = null;
        Transaction ts = null;
        try{
           session = HibernateUtil.getSession();
           ts = session.beginTransaction();
           boardRoomDevice device = new boardRoomDevice();
           device.setDeviceId(UUIDUtil.getUUID());
           device.setDeviceName(URLDecoder.decode(deviceName,"UTF-8"));
           device.setDate(new Date());
           session.saveOrUpdate(device);
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
    public boolean delDevice(String deviceId){
        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            String hql = "delete from boardRoomDevice  where deviceId=:deviceId";
            Query query = session.createQuery(hql);
            query.setString("deviceId", deviceId);
            int n = query.executeUpdate();
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
    public boolean editDevice(String deviceId,String deviceName){
        Session session = null;
        Transaction ts = null;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            String sql = "update boardRoomDevice set deviceName=:deviceName where deviceId=:deviceId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("deviceId",deviceId);
            query.setString("deviceName",URLDecoder.decode(deviceName,"UTF-8"));
            int n = query.executeUpdate();
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
    public List getDeviceList(){
        Session session = null;
        List <boardRoomDevice>deviceList = new ArrayList<boardRoomDevice>();
        try{
            session = HibernateUtil.getSession();
            String hql = "from boardRoomDevice order by date asc";
            Query query = session.createQuery(hql);
            deviceList = query.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return deviceList;
    }
    public boardRoomDevice getDeviceById(String deviceId){
        Session session = null;
        boardRoomDevice device = new boardRoomDevice();
        try{
            session = HibernateUtil.getSession();
            String hql = "from boardRoomDevice where deviceId=:deviceId";
            Query query = session.createQuery(hql);
            query.setString("deviceId",deviceId);
            device = (boardRoomDevice)query.list().get(0);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return device;
    }
}
