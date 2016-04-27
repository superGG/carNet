package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;

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
        if (update != 0){
            Car car = getDao().findOneById(car_update.getId());
            if ( car.getAlarmMessage() && car.getCarAlarm() == 1){
                logger.info("警报响起，向车主发送信息");
            } else if (car.getStateMessage() && car.getCarState() == 1){
                logger.info("汽车启动，想车主发送信息");
            } else if (car.getPropertyMessage() && (car.getEngineProperty() == 0 || car.getCarLight() == 0 || car.getTransmission() == 0|| car.getTemperature() >=100)) {
                if(car.getEngineProperty() == 0 ) {
                    logger.info("汽车的发动机出现故障，向车主发送信息");
                }
                if (car.getCarLight() == 0){
                    logger.info("汽车的车灯出现故障，向车主发送信息");
                    car.setCarLight((byte) 1);
                    getDao().updateByPrimaryKeySelective(car);
                }
                if (car.getTemperature() >=100){
                    logger.info("汽车的发动机过热，向车主发送信息");
                }
                if (car.getTransmission() == 0){
                    logger.info("汽车的转速器出现故障，向车主发送信息");
                }
            } else if(car.getOil() < car.getOilBox()*0.2){
                logger.info("汽车当前油量剩余不足20%，向车主发送信息");
            } else if (car.getMileage()%120 == 0 ) {
                logger.info("汽车每行驶120公里，向车主发送信息");
            }
            else {
            	logger.info("车辆" + car.getId() + "一切正常，无需发送信息");
                return update;
            }
        }
        return update;
    }
}
