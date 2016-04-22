package com.earl.carnet.service.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.BrandDao;
import com.earl.carnet.dao.OrderDao;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.service.BrandService;
import com.earl.carnet.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("OrderService")
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order,Order>
        implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Resource
    OrderDao orderDao;

    @Override
    protected BaseDao<Order> getDao() {
        return orderDao;
    }
}
