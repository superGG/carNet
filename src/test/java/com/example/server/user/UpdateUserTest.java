package com.example.server.user;


import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.earl.carnet.Application;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;

import cucumber.api.java.zh_cn.同时;
import cucumber.api.java.zh_cn.当;
import cucumber.api.java.zh_cn.那么;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class UpdateUserTest {

    private static Logger logger = Logger.getLogger(UpdateUserTest.class);

    @Resource
    UserService userService;

    User user = new User();

    @当("^修改账号为(.*)的信息")
    public void findOneByLoginId(String loginid){
        User user = userService.findOneByLoginId(loginid);
        this.user.setId(user.getId());
        this.user.setLoginid(user.getLoginid());
    }



    @同时("^设置用户昵称为(.*)")
    public void username(String username){
        this.user.setUsername(username);
    }

    @同时("^设置用户真实姓名为(.*)")
    public void userrealName(String username){
        this.user.setRealName(username);
    }

    @同时("^设置用户手机为(.*)")
    public void userphone(String phone){
        this.user.setPhone(phone);
    }

    @那么("^结果是(.*)")
    public void updateUser(String result){
        userService.updateByPrimaryKeySelective(this.user);

        User tmpuser = userService.findOneByLoginId(this.user.getLoginid());
        Assert.assertEquals(tmpuser.getPhone(),this.user.getPhone());
        Assert.assertEquals(tmpuser.getUsername(),this.user.getUsername());
        Assert.assertEquals(tmpuser.getRealName(),this.user.getRealName());

        Assert.assertEquals("更新成功",result);
    }

}
