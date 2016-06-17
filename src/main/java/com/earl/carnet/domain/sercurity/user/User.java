package com.earl.carnet.domain.sercurity.user;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;

@ApiModel(value="SubModel")
@JsonInclude(JsonInclude.Include.NON_NULL) //jackson 控制，放回字段为null,将被过滤
@Table(name = "user")
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    //    private Long id;
    private String username;//用户昵名

    private String realName;//用户真实姓名

    private String phone;//用户手机

    private String loginid;//登录账号

    private String password;//登陆密码

    private String userImg; //用户头像

    private String safePassword; // 安全密码

    private String relatedPhone;//绑定亲人号码

    private Boolean alarmMessage ;//是否发送警报信息

    private Boolean propertyMessage ;//是否发送行性能信息

    private Boolean stateMessage ;//是否发送车状态信息

    private static final long serialVersionUID = 1L;

    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
    }

    public String getUsername() {
        return username;
    }

//    @ApiModelProperty(value = "pet status in the store", allowableValues = "available,pending,sold")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getSafePassword() {
        return safePassword;
    }

    public void setSafePassword(String safePassword) {
        this.safePassword = safePassword;
    }

    public String getRelatedPhone() {
        return relatedPhone;
    }

    public void setRelatedPhone(String relatedPhone) {
        this.relatedPhone = relatedPhone;
    }

    public Boolean getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(Boolean alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public Boolean getPropertyMessage() {
        return propertyMessage;
    }

    public void setPropertyMessage(Boolean propertyMessage) {
        this.propertyMessage = propertyMessage;
    }

    public Boolean getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(Boolean stateMessage) {
        this.stateMessage = stateMessage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", loginid='" + loginid + '\'' +
                ", password='" + password + '\'' +
                ", userImg='" + userImg + '\'' +
                ", safePassword='" + safePassword + '\'' +
                ", relatedPhone='" + relatedPhone + '\'' +
                ", alarmMessage=" + alarmMessage +
                ", propertyMessage=" + propertyMessage +
                ", stateMessage=" + stateMessage +
                '}';
    }
}