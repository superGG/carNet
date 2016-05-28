package com.earl.carnet.dao;


import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.carnet.car.Car;

import java.util.List;

public interface CarDao extends BaseDao<Car> {


    /**
     * 获取用户当前车辆.
     * @param userId
     * @return
     */
    List<Car> getCurrentCarByUser(Long userId);

    /**
     * 更新用户当前车辆
     * @param car
     * @return
     */
    Boolean updateUserCurrentCar(Car car);
}