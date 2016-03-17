package com.earl.carnet.application;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class CommonProperties {

//    @Bean
//    public HttpMessageConverters customConverters() {
//        HttpMessageConverter<?> additional = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        return new HttpMessageConverters(additional);
//    }

	@Bean(name="public")
	public PropertiesFactoryBean publicperties(){
		PropertiesFactoryBean properties = new PropertiesFactoryBean();
		properties.setLocation(new ClassPathResource("public.properties"));
		return properties;
	}
	
	@Bean
	public Validator validator(){
		LocalValidatorFactoryBean local = new LocalValidatorFactoryBean();
		local.setProviderClass(HibernateValidator.class);
		local.setValidationMessageSource(messageSource());
		return local;
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
		message.setUseCodeAsDefaultMessage(false);
		message.setDefaultEncoding("UTF-8");
		message.setCacheSeconds(60);
		return message;
	}
}