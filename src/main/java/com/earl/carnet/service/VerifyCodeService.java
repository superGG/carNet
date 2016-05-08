package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;

import java.io.UnsupportedEncodingException;

public interface VerifyCodeService extends BaseService<VerifyCode, VerifyCode> {

    /**
     * 获取验证码.
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
     Boolean getVerifyCode(String phoneNumber);
}