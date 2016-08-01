package com.earl.carnet.webTest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.web.VerifyCodeController;

/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VerifyCodeControllerTest {

	@LocalServerPort
	public int port;
	
	private final Logger logger = LoggerFactory.getLogger(VerifyCodeControllerTest.class);

	ResultMessage result = null;

	@Resource
	private VerifyCodeController verifyCodeController;

	@Test
	// 获取验证码测试方法
	public void getVerifyCodeTest() {
		result = new ResultMessage();
		String phone = "18320489492";
		result = verifyCodeController.getVerifyCode(phone);
		logger.info("执行获取验证码方法getAllCarByUser 结果：" + result.getResultInfo());
	}

	@Test
	// 获取验证码测试方法
	public void getCodeTest() {
		int[] aa = new int[4];
		aa[0] = 1;
		int[] aab = { 1, 2, 3, 4 };
		logger.info("执行获取验证码方法getAllCarByUser 结果：" + result.getResultInfo());
	}

}
