package com.earl.carnet.web;

import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.carnet.models.Models;
import com.earl.carnet.service.BrandService;
import com.earl.carnet.service.ModelsService;
import com.wordnik.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/models")
//@Api(value="Person Rest Service") //修改生成的son路径
public class ModelsController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(ModelsController.class);

	@Autowired
	private ModelsService modelsService;

	/**
	 * GET /models -> get all the models
	 */
	@RequestMapping(value = "/{brandId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到某品牌的所有型号", notes = "find All mdoels by brand",httpMethod="GET",response=String.class)
	public ResponseEntity<List<Models>> getAll(@PathVariable Long brandId,
											   HttpServletResponse response) {
		log.debug("REST request to get all models");
		return new ResponseEntity<List<Models>>(modelsService.findModelsByBrand(brandId), HttpStatus.OK);
	}
	
}
