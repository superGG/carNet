package com.earl.carnet.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.service.CarService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/car")
//@Api(value="Person Rest Service") //修改生成的son路径
public class CarController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(CarController.class);

	@Autowired
	private CarService carService;

	private ResultMessage result = null;

	/**
	 * GET /car -> get all the car
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有车辆信息", notes = "find All car",httpMethod="GET",response=String.class)
	public ResultMessage getAll() {
		log.debug("REST request to get all Car");
		result = new ResultMessage();
		result.getResultParm().put("order",carService.findAll());
		log.info(result.toJson().toString());
		return result;
	}
	
}
