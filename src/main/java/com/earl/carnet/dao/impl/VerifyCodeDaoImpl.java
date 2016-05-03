package com.earl.carnet.dao.impl;


import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.VerifyCodeDao;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Repository("VerifyeDao")
public class VerifyCodeDaoImpl extends BaseDaoImpl<VerifyCode> implements VerifyCodeDao {


    @Override
    public List<VerifyCode> getVerifyCode(String phoneNumber) {
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setPhoneNumber(phoneNumber);
        List<VerifyCode> list = searchQuery(verifyCode);
        return list;
    }
}