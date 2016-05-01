package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.order.Order;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface OrderService extends BaseService<Order, Order> {

    /**
     * 添加新订单.
     * @param order
     * @return
     */
    int saveOrder(Order order) throws IOException, WriterException;
}