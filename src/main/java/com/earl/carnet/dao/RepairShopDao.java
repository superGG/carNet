package com.earl.carnet.dao;


import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;

import java.util.List;

public interface RepairShopDao extends BaseDao<RepairShop> {


    /**
     * 获取周围加油站信息.
     * @param lat
     * @param lon
     * @return
     */
    List<RepairShop> getAroundShop(Double lat, Double lon);
}