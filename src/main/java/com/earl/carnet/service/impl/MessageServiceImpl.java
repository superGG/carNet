package com.earl.carnet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.MessageDao;
import com.earl.carnet.domain.carnet.Message.Message;
import com.earl.carnet.service.MessageService;

@Service("MessageService")
@Transactional
public class MessageServiceImpl extends BaseServiceImpl<Message,Message>
        implements MessageService {

//    private static Logger logger = Logger.getLogger(MessageServiceImpl.class);

    @Resource
    MessageDao messageDao;

    @Override
    protected BaseDao<Message> getDao() {
        return messageDao;
    }

    @Override
    public Boolean updateUserAll(Long userId) {
        return messageDao.updateUserAll(userId);
    }
}
