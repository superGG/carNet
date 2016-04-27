package com.example.server;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.earl.carnet.Application;
import com.earl.carnet.service.ModelsService;

//Cucumber

//@RunWith(Cucumber.class)
//@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class ModelsServiceTest {

    private static Logger logger = Logger.getLogger(ModelsServiceTest.class);

    @Resource
    ModelsService service;


    @Test
    public void testFindModelsByBrand() {
        long sysDate = System.currentTimeMillis();
        service.findModelsByBrand(2l).forEach(models1 -> System.out.println(models1.toString()));
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
