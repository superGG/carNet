package com.earl.carnet.web;

import java.util.List;

import com.earl.carnet.domain.carnet.order.Order;
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
import com.earl.carnet.domain.carnet.RepairShop.RepairShop;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.RepairShopService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/repairShop")
// @Api(value="Person Rest Service") //修改生成的son路径
public class RepairShopController extends BaseController {

    private final Logger log = LoggerFactory
            .getLogger(RepairShopController.class);

    @Autowired
    private RepairShopService repairShopService;

    private ResultMessage result = null;

    /**
     * GET /brand -> get all the repairShop
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到所有维修店信息", notes = "find All repairShop", httpMethod = "GET", response = RepairShop.class, responseContainer = "List")
    public ResultMessage getAll() {
        log.debug("REST request to get all repairShop");
        result = new ResultMessage();
        result.getResultParm().put("repairShop", repairShopService.findAll());
        return result;
    }

    @Valid
    @RequestMapping(value = "/getAroundShop", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取周围维修店信息", notes = "get user's around repairshop", httpMethod = "GET", response = RepairShop.class, responseContainer = "List")
    public ResultMessage getAroundShop(
            @NotNull(message = "lon不能为空")
            @ApiParam(required = true, name = "lon", value = "纬度坐标")
            Double lon,
            @NotNull(message = "lat不能为空")
            @ApiParam(required = true, name = "lat", value = "经度坐标")
            Double lat) {
        result = new ResultMessage();
        List<RepairShop> shopList = repairShopService.getAroundShop(lat, lon);
        result.getResultParm().put("repairshop", shopList);
        if (shopList.size() == 0) throw new DomainSecurityException("周围3公里都没有加盟维修店");
        return result;
    }

    /**
     * GET /order -> get repairshop by id
     */
    @Valid
    @RequestMapping(value = "/getShopById={id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "得到维修店详情", notes = "get repairshop by id", httpMethod = "GET", response = RepairShop.class, responseContainer = "List")
    public ResultMessage getShopById(
            @PathVariable
            @ApiParam(required = true, name = "id", value = "维修店id")
            @NotNull(message = "id不能为空")
            Long id) {
        log.debug("REST request to get all Shop");
        RepairShop shop = repairShopService.findOne(id);
        result = new ResultMessage();
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
    @ApiOperation(value = "添加维修店信息", notes = "save shop", httpMethod = "POST", response = Brand.class, responseContainer = "List")
    public ResponseEntity<ResultMessage> save(RepairShop repairShop) {
        log.debug("save shop");
        int id = repairShopService.insertBackId(repairShop);
        result = new ResultMessage();
        result.getResultParm().put("repairShop", repairShopService.findOne(id));
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }


    /**
     * 删除维修店.
     *
     * @return
     */
    @Valid
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据id维修店信息", notes = "delete shop by id", httpMethod = "POST", response = String.class)
    public ResponseEntity<ResultMessage> delete(
            @ApiParam(required = true, name = "id", value = "维修店id")
            @NotNull(message = "id不能为空")
            Long id) {
        result = new ResultMessage();
        if (id == null) {
            result.setResultInfo("id为空");
            result.setServiceResult(false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        log.info("进入controller层删除维修店delete方法");
        repairShopService.delete(id);
        result.setResultInfo("删除维修店成功");
        result.setServiceResult(true);
        return new ResponseEntity<ResultMessage>(result, HttpStatus.OK);
    }


}
