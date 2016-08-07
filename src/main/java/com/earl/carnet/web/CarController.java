package com.earl.carnet.web;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/car")
//@Api(value="Person Rest Service") //修改生成的son路径
public class CarController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    /**
     * GET /car -> get all the car
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有车辆信息", notes = "find All car", httpMethod = "GET", response = Car.class, responseContainer = "List")
    public ResultMessage getAll() {
        log.debug("REST request to get all Car");
        ResultMessage result = new ResultMessage();
        result.setResultInfo("获取成功");
        result.getResultParm().put("car", carService.findAll());
        return result;
    }

    /**
     * GET /car -> get  car by id
     */
    @Valid
    @RequestMapping(value = "/car={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到单辆车信息", notes = "get one car", httpMethod = "GET", response = String.class)
    public ResultMessage getCar(
            @NotNull(message = "id不能为空")
            @ApiParam(required = true, name = "id", value = "车辆id")
            @PathVariable
            Long id) {
        log.debug("REST request to get one  Car");
        ResultMessage result = new ResultMessage();
        result.setResultInfo("获取成功");
        result.getResultParm().put("car", carService.findOne(id));
        return result;
    }

    /**
     * GET /car -> get  car by userId
     */
    @Valid
    @RequestMapping(value = "/userId={userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取用户的所有汽车", notes = "get all car by user", httpMethod = "GET", response = String.class)
    public ResultMessage getAllCarByUser(
            @NotNull(message = "userId不能为空")
            @ApiParam(required = true, name = "userId", value = "用户id")
            @PathVariable
            Long userId) {
        log.debug("REST request to get all  Car");
        List<Car> carList = carService.getAllCarByUser(userId);
        ResultMessage result = new ResultMessage();
        if (carList.size() != 0) {
            result.setResultInfo("获取成功");
            result.getResultParm().put("car", carList);
        } else {
            result.setResultInfo("用户当前无车辆");
        }
        return result;
    }

    /**
     * GET /car -> get  user current car
     */
    @Valid
    @RequestMapping(value = "/currentCar={userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取用户的当前汽车", notes = "get  user current car", httpMethod = "GET", response = String.class)
    public ResultMessage getCurrentCarByUser(
            @NotNull(message = "userId不能为空")
            @ApiParam(required = true, name = "userId", value = "用户id")
            @PathVariable
            Long userId) {
        log.debug("get  user current car");
        List<Car> carList = carService.getCurrentCarByUser(userId);
        ResultMessage result = new ResultMessage();
        if (!carList.isEmpty()) {
            result.setResultInfo("获取成功");
            result.getResultParm().put("car", carList.get(0));
        } else {
            result.setResultInfo("该用户无当前车辆");
            result.setServiceResult(false);
        }
        return result;
    }

    /**
     * GET /car -> get  tem_car by vin
     */
    @Valid
    @RequestMapping(value = "/tmpCar/vin={vin}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据车架号获取临时车辆信息", notes = "get car by vin", httpMethod = "GET", response = String.class)
    public ResultMessage getTmpCarByVin(
            @NotNull(message = "vin不能为空")
            @ApiParam(required = true, name = "vin", value = "车架号")
            @PathVariable
            String vin) {
        log.debug("REST request to get one  Car");
        Car car = carService.getTmpCarByVin(vin);
        ResultMessage result = new ResultMessage();
        if (car != null) {
            result.setResultInfo("获取成功");
            result.getResultParm().put("car", car);
        } else {
            result.setResultInfo("不存在该车辆");
        }

        return result;
    }

    /**
     * GET /car -> get  tem_car by vin
     */
    @Valid
    @RequestMapping(value = "/vin={vin}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据车架号获取汽车", notes = "get car by vin", httpMethod = "GET", response = String.class)
    public ResultMessage getCarByVin(
            @NotNull(message = "vin不能为空")
            @ApiParam(required = true, name = "vin", value = "车架号")
            @PathVariable
            String vin) {
        log.debug("REST request to get one  Car");
        Car car = carService.getCarByVin(vin);
        ResultMessage result = new ResultMessage();
        result.setResultInfo("获取成功");
        result.getResultParm().put("car", car);
        return result;
    }

    /**
     * 添加汽车.
     *
     * @return
     */
    @RequestMapping(value = "/saveCar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加一个新汽车", notes = "add a new car", httpMethod = "POST", response = String.class)
    public ResponseEntity<ResultMessage> saveCar(
            @RequestBody
            @ApiParam(required = false, name = "car", value = "车辆信息")
            Car car) {
        if (car.getVin() == null || car.getVin().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("进入controller层添加汽车saveOrder方法");
        ResultMessage result = new ResultMessage();
        //设置默认值
        int id = carService.saveCar(car);
        if (id != 0) {
            Car newCar = carService.findOne(id);
            result.getResultParm().put("car", newCar);
            result.setResultInfo("添加汽车成功");
            result.setServiceResult(true);
        } else {
            result.setResultInfo("添加汽车失败");
            result.setServiceResult(false);
        }
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }

    /**
     * 添加临时汽车信息.
     *
     * @return
     */
    @RequestMapping(value = "/saveTem_Car", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加一个新的临时车辆", notes = "add a new tem_car", httpMethod = "POST", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mark", value = "品牌标志", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "brand", value = "品牌", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "models", value = "品牌型号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "plateNumber", value = "车牌号码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vin", value = "车牌号", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "engineNumber", value = "发动机型号", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "rank", value = "车身等级", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "mileage", value = "里程数", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "oilBox", value = "油箱容积", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "oil", value = "当前油量", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "temperature", value = "温度", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "engineProperty", value = "发动机性能", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "transmission", value = "变速器性能", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "carLight", value = "车灯性能", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "carState", value = "车状态", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "carAlarm", value = "车警报", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "lon", value = "经度", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = true, dataType = "double", paramType = "query")
    })
    public ResponseEntity<ResultMessage> saveTem_Car(
            @ApiParam(required = true, name = "tem_car", value = "临时车辆信息")
            Car tem_car) {
        if (tem_car.getVin() == null || tem_car.getVin().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResultMessage result = new ResultMessage();
        carService.insertTem_Car(tem_car);
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }


    /**
     * 更新汽车.
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新汽车信息", notes = "update car message", httpMethod = "POST", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "车辆id", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "mark", value = "品牌标志", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "brand", value = "品牌", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "models", value = "品牌型号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "plateNumber", value = "车牌号码", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vin", value = "车牌号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "engineNumber", value = "发动机型号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rank", value = "车身等级", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "mileage", value = "里程数", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "oilBox", value = "油箱容积", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "oil", value = "当前油量", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "temperature", value = "温度", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "engineProperty", value = "发动机性能", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "transmission", value = "变速器性能", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "carLight", value = "车灯性能", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "carState", value = "车状态", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "carAlarm", value = "车警报", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "alarmMessage", value = "是否发送警报信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "propertyMessage", value = "是否发送行性能信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "stateMessage", value = "是否发送车状态信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "currentCar", value = "是否当前车辆", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "lon", value = "经度", required = false, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = false, dataType = "double", paramType = "query")
    })
    public ResponseEntity<ResultMessage> update(
            @ApiParam(required = false, name = "car", value = "车辆实体,不要理这个字段！！！")
            Car car) {
        if (car.getId() == null) throw new SecurityException("id不能为空");
        log.info("进入controller层更新汽车update方法");
        ResultMessage result = new ResultMessage();
        if (carService.update(car) != 0) {
            result.getResultParm().put("car", carService.findOne(car.getId()));
            result.setResultInfo("更新成功");
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 更新用户当前车辆.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/updateUserCurrentCar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新用户当前车辆", notes = "update user current car", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> updateCarByVin(
            @ApiParam(required = true, name = "id", value = "车辆id")
            @NotNull(message = "车辆id不能为空")
            Long id,
            @ApiParam(required = true, name = "userId", value = "用户id")
            @NotNull(message = "用户id不能为空")
            Long userId) throws Exception {
        ResultMessage result = new ResultMessage();
        Car car = new Car();
        car.setId(id);
        car.setUserId(userId);
        Boolean update = carService.updateUserCurrentCar(car);
        if (update) {
            result.setResultInfo("更新成功");
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 车载系统更新汽车.
     *
     * @return
     */
    @RequestMapping(value = "/updateCarByVin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据车架号更新车辆信息（用于车载系统更新车辆信息）", notes = "update car message by vin", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> updateCarByVin(Car car) throws Exception {
        if (car.getVin() == null) throw new SecurityException("vin不能为空");
        ResultMessage result = new ResultMessage();
        Boolean update = carService.updateCarByVin(car);
        if (update) {
            result.setResultInfo("更新成功");
            result.getResultParm().put("car", carService.getCarByVin(car.getVin()));
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 用户更新汽车状态.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/updateCarState", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户更新汽车状态", notes = "update car state by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> updateCarState(
            @NotNull(message = "id不能为空")
            @ApiParam(required = true, name = "id", value = "车辆id")
            Long id) {
        ResultMessage result = new ResultMessage();
        Boolean update = carService.updateCarState(id);
        if (update) {
            result.setResultInfo("更新成功");
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 用户更新汽车警报状态.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/updateCarAlarm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户更新汽车警报状态", notes = "update car alarm by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> updateCarAlarm(
            @NotNull(message = "id不能为空")
            @ApiParam(required = true, name = "id", value = "车辆id")
            Long id) {
        ResultMessage result = new ResultMessage();
        Boolean update = carService.updateCarAlarm(id);
        if (update) {
            result.setResultInfo("更新成功");
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 删除汽车.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据id删除汽车", notes = "delete car by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<ResultMessage> delete(
            @NotNull(message = "id不能为空")
            @ApiParam(required = true, name = "id", value = "车辆id")
            Long id) {
        log.info("进入controller层添加汽车delete方法");
        ResultMessage result = new ResultMessage();
        carService.deleteCar(id);
        result.setResultInfo("删除汽车成功");
        result.setServiceResult(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
