package com.earl.carnet.application.config.beetlsql;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.spring.SpringConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@AutoConfigureAfter({ DataBaseConfiguration.class })//在DataBaseConfiguration配置好后，才执行
public class BeetlSqlBaseConfiguration implements EnvironmentAware {//这里继承了EnvironmentAware,自动注入env对象

	@SuppressWarnings("unused")
	private RelaxedPropertyResolver propertyResolver;

	private static Logger log = LoggerFactory
			.getLogger(BeetlSqlBaseConfiguration.class);

	@SuppressWarnings("unused")
	private Environment env;
	
	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean
	@ConditionalOnMissingBean
	public MySqlStyle dbStyle() {
		log.info("building dbStyle");
		return new MySqlStyle();
	}

	@Bean
	@ConditionalOnMissingBean
	public DefaultNameConversion nc() {
		return new DefaultNameConversion();
	}

	@Bean
	@ConditionalOnMissingBean
	public ConnectionSource ds(DataSource dataSource) {
		return ConnectionSourceHelper.getSingle(dataSource);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ClasspathLoader sqlLoader() {
		ClasspathLoader classpathLoader = new ClasspathLoader();
		classpathLoader.setSqlRoot("/sql");
		return classpathLoader;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SpringConnectionSource cs() {
		SpringConnectionSource springConnectionSource = new SpringConnectionSource();
		return springConnectionSource;
	}
	
}	
