package com.earl.carnet.dao;


import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.carnet.Park.Park;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;

import java.util.List;

public interface ParkDao extends BaseDao<Park> {


    /**
     * 获取周围停车场信息.
     * @param lat
     * @param lon
     * @return
     */
    List<Park> getAroundPark(Double lat, Double lon);
}