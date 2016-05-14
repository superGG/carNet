package com.earl.carnet.service.impl;

import java.util.Random;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.util.EhCacheHelper;
import com.earl.carnet.commons.util.SmsbaoHelper;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.service.VerifyCodeService;

@Service("verifyCodeService")
@Transactional
public class VerifyCodeServiceImpl implements VerifyCodeService {
    private static Logger logger = Logger
            .getLogger(VerifyCodeServiceImpl.class);

    private static Cache VerifyCode_CACHE = EhCacheHelper.getCacheManage().getCache("verifyCode");

//	@Resource
//	VerifyCodeDao verifyCodeDao;

//	private ResultMessage result = null;

//	@Override
//	protected BaseDao<VerifyCode> getDao() {
//		return verifyCodeDao;
//	}

    @Override
    public Boolean getVerifyCode(String phoneNumber) {
        Boolean get_result = false;
        Element element = VerifyCode_CACHE.get(phoneNumber);
        if (element != null) {
            get_result =  true;
        } else {
            ResultMessage result = send(phoneNumber);
            if (result.getServiceResult()) {
                element = new Element(phoneNumber, result.getResultInfo());
                VerifyCode_CACHE.put(element);
                logger.info("ehcache:" + element.getObjectValue());
            }
            get_result = result.getServiceResult();
        }
        return get_result;
    }

    @Override
    /**
     * test
     */
    public Boolean comfigVerifyCode(String verifyCode, String phoneNumber) {
        Element element = VerifyCode_CACHE.get(phoneNumber);
        if (element != null) {
            logger.info("------------------ehcache:" + element.getObjectValue());
            return verifyCode.equals(element.getObjectValue());
        } else {
            return false;
        }
    }

    /**
     * 发送验证码.
     *
     * @param phoneNumber
     * @return
     */
    public ResultMessage send(String phoneNumber) {
        ResultMessage result = new ResultMessage();
        result.setServiceResult(false);

        // 生成6位数验证码
        Random random = new Random();
        Integer mobileVerifyCode = random.nextInt(899999) + 100000;
        String code = Integer.toString(mobileVerifyCode);
        result.setResultInfo(code);


        // 生成指定短信
        String mf = "【车联网】您的验证码是" + code + ",60秒有效";
        System.out.println(mf);
        try {
            int date = SmsbaoHelper.send(phoneNumber, mf);
            if (date != 0)
                throw new SecurityException("短信发送失败");
            result.setServiceResult(true);
        } catch (Exception e) {
            throw new SecurityException("短信发送失败", e);
        }
        return result;
    }

//	/**
//	 * 保存验证码到数据库.、，并在60秒后删除。
//	 * 
//	 * @param verifyCode
//	 */
//	private void saveVerifyCode(VerifyCode verifyCode) {
//		List<VerifyCode> list = verifyCodeDao.getVerifyCode(verifyCode
//				.getPhoneNumber());
//		if (list.isEmpty()) {
//			int verifyCodeId = verifyCodeDao.insertBackId(verifyCode);
//			SimpleDateFormat startdate = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");// 设置日期格式
//			System.out.println(startdate.format(new Date()));// new
//			// Date()为获取当前系统时间
//			Timer timer = new Timer();
//			timer.schedule(new TimerTask()
//			{
//				public void run() {
//					verifyCodeDao.delete(verifyCodeId);
//					SimpleDateFormat enddate = new SimpleDateFormat(
//							"yyyy-MM-dd HH:mm:ss");// 设置日期格式
//					System.out.println(enddate.format(new Date()));// new
//					// Date()为获取当前系统时间
//					System.out.println("验证码已删除");
//				}
//			}, 60000);
//		}
//	}

}
