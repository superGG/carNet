package com.earl.carnet.service.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.ParkDao;
import com.earl.carnet.domain.carnet.Park.Park;
import com.earl.carnet.service.ParkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("parkService")
@Transactional
public class ParkServiceImpl extends BaseServiceImpl<Park, Park>
        implements ParkService {

//    private static Logger logger = Logger.getLogger(RepairShopServiceImpl.class);

    @Resource
    ParkDao parkDao;

    @Override
    protected BaseDao<Park> getDao() {
        return parkDao;
    }

    @Override
    public List<Park> getAroundPark(Double lat, Double lon) {
        List<Park> shopList = parkDao.getAroundPark(lat,lon);
        return shopList;
    }
}
