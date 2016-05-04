package com.earl.carnet.application.config.beetlsql;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ConditionalOnClass({ EnableTransactionManagement.class, EntityManager.class })
//@AutoConfigureAfter({DataBaseConfiguration.class})
// 在DataBaseConfiguration配置好后，才执行
public class BeetlSqlConfiguration implements EnvironmentAware {

	private static Log logger = LogFactory.getLog(BeetlSqlConfiguration.class);

	@SuppressWarnings("unused")
	private RelaxedPropertyResolver propertyResolver;

//	@Inject
//	private ConnectionSource ds; //在非spring整合中才用到
	
	Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment,
				"mybatis.");
	}
	
	@Bean(name = "beetlSqlScannerConfigurer")
	public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
		BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
		conf.setBasePackage("boottest");
		conf.setDaoSuffix("Dao");
		conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
		return conf;
	}

		 @Bean(name = "sqlManagerFactoryBean")
	    @Primary
	    public SqlManagerFactoryBean getSqlManagerFactoryBean(DataSource datasource) {
	    	SqlManagerFactoryBean factory = new SqlManagerFactoryBean();
	    	
	    	BeetlSqlDataSource source = new BeetlSqlDataSource();
	    	source.setMasterSource(datasource);;
	    	factory.setCs(source);
	    	factory.setDbStyle(new MySqlStyle());
	    	factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
	    	factory.setNc(new DefaultNameConversion());
	    	factory.setSqlLoader(new ClasspathLoader("/sql"));
	    	return factory;
	    }
	
	// 在文章最后提到，Spring
	// Boot的自动配置机制依靠@ConditionalOnMissingBean注解判断是否执行初始化代码，即如果用户已经创建了bean，则相关的初始化代码不再执行。
	@Bean
	@ConditionalOnMissingBean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
				DataSourceTransactionManager tm = new DataSourceTransactionManager(dataSource);
				return tm;
	}
}
