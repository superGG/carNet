package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.carnet.models.Models;

import java.util.List;

public interface ModelsService extends BaseService<Models, Models> {

    /**
     * 获取某品牌下所有车辆型号.
     * @return
     */
    List<Models> findModelsByBrand(Long brandId);
	
}