/**
 * Copyright (c) 2007-2015 WteamFly.  All rights reserved. 网飞网络公司 版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */
package com.earl.carnet.commons.vo;

import com.earl.carnet.commons.util.GsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * 服务返回信息类.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TcpMessage {

    /**
     * 返回参数，又vo的json字符串组成.
     */
    private Integer messagtype;

    private String message;

    public Integer getMessagtype() {
        return messagtype;
    }

    public void setMessagtype(Integer messagtype) {
        this.messagtype = messagtype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TcpMessage{" +
                "messagtype=" + messagtype +
                ", message='" + message + '\'' +
                '}';
    }

    public String toJson(){
		return GsonUtil.toJson(this);
	}
}
