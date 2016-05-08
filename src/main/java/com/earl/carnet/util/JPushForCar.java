package com.earl.carnet.util;


import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 极光推送工具类.
 * Created by Administrator on 2016/5/7.
 */
@Component
public class JPushForCar extends BaseJPush{

    private static Logger logger = Logger.getLogger(JPushForCar.class);

    // 保存离线消息的时长。秒为单位。最多支持10天（864000秒）。
    // 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
    // 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。
//    private static int timeToLive = 86400;

    public JPushForCar(){
        super("3037ff3cb054cd7b332dd869","89500c57bff5c3381ce53689");
        logger.info("构造JPush For Car实例完成");
    }
}
