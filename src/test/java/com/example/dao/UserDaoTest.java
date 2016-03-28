package com.example.dao;

import com.earl.carnet.Application;
import com.earl.carnet.dao.UserDao;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserDaoTest {

	@Resource
	UserService userService;

	@Test
	public void contextLoads() {
//		userService.countAll();
	}

	@Test
	public void xx(){

			User user = new User();
			user.setPassword("thismeme2");
			user.setUsername("thisnamememe2");
			user.setUserimg("thisimgmeme2");

			userService.save(user);
	}

	@Test
	public void xxx(){
		long sysDate = System.currentTimeMillis();
		userService.findAll().forEach(user -> System.out.println(user.toString()));
		long sysDate2 = System.currentTimeMillis();
		System.out.println(sysDate2-sysDate);
	}

	@Test
	public void xxxxx(){
		for(int i=11506;i<11907;i++)
		userService.delete(i);
	}

}
