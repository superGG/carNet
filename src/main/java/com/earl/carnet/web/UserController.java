package com.earl.carnet.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.security.shiro.ShiroPrincipal;
import com.earl.carnet.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api")
//@Api(value="Person Rest Service") //修改生成的son路径
public class UserController extends BaseController{

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * GET /users -> get all the users
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "得到所有用户", notes = "find All User",httpMethod="GET",response=String.class)
	public ResponseEntity<List<User>> getAll() {
		log.debug("REST request to get all Users");
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}
	
	/**
	 * GET /users/:username -> get the "username" user
	 */
	@ApiOperation(value = "得到指定用户", notes = "GET POINT USER",httpMethod="GET",response=String.class)
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable String username,
			HttpServletResponse response) {
		log.info("REST request to get User : {}", username);
		User user = userService.findOneByUsername(username);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return user;
	}

	/**
	 * POST /users -> create a new user
	 * @RequestBody 专门处理非from表单数据，就是json,xml类型数据
	 */
	@ApiOperation(value = "添加一个用户", notes = "ADD ONE USER",httpMethod="POST",response=String.class)
	@RequestMapping(value = "/users",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(User userDto,
			HttpServletRequest request) {
		User user = userService.findOneByUsername(userDto.getUsername());
		if (user != null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("username already in use");
		}
		userService.save(userDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * POST /users -> create a new user
	 * @RequestBody 专门处理非from表单数据，就是json,xml类型数据
	 */
	@ApiOperation(value = "更新指定用户", notes = "UPDATE ONE USER",httpMethod="PUT",response=String.class)
	@RequestMapping(value = "/users",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody User userDto,
			HttpServletRequest request) {
		userService.updateByPrimaryKeySelective(userDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * POST /users -> create a new user
	 * @RequestBody 专门处理非from表单数据，就是json,xml类型数据
	 */
	@ApiOperation(value = "删除一个用户", notes = "DELETE ONE USER",httpMethod="DELETE",response=String.class)
	@RequestMapping(value = "/users",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(Long id,
			HttpServletRequest request) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * POST /users/change_password -> changes the current user's password
	 */
	@ApiOperation(value = "更改用户密码", notes = "CHANGE USER PASSWORD",httpMethod="POST",response=String.class)
	@RequestMapping(value = "/users/change/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody String password,String Id) {
		if (password.isEmpty() || password.length() < 5
				|| password.length() > 50) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.changePassword(Id, password);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Valid
	@RequestMapping(value = "/doLogin", produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
	@ApiOperation(value = "登录系统", notes = "loginSystem",httpMethod="POST",response=String.class)
	public ResponseEntity<Map<String,Object>> doLogin(
			@ApiParam(required = true, name = "username", value = "用户信息json数据")
			@NotEmpty(message = "设备类型不能为空")
			@RequestParam(name="username",required = false)String username
			,
			@ApiParam(required = true, value = "用户密码json数据")
			@RequestParam(name="password",required = false) String password){
		userService.doLogin(username,password);
		ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
		User user = principal.getUser();
		Map<String, Object> loginInfo = new HashMap<String,Object>();
		loginInfo.put("username",user.getUsername());
		loginInfo.put("userimg",user.getUserimg());
		loginInfo.put("loginSuccess",true);
		return new ResponseEntity<Map<String,Object>>(loginInfo,HttpStatus.OK);
	}

	/**
	 * POST /users/change_password -> changes the current user's password
	 */
	@ApiOperation(value = "上传图片", notes = "loginSystem",httpMethod="POST",response=String.class)
	@RequestMapping(value = "/users/uploadfile",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadfile(MultipartFile userfile,String username) {
		userService.uploadFile(userfile);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "得到用户信息", notes = "user info",httpMethod="GET",response=String.class)
	@RequestMapping(value = "/users/info",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShiroPrincipal> loginInfo(){
		Subject subject = SecurityUtils.getSubject();

		//可以通过下面这个对象，动态修改用户权限信息
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		System.out.println(principal.toString());
		return new ResponseEntity<ShiroPrincipal>(principal,HttpStatus.OK);
	}
	
}
