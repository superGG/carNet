package com.earl.carnet.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.service.RepairShopService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/repairShop")
// @Api(value="Person Rest Service") //修改生成的son路径
public class RepairShopController extends BaseController
{

	private final Logger log = LoggerFactory
			.getLogger(RepairShopController.class);

	@Autowired
	private RepairShopService repairShopService;

	private ResultMessage result = null;

	/**
	 * GET /brand -> get all the repairShop
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有维修店信息", notes = "find All repairShop", httpMethod = "GET", response = Brand.class, responseContainer = "List")
	public ResultMessage getAll() {
		log.debug("REST request to get all repairShop");
		result = new ResultMessage();
		result.getResultParm().put("repairShop", repairShopService.findAll());
		return result;
	}

}
