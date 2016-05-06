package com.earl.carnet.service.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.SmsbaoHelper;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.dao.VerifyCodeDao;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;
import com.earl.carnet.service.VerifyCodeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("VerifyCodeService")
@Transactional
public class VerifyCodeServiceImpl extends BaseServiceImpl<VerifyCode,VerifyCode>
        implements VerifyCodeService {

    private static Logger logger = Logger.getLogger(VerifyCodeServiceImpl.class);

    @Resource
    VerifyCodeDao verifyCodeDao;

    @Override
    protected BaseDao<VerifyCode> getDao() {
        return verifyCodeDao;
    }


    @Override
    public Boolean getVerifyCode(String phoneNumber) throws UnsupportedEncodingException {
        ResultMessage result = send(phoneNumber);
        if (result.getServiceResult()) {
            VerifyCode verifyCode = new VerifyCode();
            verifyCode.setPhoneNumber(phoneNumber);
            verifyCode.setVerifyCode(result.getResultInfo());
            saveVerifyCode(verifyCode);
        }
        return result.getServiceResult();
    }

    /**
     * 发送验证码.
     * @param phoneNumber
     * @return
     */
    public ResultMessage send(String phoneNumber) throws UnsupportedEncodingException  {
        ResultMessage result = new ResultMessage();
        result.setServiceResult(false);
        String username = "q410654146";// 短信宝帐户名
        String password = SmsbaoHelper.md5("940507");// 短信宝账户密码

        // 生成6位数验证码
        Random random = new Random();
        Integer mobileVerifyCode = random.nextInt(899999) + 100000;
        // 生成指定短信
        String code = Integer.toString(mobileVerifyCode);
        result.setResultInfo(code);
        String mf = "【车联网】您的验证码是" + code + ",60秒有效";
        System.out.println(mf);
        String content = java.net.URLEncoder.encode(mf, "utf-8");// 发送内容
        SmsbaoHelper send = new SmsbaoHelper("http://www.smsbao.com/sms?u="
                + username + "&p=" + password + "&m=" + phoneNumber + "&c="
                + content);
        try {
            int date = send.send();
            if (date != 0)
                throw new SecurityException("短信发送失败");
            result.setServiceResult(true);
        } catch (Exception e) {
            throw new SecurityException("短信发送失败",e);
        }
        return result;
    }

    /**
     * 保存验证码到数据库.、，并在60秒后删除。
     * @param verifyCode
     */
    private void saveVerifyCode(VerifyCode verifyCode) {
        List<VerifyCode> list = verifyCodeDao.getVerifyCode(verifyCode.getPhoneNumber());
        if (!list.isEmpty()) {
            list.get(0).setVerifyCode(verifyCode.getVerifyCode());
            verifyCodeDao.insertBackId(list.get(0));
        } else {
            int verifyCodeId = verifyCodeDao.insertBackId(verifyCode);
            SimpleDateFormat startdate = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");// 设置日期格式
            System.out.println(startdate.format(new Date()));// new
            // Date()为获取当前系统时间
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    verifyCodeDao.delete(verifyCodeId);
                    SimpleDateFormat enddate = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    System.out.println(enddate.format(new Date()));// new
                    // Date()为获取当前系统时间
                    System.out.println("验证码已删除");
                }
            }, 60000);
        }

    }
}
