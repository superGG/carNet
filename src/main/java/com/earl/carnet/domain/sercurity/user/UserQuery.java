package com.earl.carnet.domain.sercurity.user;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.earl.carnet.commons.domain.DateQuery;

@Table(name = "user")
public class UserQuery extends AbstractAuditingEntity<Long> implements Serializable {

    //    private Long id;
    private String username;

    private String realname;

    private String phone;

    private String loginid;

    private String password;

    private String userimg;

    private static final long serialVersionUID = 1L;

//    private DateQuery xxx;
    
//    public DateQuery getXxx() {
//		return xxx;
//	}

//	public void setXxx(DateQuery xxx) {
//		this.xxx = xxx;
//	}

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

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", phone='" + phone + '\'' +
                ", loginid='" + loginid + '\'' +
                ", password='" + password + '\'' +
                ", userimg='" + userimg + '\'' +
                '}';
    }
}