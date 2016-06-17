package com.earl.carnet.dao.impl;

import java.util.List;

import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;

@Repository("carDao")
public class CarDaoImpl extends BaseDaoImpl<Car> implements CarDao {


    @Override
    public List<Car> getCurrentCarByUser(Long userId) {
        String sql = "select * from car c where c.userId = ? and currentCar = true";
        SQLReady sqlReady = new SQLReady(sql,userId);
        List<Car> carList = sqlManager.execute(sqlReady, Car.class);
        return carList;
    }

    @Override
    public Boolean updateUserCurrentCar(Car car) {
        SQLReady sqlReady;
        //先将用户所有的车辆currentCar字段置为false
        String sql_else_car = "update car set currentCar = false where userId = ?";
        sqlReady = new SQLReady(sql_else_car,car.getUserId());
        sqlManager.executeUpdate(sqlReady);

        //再将设置id车辆的currentCar字段置为true
        String sql_current = "update car set currentCar = true where id=?";
        sqlReady = new SQLReady(sql_current,car.getId());
        sqlManager.executeUpdate(sqlReady);
        return true;
    }
}