package com.earl.carnet.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.OrderService;
import com.earl.carnet.service.UserService;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/order")
//@Api(value="Person Rest Service") //修改生成的son路径
public class OrderController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    private ResultMessage result = null;

    /**
     * GET /order -> get all the order
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有订单信息", notes = "find All order", httpMethod = "GET", response = Order.class, responseContainer = "List")
    public ResultMessage getAll() {
        log.debug("REST request to get all Order");
        List<Order> orderList = orderService.findAllOrder();
        result = new ResultMessage();
        result.getResultParm().put("order", orderList);
        return result;
    }

    /**
     * GET /order -> get order by id
     */
    @Valid
    @RequestMapping(value = "/getOrderById={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到订单明细", notes = "get order by id", httpMethod = "GET", response = Order.class, responseContainer = "List")
    public ResultMessage getOrderById(
            @PathVariable
            @ApiParam(required = true, name = "id", value = "订单id")
            @NotNull(message = "id不能为空")
            Long id) {
        log.debug("REST request to get all Order");
        Order order = orderService.findOne(id);
        User user = userService.findOne(order.getUserId());
        order.setUserName("");
        if (user.getRealName() != null) order.setUserName(user.getRealName());
        result = new ResultMessage();
        result.getResultParm().put("order", order);
        return result;
    }

    /**
     * GET /order -> get order by userId
     */
    @Valid
    @RequestMapping(value = "/getUserOrder={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到订单明细", notes = "get order by userId", httpMethod = "GET", response = Order.class, responseContainer = "List")
    public ResultMessage getUserOrder(
            @PathVariable
            @ApiParam(required = true, name = "id", value = "订单id")
            @NotNull(message = "id不能为空")
            Long id) {
        log.debug("REST request to get order by userId");
        List<Order> orderList = orderService.getUserOrder(id);
        result = new ResultMessage();
        result.getResultParm().put("order", orderList);
        return result;
    }


    /**
     * 添加订单.
     *
     * @return
     */
    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加一个新订单", notes = "add a new order", httpMethod = "POST", response = ResultMessage.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plateNumber", value = "车牌号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "stationName", value = "加油站名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "加油站地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "brandName", value = "加油站品牌 （中石油、中石化）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "agreementTime", value = "预约时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "加油类别 E90  E93 E97", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "units", value = "加油单位  元/升", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "每单位价格", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "加油数量", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "amounts", value = "总价", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "订单状态", required = true, dataType = "Integer", paramType = "query")
    })
    public ResultMessage saveOrder(
            @ApiParam(required = false, name = "order", value = "订单实体,这个字段不要理！！！")
            Order order) {
        log.info("进入controller层添加订单saveOrder方法");
        result = new ResultMessage();
        Long orderId = orderService.saveOrder(order);
        System.out.println("-----------------------" + orderId);
        if (orderId != 0) {
            Order new_order = orderService.findOne(orderId);
            result.getResultParm().put("order", new_order);
            result.setResultInfo("添加订单成功");
            result.setServiceResult(true);
        } else {
            result.setResultInfo("添加订单失败");
            result.setServiceResult(false);
        }
        return result;
    }

    /**
     * 更新订单.
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新订单信息", notes = "update order message", httpMethod = "POST", response = ResultMessage.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id(不能为空)", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "plateNumber", value = "车牌号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "stationName", value = "加油站名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "加油站地址", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "brandName", value = "加油站品牌 （中石油、中石化）", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "agreementTime", value = "预约时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "加油类别 E90  E93 E97", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "units", value = "加油单位  元/升", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "每单位价格", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "加油数量", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "amounts", value = "总价", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "订单状态", required = false, dataType = "Integer", paramType = "query")
    })
    public ResponseEntity<ResultMessage> update(
            @ApiParam(required = false, name = "order", value = "订单实体，这个字段不要理！！！")
            Order order) {
        log.info("进入controller层添加订单update方法");
        if (order.getId() == null) throw new DomainSecurityException("所更新的订单id不能为空");
        result = new ResultMessage();
        if (orderService.updateByPrimaryKeySelective(order) != 0) {
            result.setServiceResult(true);
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
	 * 真实支付订单，修改订单状态为未发货.
	 * @author 黄祥谦.
     * @throws Exception 
	 * @throws IOException 
	 */
    @RequestMapping(value = "/realPayOrders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage> realPayOrders(HttpServletRequest request,HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF8");
        //获取头部所有信息
        @SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key+" "+value);
        }
        // 获得 http body 内容
        BufferedReader reader = request.getReader();
        StringBuilder buffer = new StringBuilder();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        // 解析异步通知数据
        Event event = Webhooks.eventParse(buffer.toString());
        if ("charge.succeeded".equals(event.getType())) {
           response.setStatus(200);
        } else if ("refund.succeeded".equals(event.getType())) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        Map<String, Object> object = event.getData();
        @SuppressWarnings("unchecked")
		Map<String, Object> object2 = (Map<String, Object>) object.get("object");
        String object3 = (String) object2.get("order_no");
        System.out.println("order_no:"+object3);
        orderService.realPayOrders(Long.valueOf(object3));
		ResultMessage result = new ResultMessage();
		result.setResultInfo("支付成功");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    
    /**
     * 删除订单.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据id删除订单", notes = "delete order by id", httpMethod = "POST", response = ResultMessage.class)
    public ResponseEntity<ResultMessage> delete(
            @ApiParam(required = true, name = "id", value = "订单id")
            @NotNull(message = "id不能为空")
            Long id) {
        log.info("进入controller层添加订单delete方法");
        result = new ResultMessage();
        orderService.delete(id);
        result.setResultInfo("删除订单成功");
        result.setServiceResult(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
	/**
	 * 支付订单费用.
	 * @author 黄祥谦.
	 */
    @Valid
    @RequestMapping(value = "/payOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage> payForOrdersWithAlipay(  @NotNull(message = "订单id") Long orderId,  @NotNull(message = "支付渠道不能为空") String channel){
		Charge charge = orderService.payForOrders(orderId, channel);
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("charge", charge);
		result.setResultInfo("下单成功");
        result.setServiceResult(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}


}
