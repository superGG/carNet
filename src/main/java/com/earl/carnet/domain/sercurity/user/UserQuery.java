package com.earl.carnet.domain.sercurity.user;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;


/**
 * 专门用来查询的对象，不能作为查询条件的属性不要放进来，提高查询
 */


@Table(name = "user")
public class UserQuery extends AbstractAuditingEntity<Long> implements Serializable {

    //    private Long id;
    private String username;

    private String realName;

    private String phone;

    private String loginid;

    private String userImg;


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


    @Override
    public String toString() {
        return "UserQuery{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", loginid='" + loginid + '\'' +
                ", userImg='" + userImg + '\'' +
                '}';
    }
}