package com.example;

import cucumber.api.java.zh_cn.假如;
import cucumber.api.java.zh_cn.当;
import cucumber.api.java.zh_cn.那么;
import org.junit.Assert;

public class OrderDemo {

    private String action;

    private String subject;

    @假如("^客户下了一个(.*)")
    public void isChinese(String action){

       this.action = action;
        if("中文".equals(action)){
            throw new RuntimeException("xxxx");
        }
    }

    @当("^订单的(.*)不足")
    public void input(String subject){

        this.subject = subject;

    }

    @那么("^下单(.*)")
    public void outPut(String gretting){

        Assert.assertEquals("失败", gretting);

    }

}