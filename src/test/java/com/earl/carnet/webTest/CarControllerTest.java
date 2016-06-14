package com.earl.carnet.webTest;

import com.earl.carnet.Application;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.web.CarController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CarControllerTest {

    private final Logger logger = LoggerFactory.getLogger(CarControllerTest.class);

    ResultMessage result = null;

    @Resource
    private CarController carController;



    @Test
    public void getAllCarByUserTest() {
        result = new ResultMessage();
        Long userId = 1l;
        result = carController.getAllCarByUser(userId);
        logger.info(result.toString());
    }



}
