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
import com.earl.carnet.domain.carnet.Park.Park;
import com.earl.carnet.web.ParkController;

import net.sf.ehcache.CacheException;

/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ParkControllerTest {

    private final Logger logger = LoggerFactory.getLogger(ParkControllerTest.class);

    @LocalServerPort
    public int port;
    
    ResultMessage result = null;

    @Resource
    private ParkController parkController;



    @Test
    //搜索附近停车场测试方法
    public void getAroundParkTest() {
        result = new ResultMessage();
        //当前位置坐标
        Double lon = 110.2131;
        Double lat = 21.2131;
        result = parkController.getAroundShop(lon,lat);
        logger.info("执行搜索附近停车场方法 getAroundShop 结果：" + result.getResultInfo());
    }

    @Test
    //查看停车场详情测试方法
    public void getParkByIdTest() {
        result = new ResultMessage();
        Long parkId = 1l;
        result = parkController.getParkById(parkId);
        logger.info("执行查看停车场详情方法 getParkById 结果：" + result.getResultInfo());
    }

    @Test
    //添加停车场测试方法
    public void saveTest() {
        result = new ResultMessage();
        Park park = new Park();
        park.setName("海大停车场");
        park.setIntroduce("这里是海大停车场介绍");
        park.setAddress("广东海洋大学");
        park.setPhoneNumber("18320489492");
        park.setLat(21.123123);
        park.setLon(110.123123);
        result = parkController.save(park).getBody();
        logger.info("执行添加停车场方法 save 结果：" + result.getResultInfo());
    }

    @Test
    //删除停车场测试方法
    public void deleteTest() {
        result = new ResultMessage();
        Long parkId = 1l;
        result = parkController.delete(parkId).getBody();
        logger.info("执行删除停车场方法 delete 结果：" + result.getResultInfo());
    }
    
    //TODO 这里有线程知识，在同一个线程中，对象锁可以重入,重新进入
    public static void mytest(){
    	synchronized (ParkController.class){
    		System.out.println("djdjdjdjdjd1111");
    	newInstance();

    	}

    	}
    private static String newInstance() throws CacheException {
        synchronized (ParkController.class) {System.out.println("djdjdjdjdjd");
            return "结果：";
        }
    }
    public static void main(String[] dodo){
    	mytest();
    	}}
