package com.earl.carnet.service;


public interface VerifyCodeService {

    /**
     * 获取验证码.
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
     Boolean getVerifyCode(String phoneNumber);

    Boolean comfigVerifyCode(String verifyCode, String phoneNumber);

    /**
     * test
     * @return
     */
    Boolean test();
}