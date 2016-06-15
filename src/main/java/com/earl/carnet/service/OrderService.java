package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.order.Order;
import com.google.zxing.WriterException;
import com.pingplusplus.model.Charge;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface OrderService extends BaseService<Order, Order> {

    /**
     * 添加新订单.
     * @param order
     * @return
     */
    Long saveOrder(Order order, HttpServletRequest request);

    /**
     * 获取所有订单.
     * @return
     */
    List<Order> findAllOrder();

    /**
     * 获取用户订单.
     * @param id
     * @return
     */
    List<Order> getUserOrder(Long id);

	Charge payForOrders(Long ordersId, String channel);

	void realPayOrders(Long valueOf);
}