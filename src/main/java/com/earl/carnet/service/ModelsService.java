package com.earl.carnet.service;

import java.util.List;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.models.Models;

public interface ModelsService extends BaseService<Models, Models> {

    /**
     * 获取某品牌下所有车辆型号.
     * @return
     */
    List<Models> findModelsByBrand(Long brandId);
	
}