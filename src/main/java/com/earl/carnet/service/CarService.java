package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.domain.carnet.tem_car.Tem_Car;

import java.util.List;

public interface CarService extends BaseService<Car, Car> {

    /**
     * 更新汽车信息。
     * @param car
     * @return
     */
    int update(Car car);

    /**
     * 车架号更新车辆信息.
     * @param vin
     * @return
     */
    Boolean updateCarByVin(Car car) throws Exception;

    /**+
     * 获取用户的所有汽车.
     * @param userId
     * @return
     */
    List<Car> getAllCarByUser(Long userId);

    /**
     * 添加车辆.
     * @param car
     * @return
     */
    int saveCar(Car car);

    /**
     * 根据车架号获取车辆信息.
     * @param vin
     * @return
     */
    Tem_Car getCarByVin(String vin);

    /**
     * 用户请求更新汽车状态.
     * @param id
     * @return
     */
    Boolean updateCarState(Long id);

    /**
     * 用户请求更新汽车警报状态.
     * @param id
     * @return
     */
    Boolean updateCarAlarm(Long id);

    /**
     * 添加临时车辆信息.
     * @param tem_car
     */
    void insertTem_Car(Tem_Car tem_car);
}