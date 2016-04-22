package com.example.util;

import com.earl.carnet.commons.util.Assert;
import com.earl.carnet.util.SendMessage;
import org.junit.Test;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SendMessageTest {


    @Test
   public void testSendMessage(){
       SendMessage sendMessage = new SendMessage();
        Boolean send = sendMessage.sendTo("16user").withMessage("i love you").send();
        Assert.assertTrue(send,"success");
    }

}
