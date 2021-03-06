/**
 * Copyright (c) 2007-2015 WteamFly.  All rights reserved. 网飞网络公司 版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */
package com.earl.carnet.commons.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;


/**
 * 服务返回信息类.
 * 
 * @author 侯骏雄
 * @since 3.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMessage extends JsonEntry{
	public ResultMessage(){
		this.resultParm = new HashMap<String, Object>();
		this.serviceResult = true;
	}
	
    /**
     * 用户令牌判断结果.
     */
    private Boolean userToken;

    private Long messageCode;

    /**
     * 服务类执行结果.
     */
    private Boolean serviceResult;

    /**
     * 返回结果信息.
     */
    private String resultInfo;

    /**
     * 返回参数，又vo的json字符串组成.
     */
    private Map<String, Object> resultParm;

    /**
     * @return 获取的userToken
     */
    public final Boolean getUserToken() {
        return userToken;
    }

    /**
     * 设置userToken的方法.
     * 
     * @param userToken
     *            赋值给userToken的值
     */
    public final void setUserToken(final Boolean userToken) {
        this.userToken = userToken;
    }

    /**
     * @return 获取的serviceResult
     */
    public final Boolean getServiceResult() {
        return serviceResult;
    }

    /**
     * 设置serviceResult的方法.
     * 
     * @param serviceResult
     *            赋值给serviceResult的值
     */
    public final void setServiceResult(final Boolean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public Long getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Long messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * @return 获取的resultParm
     */
    public final Map<String, Object> getResultParm() {
        return resultParm;
    }

    /**
     * 设置resultParm的方法.
     * 
     * @param resultParm
     *            赋值给resultParm的值
     */
    public final void setResultParm(final Map<String, Object> resultParm) {
        this.resultParm = resultParm;
    }

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	@Override
	public String toString() {
		return "ResultMessage [userToken=" + userToken + ", serviceResult="
				+ serviceResult + ", resultInfo=" + resultInfo
				+ ", resultParm=" + resultParm + "]";
	}
	

}
