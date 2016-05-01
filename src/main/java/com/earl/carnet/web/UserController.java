package com.earl.carnet.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.security.shiro.ShiroPrincipal;
import com.earl.carnet.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/users")
//@Api(value="Person Rest Service") //修改生成的son路径
public class UserController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    private  ResultMessage result = null;

    /**
     * GET /users -> get all the users
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有用户信息", notes = "find All User", httpMethod = "GET", response = String.class)
    public ResponseEntity<ResultMessage> getAll() {
        log.debug("REST request to get all Users");
        result = new ResultMessage();
        result.setServiceResult(true);
        result.getResultParm().put("user", userService.findAll());
        log.info(result.toJson().toString());
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * GET /users/:username -> get the "username" user
     */
    @ApiOperation(value = "得到指定用户", notes = "GET POINT USER", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/username={username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> getUser(@PathVariable String username) {
        log.info("REST request to get User : {}", username);
        result = new ResultMessage();
        User user = userService.findOneByUsername(username);
        if (user != null) {
            result.setServiceResult(true);
            result.getResultParm().put("user", user);
        } else {
            result.setServiceResult(false);
            result.setResultInfo("获取用户失败");
        }
        log.info(result.toJson().toString());
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * POST /users -> create a new user
     *
     * @RequestBody 专门处理非from表单数据，就是json,xml类型数据
     */
    @ApiOperation(value = "添加一个用户", notes = "ADD ONE USER", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(User userDto) {
        result = new ResultMessage();
        User user = userService.findOneByUsername(userDto.getUsername());
        if (user != null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("username already in use");
        }
        result.setServiceResult(userService.saveUser(userDto));
        log.info(result.toJson().toString());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 更新指定用户.
     * @param user
     * @return
     */
    @ApiOperation(value = "更新指定用户", notes = "UPDATE ONE USER", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(User user) {
        result = new ResultMessage();
        if(userService.updateByPrimaryKeySelective(user) != 0) {
            result.setServiceResult(true);
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        log.info(result.toJson().toString());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value = "删除一个用户", notes = "DELETE ONE USER", httpMethod = "DELETE", response = String.class)
    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /change -> changes the current user's password
     */
    @ApiOperation(value = "更改用户密码", notes = "CHANGE USER PASSWORD", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> changePassword(String oldPassword, String Id, String newPassword) {
        if (newPassword.isEmpty() || newPassword.length() < 5
                || newPassword.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(Id, oldPassword,newPassword);
        result = new ResultMessage();
        result.setResultInfo("修改密码成功");
        return new ResponseEntity<ResultMessage>(result,HttpStatus.OK);
    }

    /**
     * 用户登录
     * @param loginid
     * @param password
     * @return
     */
    @Valid
    @RequestMapping(value = "/doLogin", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ApiOperation(value = "登录系统", notes = "loginSystem", httpMethod = "POST", response = String.class)
    public ResponseEntity<ResultMessage> doLogin(
            @ApiParam(required = true, name = "loginid", value = "用户账号")
            @NotEmpty(message = "登录账号不能为空")
            @RequestParam(name = "loginid", required = true)
            String loginid,
            @ApiParam(required = true, value = "用户登录密码")
            @RequestParam(name = "password", required = true) String password) {
        userService.doLogin(loginid, password);
        ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
        User user = principal.getUser();

        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultInfo("登录成功");
        return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.OK);
    }

    /**
     * 上传图片
     */
    @ApiOperation(value = "更新用户头像", notes = "loginSystem", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/updateImg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMessage updateImg(MultipartFile userfile, Long id) {

        result = new ResultMessage();
        if (userService.updateImg(userfile, id)){
            result.setResultInfo("更新用户头像成功");
            result.setServiceResult(true);
        } else {
            result.setResultInfo("更新用户头像失败");
            result.setServiceResult(false);
        }
        return  result;
    }

    @ApiOperation(value = "得到用户信息", notes = "user info", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/users/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShiroPrincipal> loginInfo() {
        Subject subject = SecurityUtils.getSubject();

        //可以通过下面这个对象，动态修改用户权限信息
        ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
        System.out.println(principal.toString());
        return new ResponseEntity<ShiroPrincipal>(principal, HttpStatus.OK);
    }

    @ApiOperation(value = "用户注册", notes = "user register system account ", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> regitsterAccount(
            @ApiParam(required = true, name = "loginid", value = "用户登录账号")
            @NotEmpty(message = "注册账号不能为空")
            @RequestParam(name = "loginid", required = true)
            String loginid,
            @ApiParam(required = true, name = "password", value = "用户登录密码")
            @NotEmpty(message = "登录密码不能为空")
            @RequestParam(name = "password", required = true)
            String password) {
        userService.registerAccount(loginid, password);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultInfo("注册成功");
        return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.OK);
    }
}
