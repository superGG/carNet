package com.earl.carnet.commons.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author 黄祥谦.
 * @date:2016-1-16 上午11:22:20
 * @version :
 */
public class GsonUtil {
    /**
     * @Title: toJson
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param bean
     * @return String 返回类型
     * @throws：
     */
    public static String toJson(Object bean){
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(bean);
    }
    
    /**
     * @Title: fromJson
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param <T>
     * @param json
     * @param classOfT
     * @return T 返回类型
     * @throws：
     */
    public  static <T>T fromJson(String json,Class<T> beanClass){
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(json, beanClass);
    }
}
