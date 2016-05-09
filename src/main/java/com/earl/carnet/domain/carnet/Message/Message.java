package com.earl.carnet.domain.carnet.Message;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.beetl.sql.core.annotatoin.AutoID;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 消息实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name ="message")
public class Message extends AbstractAuditingEntity<Long> implements Serializable {

    private Long userId;//用户id

    private String content;//消息内容

    private Boolean state; //消息状态

    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
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

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId=" + userId +
                ", content='" + content + '\'' +
                ", state=" + state +
                '}';
    }
}
