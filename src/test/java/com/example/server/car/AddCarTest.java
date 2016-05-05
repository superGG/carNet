package com.example.server.car;


import com.earl.carnet.Application;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;
import com.earl.carnet.service.UserService;
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
public class AddCarTest {

    private static Logger logger = Logger.getLogger(AddCarTest.class);

    @Resource
    CarService carService;

    @Resource
    CarDao carDao;


    @当("用户扫描二维码，得到车辆信息")
    public void getCarInfo(){
        Car car = new Car();
        car.setMark("别克");
        car.setBrand("宝马");
        car.setModels("宝马");
        car.setVin("yilinfeng");
        car.setEngineNumber("sdlkfjsldkj");
        car.setRank("2门");
        car.setMileage(1000d);
        car.setOilBox(1000d);
        car.setCarState((byte) 1);
        car.setAlarmMessage(true);
        car.setTemperature(1000d);
        car.setEngineProperty((byte) 1);
        car.setCarLight((byte) 1);
        car.setTransmission((byte) 1);
    }
    @而且("服务器不存在相同的车辆信息")
    public void findnocar(){

    }

    @那么("添加成功")
    public void success(){



    }
}
