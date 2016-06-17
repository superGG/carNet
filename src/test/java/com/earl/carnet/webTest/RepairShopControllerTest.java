package com.earl.carnet.webTest;

import com.earl.carnet.Application;
import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.Park.Park;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;
import com.earl.carnet.web.ParkController;
import com.earl.carnet.web.RepairShopController;
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
public class RepairShopControllerTest {

    private final Logger logger = LoggerFactory.getLogger(RepairShopControllerTest.class);

    ResultMessage result = null;

    @Resource
    private RepairShopController repairShopController;



    @Test
    //搜索附近维修店测试方法
    public void getAroundShopTest() {
        result = new ResultMessage();
        //当前位置坐标
        Double lon = 110.2131;
        Double lat = 21.2131;
        result = repairShopController.getAroundShop(lon,lat);
        logger.info("执行搜索附近维修店方法 getAroundShop 结果：" + result.getResultInfo());
    }

    @Test
    //查看维修店详情测试方法
    public void getParkByIdTest() {
        result = new ResultMessage();
        Long shopId = 1l;
        result = repairShopController.getShopById(shopId);
        logger.info("执行查看维修店详情方法 getParkById 结果：" + result.getResultInfo());
    }

    @Test
    //添加维修店测试方法
    public void saveTest() {
        result = new ResultMessage();
        RepairShop shop = new RepairShop();
        shop.setName("海大维修店");
        shop.setIntroduce("这里是海大维修店介绍");
        shop.setAddress("广东海洋大学");
        shop.setPhoneNumber("18320489492");
        shop.setLat(21.123123);
        shop.setLon(110.123123);
        result = repairShopController.save(shop).getBody();
        logger.info("执行添加维修店方法 save 结果：" + result.getResultInfo());
    }

    @Test
    //删除维修店测试方法
    public void deleteTest() {
        result = new ResultMessage();
        Long parkId = 1l;
        result = repairShopController.delete(parkId).getBody();
        logger.info("执行删除维修店方法 delete 结果：" + result.getResultInfo());
    }


}
