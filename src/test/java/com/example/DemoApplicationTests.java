package com.example;

import com.earl.carnet.Application;
import cucumber.api.java.zh_cn.假如;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DemoApplicationTests {

//	@假如("^我们(.*)")
	@Test
	public void contextLoads(String action) {

		System.out.println("xxoo");
	}

}
