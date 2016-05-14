package com.earl.carnet.domain.carnet.tem_car;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.beetl.sql.core.annotatoin.AutoID;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 汽车临时信息实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tem_car")
public class Tem_Car extends AbstractAuditingEntity<Long> implements Serializable {

    private String mark;//品牌标志

    private String brand;//品牌

    private String models;//品牌型号

    private String vin; //车架号

    private String engineNumber;//发动机号

    private String rank;//车身等级

    private Double mileage;//里程数

    private Double oilBox;//油箱容积

    private Double oil;//当前油量

    private Double temperature;//温度

    private Boolean engineProperty ;//发动机性能

    private Boolean transmission;//变速器性能

    private Boolean carLight;//车灯性能

    private Boolean carState;//车状态

    private Boolean carAlarm;//车警报

    private Double lon;//经度

    private Double lat;//纬度

    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public Double getOilBox() {
        return oilBox;
    }

    public void setOilBox(Double oilBox) {
        this.oilBox = oilBox;
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


    public Boolean getTransmission() {
        return transmission;
    }

    public void setTransmission(Boolean transmission) {
        this.transmission = transmission;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Boolean getEngineProperty() {
        return engineProperty;
    }

    public void setEngineProperty(Boolean engineProperty) {
        this.engineProperty = engineProperty;
    }

    public Boolean getCarLight() {
        return carLight;
    }

    public void setCarLight(Boolean carLight) {
        this.carLight = carLight;
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


    public Boolean getCarState() {
        return carState;
    }

    public void setCarState(Boolean carState) {
        this.carState = carState;
    }

    public Boolean getCarAlarm() {
        return carAlarm;
    }

    public void setCarAlarm(Boolean carAlarm) {
        this.carAlarm = carAlarm;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + super.getId() +
                ", mark='" + mark + '\'' +
                ", brand='" + brand + '\'' +
                ", models='" + models + '\'' +
                ", vin='" + vin + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", rank='" + rank + '\'' +
                ", mileage=" + mileage +
                ", oilbox=" + oilBox +
                ", oil=" + oil +
                ", temperature=" + temperature +
                ", engineProperty=" + engineProperty +
                ", transmission=" + transmission +
                ", carLight=" + carLight +
                ", carState=" + carState +
                ", carAlarm=" + carAlarm +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}