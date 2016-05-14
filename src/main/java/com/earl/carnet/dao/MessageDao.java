package com.earl.carnet.dao;


import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.carnet.Message.Message;

public interface MessageDao extends BaseDao<Message> {


    /**
     * 更新用户所有未读信息。
     * @param userId
     * @return
     */
    Boolean updateUserAll(Long userId);
}