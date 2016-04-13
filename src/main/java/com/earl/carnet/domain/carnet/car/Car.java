package com.earl.carnet.domain.carnet.car;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 汽车信息尸体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "car")
public class Car extends AbstractAuditingEntity<Long> implements Serializable {

    private Long userid;//用户id

    private String mark;//品牌标志

    private String brand;//品牌

    private String models;//品牌型号

    private String platenumber;//车牌号码

    private String vin; //车架号

    private String enginenumber;//发动机号

    private String rank;//车身等级

    private Double mileage;//里程数

    private Double oilbox;//油箱容积

    private Double speed;//车速

    private Double tachometer;//转速

    private Double oil;//当前油量

    private Double temperature;//温度

    private Byte enginepropetry;//发动机性能

    private Byte transmission;//变速器性能

    private Byte carlight;//车灯性能

    private Double lon;//经度

    private Double lat;//纬度

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEnginenumber() {
        return enginenumber;
    }

    public void setEnginenumber(String enginenumber) {
        this.enginenumber = enginenumber;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getOilbox() {
        return oilbox;
    }

    public void setOilbox(Double oilbox) {
        this.oilbox = oilbox;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTachometer() {
        return tachometer;
    }

    public void setTachometer(Double tachometer) {
        this.tachometer = tachometer;
    }

    public Double getOil() {
        return oil;
    }

    public void setOil(Double oil) {
        this.oil = oil;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Byte getEnginepropetry() {
        return enginepropetry;
    }

    public void setEnginepropetry(Byte enginepropetry) {
        this.enginepropetry = enginepropetry;
    }

    public Byte getTransmission() {
        return transmission;
    }

    public void setTransmission(Byte transmission) {
        this.transmission = transmission;
    }

    public Byte getCarlight() {
        return carlight;
    }

    public void setCarlight(Byte carlight) {
        this.carlight = carlight;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Car{" +
                "userid=" + userid +
                ", mark='" + mark + '\'' +
                ", brand='" + brand + '\'' +
                ", models='" + models + '\'' +
                ", platenumber='" + platenumber + '\'' +
                ", vin='" + vin + '\'' +
                ", enginenumber='" + enginenumber + '\'' +
                ", rank='" + rank + '\'' +
                ", mileage=" + mileage +
                ", oilbox=" + oilbox +
                ", speed=" + speed +
                ", tachometer=" + tachometer +
                ", oil=" + oil +
                ", temperature=" + temperature +
                ", enginepropetry=" + enginepropetry +
                ", transmission=" + transmission +
                ", carlight=" + carlight +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
