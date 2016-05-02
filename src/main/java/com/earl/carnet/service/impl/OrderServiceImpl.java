package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import com.earl.carnet.commons.util.GsonUtil;
import com.earl.carnet.commons.util.QRCodeUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.OrderDao;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.service.OrderService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.io.IOException;

@Service("OrderService")
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order,Order>
        implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

    /**
     * 二维码保存地址。
     */
    private String codefilePath;

    @Resource
    OrderDao orderDao;

    @Override
    protected BaseDao<Order> getDao() {
        return orderDao;
    }

    @Override
    public int saveOrder(Order order)  {
    	logger.info("进入service层的saveOrder方法");
        int orderId = orderDao.insertBackId(order);
        String order_json = GsonUtil.toJson(orderDao.findOneById(orderId)).toString();
        String orderId_json = GsonUtil.toJson(orderId).toString();
        try {
			String path = "src\\main\\webapp\\QRCodeImg"; //二维码保存路径
			File filePath = new File(path);
			FileOutputStream out = new FileOutputStream(filePath+"//"+orderId+".png");
			String rootPath = OrderServiceImpl.class.getClassLoader().getResource("").getPath(); //TODO 迁移到服务器上时使用
            QRCodeUtil.encode(orderId_json,"D:\\My Documents\\GitHub\\carNet\\src\\main\\webapp\\img\\earl.jpg",out,true);
            out.flush();
            out.close();
		} catch (Exception e) {
            throw new SecurityException("生成二维码失败",e);
		}
        return orderId;
    }
}
