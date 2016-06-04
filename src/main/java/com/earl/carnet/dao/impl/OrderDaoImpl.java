package com.earl.carnet.dao.impl;


import org.beetl.sql.core.db.KeyHolder;
import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.OrderDao;
import com.earl.carnet.domain.carnet.order.Order;

import java.util.Map;


@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {


    @Override
    public Long insertBackLongId(Order order) {
        //添加一条新纪录，返回该记录的id
            Map<String, Object> notNullProperties = getNotNullProperties(order);
            KeyHolder keyHolder = new KeyHolder();
            sqlManager.insert(Order.class, notNullProperties, keyHolder);
            return keyHolder.getLong();
    }
}