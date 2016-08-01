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
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.web.OrderController;

/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    private final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);

    ResultMessage result = null;

    @LocalServerPort
    public int port;
    
    @Resource
    private OrderController orderController;



    @Test
    //添加预约订单测试方法
    public void saveOrderTest() {
        result = new ResultMessage();
        Order order = new Order();
        order.setUserId(1l);
        order.setPlateNumber("粤A88888");
        order.setStationName("湖光加油站");
        order.setAddress("广东省湛江市麻章区湖光镇广东海洋大学主校区");
        order.setBrandName("中石油");
        order.setAgreementTime("2016-03-14 12:00:36");
        order.setType("97#");
        order.setUnits(1);
        order.setPrice(6.88);
        order.setNumber(29.06);
        order.setAmounts(200.0);
        result = orderController.saveOrder(order);
        logger.info("执行添加预约订单saveOrder 结果：" + result.getResultInfo());
    }

    @Test
    //获取用户所有订单测试方法
    public void getUserOrderTest() {
        Long userId = 1l;
        result = orderController.getUserOrder(userId);
        logger.info("执行获取用户所有订单方法getUserOrder 结果：" + result.getResultInfo());
    }

    @Test
    //查看订单明细测试方法
    public void getOrderByIdTest() {
        Long orderId = 116541613165l;
        result = orderController.getOrderById(orderId);
        logger.info("执行查看订单明细方法getOrderById 结果：" + result.getResultInfo());
    }

    @Test
    //删除订单测试方法
    public void deleteTest() {
        Long orderId = 116541613165l;
        result = orderController.delete(orderId).getBody();
        logger.info("执行删除订单方法delete 结果：" + result.getResultInfo());
    }
   



}
