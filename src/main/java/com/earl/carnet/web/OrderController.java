package com.earl.carnet.web;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.order.Order;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.OrderService;
import com.earl.carnet.service.UserService;
import com.google.zxing.WriterException;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<Order> orders = orderService.findAll();
        List<Order> orderList = new ArrayList<Order>();
        User user = new User();
        for (Order order : orders) {
            user = userService.findOne(order.getUserId());
            order.setUserName("");
            if (user.getRealName() != null)
                order.setUserName(user.getRealName());
            orderList.add(order);
        }
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
            Order order) throws IOException, WriterException {
        log.info("进入controller层添加订单saveOrder方法");
        result = new ResultMessage();
        Long orderId = orderService.saveOrder(order);
        System.out.println("-----------------------"+orderId);
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
     * 删除订单.
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据id删除订单", notes = "delete order by id", httpMethod = "POST", response = ResultMessage.class)
    public ResponseEntity<ResultMessage> delete(
            @ApiParam(required = true, name = "id", value = "订单id")
            Long id) {
        log.info("进入controller层添加订单delete方法");
        result = new ResultMessage();
        orderService.delete(id);
        result.setResultInfo("删除订单成功");
        result.setServiceResult(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
