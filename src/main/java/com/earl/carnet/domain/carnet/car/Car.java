package com.earl.carnet.domain.carnet.car;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.beetl.sql.core.annotatoin.AutoID;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 汽车信息尸体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "car")
public class Car extends AbstractAuditingEntity<Long> implements Serializable {

    private Long userId;//用户id

    private String mark;//品牌标志

    private String brand;//品牌

    private String models;//品牌型号

    private String plateNumber;//车牌号码

    private String vin; //车架号

    private String engineNumber;//发动机号

    private String rank;//车身等级

    private Double mileage;//里程数

    private Double oilBox;//油箱容积

    private Double oil;//当前油量

    private Double temperature;//温度

    private Byte engineProperty;//发动机性能

    private Byte transmission;//变速器性能

    private Byte carLight;//车灯性能

    private Byte carState;//车状态
    
    private Byte carAlarm;//车警报
    
    private Byte alarmMessage;//是否发送警报信息
    
    private Byte propertyMessage;//是否发送行性能信息
    
    private Byte stateMessage;//是否发送车状态信息
    
    private Byte currentCar;//是否当前车辆

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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


    public Byte getTransmission() {
        return transmission;
    }

    public void setTransmission(Byte transmission) {
        this.transmission = transmission;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Byte getEngineProperty() {
        return engineProperty;
    }

    public void setEngineProperty(Byte engineProperty) {
        this.engineProperty = engineProperty;
    }

    public Byte getCarLight() {
        return carLight;
    }

    public void setCarLight(Byte carLight) {
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


    public Byte getCarState() {
        return carState;
    }

    public void setCarState(Byte carState) {
        this.carState = carState;
    }

    public Byte getCarAlarm() {
        return carAlarm;
    }

    public void setCarAlarm(Byte carAlarm) {
        this.carAlarm = carAlarm;
    }

    public Byte getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(Byte alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public Byte getPropertyMessage() {
        return propertyMessage;
    }

    public void setPropertyMessage(Byte propertyMessage) {
        this.propertyMessage = propertyMessage;
    }

    public Byte getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(Byte stateMessage) {
        this.stateMessage = stateMessage;
    }

    public Byte getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Byte currentCar) {
        this.currentCar = currentCar;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + super.getId() +
                ", userId=" + userId +
                ", mark='" + mark + '\'' +
                ", brand='" + brand + '\'' +
                ", models='" + models + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
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
                ", alarmMessage=" + alarmMessage +
                ", propertyMessage=" + propertyMessage +
                ", stateMessage=" + stateMessage +
                ", currentCar=" + currentCar +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
