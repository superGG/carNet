package com.earl.carnet.service.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.BrandDao;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.BrandService;
import com.earl.carnet.service.CarService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("CarService")
@Transactional
public class CarServiceImpl extends BaseServiceImpl<Car,Car>
        implements CarService {

    private static Logger logger = Logger.getLogger(CarServiceImpl.class);

    @Resource
    CarDao carDao;

    @Override
    protected BaseDao<Car> getDao() {
        return carDao;
    }
}
