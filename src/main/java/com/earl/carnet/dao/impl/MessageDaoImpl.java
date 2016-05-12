package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.MessageDao;
import com.earl.carnet.domain.carnet.Message.Message;
import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {

    @Override
    public Boolean updateUserAll(Long userId) {
        String sql = "update message set state=1 where userId = ?";
        SQLReady sqlReady = new SQLReady(sql,userId);
        int i = sqlManager.executeUpdate(sqlReady);
        if (i!=0){
            return true;
        } else {
            return false;
        }
    }
}