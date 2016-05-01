package com.earl.carnet.dao.impl;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;

@Repository("carDao")
public class CarDaoImpl extends BaseDaoImpl<Car> implements CarDao {
   

}