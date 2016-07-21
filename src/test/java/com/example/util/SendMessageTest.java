package com.example.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
//import com.earl.carnet.util.SendMessage;
import java.util.Set;

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
    
    @Test
    public void testset(){
    	Set<String> dodo = new HashSet<String>();
    	dodo.add("dodo");
    	dodo.add("dodo1");
    	dodo.add("dodo2");
    	dodo.add("dodo3");
    	dodo.add("dodo4");
    	
    	for (String object : dodo) {
			System.out.println(object);
		}
    }

}
