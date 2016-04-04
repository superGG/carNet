package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.BrandDao;
import com.earl.carnet.domain.sercurity.brand.Brand;
import org.springframework.stereotype.Repository;

@Repository("brandDao")
public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao{
   

}