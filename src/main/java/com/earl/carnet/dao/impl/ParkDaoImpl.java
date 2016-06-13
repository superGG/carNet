package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.ParkDao;
import com.earl.carnet.domain.carnet.Park.Park;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;
import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ParkDao")
public class ParkDaoImpl extends BaseDaoImpl<Park> implements ParkDao {

    @Override
    public List<Park> getAroundPark(Double lat, Double lon) {
        //经纬度 1度 = 111千米. 0.09 = 10 千米
        //获取做坐标点周围10千米范围内的停车场
        String range = "0.09";
        String sql = "select * from park r where r.lat<?+" + range + " and r.lat>?-" + range + " and r.lon <?+" + range + " and r.lon>?-" + range;
        SQLReady sqlReady = new SQLReady(sql, lat, lat, lon, lon);
        List<Park> list = sqlManager.execute(sqlReady, Park.class);
        return list;
    }
}