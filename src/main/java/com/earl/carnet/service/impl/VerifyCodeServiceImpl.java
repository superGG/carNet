package com.earl.carnet.service.impl;

import com.earl.carnet.commons.util.EhCacheHelper;
import com.earl.carnet.commons.util.SmsbaoHelper;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.service.VerifyCodeService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

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
            VerifyCode_CACHE.remove(phoneNumber);//如果缓存存在，则先清除缓存
        }
        ResultMessage result = send(phoneNumber);
        if (result.getServiceResult()) {
            element = new Element(phoneNumber, result.getResultInfo());
            VerifyCode_CACHE.put(element);
            logger.info("ehcache:" + element.getObjectValue());
        }
        get_result = result.getServiceResult();
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

    @Override
    public Boolean test() {
//        VerifyCode_CACHE.put(new Element("test","test1"));
        Element test_elenment = VerifyCode_CACHE.get("test");
//        logger.info("--------------------test_element:"+ test_elenment.getObjectValue());
        logger.info("--------------------test_element:" + test_elenment.toString());
//        DefaultElementEvictionData new_element = new DefaultElementEvictionData(test_elenment.getLastAccessTime());
//        test_elenment.setElementEvictionData(new_element);
        logger.info("------------time:" + (test_elenment.getLastAccessTime() - test_elenment.getCreationTime()));
        Element new_element = new Element(test_elenment.getObjectKey(),
                "2", test_elenment.getVersion(),
                test_elenment.getCreationTime(), test_elenment.getLastAccessTime(),
                test_elenment.getLastUpdateTime(), test_elenment.getHitCount());
        VerifyCode_CACHE.remove("test");
        VerifyCode_CACHE.put(new_element);

        Element new_test = VerifyCode_CACHE.get("test");
        if (new_test != null) {
            logger.info("-----------new_test:" + new_test.toString());
            logger.info("------------new_test:" + (new_test.getLastAccessTime() - new_test.getCreationTime()));
        }
        return true;
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
