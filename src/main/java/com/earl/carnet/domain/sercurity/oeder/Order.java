package com.earl.carnet.domain.sercurity.oeder;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.joda.time.DateTime;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 订单实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)//jackson 控制，放回字段为null,将被过滤
@Table(name = "odrder")
public class Order extends AbstractAuditingEntity<Long> implements Serializable{

    private Long carid;

    private Long userid;

    private String stationname;

    private String address;

    private String brandname;

    private DateTime agreementtime;

    private String type;

    private Integer units;

    private Double price;

    private Double number;

    private Double amounts;

    private Integer state;

    public Long getCarid() {
        return carid;
    }

    public void setCarid(Long carid) {
        this.carid = carid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public DateTime getAgreementtime() {
        return agreementtime;
    }

    public void setAgreementtime(DateTime agreementtime) {
        this.agreementtime = agreementtime;
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
                "carid=" + carid +
                ", userid=" + userid +
                ", stationname='" + stationname + '\'' +
                ", address='" + address + '\'' +
                ", brandname='" + brandname + '\'' +
                ", agreementtime=" + agreementtime +
                ", type='" + type + '\'' +
                ", units=" + units +
                ", price=" + price +
                ", number=" + number +
                ", amounts=" + amounts +
                ", state=" + state +
                '}';
    }
}
