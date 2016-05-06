package com.earl.carnet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.ModelsDao;
import com.earl.carnet.domain.carnet.models.Models;
import com.earl.carnet.service.ModelsService;

@Service("ModelsService")
@Transactional
public class ModelsServiceImpl extends BaseServiceImpl<Models,Models>
        implements ModelsService {

    private static Logger logger = Logger.getLogger(ModelsServiceImpl.class);

    @Resource
    ModelsDao modelsDao;

    @Override
    protected BaseDao<Models> getDao() {
        return modelsDao;
    }

    @Override
    public List<Models> findModelsByBrand(Long brandId) {
        Models models = new Models();
        models.setBrandId(brandId);
        return modelsDao.searchQuery(models);
    }
}
