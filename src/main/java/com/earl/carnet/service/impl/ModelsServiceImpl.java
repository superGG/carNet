package com.earl.carnet.service.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.BrandDao;
import com.earl.carnet.dao.ModelsDao;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.carnet.models.Models;
import com.earl.carnet.service.BrandService;
import com.earl.carnet.service.ModelsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("ModelsService")
@Transactional
public class ModelsServiceImpl extends BaseServiceImpl<Models,Models>
        implements ModelsService {

    private static Logger logger = Logger.getLogger(ModelsServiceImpl.class);

    @Resource
    ModelsDao modelDao;

    @Override
    protected BaseDao<Models> getDao() {
        return modelDao;
    }
}
