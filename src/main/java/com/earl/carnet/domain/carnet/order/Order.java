package com.earl.carnet.domain.carnet.order;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.beetl.sql.core.annotatoin.AutoID;
import org.joda.time.DateTime;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)//jackson 控制，放回字段为null,将被过滤
@Table(name = "odrder")
public class Order extends AbstractAuditingEntity<Long> implements Serializable{

    private Long carId;  //汽车id

    private Long userId; //用户id

    private String stationName;//加油站名称

    private String address;//加油站地址

    private String brandName;//加油站品牌 （中石油、中石化）

    private Date agreementTime;//预约时间

    private String type;//加油类别 E90  E93 E97

    private Integer units;//加油单位  元/升

    private Double price;//每单位价格

    private Double number;//加油数量

    private Double amounts;//总价

    private Integer state;//订单状态

    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getAgreementTime() {
        return agreementTime;
    }

    public void setAgreementTime(Date agreementTime) {
        this.agreementTime = agreementTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getAmounts() {
        return amounts;
    }

    public void setAmounts(Double amounts) {
        this.amounts = amounts;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + super.getId() +
                ", carId=" + carId +
                ", userId=" + userId +
                ", stationName='" + stationName + '\'' +
                ", address='" + address + '\'' +
                ", brandName='" + brandName + '\'' +
                ", agreementTime=" + agreementTime +
                ", type='" + type + '\'' +
                ", units=" + units +
                ", price=" + price +
                ", number=" + number +
                ", amounts=" + amounts +
                ", state=" + state +
                '}';
    }
}
