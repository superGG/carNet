package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.RepairShopDao;
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;
import com.earl.carnet.service.RepairShopService;

@Service("repairShopService")
@Transactional
public class RepairShopServiceImpl extends BaseServiceImpl<RepairShop, RepairShop>
        implements RepairShopService {

//    private static Logger logger = Logger.getLogger(RepairShopServiceImpl.class);

    @Resource
    RepairShopDao repairShopDao;

    @Override
    protected BaseDao<RepairShop> getDao() {
        return repairShopDao;
    }
}
