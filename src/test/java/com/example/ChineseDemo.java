package com.example;

import cucumber.api.java.zh_cn.*;
import org.junit.Assert;

public class ChineseDemo {

    private String action;

    private String subject;

    @假如("^当前是(.*)")
    public void isChinese(String action){

       this.action = action;
        System.out.println(action);
//        if("中文".equals(action)){
//            throw new RuntimeException("xxxx");
//        }
    }

    @当("^输入是(.*)")
    public void input(String subject){

        this.subject = subject;
        System.out.println(subject);

    }

    @而且("^用户没有(.*)")
    public void xxoo(String xxoo){
        System.out.println(xxoo);
    }

    @那么("^能看到(.*)")
    public void outPut(String gretting){
        System.out.println(gretting);
        Assert.assertEquals(action + subject, gretting);

    }

    @但是("^没有(.*)")
    public void ooxx(String gretting){
        System.out.println(gretting);
        Assert.assertEquals(action + subject, gretting);
    }

    @同时("^简单(.*)")
    public void werpu(String xxoo){
        System.out.println(xxoo);
    }

    @并且("^简单1(.*)")
    public void werpu1(String xxoo){
        System.out.println(xxoo);
    }
}