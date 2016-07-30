package com.earl.carnet.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.models.Models;
import com.earl.carnet.service.ModelsService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/models")
//@Api(value="Person Rest Service") //修改生成的son路径
public class ModelsController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(ModelsController.class);

	@Autowired
	private ModelsService modelsService;

	/**
	 * GET /users -> get all the models
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有用户信息", notes = "find All Models", httpMethod = "GET", response = Models.class,responseContainer = "List")
	public ResultMessage getAll() {
		log.debug("REST request to get all Models");
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("models", modelsService.findAll());
		return result;
	}


	/**
	 * GET /models -> get all the models by the brandId
	 */
	@RequestMapping(value = "/brandId={brandId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到某品牌的所有型号", notes = "find All mdoels by brand",httpMethod="GET",response=ResultMessage.class)
	public ResultMessage getModelsByBrandId(
			@ApiParam(required = true, name = "brandId", value = "品牌id")
			@PathVariable
			Long brandId) {
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("models",modelsService.findModelsByBrand(brandId));
		return result;
	}


	
}
