package com.wxx.conference.util;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 王曉旭
 */
public class SessionUtil {

    private static ThreadLocal threadLocal = new ThreadLocal();
    private Map<String, Object> session;
    private String prefix = "ICSS_";

    public static SessionUtil getInstance()
    {
        Object obj = threadLocal.get();

        SessionUtil instance = (SessionUtil)obj;

        //测试数据
//        List<String> roleCode = new ArrayList<String>();
//        List<String> resourceCode = new ArrayList<String>();
//
//        roleCode.add("OEAS_ROLE_ADMIN");
//        resourceCode.add("OEAS_RES_ADMIN");
//
//        instance.setUserInfo("000001", "管理员", "220104198905176312",
//                "13811184457","010-12345678","10525379@qq.com",
//                "orgid123456", "caac", "民航局飞标司",
//                roleCode, resourceCode);

        return instance;
    }

    public static void setInstance(SessionUtil instance)
    {
        if(instance == null){
            instance = new SessionUtil();
        }
        threadLocal.set(instance);
    }

    public SessionUtil(){
        if(this.session == null){
            this.session = new HashMap<String, Object>();
        }
    }

    public void setUserInfo(String userid, String username, String idnum,
                            String mobile, String officetel, String email,
                            String orgid, String orgcode, String orgname,
                            List<String> roleCode, List<String> resourceCode){

        setValue("UserID", userid);
        setValue("UserName",username);
        setValue("IDNum",idnum);
        setValue("Mobile",mobile);
        setValue("Officetel",officetel);
        setValue("Email",email);

        setValue("OrgID",orgid);
        setValue("OrgCode",orgcode);
        setValue("OrgCode","1234");
        setValue("OrgName",orgname);
        setObject("RoleCode", roleCode);
        setObject("ResourceCode", resourceCode);
    }

    public String getValue(String name){
        return session.get(prefix.concat(name)) != null ? session.get(prefix.concat(name)).toString() : "";
    }

    public void setValue(String name, String value)
    {
        session.put(prefix.concat(name), value);
    }

    public Object getObject(String name){
        return session.get(prefix.concat(name)) != null ? session.get(prefix.concat(name)) : null;
    }

    public void setObject(String name, Object value)
    {
        session.put(prefix.concat(name), value);
    }

    public String getUserID(){

        return getValue("UserID");
    }

    public String getUsername(){

        return getValue("UserName");
    }

    public String getIDNum(){

        return getValue("IDNum");
    }

    public String getMobile(){

        return getValue("Mobile");
    }

    public String getOfficetel(){

        return getValue("Officetel");
    }

    public String getEmail(){

        return getValue("Email");
    }

    public String getOrgID(){

        return getValue("OrgID");
    }

    public String getOrgCode(){

        return getValue("OrgCode");
    }

    public String getOrgName(){

        return getValue("OrgName");
    }

    public List<String> getRoleCode(){
        Object roles = getObject("RoleCode");
        if(roles != null)
        {
            return (List<String>)roles;
        }
        return new ArrayList<String>();
    }

    public List<String> getResourceCode(){
        Object roles = getObject("ResourceCode");
        if(roles != null)
        {
            return (List<String>)roles;
        }
        return new ArrayList<String>();
    }
}

