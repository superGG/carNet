package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.OrderDao;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.service.OrderService;

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
