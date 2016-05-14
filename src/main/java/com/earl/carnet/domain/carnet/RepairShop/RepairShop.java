package com.earl.carnet.domain.carnet.RepairShop;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.beetl.sql.core.annotatoin.AutoID;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 汽车维修店实体类.
 * Created by Administrator on 2016/5/12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name ="repairshop")
public class RepairShop extends AbstractAuditingEntity<Long> implements Serializable {

    private String name;//店名

    private String img;//店图

    private String address;//地址

    private String phoneNumber;//联系电话

    private Double lat;//纬度

    private Double lon;//经度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "RepairShop{" +
                "id=" + super.getId() +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}