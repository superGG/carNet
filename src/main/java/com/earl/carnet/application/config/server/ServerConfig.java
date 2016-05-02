package com.earl.carnet.application.config.server;

import javax.servlet.Filter;

import org.apache.catalina.filters.CorsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.net.Inet4Address;

@Configuration
@Profile("demo")
// Loads the spring beans required by the framework
public class ServerConfig implements EnvironmentAware {


    private RelaxedPropertyResolver propertyResolver;

    private static Logger log = LoggerFactory
            .getLogger(ServerConfig.class);

    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        this.propertyResolver = new RelaxedPropertyResolver(env, "server.");
    }

//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//
//        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//
//        if (propertyResolver.getProperty("port") == null
//                || "".equals(propertyResolver.getProperty("port"))) {
//        }else{
//            factory.setPort(Integer.valueOf(propertyResolver.getProperty("port")));
//        }
//        if (propertyResolver.getProperty("contextPath") == null
//                || "".equals(propertyResolver.getProperty("contextPath"))) {
//        }else{
//            factory.setContextPath(propertyResolver.getProperty("contextPath"));
//
//        }
//
//        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
////        factory.setSessionTimeout(10, TimeUnit.MINUTES);
//        return factory;
//    }



    //下面两个bean是添加tomcat的跨域请求设置
    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(crosFilter());
        registration.addUrlPatterns("/*");
//        registration.addInitParameter("paramName", "paramValue");
        registration.setName("CorsFilter");
        return registration;
    }

    @Bean(name = "corsFilter")
    public Filter crosFilter() {
        return new CorsFilter();
    }
}