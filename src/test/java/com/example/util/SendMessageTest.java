package com.example.util;

import java.util.Map;
//import com.earl.carnet.util.SendMessage;

import org.junit.Test;

import com.earl.carnet.util.AddressHelper;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SendMessageTest {


    @Test
   public void testSendMessage(){
//       SendMessage sendMessage = new SendMessage();
//        Boolean send = sendMessage.sendTo("16user").withMessage("i love you").send();
//        Assert.assertTrue(send,"success");
    }

    @Test
    public void testAddresshelper(){
        String addr = AddressHelper.getAddress(21.8545210000, 111.5016700000);
        System.out.println(addr);
    }


    @Test
    public void getLocation(){
        Map<String, Float> addr = AddressHelper.getLocation("广东海洋大学");

        System.out.println("lon:" + addr.get("lon"));
        System.out.println("lat:" + addr.get("lat"));
    }

}
