package com.earl.carnet.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

	@Autowired
    private CarService carService;

//    @Autowired
//    private Reverser reverser; //反转当前操作

    @Test
    public void exampleTest() {
    	System.out.println(carService.count());
        // RemoteService has been injected into the reverser bean
//        given(this.carService.count()).willReturn(3l);
//        String reverse = reverser.reverseSomeCall();
//        assertThat("aa").isEqualTo("kcom");
    	
    	
    }

}
