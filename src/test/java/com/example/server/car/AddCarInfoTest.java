package com.example.server.car;


import com.earl.carnet.Application;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.exception.ApplicationException;
import com.earl.carnet.service.CarService;
import cucumber.api.java.zh_cn.并且;
import cucumber.api.java.zh_cn.当;
import cucumber.api.java.zh_cn.而且;
import cucumber.api.java.zh_cn.那么;
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
public class AddCarInfoTest {

    private static Logger logger = Logger.getLogger(AddCarInfoTest.class);

    @Resource
    CarService carService;

    @Resource
    CarDao carDao;

    Car car;

    @当("^用户扫描二维码，(.*)")
    public void getCarInfo(String carInfo){
        car = new Car();
        car.setMark("别克");
        car.setBrand("宝马");
        car.setModels("宝马");
        car.setVin("yilinfeng");
        car.setEngineNumber("sdlkfjsldkj");
        car.setRank("2门");
        car.setMileage(1000d);
        car.setOilBox(1000d);
        car.setCarState(true);
        car.setAlarmMessage(true);
        car.setTemperature(1000d);
        car.setEngineProperty(false);
        car.setCarLight(true);
        car.setTransmission(false);
    }

    @并且("^服务器(不存在)?相同的车辆信息")
    public void findnocar(String exits){
//            throw new ApplicationException("未实现");
        System.out.println("sldfj");
    }

    @那么("^添加车辆(.*)")
    public void success(String result){
//            throw new ApplicationException("为实现");
        System.out.println("slkdjf");
    }
}
