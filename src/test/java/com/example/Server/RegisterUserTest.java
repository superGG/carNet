package com.example.Server;


import com.earl.carnet.Application;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;
import cucumber.api.java.Before;
import cucumber.api.java.zh_cn.*;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class RegisterUserTest {

    private static Logger logger = Logger.getLogger(RegisterUserTest.class);

    @Resource
    UserService userService;

    private User user = new User();

    private String loginid;
    private String password;


    @Before("@NeedBeforStep")
    public void clearUserData(){
        User user = new User();
        user.setLoginid("18719425973");
        userService.deleteByQuery(user);
    }

    @当("^填写的登录账号为(.*)")
    public void username(String loginid) {
        this.user.setLoginid(loginid);
    }

    @并且("^不存在重复注册的账号")
    public void noexit(){
        User user = userService.findOneByLoginId(this.user.getLoginid());
        if(user != null){
            throw new RuntimeException("改账号已经被注册");
        }
    }

    @而且("^填写的密码是(.*)")
    public void pass(String password){
        this.user.setPassword(password);
    }

    @那么("^看到的结果是(.*)")
    public void result(String result){

        User tmpuser = userService.findOneByLoginId(this.user.getLoginid());
        if(tmpuser == null){
            userService.saveUser(this.user);
            Assert.assertEquals("注册成功",result);
        }else{
            Assert.assertEquals("已存在该用户",result);
        }
    }

}
