package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.Message.Message;

import java.util.List;

public interface MessageService extends BaseService<Message, Message> {

    /**
     * 更新用户所有未读信息.
     * @param userId
     * @return
     */
    Boolean updateUserAll(Long userId);
}