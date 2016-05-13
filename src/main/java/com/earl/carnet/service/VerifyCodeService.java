package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;

import javax.servlet.http.HttpSession;

public interface VerifyCodeService extends BaseService<VerifyCode, VerifyCode> {

    /**
     * 获取验证码.
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
     Boolean getVerifyCode(String phoneNumber, HttpSession session);

    Boolean comfigVerifyCode(String verifyCode, String phoneNumber);
}