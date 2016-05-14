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
	 * GET /verifyCode -> get the verifyCode
	 */
	@RequestMapping(value = "/phoneNumber={phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取验证码", notes = "get verifyCode ",httpMethod="GET",response=String.class)
	public ResultMessage getVerifyCode( @PathVariable String phoneNumber){
		log.info("进入controller层的getVerifyCode方法");
		Boolean get = verifyCodeService.getVerifyCode(phoneNumber);

		result = new ResultMessage();
		result.setServiceResult(get);
//		result.getResultParm().put("verifyCode",verifyCodeService.findAll());
//		result.getResultParm().put("sessionId",session.getId());
//		result.getResultParm().put("verifyCode",session.getAttribute("verifyCode"));
		return result;
	}

	/**
	 * GET /test -> test the verifyCode
	 */
	@RequestMapping(value = "/v={verifyCode}/p={phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取验证码", notes = "test verifyCode ",httpMethod="GET",response=String.class)
	public ResultMessage comfigVerifyCode( @PathVariable String verifyCode , @PathVariable String phoneNumber){
		Boolean get = verifyCodeService.comfigVerifyCode(verifyCode,phoneNumber);

		result = new ResultMessage();
		result.setServiceResult(get);
//		result.getResultParm().put("verifyCode",verifyCodeService.findAll());
//		result.getResultParm().put("sessionId",session.getId());
//		result.getResultParm().put("verifyCode",session.getAttribute("verifyCode"));
		return result;
	}

}
