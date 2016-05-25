package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;

import java.util.List;

public interface RepairShopService extends BaseService<RepairShop, RepairShop> {


    /**
     * 获取周围维修站信息.
     * @param lat
     * @param lon
     * @return
     */
    List<RepairShop> getAroundShop(Double lat, Double lon);
}