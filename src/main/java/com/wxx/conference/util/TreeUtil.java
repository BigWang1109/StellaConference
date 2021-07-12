package com.wxx.conference.util;


import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wxx on 16-11-25.
 */
public class TreeUtil {

    private static HashMap<String, Method> methodMap = new HashMap<String, Method>();
    private static HashMap<String, String[]> styleMap = new HashMap<String, String[]>();


    /**
     * 根据提供的数据一次性转换为 tree 结构的 json 字符串
     *
     * @param list 已经排序的泛型的树型结构数据, 具备id, parentID属性
     * @param idColumn id 属性名称
     * @param parentIDColumn parentID 属性名称
     * @param initRootID 初始化树的根节点id
     * @param initParentID 初始化树的父节点id
     * @param textFormat 树节点显示属性的格式,例如 %1$s-[%2$s]
     * @param textColumns 树节点显示属性的数字,需要与 textFormat 中参数一一对应
     * @param iconStyle 可以输入null, 树节点自定义图标样式 "expression >>> style"
     *                  expression 为 返回 bool 类型的 javascript 表达式, 表达式中可以使用实体属性值作为判断条件, 属性用 [] 包括起来
     *                  style 为 easyui 自定义 icon 的 class 具体可以查看 themes\icon.css
     *                  示例:
     *                  "\"[state]\".indexOf('1') > -1 >>> icon-man"
     *                  "\"[state]\" == "1" >>> icon-man"
     * @param attributes 可以输入null, 树节点扩展属性
     * @param checkList 可以输入null, 默认选中节点的id
     * @return json 字符串
     */
    public static <T> String toJSON(List<T> list, String idColumn, String parentIDColumn, String initRootID, String initParentID,
                                    String textFormat, String[] textColumns,
                                    String[] iconStyle, String[] attributes, List<String> checkList) {

        String json ="";
        if(list!=null && list.size()>0){
            Field[] fields = list.get(0).getClass().getDeclaredFields();
            if(iconStyle == null) iconStyle = new String[]{};
            if(attributes == null) attributes = new String[]{};
            if(checkList == null) checkList = new ArrayList<String>();

            json = _toJSON(list, fields, idColumn, parentIDColumn, initRootID, initParentID, 0, textFormat, textColumns, iconStyle, attributes, checkList);
            if(json.length()>0) json= "["+ json.replace("'","\"")+"]";
        }

        return json;
    }


    private static <T> String _toJSON(List<T> list, Field[] fields, String idColumn, String parentIDColumn, String initRootID, String initParentID, int leve, String textFormat, String[] textColumns, String[] iconStyle, String[] attributes, List<String> checkList)
    {
        //isChildrenExpland = false;
        StringBuilder json = new StringBuilder();
        String temp = "";
        String id = "";
        String parentID = "";
        //节点显示名称
        String text = "";
        //节点值
        String value = "";
        //节点样式
        String icon = "";
        //扩展属性
        String attrs = "";

        List<String> texts = new ArrayList<String>();
        for (T model : list) {
            //通过反射获取值
            texts.clear();
            icon = "";
            attrs = "";
            for (Field field : fields) {
                String filedName = field.getName();
                if("SerialVersionUID".toUpperCase().equals(filedName.toUpperCase())) continue;

                String tmp = invokeString(model, field);

                if (idColumn.toUpperCase().equals(filedName.toUpperCase())) {
                    id = tmp;
                    value = tmp;
                }
                if (parentIDColumn.toUpperCase().equals(filedName.toUpperCase())) {
                    parentID = tmp;
                }
                for (int i = textColumns.length - 1; i >= 0; i--) {
                    //倒序处理，正序匹配format参数
                    if (textColumns[i].toUpperCase().equals(filedName.toUpperCase())) {
                        texts.add(tmp);
                    }
                }
                for (int i = iconStyle.length - 1; i >= 0; i--) {
                    //String[] iconStyle = {"\"[orgname]\".indexOf('管理局') > -1 >>> icon-man"};

                    if (iconStyle[i].indexOf("["+ filedName +"]") > -1 && tmp != null) {
                        String expression = iconStyle[i];
                        expression = expression.replace("["+ filedName +"]", tmp);
                        String[] array = styleMap.get(expression);
                        icon = "";
                        if(array == null){
                            if(eval(expression.split(">>>")[0].trim())) {
                                icon = iconStyle[i].split(">>>")[1].trim();
                                icon = String.format(", 'iconCls':'%1$s'", icon);
                                array = new String[]{"true", icon};
                                styleMap.put(expression, array);
                            }
                            else{

                                icon = "";
                                array = new String[]{"false", icon};
                                styleMap.put(expression, array);
                            }
                        }
                        else{
                            if("true".equals(array[0])){
                                icon = array[1];
                            }
                        }
                        break;
                    }
                }
                for (int i = attributes.length - 1; i >= 0; i--) {
                    if (attributes[i].toUpperCase().equals(filedName.toUpperCase()) && tmp != null) {
                        attrs += String.format("'%1$s':'%2$s',", filedName, tmp);
                    }
                }
            }

            text = String.format(textFormat, texts.toArray());
            if (attrs.length() > 0) {
                attrs = ", 'attributes':{" + attrs.substring(0, attrs.length() -1) + "}";
            }

            String t = org.apache.commons.lang3.StringUtils.repeat('\t', leve);

            if (!StringUtils.isEmpty(initRootID) && id.equals(initRootID))
            {
                initRootID = "";
                //当前节点
                if (checkList.contains(id))
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s', 'checked':'true'[icon][state][children][attrs]},", value, text);
                }
                else
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s'[icon][state][children][attrs]},", value, text);
                }
                temp = temp.replace("[icon]", icon);
                temp = temp.replace("[state]", "");
                temp = temp.replace("[attrs]", attrs);
                String children = _toJSON(list, fields, idColumn, parentIDColumn, initRootID, id, leve, textFormat, textColumns, iconStyle, attributes, checkList);

                if (StringUtils.isEmpty((children.trim())))
                {
                    temp = temp.replace("[children]", "");
                }
                else
                {
                    temp = temp.replace("[children]", ", 'children':[\r\n" + t + children + t + "]");
                }
                json.append(temp);
                //查找到根节点的本轮后续循退出，避免重复加载
                break;
            }
            else if (parentID != null && parentID.equals(initParentID))
            {
                if (checkList.contains(id))
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s', 'checked':'true'[icon][state][children][attrs]},", value, text);
                }
                else
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s'[icon][state][children][attrs]},", value, text);
                }

                temp = temp.replace("[icon]", icon);
                temp = temp.replace("[attrs]", attrs);


                String children = _toJSON(list, fields, idColumn, parentIDColumn, initRootID, id, leve, textFormat, textColumns, iconStyle, attributes, checkList);
                if (StringUtils.isEmpty(children.trim()))
                {
                    temp = temp.replace("[state]", "");
                    temp = temp.replace("[children]", "");
                }
                else if (!StringUtils.isEmpty(children.trim()))
                {
                    temp = temp.replace("[state]", ", 'state':'closed'");
                    temp = temp.replace("[children]", ", 'children':[\r\n" + t + children + t + "]");
                }

                json.append(temp);
            }
        }
        leve++;

        return  json.length()>0 ? json.delete(json.length()-1, json.length()).toString() + "\r\n" : "";
    }


    /**
     * 根据提供的数据加载下一级 tree 结构的 json 字符串
     *
     * @param list 已经排序的泛型的树型结构数据, 具备id, parentID属性
     * @param idColumn id 属性名称
     * @param parentIDColumn parentID 属性名称
     * @param initRootID 初始化树的根节点id
     * @param initParentID 初始化树的父节点id
     * @param textFormat 树节点显示属性的格式,例如 %1$s-[%2$s]
     * @param textColumns 树节点显示属性的数字,需要与 textFormat 中参数一一对应
     * @param iconStyle 可以输入null, 树节点自定义图标样式 "expression >>> style"
     *                  expression 为 返回 bool 类型的 javascript 表达式, 表达式中可以使用实体属性值作为判断条件, 属性用 [] 包括起来
     *                  style 为 easyui 自定义 icon 的 class 具体可以查看 themes\icon.css
     *                  示例:
     *                  "\"[state]\".indexOf('1') > -1 >>> icon-man"
     *                  "\"[state]\" == "1" >>> icon-man"
     * @param attributes 可以输入null, 树节点扩展属性
     * @param checkList 可以输入null, 默认选中节点的id
     * @return json 字符串
     */
    public static <T> String asyncJSON(List<T> list, String idColumn, String parentIDColumn, String initRootID, String initParentID,
                                    String textFormat, String[] textColumns,
                                    String[] iconStyle, String[] attributes, List<String> checkList) {

        String json ="";
        if(list.size()>0){
            Field[] fields = list.get(0).getClass().getDeclaredFields();
            if(iconStyle == null) iconStyle = new String[]{};
            if(attributes == null) attributes = new String[]{};
            if(checkList == null) checkList = new ArrayList<String>();
            json = _asyncJSON(list, fields, idColumn, parentIDColumn, initRootID, initParentID, 0, textFormat, textColumns, iconStyle, attributes, checkList);
            if(json.length()>0) json= "["+ json.replace("'","\"")+"]";
        }

        return json;
    }



    private static <T> String _asyncJSON(List<T> list, Field[] fields, String idColumn, String parentIDColumn, String initRootID, String initParentID, int leve, String textFormat, String[] textColumns, String[] iconStyle, String[] attributes, List<String> checkList)
    {
        //isChildrenExpland = false;
        StringBuilder json = new StringBuilder();
        String temp = "";
        String id = "";
        String parentID = "";
        //节点显示名称
        String text = "";
        //节点值
        String value = "";
        //节点样式
        String icon = "";
        //扩展属性
        String attrs = "";

        List<String> texts = new ArrayList<String>();
        for (T model : list) {
            //通过反射获取值
            texts.clear();
            icon = "";
            attrs = "";
            for (Field field : fields) {
                String filedName = field.getName();
                if("SerialVersionUID".toUpperCase().equals(filedName.toUpperCase())) continue;

                String tmp = invokeString(model, field);

                if (idColumn.toUpperCase().equals(filedName.toUpperCase())) {
                    id = tmp;
                    value = tmp;
                }
                if (parentIDColumn.toUpperCase().equals(filedName.toUpperCase())) {
                    parentID = tmp;
                }
                for (int i = textColumns.length - 1; i >= 0; i--) {
                    //倒序处理，正序匹配format参数
                    if (textColumns[i].toUpperCase().equals(filedName.toUpperCase())) {
                        texts.add(tmp);
                    }
                }
                for (int i = iconStyle.length - 1; i >= 0; i--) {
                    //String[] iconStyle = {"\"[orgname]\".indexOf('管理局') > -1 >>> icon-man"};

                    if (iconStyle[i].indexOf("["+ filedName +"]") > -1 && tmp != null) {
                        String expression = iconStyle[i];
                        expression = expression.replace("["+ filedName +"]", tmp);
                        String[] array = styleMap.get(expression);
                        icon = "";
                        if(array == null){
                            if(eval(expression.split(">>>")[0].trim())) {
                                icon = iconStyle[i].split(">>>")[1].trim();
                                icon = String.format(", 'iconCls':'%1$s'", icon);
                                array = new String[]{"true", icon};
                                styleMap.put(expression, array);
                            }
                            else{

                                icon = "";
                                array = new String[]{"false", icon};
                                styleMap.put(expression, array);
                            }
                        }
                        else{
                            if("true".equals(array[0])){
                                icon = array[1];
                            }
                        }
                        break;
                    }
                }
                for (int i = attributes.length - 1; i >= 0; i--) {
                    if (attributes[i].toUpperCase().equals(filedName.toUpperCase()) && tmp != null) {
                        attrs += String.format("'%1$s':'%2$s',", filedName, tmp);
                    }
                }
            }

            text = String.format(textFormat, texts.toArray());
            if (attrs.length() > 0) {
                attrs = ", 'attributes':{" + attrs.substring(0, attrs.length() -1) + "}";
            }

            String t = org.apache.commons.lang3.StringUtils.repeat('\t', leve);

            if (!StringUtils.isEmpty(initRootID) && id.equals(initRootID))
            {
                initRootID = "";
                //当前节点
                if (checkList.contains(id))
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s', 'checked':'true'[icon][state][children][attrs]},", value, text);
                }
                else
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s'[icon][state][children][attrs]},", value, text);
                }
                temp = temp.replace("[icon]", icon);
                temp = temp.replace("[state]", "");
                temp = temp.replace("[attrs]", attrs);
                String children = _asyncJSON(list, fields, idColumn, parentIDColumn, initRootID, id, leve, textFormat, textColumns, iconStyle, attributes, checkList);

                if (StringUtils.isEmpty((children.trim())))
                {
                    temp = temp.replace("[children]", "");
                }
                else
                {
                    temp = temp.replace("[children]", ", 'children':[\r\n" + t + children + t + "]");
                }
                json.append(temp);
                //查找到根节点的本轮后续循退出，避免重复加载
                break;
            }
            else if (parentID != null && parentID.equals(initParentID))
            {
                if (checkList.contains(id))
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s', 'checked':'true'[icon][state][children][attrs]},", value, text);
                }
                else
                {
                    temp = String.format("{'id':'%1$s', 'text':'%2$s'[icon][state][children][attrs]},", value, text);
                }

                temp = temp.replace("[icon]", icon);
                temp = temp.replace("[attrs]", attrs);
                temp = temp.replace("[state]", ", 'state':'closed'");
                temp = temp.replace("[children]", "");

                json.append(temp);
            }
        }
        leve++;

        return  json.length()>0 ? json.delete(json.length()-1, json.length()).toString() + "\r\n" : "";
    }

    /**
     * 把一个字符串的第一个字母大写
     *
     * @param fildeName 字段名
     * @return String
     */
    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 通过反射获取某个属性的值
     *
     * @param model 泛型实体对象
     * @param field Field对象
     * @return String
     */
    private static <T> String invokeString(T model, Field field){

        String filedName = field.getName();
        Method m = null;
        try {
            String methodName = "get" + getMethodName(filedName);
            String key = model.getClass() + "." + methodName;
            m = methodMap.get(key);
            if(m == null){
                m = (Method) model.getClass().getMethod(methodName);
                methodMap.put(key, m);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        String value = null; // 调用getter方法获取属性值
        try {
            if("int".equals(field.getType().toString())){
                value = String.valueOf(m.invoke(model));
            }
            else
            {
                value = (String) m.invoke(model);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * 判断 javascript 表达式是否成立
     *
     * @param expression javascript 表达式字符串
     * @return boolean
     */
    public static boolean eval(String expression){
        boolean result = false;
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine enginer = mgr.getEngineByName("javascript");
        try{
            Object obj = enginer.eval(expression);
            result = Boolean.valueOf(obj.toString());
        }catch(ScriptException e){
            e.printStackTrace();
        }
        return result;
    }

}
