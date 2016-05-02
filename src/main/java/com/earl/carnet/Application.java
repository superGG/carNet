package com.earl.carnet;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication//等价于@Configuration,@EnableAutoConfiguration,@ComponentScan
public class Application {

	private static Logger logger = Logger.getLogger(Application.class);

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info("进入SpringApplicationBuilder方法");
		return application.sources(Application.class);
	}
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}