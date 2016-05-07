package com.earl.carnet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.earl.carnet.commons.vo.TcpMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;
import com.earl.carnet.util.JPushForCar;
import com.earl.carnet.util.JPushForUser;

@Service("CarService")
@Transactional
public class CarServiceImpl extends BaseServiceImpl<Car, Car>
        implements CarService {

    private static Logger logger = Logger.getLogger(CarServiceImpl.class);

    @Resource
    CarDao carDao;

    @Resource
    JPushForUser jpushForUser;
    
    @Resource
    JPushForCar jpushForCar;
    
    @Override
    protected BaseDao<Car> getDao() {
        return carDao;
    }

    @Override
    public int update(Car car_update) {
        int update = getDao().updateByPrimaryKeySelective(car_update);
        return update;
    }

    @Override
    public Boolean updateCarByVin(Car model) {
        Boolean result = false;
        Car car_update = new Car();
        car_update.setVin(model.getVin());
        List<Car> carList = getDao().searchQuery(car_update);
        if (carList.size() != 0) {
            Car model_data = carList.get(0);
            model.setId(model_data.getId());
            monitorCarLight(model,model_data);//车灯监听
            monitorAlarm(model,model_data);//汽车警报
            monitorEngineProperty(model,model_data);//发动机
            monitorMileage(model,model_data);//里程数
            monitorOil(model,model_data);//油量
            monitorState(model,model_data);//状态
            monitorTemperature(model,model_data);//温度
            monitorTransmission(model,model_data);//转速器
            int update = carDao.updateByPrimaryKeySelective(model);
//          int update = carDao.updateByNotSameParam(model,model_data);
            return true;
        }
        return result;
    }

    /**
     * 汽车警报器监听器
     * @param model
     * @param model_date
     */
    private void monitorAlarm(Car model,Car model_date){
        if (model.getCarAlarm() != model_date.getCarAlarm()){ //当与数据库不同时
            if (model.getCarAlarm() && model.getAlarmMessage()){     // 车灯坏了
                //TODO  发送信息通知车主
                logger.info("汽车警报器响了");
            }
        }
    }

    /**
     * 汽车状态监听器
     * @param model
     * @param model_date
     */
    private void monitorState(Car model,Car model_date){
        if (model.getCarState() != model_date.getCarState()){ //当与数据库不同时
            if (model.getCarState() && model.getStateMessage()){     // 车灯坏了
                //TODO  发送信息通知车主
                logger.info("汽车启动了");
            }
        }
    }

    /**
     * 发动机监听器
     * @param model
     * @param model_date
     */
    private void monitorEngineProperty(Car model,Car model_date){
        if (model.getEngineProperty() != model_date.getEngineProperty()){ //当与数据库不同时
            if (!model.getEngineProperty() && model.getPropertyMessage()){     // 车灯坏了
                //TODO  发送信息通知车主
                logger.info("发动机坏了");
            }
        }
    }

    /**
     * 转速器监听器
     * @param model
     * @param model_date
     */
    private void monitorTransmission(Car model,Car model_date){
        if (model.getTransmission() != model_date.getTransmission()){ //当与数据库不同时
            if (!model.getTransmission() && model.getPropertyMessage()){     // 车灯坏了
                //TODO  发送信息通知车主
                logger.info("转速器坏了");
            }
        }
    }

    /**
     * 车灯监听器
     * @param model
     * @param model_date
     */
    private void monitorCarLight(Car model,Car model_date){
        if (model.getCarLight() != model_date.getCarLight()){ //当与数据库不同时
            if (!model.getCarLight() && model.getPropertyMessage()){     // 车灯坏了
                //TODO  发送信息通知车主
                logger.info("车灯坏了");
                jpushForUser.sendPush_Alias(model.getUserId().toString(),"车灯坏了");
            }
        }
    }

    /**
     * 温度监听器
     * @param model
     * @param model_date
     */
    private void monitorTemperature(Car model,Car model_date){
        if (model.getTemperature()>model_date.getTemperature() && model.getTemperature()>=100) { //当汽车当前水温>数据库水温，并且数据库水温>=100度
            if (model.getTemperature()%5 < model_date.getTemperature()%5) { //避免多次发送信息，每升高5个单位的温度就通知车主一次
                //TODO  发送信息通知车主
                logger.info("汽车温度过高，需要降温");
            }
        }
    }

    /**
     * 油箱监听器
     * @param model
     * @param model_date
     */
    private void monitorOil(Car model,Car model_date){
        if (model.getOil()<model_date.getOil() && model_date.getOil()<model_date.getOilBox()*0.2) { //当前油量<数据库油量 并且 数据库油量剩余不足20%
            if (model.getOil()%5 < model_date.getOil()%5) { //避免多次发送信息，每降低5个单位量的油量就通知车主一次
                //TODO  发送信息通知车主
                logger.info("汽车油量不足，请及时加油");
            }
        }
    }

    /**
     * 里程表监听器
     * @param model
     * @param model_date
     */
    private void monitorMileage(Car model,Car model_date){
        if (model.getMileage()%15000 < model_date.getMileage()%15000 ){
            //TODO 发信息通知车主
            logger.info("汽车已行驶超过15000公里，请及时对汽车进行检查维修");
        }
    }


    @Override
    public List<Car> getAllCarByUser(Long userId) {
        Car car = new Car();
        car.setUserId(userId);
        List<Car> carList = getDao().searchQuery(car);
        return carList;
    }

    @Override
    public int saveCar(Car car) {
        Car car_data = new Car();
        car_data.setVin(car.getVin());
        List<Car> carList = getDao().searchQuery(car_data);
        if (carList.size() == 0) {
            car.setAlarmMessage(true);
            car.setPropertyMessage(true);
            car.setStateMessage(true);
            return insertBackId(car);
        } else {
            throw new SecurityException("该车辆已经被注册");
        }
    }



}
