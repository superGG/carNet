package com.earl.carnet.service;

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
    Boolean updateCarByVin(Car car);
}