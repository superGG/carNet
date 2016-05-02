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
import com.wordnik.swagger.annotations.ApiOperation;

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
    @ApiOperation(value = "得到所有车辆信息", notes = "find All car", httpMethod = "GET", response = String.class)
    public ResultMessage getAll() {
        log.debug("REST request to get all Car");
        result = new ResultMessage();
        result.getResultParm().put("car", carService.findAll());
        log.info(result.toJson().toString());
        return result;
    }

    /**
     * GET /car -> get  car by id
     */
    @RequestMapping(value = "/car={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到单辆车信息", notes = "get one car", httpMethod = "GET", response = String.class)
    public ResultMessage getCar(@PathVariable Long id) {
        log.debug("REST request to get one  Car");
        result = new ResultMessage();
        result.getResultParm().put("car", carService.findOne(id));
        log.info(result.toJson().toString());
        return result;
    }

    /**
     * 添加汽车.
     *
     * @return
     */
    @RequestMapping(value = "/saveCar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加一个新汽车", notes = "add a new car", httpMethod = "POST", response = String.class)
    public ResultMessage saveCar(Car car) {
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
        log.info(result.toJson().toString());
        return result;
    }

    /**
     * 更新汽车.
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新汽车信息", notes = "update car message", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> update(Car car) {
        log.info("进入controller层更新汽车update方法");
        result = new ResultMessage();
        if (carService.update(car) != 0) {
            result.getResultParm().put("car", carService.findOne(car.getId()));
            result.setServiceResult(true);
        } else {
            result.setServiceResult(false);
            result.setResultInfo("更新失败");
        }
        log.info(result.toJson().toString());
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
            result.setServiceResult(true);
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
    @ApiOperation(value = "根据id删除汽车", notes = "delete car by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<?> delete(Long id) {
        log.info("进入controller层添加汽车delete方法");
        result = new ResultMessage();
        carService.delete(id);
        result.setResultInfo("删除汽车成功");
        result.setServiceResult(true);
        log.info(result.toJson().toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
