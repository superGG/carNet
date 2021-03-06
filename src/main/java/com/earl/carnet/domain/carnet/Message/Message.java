package com.earl.carnet.domain.carnet.Message;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 消息实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name ="message")
public class Message extends AbstractAuditingEntity<Long> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;//用户id

    private String content;//消息内容

    private Boolean state; //消息状态

    private String title;//标题

    private Integer type;//消息类型 1为普通消息，2为警告消息，3为危险消息

    private String createTime;//消息时间

    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getState() {
        return state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + super.getId() +
                "userId=" + userId +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
