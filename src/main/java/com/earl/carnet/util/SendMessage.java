package com.earl.carnet.util;

import com.earl.carnet.commons.vo.TcpMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SendMessage extends HttpBase{

    String towhom;

    String messageContent;

    public SendMessage sendTo(String anotheruser){
        this.towhom = anotheruser;
        return this;
    }

    public SendMessage withMessage(String messageContent){
        this.messageContent = messageContent;
        return this;
    }

    public Boolean send(){
        String targetURL = basePath + towhom;

        TcpMessage tcpMessage = new TcpMessage();
        tcpMessage.setMessagtype(1);
        tcpMessage.setMessage("我是超级大笨蛋");


        Part[] params = {
                 new StringPart("message", tcpMessage.toJson(), "utf-8")
                // ,new StringPart("password", "123456", "utf-8")
        };

        String sendHttpRequest = sendHttpRequest(targetURL,params);
        System.out.println(sendHttpRequest);
        return Boolean.valueOf(sendHttpRequest);
    }
}
