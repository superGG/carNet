package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.BrandDao;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.service.BrandService;

@Service("BrandService")
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand,Brand>
        implements BrandService {

//    private static Logger logger = Logger.getLogger(BrandServiceImpl.class);

    @Resource
    BrandDao brandDao;

    @Override
    protected BaseDao<Brand> getDao() {
        return brandDao;
    }
}
