package com.earl.carnet.application.config.beetlsql;

import java.util.Arrays;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration implements EnvironmentAware {//这里继承了EnvironmentAware,自动注入env对象

	private RelaxedPropertyResolver propertyResolver;

	private static Logger log = LoggerFactory
			.getLogger(DataBaseConfiguration.class);

	private Environment env;

	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean(destroyMethod = "shutdown")//指定bean的销毁方法，shutdown
	public DataSource dataSource() {
		log.debug("build DataSource");
		if (propertyResolver.getProperty("url") == null
				&& propertyResolver.getProperty("databaseName") == null) {
			log.error("Your database conncetion pool configuration is incorrct ! The application "
					+ "cannot start . Please check your jdbc");
			Arrays.toString(env.getActiveProfiles());
			throw new ApplicationContextException(
					"DataBase connection pool is not configured correctly");
		}
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(propertyResolver
				.getProperty("dataSourceClassName"));
		if (propertyResolver.getProperty("url") == null
				|| "".equals(propertyResolver.getProperty("url"))) {
			config.addDataSourceProperty("databaseName",
					propertyResolver.getProperty("databaseName"));
			config.addDataSourceProperty("serverName",
					propertyResolver.getProperty("serverName"));
		} else {
			config.addDataSourceProperty("url",
					propertyResolver.getProperty("url"));
		}
		config.setUsername(propertyResolver.getProperty("username"));
		config.setPassword(propertyResolver.getProperty("password"));
		if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource"
				.equals(propertyResolver.getProperty("dataSourceName"))) {
			config.addDataSourceProperty("cachePrepStmts",
					propertyResolver.getProperty("cachePrepStmts"));
			config.addDataSourceProperty("prepStmtCacheSize",
					propertyResolver.getProperty("prepStmtsCacheSize"));
			config.addDataSourceProperty("prepStmtCacheSqlLimit",
					propertyResolver.getProperty("prepStmtCacheSqlLimit"));
			config.addDataSourceProperty("userServerPrepStmts",
					propertyResolver.getProperty("userServerPrepStmts"));
		}
		return new HikariDataSource(config);
	}
}
