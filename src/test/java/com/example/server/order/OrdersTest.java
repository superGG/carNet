package com.example.server.order;


import com.earl.carnet.Application;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;
import cucumber.api.java.zh_cn.同时;
import cucumber.api.java.zh_cn.当;
import cucumber.api.java.zh_cn.那么;
import junit.framework.Assert;
import org.apache.log4j.Logger;
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
public class OrdersTest {

    private static Logger logger = Logger.getLogger(OrdersTest.class);

}
