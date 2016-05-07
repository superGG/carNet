package com.earl.carnet.util;


import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
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
