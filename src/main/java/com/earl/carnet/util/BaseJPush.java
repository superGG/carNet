package com.earl.carnet.util;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * Created by 宋 on 2016/5/7.
 */
public class BaseJPush {

    private static Logger logger = Logger.getLogger(BaseJPush.class);
    public  String appKey;
    public  String masterSecret;

    // 对android和ios设备发送,同时指定离线消息保存时间
    private JPushClient jpush = null;

    // 指定某种设备发送，并且指定离线消息保存时间  DeviceEnum 为null时同时至此Andriod和IOS
//    JPushClient jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.IOS);
    public BaseJPush(String masterSecret, String appKey){
        this.jpush = new JPushClient(masterSecret,appKey);
    }

    /**
     * 向所有平台发送信息.
     * @param content 内容.
     */
    public void sendPush_alertAll (String content) {
//        jpush = new JPushClient(masterSecret,appKey);
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
    public void sendPush_Alias(String alias,String content,String jsonObject) {
//        jpush = new JPushClient(masterSecret,appKey);
    	
    	
        //生成推送的内容
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())//所有平台
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(jsonObject))
                .build();
        //推送
        send(payload);
    }

    /**
     * 向推送标签推送信息.
     * @param tag 目标标签
     * @param content 内容
     */
    public void sendPush_Tag(String tag,String content) {
//        jpush = new JPushClient(masterSecret,appKey);

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
    public void sendPush_Alias(String alias,String content,String title,String jsonObject) {
//        jpush = new JPushClient(masterSecret,appKey);
        //生成推送的内容
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//Andriod平台
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(content,title,null))
                .setMessage(Message.newBuilder()
                        .setMsgContent(jsonObject)
                        .addExtra("from", "JPush")
                        .build())
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
    public void sendPush_Tag(String tag,String content,String title) {
//        jpush = new JPushClient(masterSecret,appKey);
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
    private void send(PushPayload payload){
        try {
            logger.info(payload.toString());
            PushResult result = jpush.sendPush(payload);
            logger.info(result+"................................");
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
