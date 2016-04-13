package com.example.Server;


import com.earl.carnet.Application;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;
import cucumber.api.java.zh_cn.假如;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class AddUserTest {

    private static Logger logger = Logger.getLogger(AddUserTest.class);

    @Resource
    UserService userService;

    String username;

//    @假如("^用户的姓名叫(.*)")
//    public void username(String username) {
//        this.username = username;
//    }
//    @假如("^用户的姓名叫(.*)")
//    public void username(String username) {
//        this.username = username;
//    }    @假如("^用户的姓名叫(.*)")
//    public void username(String username) {
//        this.username = username;
//    }

}
