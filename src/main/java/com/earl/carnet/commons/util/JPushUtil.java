package com.earl.carnet.commons.util;


import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送工具类.
 * Created by Administrator on 2016/5/7.
 */
public class JPushUtil {

    private static Logger logger = Logger.getLogger(JPushUtil.class);

    //Portal上注册应用时生成的 masterSecret
    private static String masterSecret = "812cf01c69a3b1da0c68ffb2";

    //Portal上注册应用时生成的 appKey
    private static String appKey = "434549c4fd934c2af57ef4e1";

    // 保存离线消息的时长。秒为单位。最多支持10天（864000秒）。
    // 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
    // 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。
    private static int timeToLive = 86400;

    // 对android和ios设备发送,同时指定离线消息保存时间
    private static JPushClient jpush = null;


    // 指定某种设备发送，并且指定离线消息保存时间  DeviceEnum 为null时同时至此Andriod和IOS
//    JPushClient jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.IOS);


    /**
     * 向所有平台发送信息.
     * @param content 内容.
     */
    public static void sendPush_alertAll (String content) {
        jpush = new JPushClient(masterSecret,appKey,timeToLive);
        //生成推送的内容
        PushPayload payload = PushPayload.alertAll(content);
        //推送
        send(payload);
    }

    /**
     * 向推送目标推送信息.
     * @param alias 目标别名.
     * @param content 内容.
     */
    public static void sendPush_Alias(String alias,String content) {
        jpush = new JPushClient(masterSecret,appKey,timeToLive);
        //生成推送的内容
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())//所有平台
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(content))
                .build();
        //推送
        send(payload);
    }

    /**
     * 向推送标签推送信息.
     * @param tag 目标标签
     * @param content 内容
     */
    public static void sendPush_Tag(String tag,String content) {
        jpush = new JPushClient(masterSecret,appKey,timeToLive);
        //生成推送的内容
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())//所有平台
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.alert(content))
                .build();
        //推送
        send(payload);
    }


    /**
     * 向Andriod平台推送消息
     * @param alias 目标别名
     * @param content 消息内容
     * @param title 消息标题
     */
    public static void sendPush_Alias(String alias,String content,String title) {
        jpush = new JPushClient(masterSecret,appKey,timeToLive);
        //生成推送的内容
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//Andriod平台
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(content,title,null))
                .build();
        //推送
        send(payload);
    }

    /**
     * 向Andriod平台推送消息
     * @param tag 目标标签
     * @param content 消息内容
     * @param title 消息标题
     */
    public static void sendPush_Tag(String tag,String content,String title) {
        jpush = new JPushClient(masterSecret,appKey,timeToLive);
        //生成推送的内容
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//Andriod平台
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(content,title,null))
                .build();
        //推送
        send(payload);
    }





    /**
     * 发送方法。
     * @param payload
     */
    private static void send(PushPayload payload){
        try {
            System.out.println(payload.toString());
            PushResult result = jpush.sendPush(payload);
            System.out.println(result+"................................");
            logger.info("Got result - " + result);
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
        }
    }




}
