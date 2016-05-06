package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import com.earl.carnet.domain.sercurity.user.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;

import java.util.List;

@Service("CarService")
@Transactional
public class CarServiceImpl extends BaseServiceImpl<Car, Car>
        implements CarService {

    private static Logger logger = Logger.getLogger(CarServiceImpl.class);

    @Resource
    CarDao carDao;

    @Override
    protected BaseDao<Car> getDao() {
        return carDao;
    }

    @Override
    public int update(Car car_update) {
        //TODO 重点，未测试
        int update = getDao().updateByPrimaryKeySelective(car_update);
//        if (update != 0){
//            Car car = getDao().findOneById(car_update.getId());
//            if ( car.getAlarmMessage() && car.getCarAlarm() == 1){
//                logger.info("警报响起，向车主发送信息");
//            } else if (car.getStateMessage() && car.getCarState() == 1){
//                logger.info("汽车启动，想车主发送信息");
//            } else if (car.getPropertyMessage() && (car.getEngineProperty() == 0 || car.getCarLight() == 0 || car.getTransmission() == 0|| car.getTemperature() >=100)) {
//                if(car.getEngineProperty() == 0 ) {
//                    logger.info("汽车的发动机出现故障，向车主发送信息");
//                }
//                if (car.getCarLight() == 0){
//                    logger.info("汽车的车灯出现故障，向车主发送信息");
//                    car.setCarLight((byte) 1);
//                    getDao().updateByPrimaryKeySelective(car);
//                }
//                if (car.getTemperature() >=100){
//                    logger.info("汽车的发动机过热，向车主发送信息");
//                }
//                if (car.getTransmission() == 0){
//                    logger.info("汽车的转速器出现故障，向车主发送信息");
//                }
//            } else if(car.getOil() < car.getOilBox()*0.2){
//                logger.info("汽车当前油量剩余不足20%，向车主发送信息");
//            } else if (car.getMileage()%120 == 0 ) {
//                logger.info("汽车每行驶120公里，向车主发送信息");
//            }
//            else {
//            	logger.info("车辆" + car.getId() + "一切正常，无需发送信息");
//                return update;
//            }
//        }
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
            int update = carDao.updateByPrimaryKeySelective(model);
//            int update = carDao.updateByNotSameParam(model,model_data);
            return true;
        }
        return result;
    }

    /**
     * 车灯监听器
     * @param model
     * @param model_date
     */
    private void monitorCarLight(Car model,Car model_date){
        if (model.getCarLight() != model_date.getCarLight()){ //当与数据库不同时
            if (!model.getCarLight()){     // 车灯坏了
                //TODO  发送信息通知车主
                logger.info("车灯坏了");
            }
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
