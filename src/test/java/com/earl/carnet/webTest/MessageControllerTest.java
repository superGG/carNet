package com.earl.carnet.webTest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.earl.carnet.commons.vo.ResultMessage;
import com.earl.carnet.domain.carnet.Message.Message;
import com.earl.carnet.web.MessageController;

/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT) //随机端口测试
public class MessageControllerTest {

	@LocalServerPort// 通过这个注解,得到当前使用的随机端口
	public int port; 
	
    private final Logger logger = LoggerFactory.getLogger(MessageControllerTest.class);

    ResultMessage result = null;

    @Resource
    private MessageController messageController;



    @Test
    //获取用户未读信息测试方法
    public void getUnreadTest() {
        result = new ResultMessage();
        Long userId = 1l;
        result = messageController.getUnread(userId);
        logger.info("执行获取用户未读信息方法getUnread 结果：" + result.getResultInfo());
    }

    @Test
    //获取用户所有信息测试方法
    public void getUserAllTest() {
        result = new ResultMessage();
        Long userId = 1l;
        result = messageController.getUserAll(userId);
        logger.info("执行获取用户所有信息方法 getUserAll 结果：" + result.getResultInfo());
    }

    @Test
    //获取用户所有信息测试方法
    public void saveTest() {
        result = new ResultMessage();
        Message message = new Message();
        message.setUserId(1l);
        message.setContent("测试信息内容");
        message.setTitle("测试信息标题");
        message.setType(1);
        result = messageController.save(message).getBody();
        logger.info("执行获取用户所有信息方法 save 结果：" + result.getResultInfo());
    }

    @Test
    //删除信息测试方法
    public void deleteTest() {
        result = new ResultMessage();
        Long messageId = 1l;
        result = messageController.delete(messageId).getBody();
        logger.info("执行删除信息方法 delete 结果：" + result.getResultInfo());
    }


}
