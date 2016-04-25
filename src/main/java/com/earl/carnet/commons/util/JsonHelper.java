package com.earl.carnet.commons.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json操作工具类.
 * 
 * @since 3.0.0
 */
public final class JsonHelper {
    /**
     * log4j实例对象.
     */
    private static final 	Logger logger = LogManager.getLogger(JsonHelper.class
            .getName());

    /**
     * json操作工具类的私有构造方法，其作用是为了防止用户显式生成工具类的实例对象.
     * 
     */
    private JsonHelper() {
    }

    /**
     * 将json字符串转换为指定javabean的列表.
     * 
     * @param json
     *            需要转换的json字符串
     * @param beanClass
     *            javabean类对象
     * @param <T>
     *            javabean类型
     * @return 转换后的Javabean列表
     */
    public static <T> List<T> jsonToBeanList(final String json,
            final Class<T> beanClass) {
        logger.debug("进入jsonToBeanList方法");
        List<T> list = new ArrayList<T>();
        StringBuilder jsonBuffer = new StringBuilder(json.trim());
        jsonBuffer.deleteCharAt(jsonBuffer.indexOf("["));
        jsonBuffer.deleteCharAt(jsonBuffer.lastIndexOf("]"));
        String tempJson = jsonBuffer.toString();
        if (!tempJson.trim().equals("")) {
            String[] beanJson = tempJson.split("},");
            for (String s : beanJson) {
                if (s.charAt(s.length() - 1) != '}') {
                    s =  s + "}";
                }
                list.add(jsonToBean(s, beanClass));
            }
        }
        logger.debug("退出jsonToBeanList方法");
        return list;
    }
    
    /**
     * 将json格式的文件字符串转换成list类型
     *@author 宋文光.
     * @param json
     * @return
     */
	public static List<String> fileJsonToStringList(final String json) {
        logger.debug("进入jsonToStringList方法");
        List<String> list = new ArrayList<String>();
        StringBuilder jsonBuffer = new StringBuilder(json.trim());
        jsonBuffer.deleteCharAt(jsonBuffer.indexOf("["));
        jsonBuffer.deleteCharAt(jsonBuffer.lastIndexOf("]"));
        String tempJson = jsonBuffer.toString();
        if (!"".equals(tempJson.trim())) {
            String[] beanJson = tempJson.split(",");
            for (String s : beanJson) {
                s = s.substring(1, s.length()-1);
                list.add(s);
            }
        }
        logger.debug("退出jsonToStringList方法");
        return list;
    }

    /**
     * 将json字符串转换为指定javabean.
     * 
     * @param json
     *            需要转换的json字符串
     * @param beanClass
     *            javabean类对象
     * @param <T>
     *            javabean类型
     * @return 转换后的Javabean
     */
    public static <T> T jsonToBean(final String json, final Class<T> beanClass) {
        logger.debug("进入jsonToBean方法");
        T bean = null;
        try {
        	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            bean = gson.fromJson(json, beanClass);
        } catch (Exception e) {
//            boolean result = ValidateHelper.vaildate(PatternType.JSON, json);
//            if (!result) {
//                logger.error("json：" + json + "格式不正确，注意勿出现中文全角标点符号");
//            }
            logger.error("Exception:", e);
            e.printStackTrace();
        }
        logger.debug("退出jsonToBean方法");
        return bean;
    }

    /**
     * 将json字符串转换为map键值对.
     * 
     * @param json
     *            需要转换的json字符串
     * @return 将存有json内容的键值对map
     */
    public static Map<String, Object> jsonToMapForSina(final String json) {
        logger.debug("进入jsonToMapForSina方法");
        Map<String, Object> map = null;

        Gson gson = new Gson();
        TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>() {
        };
        Type type = typeToken.getType();
        map = gson.fromJson(json, type);

        logger.debug("退出jsonToMapForSina方法");
        return map;
    }

    /**
     * 将object转换为json字符串.
     * 
     * @param obj
     *            需要转换的对象
     * @return json字符串
     */
    public static String toJson(final Object obj) {
        logger.debug("进入toJson方法");
        String json = null;

//        Gson gson = new Gson();
        //TODO 以后要改成下面的方式
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        json = gson.toJson(obj);

        logger.debug("退出toJson方法");
        return json;
    }
}
