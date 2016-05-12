package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.RepairShopDao;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;
import org.springframework.stereotype.Repository;

@Repository("repairShopDao")
public class RepairShopDaoImpl extends BaseDaoImpl<RepairShop> implements RepairShopDao {

}