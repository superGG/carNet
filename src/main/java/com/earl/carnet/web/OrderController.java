package com.earl.carnet.web;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.service.OrderService;
import com.wordnik.swagger.annotations.ApiOperation;

import java.io.IOException;

@RestController
@RequestMapping(value = "/order")
//@Api(value="Person Rest Service") //修改生成的son路径
public class OrderController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	private ResultMessage result = null;

	/**
	 * GET /order -> get all the order
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有订单信息", notes = "find All order",httpMethod="GET",response=String.class)
	public ResultMessage getAll() {
		log.debug("REST request to get all Order");
		result = new ResultMessage();
		result.getResultParm().put("order",orderService.findAll());
		log.info(result.toJson().toString());
		return result;
	}

	/**
	 * 添加订单.
	 * @return
	 */
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "添加一个新订单", notes = "add a new order",httpMethod="POST",response=String.class)
	public ResultMessage saveOrder(Order order) throws IOException, WriterException {
		log.info("进入controller层添加订单saveOrder方法");
		result = new ResultMessage();
		int orderId = orderService.saveOrder(order);
		System.out.println(orderId);
		if (orderId !=0) {
			Order new_order = orderService.findOne(orderId);
			result.getResultParm().put("order",new_order);
			result.setResultInfo("添加订单成功");
			result.setServiceResult(true);
		}else {
			result.setResultInfo("添加订单失败");
			result.setServiceResult(false);
		}
		log.info(result.toJson().toString());
		return result;
	}

	/**
	 * 更新订单.
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "更新订单信息", notes = "update order message",httpMethod="POST",response=String.class)
	public ResponseEntity<?> update(Order order) {
		log.info("进入controller层添加订单update方法");
		result = new ResultMessage();
		if(orderService.updateByPrimaryKeySelective(order) != 0) {
			result.setServiceResult(true);
		} else {
			result.setServiceResult(false);
			result.setResultInfo("更新失败");
		}
		log.info(result.toJson().toString());
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

	/**
	 * 删除订单.
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "根据id删除订单", notes = "delete order by id",httpMethod="POST",response=String.class)
	public ResponseEntity<?> delete(Long id) {
		log.info("进入controller层添加订单delete方法");
		result = new ResultMessage();
		orderService.delete(id);
		result.setResultInfo("删除订单成功");
		result.setServiceResult(true);
		log.info(result.toJson().toString());
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

	
}
