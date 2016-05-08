package com.earl.carnet.web;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.service.VerifyCodeService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/verifyCode")
//@Api(value="Person Rest Service") //修改生成的son路径
public class VerifyCodeController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(VerifyCodeController.class);

	@Autowired
	private VerifyCodeService verifyCodeService;

	private ResultMessage result = null;

	/**
	 * GET /brand -> get all the verifyCode
	 */
	@RequestMapping(value = "/phoneNumber={phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取验证码", notes = "get verifyCode ",httpMethod="GET",response=String.class)
	public ResultMessage getVerifyCode( @PathVariable String phoneNumber){
		log.debug("REST request to get all Brand");
		Boolean get = verifyCodeService.getVerifyCode(phoneNumber);

		result = new ResultMessage();
		result.getResultParm().put("verifyCode",verifyCodeService.findAll());
		return result;
	}
	
}
