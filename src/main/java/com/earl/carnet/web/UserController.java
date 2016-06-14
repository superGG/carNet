package com.earl.carnet.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.earl.carnet.domain.carnet.car.Car;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Length;
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
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/users")
//@Api(value="Person Rest Service") //修改生成的son路径
public class UserController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    private ResultMessage result = null;

    /**
     * GET /users -> get all the users
     */
    @RequestMapping(value = "/getAlls", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有用户信息", notes = "find All User", httpMethod = "GET", response = User.class, responseContainer = "List")
    public ResponseEntity<ResultMessage> getAll() {
        log.debug("REST request to get all Users");
        result = new ResultMessage();
        result.setServiceResult(true);
        result.getResultParm().put("user", userService.findAll());
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * GET /users/:username -> get the "username" user
     */
    @ApiOperation(value = "得到指定用户", notes = "GET POINT USER", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/username={username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> getUser(
            @ApiParam(required = true, name = "username", value = "用户昵称")
            @PathVariable
            String username) {
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
//        log.info(result.toJson());
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * GET /users/:id -> get the "id" user
     */
    @Valid
    @ApiOperation(value = "得到id指定用户", notes = "GET POINT USER", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/id={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> getUser(
            @ApiParam(required = true, name = "id", value = "用户id")
            @NotNull(message = "用户id不能为空") 
            @PathVariable
            Long id) {
        log.info("REST request to get User : {}", id);
        result = new ResultMessage();
        User user = userService.findOne(id);
        if (user != null) {
            result.setServiceResult(true);
            result.getResultParm().put("user", user);
        } else {
            result.setServiceResult(false);
            result.setResultInfo("获取用户失败");
        }
//        log.info(result.toJson());
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * POST /users -> create a new user
     *
     * @RequestBody 专门处理非from表单数据，就是json,xml类型数据
     */
    @ApiOperation(value = "添加一个用户", notes = "ADD ONE USER", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "User's name", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "User's email", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "User ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "loginid", value = "User ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "loginid", value = "User ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "userImg", value = "User ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "safePassword", value = "User ID", required = false, dataType = "long", paramType = "query")
    })
    public ResponseEntity<?> create(
            @ApiParam(required = false, name = "user", value = "这个字段不要理！！！！")
            @RequestParam(name = "userDto", required = false)
            User userDto) {
        result = new ResultMessage();
        User user = userService.findOneByUsername(userDto.getUsername());
        if (user != null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("username already in use");
        }
        result.setServiceResult(userService.saveUser(userDto));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 更新指定用户.
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "更新指定用户", notes = "UPDATE ONE USER", httpMethod = "POST", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "User's name", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "User's email", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "User ID", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "loginid", value = "User ID", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "userImg", value = "User ID", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "safePassword", value = "User ID", required = false, dataType = "long", paramType = "query")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @ApiParam(required = false, name = "user", value = "这个字段不要理！！！！")
            User user) {
        result = new ResultMessage();
        if (userService.updateByPrimaryKeySelective(user) != 0) {
            result.setServiceResult(true);
            result.setResultInfo("更新成功");
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Valid
    @ApiOperation(value = "删除一个用户", notes = "DELETE ONE USER", httpMethod = "DELETE", response = String.class)
    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(
            @ApiParam(required = true, name = "id", value = "用户id")
            @NotNull(message = "用户id不能为空")
            @RequestParam(name = "id", required = true)
            Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /change -> changes the current user's password
     */
    @Valid
    @ApiOperation(value = "更改用户密码", notes = "CHANGE USER PASSWORD", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> changePassword(
            @ApiParam(required = true, name = "oldPassword", value = "旧密码")
            @RequestParam(name = "oldPassword", required = true)
            String oldPassword,
            @ApiParam(required = true, name = "id", value = "用户id")
            @NotNull(message = "用户id不能为空")
            @RequestParam(name = "id", required = true)
            Long id,
            @ApiParam(required = true, name = "newPassword", value = "新密码")
            @RequestParam(name = "newPassword", required = true)
            @Length(min = 5, max = 32)
            String newPassword) {
        result = new ResultMessage();
        result.setServiceResult(userService.changePassword(id, oldPassword, newPassword));
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * POST /change -> changes the current user's safePassword
     */
    @Valid
    @ApiOperation(value = "更改用户安全密码", notes = "CHANGE USER SAFEPASSWORD", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/changeSafePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> changeSafePassword(
            @ApiParam(required = true, name = "oldSafePassword", value = "旧安全密码")
            @RequestParam(name = "oldSafePassword", required = true)
            String oldSafePassword,
            @ApiParam(required = true, name = "id", value = "用户id")
            @NotNull(message = "用户id不能为空")
            @RequestParam(name = "id", required = true)
            Long id,
            @ApiParam(required = true, name = "newSafePassword", value = "新安全密码")
            @RequestParam(name = "newSafePassword", required = true)
            @Length(min = 5, max = 32)
            String newSafePassword) {
        result = new ResultMessage();
        result.setServiceResult(userService.changeSafePassword(id, oldSafePassword, newSafePassword));
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * POST /change -> changes the current user's relatedPhone
     */
    @Valid
    @ApiOperation(value = "更改至亲手机号码", notes = "CHANGE USER PASSWORD", httpMethod = "POST", response = ResultMessage.class)
    @RequestMapping(value = "/changeRelatedPhone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> changeRelatedPhone(
            @ApiParam(required = true, name = "id", value = "用户id")
            @NotNull(message = "用户id不能为空")
            @RequestParam(name = "id", required = true)
            String id,
            @ApiParam(required = true, name = "verifyCode", value = "验证码")
            @RequestParam(name = "verifyCode", required = true)
            String verifyCode,
            @ApiParam(required = true, name = "newPhone", value = "新号码")
            @RequestParam(name = "newPhone", required = true)
            String newPhone) {
        if (newPhone.isEmpty() || newPhone.length() < 5
                || newPhone.length() > 15) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.changeRelatedPhone(id, newPhone, verifyCode);
        result = new ResultMessage();
        result.setResultInfo("修改亲人号码成功");
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

//    /**
//     * POST /save -> add the current user's relatedPhone
//     */
//    @RequestMapping(value = "/addRelatedPhone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResultMessage> addRelatedPhone(
//            @ApiParam(required = true, name = "id", value = "用户id")
//            @RequestParam(name = "id", required = true)
//            String id,
//            @ApiParam(required = true, name = "verifyCode", value = "验证码")
//            @RequestParam(name = "verifyCode", required = true)
//            String verifyCode,
//            @ApiParam(required = true, name = "relatedPhone", value = "亲人号码")
//            @RequestParam(name = "relatedPhone", required = true)
//            String relatedPhone) {
//        if (relatedPhone.isEmpty() || relatedPhone.length() < 5
//                || relatedPhone.length() > 15) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        userService.changeRelatedPhone(id, relatedPhone,verifyCode);
//        result = new ResultMessage();
//        result.setResultInfo("添加亲人号码成功");
//        return new ResponseEntity<ResultMessage>(result,HttpStatus.OK);
//    }

    /**
     * 用户登录
     *
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
        Car car_current = userService.getCurrentCar(loginid);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultInfo("登录成功");
        ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
        User user = principal.getUser();

        resultMessage.getResultParm().put("user", user);
        if (car_current != null) {
            resultMessage.getResultParm().put("car", car_current);
        }
        return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.OK);
    }

    /**
     * 上传图片
     */
    @Valid
    @ApiOperation(value = "更新用户头像", notes = "loginSystem", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/updateImg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMessage updateImg(
            MultipartFile userfile,
            @NotNull(message = "用户id不能为空")
            @ApiParam(required = true, name = "id", value = "用户id")
            Long id) {
        result = new ResultMessage();
        if (userService.updateImg(userfile, id)) {
            result.setResultInfo("更新用户头像成功");
            result.setServiceResult(true);
        } else {
            result.setResultInfo("更新用户头像失败");
            result.setServiceResult(false);
        }
        return result;
    }

    @ApiOperation(value = "得到用户信息", notes = "user info", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/users/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShiroPrincipal> loginInfo2() {
        Subject subject = SecurityUtils.getSubject();

        //可以通过下面这个对象，动态修改用户权限信息
        ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
        System.out.println(principal.toString());
        return new ResponseEntity<ShiroPrincipal>(principal, HttpStatus.OK);
    }

    @Valid
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

    @Valid
    @ApiOperation(value = "验证用户安全密码", notes = "confirmSafePassword", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/confirmSafePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage> confirmSafePassword(
            @NotNull(message="用户id不能为空")
            Long id, String safePassword) {
        result = new ResultMessage();
        userService.confirmSafePassword(id, safePassword);
        result.setResultInfo("安全验证成功");
        result.setServiceResult(true);
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }


}
