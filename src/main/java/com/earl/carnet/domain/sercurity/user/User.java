package com.earl.carnet.domain.sercurity.user;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@ApiModel(value="SubModel")
@JsonInclude(JsonInclude.Include.NON_NULL) //jackson 控制，放回字段为null,将被过滤
@Table(name = "user")
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    //    private Long id;
    private String username;

    private String realName;

    private String phone;

    private String loginid;

    private String password;

    private String userImg;

    private String safePassword;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", loginid='" + loginid + '\'' +
                ", password='" + password + '\'' +
                ", userImg='" + userImg + '\'' +
                ", safePassword='" + safePassword + '\'' +
                '}';
    }
}