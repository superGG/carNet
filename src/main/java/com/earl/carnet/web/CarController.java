package com.earl.carnet.web;

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
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import java.util.List;

@RestController
@RequestMapping(value = "/car")
//@Api(value="Person Rest Service") //修改生成的son路径
public class CarController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    private ResultMessage result = null;

    /**
     * GET /car -> get all the car
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有车辆信息", notes = "find All car", httpMethod = "GET", response = Car.class,responseContainer = "List")
    public ResultMessage getAll() {
        log.debug("REST request to get all Car");
        result = new ResultMessage();
        result.setResultInfo("获取成功");
        result.getResultParm().put("car", carService.findAll());
        return result;
    }

    /**
     * GET /car -> get  car by id
     */
    @RequestMapping(value = "/car={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到单辆车信息", notes = "get one car", httpMethod = "GET", response = String.class)
    public ResultMessage getCar(
            @ApiParam(required = true, name = "id", value = "车辆id")
            @PathVariable
            Long id) {
        log.debug("REST request to get one  Car");
        result = new ResultMessage();
        result.setResultInfo("获取成功");
        result.getResultParm().put("car", carService.findOne(id));
        return result;
    }

    /**
     * GET /car -> get  car by userId
     */
    @RequestMapping(value = "/userId={userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取用户的所有汽车", notes = "get all car by user", httpMethod = "GET", response = String.class)
    public ResultMessage getAllCarByUser(
            @ApiParam(required = true, name = "userId", value = "用户id")
            @PathVariable
            Long userId) {
        log.debug("REST request to get one  Car");
        List<Car> carList = carService.getAllCarByUser(userId);
        result = new ResultMessage();
        result.setResultInfo("获取成功");
        result.getResultParm().put("cars",carList);
        return result;
    }

    /**
     * 添加汽车.
     *
     * @return
     */
    @RequestMapping(value = "/saveCar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加一个新汽车", notes = "add a new car", httpMethod = "POST", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "mark", value = "品牌标志", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "brand", value = "品牌", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "models", value = "品牌型号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "plateNumber", value = "车牌号码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vin", value = "车架号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "engineNumber", value = "发动机型号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rank", value = "车身等级", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mileage", value = "里程数", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "oilBox", value = "油箱容积", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "oil", value = "当前油量", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "temperature", value = "温度", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "engineProperty", value = "发动机性能", required = true, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "transmission", value = "变速器性能", required = true, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "carLight", value = "车灯性能", required = true, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "carState", value = "车状态", required = true, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "carAlarm", value = "车警报", required = true, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "alarmMessage", value = "是否发送警报信息", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "propertyMessage", value = "是否发送行性能信息", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "stateMessage", value = "是否发送车状态信息", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "currentCar", value = "是否当前车辆", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "lon", value = "经度", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = true, dataType = "double", paramType = "query")
    })
    public ResultMessage saveCar(
            @ApiParam(required = false, name = "car", value = "车辆信息")
            Car car) {
        log.info("进入controller层添加汽车saveOrder方法");
        result = new ResultMessage();
        //设置默认值
        car.setAlarmMessage(true);
        car.setStateMessage(true);
        car.setPropertyMessage(true);
        int id = carService.insertBackId(car);
        if (id != 0) {
            Car newCar = carService.findOne(id);
            result.getResultParm().put("car", newCar);
            result.setResultInfo("添加汽车成功");
            result.setServiceResult(true);
        } else {
            result.setResultInfo("添加汽车失败");
            result.setServiceResult(false);
        }
        return result;
    }

    /**
     * 更新汽车.
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新汽车信息", notes = "update car message", httpMethod = "POST", response = String.class)
    @ApiImplicitParams({
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
            @ApiImplicitParam(name = "oil", value = "当前油量", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "temperature", value = "温度", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "engineProperty", value = "发动机性能", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "transmission", value = "变速器性能", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "carLight", value = "车灯性能", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "carState", value = "车状态", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "carAlarm", value = "车警报", required = false, dataType = "byte", paramType = "query"),
            @ApiImplicitParam(name = "alarmMessage", value = "是否发送警报信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "propertyMessage", value = "是否发送行性能信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "stateMessage", value = "是否发送车状态信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "currentCar", value = "是否当前车辆", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "lon", value = "经度", required = false, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = false, dataType = "double", paramType = "query")
    })
    public ResponseEntity<?> update(
            @ApiParam(required = false, name = "car", value = "车辆实体,不要理这个字段！！！")
            Car car) {
        log.info("进入controller层更新汽车update方法");
        result = new ResultMessage();
        if (carService.update(car) != 0) {
            result.getResultParm().put("car", carService.findOne(car.getId()));
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




    /**
     * 更新汽车.
     *
     * @return
     */
    @RequestMapping(value = "/updateCarByVin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据车架号更新车辆信息（用于车载系统更新车辆信息）", notes = "update car message by vin", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> updateCarByVin(Car car) {
        result = new ResultMessage();
        Boolean update = carService.updateCarByVin(car);
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
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据id删除汽车", notes = "delete ca3r by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> delete(
            @ApiParam(required = true, name = "id", value = "车辆id")
            Long id) {
        log.info("进入controller层添加汽车delete方法");
        result = new ResultMessage();
        carService.delete(id);
        result.setResultInfo("删除汽车成功");
        result.setServiceResult(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
