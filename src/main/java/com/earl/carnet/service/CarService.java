package com.earl.carnet.service;

import java.util.List;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.car.Car;

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
//     * 根据车架号获取车辆信息.
//     * @param vin
//     * @return
//     */
//    Car getTem_CarByVin(String vin);

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
    void insertTem_Car(Car tem_car);

    /**
     * 更新用户当前车辆
     * @param car
     * @return
     */
    Boolean updateUserCurrentCar(Car car);

    /**
     * 获取用户当前车辆.
     * @param userId
     * @return
     */
    List<Car> getCurrentCarByUser(Long userId);

    /**
     * 根据车架号获取车辆信息.
     * @param vin
     * @return
     */
    Car getCarByVin(String vin);

    Car getTmpCarByVin(String vin);

    /**
     * 删除非当前车辆.
     * @param id
     */
    void deleteCar(Long id);
}