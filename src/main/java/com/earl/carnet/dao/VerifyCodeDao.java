package com.earl.carnet.dao;


import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;

import java.util.List;

public interface VerifyCodeDao extends BaseDao<VerifyCode> {


    /**
     * 根据号码查询验证码.
     * @param phoneNumber
     * @return
     */
    List<VerifyCode> getVerifyCode(String phoneNumber);
}