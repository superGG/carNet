package com.example.dao;


import javax.annotation.Resource;

import cucumber.api.java.zh_cn.假如;
import cucumber.api.junit.Cucumber;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.earl.carnet.Application;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;

//Cucumber

//@RunWith(Cucumber.class)
//@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class UserDaoTest {

    private static Logger logger = Logger.getLogger(UserDaoTest.class);

    @Resource
    UserService userService;

    @Test
    public void contextLoads() {
//		userService.countAll();
    }

    @假如("^我们(.*)")
    @Test
    public void saveUser(String action) {
//        System.out.println(action);
        User user = new User();
        user.setPassword("123456");
        user.setRealname("testName");
        user.setPhone("testPhone");
        user.setUsername("testUser");
        user.setLoginid("testLogin");
        user.setUserimg(".../user/testUser.jpg");

        userService.saveUser(user);
    }

    @Test
    public void testFinlAllUser() {
        long sysDate = System.currentTimeMillis();
        userService.findAll().forEach(user -> System.out.println(user.toString()));
        long sysDate2 = System.currentTimeMillis();
        logger.info(sysDate2 - sysDate);
//        System.out.println(sysDate2 - sysDate);
    }

    @Test
    public void testDeleUser() {
//        for (int i = 11506; i < 11907; i++)
//            userService.delete(11912);
    }

}
