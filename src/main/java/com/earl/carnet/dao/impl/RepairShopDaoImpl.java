package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.RepairShopDao;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;
import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repairShopDao")
public class RepairShopDaoImpl extends BaseDaoImpl<RepairShop> implements RepairShopDao {

    @Override
    public List<RepairShop> getAroundShop(Double lat, Double lon) {
        //经纬度 1度 = 111千米. 0.09 = 10 千米
        //获取做坐标点周围10千米范围内的维修店
        String range = "0.09";
        String sql = "select * from repairshop r where r.lat<?+" + range + " and r.lat>?-" + range + " and r.lon <?+" + range + " and r.lon>?-" + range;
        SQLReady sqlReady = new SQLReady(sql, lat, lat, lon, lon);
        List<RepairShop> list = sqlManager.execute(sqlReady, RepairShop.class);
        return list;
    }
}