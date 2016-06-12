package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.Park.Park;

import java.util.List;

public interface ParkService extends BaseService<Park, Park> {


    /**
     * 获取周围停车场信息.
     * @param lat
     * @param lon
     * @return
     */
    List<Park> getAroundPark(Double lat, Double lon);
}