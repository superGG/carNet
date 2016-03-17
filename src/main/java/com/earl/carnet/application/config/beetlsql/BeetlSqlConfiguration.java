package com.earl.carnet.application.config.beetlsql;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring.SpringBeetlSql;
import org.beetl.sql.ext.spring.SpringConnectionSource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnClass({ EnableTransactionManagement.class, EntityManager.class })
@AutoConfigureAfter({BeetlSqlBaseConfiguration.class})
// 在DataBaseConfiguration配置好后，才执行
public class BeetlSqlConfiguration implements EnvironmentAware {

	private static Log logger = LogFactory.getLog(BeetlSqlConfiguration.class);

	@SuppressWarnings("unused")
	private RelaxedPropertyResolver propertyResolver;

	@Resource
	private DataSource dataSource;

	@Resource
	private MySqlStyle dbStyle;

	@Resource
	private DefaultNameConversion nc;
	
//	@Inject
//	private ConnectionSource ds; //在非spring整合中才用到
	
	@Resource
	private SpringConnectionSource cs;
	
	@Resource
	private ClasspathLoader sqlLoader;
	
	Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment,
				"mybatis.");
	}

	@Bean
	@ConditionalOnMissingBean
	public SQLManager sqlManager() {
		logger.info("building SQLManager");
		Interceptor[] inters={new DebugInterceptor()};
		
				SpringBeetlSql beetlSqlManager = new SpringBeetlSql();
				
				cs.setMasterSource(dataSource);
				beetlSqlManager.setCs(cs);
				beetlSqlManager.setNc(nc);
				beetlSqlManager.setInterceptors(inters);
				beetlSqlManager.setDbStyle(dbStyle);
				beetlSqlManager.setSqlLoader(sqlLoader);
				beetlSqlManager.init();
				beetlSqlManager.getSQLMananger();
				SQLManager sqlMananger = beetlSqlManager.getSQLMananger();
				return sqlMananger;
	}
	
	// 在文章最后提到，Spring
	// Boot的自动配置机制依靠@ConditionalOnMissingBean注解判断是否执行初始化代码，即如果用户已经创建了bean，则相关的初始化代码不再执行。
	@Bean
	@ConditionalOnMissingBean
	public DataSourceTransactionManager transactionManager() {
				DataSourceTransactionManager tm = new DataSourceTransactionManager(dataSource);
				return tm;
	}
}
