package com.earl.carnet.web;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.security.shiro.ShiroPrincipal;
import com.earl.carnet.service.BrandService;
import com.earl.carnet.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/brand")
//@Api(value="Person Rest Service") //修改生成的son路径
public class BrandController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;

	private ResultMessage result = null;

	/**
	 * GET /brand -> get all the brand
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有品牌信息", notes = "find All brand",httpMethod="GET",response=String.class)
	public ResultMessage getAll() {
		log.debug("REST request to get all Brand");
		result = new ResultMessage();
		result.getResultParm().put("brand",brandService.findAll());
		log.info(result.toJson().toString());
		return result;
	}
	
}
