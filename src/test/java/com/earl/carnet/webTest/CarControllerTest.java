package com.earl.carnet.webTest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.web.CarController;

/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerTest {

    private final Logger logger = LoggerFactory.getLogger(CarControllerTest.class);

    ResultMessage result = null;

    @Resource
    private CarController carController;



    @Test
    //获取指定用户所有车辆测试方法
    public void getAllCarByUserTest() {
        result = new ResultMessage();
        Long userId = 1l;
        result = carController.getAllCarByUser(userId);
        logger.info("执行获取用户所有车辆方法getAllCarByUser 结果：" + result.getResultInfo());
    }

    @Test
    //添加新车辆测试方法
    public void saveCarTest() {
        result = new ResultMessage();
         Car car = new Car();

        car.setMark("img/aodi.jpg");
        car.setBrand("奥迪");
        car.setModels("A5");
        car.setVin("12312312");
        car.setEngineNumber("32132132");
        car.setRank("二门二坐");
        car.setMileage(455.5);
        car.setOilBox(200.0);
        car.setOil(46.9);
        car.setTemperature(100.0);
        car.setEngineProperty(true);
        car.setTransmission(true);
        car.setCarLight(true);
        car.setCarState(false);
        car.setCarAlarm(false);
        car.setSRS(false);
        car.setLon(110.364977);
        car.setLat(21.274989);

        //测在系统生成二维码并将车辆信息在服务器端缓存
        result = carController.saveTem_Car(car).getBody();
        logger.info("执行车载系统生成二维码saveTem_Carr结果：" + result.getResultInfo());
        if (result.getServiceResult()) { //车载系统生成二维码成功
            car.setUserId(1l);
            car.setPlateNumber("粤A88888");
            result = carController.saveCar(car).getBody(); //用户扫描二维码添加车辆
            logger.info("执行添加车辆方法saveCar结果：" + result.getResultInfo());
            Car new_car = (Car) result.getResultParm().get("car");
            if (new_car != null) {
                ResultMessage result_delete = carController.delete(new_car.getId()).getBody();//删除车辆
                logger.info("执行删除车辆方法delete结果：" + result_delete.getResultInfo());
            }
            logger.info(result.toString());
        }
    }

    @Test
    //获取车辆详情测试方法
    public void getCarTest() {
        result = new ResultMessage();
        Long id = 1l;
        result = carController.getCar(id);
        logger.info("执行获取车辆详情方法getCar 结果：" + result.getResultInfo());
    }

    @Test
    //更改车辆车牌号测试
    public void updateTest() {
        result = new ResultMessage();
        Car car = new Car();
        car.setId(1l);
        car.setPlateNumber("粤A88888");
        result = carController.update(car).getBody();
        logger.info("执行更改车牌号方法update 结果：" + result.getResultInfo());
    }


}
