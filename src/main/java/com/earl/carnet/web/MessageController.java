package com.earl.carnet.web;

import java.util.List;

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
import com.earl.carnet.domain.carnet.Message.Message;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.service.MessageService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/message")
//@Api(value="Person Rest Service") //修改生成的son路径
public class MessageController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	/**
	 * GET /message -> get all the message
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有消息", notes = "find All message",httpMethod="GET",response=Brand.class,responseContainer = "List")
	public ResultMessage getAll() {
		log.debug("REST request to get all message");
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("message",messageService.findAll());
		return result;
	}

	/**
	 * POST /message -> update message's state
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "更新消息状态", notes = "update message's state",httpMethod="POST",response=Brand.class,responseContainer = "List")
	public ResultMessage update(Long id) {
		log.debug("update message's state");
		Message message = messageService.findOne(id);
		message.setState(true);
		messageService.updateByPrimaryKeySelective(message);
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("message",message);
		return result;
	}

	/**
	 * GET /message -> get user's unread message
	 */
	@RequestMapping(value = "/getUnread/userId={userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取用户的未读信息", notes = "get user's unread message",httpMethod="GET",response=Brand.class,responseContainer = "List")
	public ResultMessage getUnread(
			@ApiParam(required = true, name = "userId", value = "用户id")
			@PathVariable Long userId) {
		log.debug("get user's unread message");
		Message message = new Message();
		message.setUserId(userId);
		message.setState(false);
		List<Message> messageList = messageService.searchQuery(message);
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("message",messageList);
		result.getResultParm().put("count",messageList.size());
		return result;
	}

	/**
	 * GET /message -> get user's all message
	 */
	@RequestMapping(value = "/getAll/userId={userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取用户所有消息", notes = " get user's all message",httpMethod="GET",response=Brand.class,responseContainer = "List")
	public ResultMessage getUserAll(
			@ApiParam(required = true, name = "userId", value = "用户id")
			@PathVariable	Long userId) {
		log.debug(" get user's all message");
		Message message = new Message();
		message.setUserId(userId);
		List<Message> messageList = messageService.searchAccurate(message);
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("message",messageList);
		result.getResultParm().put("count",messageList.size());
		return result;
	}

	/**
	 * GET /message -> update user's all unread message
	 */
	@RequestMapping(value = "/update/userId={userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "更新用户所有未读信息", notes = " update user's all unread message",httpMethod="GET",response=Brand.class,responseContainer = "List")
	public ResultMessage updateUserAll(
			@ApiParam(required = true, name = "userId", value = "用户id")
			@PathVariable Long userId) {
		log.debug(" get user's all message");
		Boolean update = messageService.updateUserAll(userId);
		ResultMessage result = new ResultMessage();
		result.setServiceResult(update);
		return result;
	}

	/**
	 * POST /message -> save message
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "添加信息", notes = "save message",httpMethod="POST",response=Brand.class,responseContainer = "List")
	public ResponseEntity<ResultMessage> save(Message message) {
		log.debug("save message");
		if (message.getUserId() == null) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = messageService.insertBackId(message);
		ResultMessage result = new ResultMessage();
		result.getResultParm().put("message",messageService.findOne(id));
		return new ResponseEntity<ResultMessage>(result,HttpStatus.OK);
	}


	/**
	 * 删除消息.
	 *
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "根据id信息", notes = "delete message by id", httpMethod = "POST", response = String.class)
	public ResponseEntity<ResultMessage> delete(
			@ApiParam(required = true, name = "id", value = "消息id")
			Long id) {
		ResultMessage result;
		if (id == null) {
			result = new ResultMessage();
			result.setResultInfo("id为空");
			result.setServiceResult(false);
			return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}
		log.info("进入controller层添加消息delete方法");
		result = new ResultMessage();
		messageService.delete(id);
		result.setResultInfo("删除消息成功");
		result.setServiceResult(true);
		return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
	}
	
}
