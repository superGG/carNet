package com.earl.carnet.util;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 极光推送工具类.
 * Created by Administrator on 2016/5/7.
 */
@Component
public class JPushForUser extends BaseJPush{

    private static Logger logger = Logger.getLogger(JPushForUser.class);

    public JPushForUser(){
        super("812cf01c69a3b1da0c68ffb2", "434549c4fd934c2af57ef4e1");
        logger.info("构造JPush For User实例完成");
    }
}
