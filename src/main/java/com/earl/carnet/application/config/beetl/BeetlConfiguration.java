package com.earl.carnet.application.config.beetl;

import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;

@Configuration
//@EnableTransactionManagement
public class BeetlConfiguration implements EnvironmentAware {//这里继承了EnvironmentAware,自动注入env对象

	private RelaxedPropertyResolver propertyResolver;

	private static Logger log = LoggerFactory
			.getLogger(BeetlConfiguration.class);

	private Environment env;

	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean(initMethod = "init", name = "beetlConfig")
	public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {

		BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

		try {
			String root =  patternResolver.getResource("classpath:./").getFile().toString();
			WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(root);
			beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);

			beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
			return beetlGroupUtilConfiguration;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Bean(name = "beetlViewResolver")
	public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		return beetlSpringViewResolver;
	}

}
