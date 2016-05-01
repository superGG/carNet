package com.earl.carnet.dao.impl;


import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.OrderDao;
import com.earl.carnet.domain.carnet.order.Order;


@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {
   

	
}