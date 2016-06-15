package com.earl.carnet.webTest;

import com.earl.carnet.Application;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.web.CarController;
import com.earl.carnet.web.UserController;
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
public class UserControllerTest {

    private final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    ResultMessage result = null;

    @Resource
    private UserController userController;



    @Test
    //用户注册测试方法
    public void regitsterAccountTest() {
        result = new ResultMessage();
        String loginid = "qtest";
        String password = "123456";
        result = userController.regitsterAccount(loginid,password).getBody();
        logger.info("执行用户注册账号regitsterAccount 结果：" + result.getResultInfo());
    }

    @Test
    //用户登录测试方法
    public void doLoginTest() {
        result = new ResultMessage();
        String loginid = "qtest";
        String password = "123456";
        result = userController.doLogin(loginid,password).getBody();
        logger.info("执行用户登录doLogin 结果：" + result.getResultInfo());
    }

    @Test
    //用户更改密码测试方法
    public void changePasswordTest() {
        result = new ResultMessage();
        Long id = 1l;
        String old_password = "123456";
        String new_password = "1234567";
        result = userController.changePassword(old_password,id,new_password).getBody();
        logger.info("执行用户更改密码changePassword 结果：" + result.getResultInfo());
    }

    @Test
    //用户绑定亲人手机号码测试方法
    public void changeRelatedPhoneTest() {
        result = new ResultMessage();
        String id = "1";
        String verifyCode = "123456";
        String newPhone = "18320489494";
        result = userController.changeRelatedPhone(id,verifyCode,newPhone).getBody();
        logger.info("执行用户绑定亲人号码changeRelatedPhone 结果：" + result.getResultInfo());
    }

    @Test
    //用户更改安全密码测试方法
    public void changeSafePasswordTest() {
        result = new ResultMessage();
        Long id = 1l;
        String old_password = "123456";
        String new_password = "1234567";
        result = userController.changeSafePassword(old_password,id,new_password).getBody();
        logger.info("执行用户更改安全密码changeSafePassword 结果：" + result.getResultInfo());
    }



}
