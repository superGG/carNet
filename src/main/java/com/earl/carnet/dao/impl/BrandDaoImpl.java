package com.earl.carnet.dao.impl;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.BrandDao;
import com.earl.carnet.domain.carnet.brand.Brand;

@Repository("brandDao")
public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao {

}