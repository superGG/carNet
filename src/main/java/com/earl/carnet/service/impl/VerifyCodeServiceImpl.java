package com.earl.carnet.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.earl.carnet.commons.util.EhCacheHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.SmsbaoHelper;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.dao.VerifyCodeDao;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;
import com.earl.carnet.service.VerifyCodeService;

@Service("VerifyCodeService")
@Transactional
public class VerifyCodeServiceImpl extends
		BaseServiceImpl<VerifyCode, VerifyCode> implements VerifyCodeService
{
	private static Logger logger = Logger
			.getLogger(VerifyCodeServiceImpl.class);

	private static Cache VerifyCode_CACHE = EhCacheHelper.getCacheManage().getCache("verifyCode");

	@Resource
	VerifyCodeDao verifyCodeDao;

//	private ResultMessage result = null;

	@Override
	protected BaseDao<VerifyCode> getDao() {
		return verifyCodeDao;
	}

	@Override
	public Boolean getVerifyCode(String phoneNumber, HttpSession session) {
		VerifyCode v = new VerifyCode();
		v.setPhoneNumber(phoneNumber);
		List<VerifyCode> list = getDao().searchAccurate(v);
		ResultMessage result = new ResultMessage();
		if (list.size()== 0) {
			result = send(phoneNumber);// 发送验证码操作
			if (result.getServiceResult()) {
				VerifyCode verifyCode = new VerifyCode();
				verifyCode.setPhoneNumber(phoneNumber);
				verifyCode.setVerifyCode(result.getResultInfo());
				saveVerifyCode(verifyCode);
			}
			
		} else {
			result.setResultInfo(list.get(0).getVerifyCode());
		}
		Element element = new Element(phoneNumber,result.getResultInfo());
		VerifyCode_CACHE.put(element);
		logger.info("ehcache:" + VerifyCode_CACHE.get(phoneNumber));
		
		return result.getServiceResult();
	}

	@Override
	public Boolean comfigVerifyCode(String verifyCode, String phoneNumber) {
		Element element = VerifyCode_CACHE.get(phoneNumber);
		if (element!=null) {
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

	/**
	 * 保存验证码到数据库.、，并在60秒后删除。
	 * 
	 * @param verifyCode
	 */
	private void saveVerifyCode(VerifyCode verifyCode) {
		List<VerifyCode> list = verifyCodeDao.getVerifyCode(verifyCode
				.getPhoneNumber());
		if (list.isEmpty()) {
			int verifyCodeId = verifyCodeDao.insertBackId(verifyCode);
			SimpleDateFormat startdate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 设置日期格式
			System.out.println(startdate.format(new Date()));// new
			// Date()为获取当前系统时间
			Timer timer = new Timer();
			timer.schedule(new TimerTask()
			{
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
