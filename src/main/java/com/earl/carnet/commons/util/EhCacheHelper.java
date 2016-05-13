package com.earl.carnet.commons.util;

import net.sf.ehcache.CacheManager;

import java.net.URL;

/**
 * 宋.
 * Created by Administrator on 2016/5/13.
 */
public class EhCacheHelper {

    private static final CacheManager CACHE = build_CacheManage();

    /**
     * 私有构造方法.
     */
    private EhCacheHelper() {
    }

    /**
     * build_CacheManage.
     */
    private static CacheManager build_CacheManage() {
        URL url = EhCacheHelper.class.getResource("/ehcache.xml");
        return CacheManager.create(url);
    }


    /**
     * @return 获取的CacheManage
     */
    public static CacheManager getCacheManage() {
        return CACHE;
    }





}
