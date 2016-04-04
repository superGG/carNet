package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.dao.UserRoleDao;
import com.earl.carnet.domain.sercurity.car.Car;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.userrole.UserRole;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("carDao")
public class CarDaoImpl extends BaseDaoImpl<Car> implements CarDao {
   

}