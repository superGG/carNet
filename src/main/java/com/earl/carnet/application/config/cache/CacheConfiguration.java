package com.earl.carnet.application.config.cache;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class CacheConfiguration implements EnvironmentAware {

	private static Log logger = LogFactory.getLog(CacheConfiguration.class);

	@SuppressWarnings("unused")
	private RelaxedPropertyResolver propertyResolver;


	Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "mybatis.");
	}

	@Bean(name = "keyGenderator")
    public KeyGenerator keyGenderator(){  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append(obj.toString());  
                }  
                logger.info(sb.toString());
                return sb.toString();  
            }  
        };  
  
    }  

}
