package com.example.dao;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.earl.carnet.Application;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserDaoTest {

    private static Logger logger = Logger.getLogger(UserDaoTest.class);

    @Resource
    UserService userService;

    @Test
    public void contextLoads() {
//		userService.countAll();
    }

    @Test
    public void saveUser() {

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
