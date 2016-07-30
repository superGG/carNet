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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.Park.Park;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.ParkService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/park")
// @Api(value="Person Rest Service") //修改生成的son路径
public class ParkController extends BaseController {

    private final Logger log = LoggerFactory
            .getLogger(ParkController.class);

    @Autowired
    private ParkService parkService;

    /**
     * GET /park -> get all the park
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有停车场信息", notes = "find All park", httpMethod = "GET", response = Park.class, responseContainer = "List")
    public ResultMessage getAll() {
        log.debug("REST request to get all park");
        ResultMessage result = new ResultMessage();
        result.getResultParm().put("park", parkService.findAll());
        return result;
    }

    @Valid
    @RequestMapping(value = "/getAroundPark", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取周围停车场信息", notes = "get user's around park", httpMethod = "GET", response = Park.class, responseContainer = "List")
    public ResultMessage getAroundShop(
            @NotNull(message = "lon不能为空")
            @ApiParam(required = true, name = "lon", value = "纬度坐标")
            Double lon,
            @NotNull(message = "lat不能为空")
            @ApiParam(required = true, name = "lat", value = "经度坐标")
            Double lat) {
        ResultMessage result = new ResultMessage();
        List<Park> shopList = parkService.getAroundPark(lat, lon);
        result.getResultParm().put("park", shopList);
        if (shopList.isEmpty()) throw new DomainSecurityException("周围10公里都没有加盟停车场");
        return result;
    }

    /**
     * GET /park -> get park by id
     */
    @Valid
    @RequestMapping(value = "/getParkById={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到停车场详情", notes = "get park by id", httpMethod = "GET", response = Park.class, responseContainer = "List")
    public ResultMessage getParkById(
            @PathVariable
            @ApiParam(required = true, name = "id", value = "停车场id")
            @NotNull(message = "id不能为空")
            Long id) {
        log.debug("REST request to get all Shop");
        Park shop = parkService.findOne(id);
        ResultMessage result = new ResultMessage();
        if (shop != null) {
            result.getResultParm().put("shop", shop);
        } else {
            result.setServiceResult(false);
        }
        return result;
    }

    /**
     * POST /shop -> save shop
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加停车场信息", notes = "save shop", httpMethod = "POST", response = Park.class, responseContainer = "List")
    public ResponseEntity<ResultMessage> save(Park park) {
        log.debug("save shop");
        int id = parkService.insertBackId(park);
        ResultMessage result = new ResultMessage();
        result.getResultParm().put("park", parkService.findOne(id));
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }


    /**
     * 删除停车场.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据id停车场信息", notes = "delete shop by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<ResultMessage> delete(
            @ApiParam(required = true, name = "id", value = "停车场id")
            @NotNull(message = "id不能为空")
            Long id) {
        ResultMessage result = new ResultMessage();
        if (id == null) {
            result.setResultInfo("id为空");
            result.setServiceResult(false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        log.info("进入controller层删除停车场delete方法");
        parkService.delete(id);
        result.setResultInfo("删除停车场成功");
        result.setServiceResult(true);
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }


}
