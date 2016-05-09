package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.MessageDao;
import com.earl.carnet.domain.carnet.Message.Message;
import org.springframework.stereotype.Repository;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {

}