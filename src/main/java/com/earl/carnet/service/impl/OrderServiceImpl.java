package com.earl.carnet.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.QRCodeUtil;
import com.earl.carnet.dao.OrderDao;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.OrderService;
import com.earl.carnet.service.UserService;
import com.earl.carnet.util.PayChargeUtil;
import com.pingplusplus.model.Charge;

@Service("OrderService")
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order, Order> implements
        OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserService userService;

    /**
     * 二维码保存地址。
     */
    private String codefilePath;

    private Integer UNPAY = 1; // 订单状态：未支付

    private Integer PAYED = 0;// 订单状态：已支付

    private Integer PAST = 2;// 订单状态：已过期

    @Resource
    OrderDao orderDao;

    @Override
    protected BaseDao<Order> getDao() {
        return orderDao;
    }

    @Override
    public Long saveOrder(Order order, HttpServletRequest request) {
        logger.info("进入service层的saveOrder方法");
        String URL = "localhost:8080";
        order.setState(UNPAY);
        order = confirmOrder(order);
        Long orderId = orderDao.insertBackLongId(order);
        String OrderUrl = URL + "/order/getOrderById=" + orderId.toString();
        logger.info("-------------------------------------二维码内容：" + OrderUrl);

        String rootPath = request.getSession().getServletContext()
                .getRealPath("/");
        logger.info("-------------- rootPath" + rootPath);
        try {
            String path = rootPath + "QRCodeImg"; // 二维码保存路径
            File filePath = new File(path);
            if (!filePath.exists()) {
                logger.info("不存在QRCodeImg文件，自动创建");
                filePath.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath + "//"
                    + orderId + ".png");
            QRCodeUtil.encode(OrderUrl, rootPath + "img\\earl.jpg", out, true);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DomainSecurityException("生成二维码失败");
        }
        return orderId;
    }

    @Override
    public List<Order> findAllOrder() {
        List<Order> orders = orderDao.findAll();
        // 检查更新订单状态
        orders = updateOutOfDateOrderState(orders);
//		List<Order> orderList = addUserName(orders);
        return orders;
    }

    @Override
    public List<Order> getUserOrder(Long id) {
        Order order = new Order();
        order.setUserId(id);
        List<Order> orders = orderDao.searchAccurate(order);
        // 检查更新订单状态
        orders = updateOutOfDateOrderState(orders);
        List<Order> orderList = addUserName(orders, id);
        return orderList;
    }

    /**
     * 给订单添加用户名.
     *
     * @param orders
     * @return
     */
    private List<Order> addUserName(List<Order> orders, Long id) {
        List<Order> orderList = new ArrayList<Order>();
        User user = new User();
        user = userService.findOne(id);
        for (Order order : orders) {
            order.setUserName("");
            if (user.getRealName() != null)
                order.setUserName(user.getRealName());
            orderList.add(order);
        }
        return orderList;
    }
    
	@Override
	public Charge payForOrders(Long ordersId, String channel) {
		Order orderPo = orderDao.findOneById(ordersId);
		Double price = orderPo.getAmounts() * 100;
		Charge charge = PayChargeUtil.charge(orderPo.getId(),
				price.longValue(), channel, orderPo.getStationName(),
				orderPo.getUserName());
		return charge;
	}


    /**
     * 检查更新所有订单状态
     */
    private List<Order> updateOutOfDateOrderState(List<Order> orders) {
        List<Order> orderList = new ArrayList<Order>();
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("--------------nowDate:" + nowDate);
        for (Order order : orders) {
            try {
                Date orderDate = sdf.parse(order.getAgreementTime());
                if (order.getState()!=PAST &&orderDate.getTime() < nowDate.getTime()) {
                    logger.info("------------------------该订单已过期"
                            + order.getId());
                    order.setState(PAST);
                    getDao().updateByPrimaryKeySelective(order);
                }
            } catch (ParseException e) {
                throw new DomainSecurityException("Date格式报错");
            }
            orderList.add(order);
        }
        return orderList;
    }

    /**
     * 确认订单 单价*数量 = 总价
     */
    private Order confirmOrder(Order model) {
        if (model.getPrice() * model.getNumber() != model.getAmounts()) {
            model.setAmounts(model.getPrice() * model.getNumber());
        }
        return model;
    }

	@Override
	public void realPayOrders(Long orderId) {
		// TODO Auto-generated method stub
		updateOrderState(orderId, PAYED);
		
	}
	
	private void updateOrderState(Long orderId,Integer status){
		Order order = orderDao.findOneById(orderId);
		order.setState(status);
		orderDao.updateByPrimaryKeySelective(order);
	}

}
