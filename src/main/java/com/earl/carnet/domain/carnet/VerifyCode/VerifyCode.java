package com.earl.carnet.domain.carnet.VerifyCode;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)//jackson 控制，放回字段为null,将被过滤
@Table(name = "verifycode")
public class VerifyCode  extends AbstractAuditingEntity<Long> implements Serializable{
    /**
     * 字段描述：Long
     * 字段类型：verifycodeId
     */
    private Long verifycodeId ;

    /**
     * 字段描述：String
     * 字段类型：phoneNumber
     */
    private String phoneNumber ;

    /**
     * 字段描述：String
     * 字段类型：verifyCode
     */
    private String verifyCode;


    /**
     * 字段描述：Date
     * 字段类型：createTime
     */
    private Date createTime ;

    /**
     * 字段描述：Long
     * 字段类型：creatorId
     */
    private Long creatorId ;

    /**
     * 字段描述：Boolean
     * 字段类型：isDelete
     */
    private Boolean isDelete ;

    /**
     * 字段描述：Long
     * 字段类型：version
     */
    private Long version ;

    public Long getVerifycodeId() {
        return verifycodeId;
    }

    public void setVerifycodeId(Long verifycodeId) {
        this.verifycodeId = verifycodeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "verifycodeId=" + verifycodeId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", createTime=" + createTime +
                ", creatorId=" + creatorId +
                ", isDelete=" + isDelete +
                ", version=" + version +
                '}';
    }
}
