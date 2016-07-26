package com.earl.carnet.webTest;

import com.earl.carnet.Application;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.web.CarController;
import com.earl.carnet.web.VerifyCodeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class VerifyCodeControllerTest {

    private final Logger logger = LoggerFactory.getLogger(VerifyCodeControllerTest.class);

    ResultMessage result = null;

    @Resource
    private VerifyCodeController verifyCodeController;



    @Test
    //获取验证码测试方法
    public void getVerifyCodeTest() {
        result = new ResultMessage();
        String phone = "18320489492";
        result = verifyCodeController.getVerifyCode(phone);
        logger.info("执行获取验证码方法getAllCarByUser 结果：" + result.getResultInfo());
    }
    @Test
    //获取验证码测试方法
    public void getCodeTest() {
    	int[] aa = new int [4];aa[0] = 1;int [] aab = {1,2,3,4};
    	logger.info("执行获取验证码方法getAllCarByUser 结果：" + result.getResultInfo());
    }


}
