package com.earl.carnet.application.config.mvc;

import com.earl.carnet.application.config.beetlsql.BeetlSqlConfiguration;
import com.earl.carnet.interceptor.Ip2Interceptor;
import com.earl.carnet.interceptor.IpInterceptor;
import com.earl.carnet.interceptor.SystemExceptionHandler;
import com.earl.carnet.interceptor.ValidationInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@ComponentScan
//@AutoConfigureAfter({BeetlSqlConfiguration.class})
public class mvcConfiguration extends WebMvcConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(mvcConfiguration.class);

    @Resource
    ValidationInterceptor validationInterceptor;

    //	 @Bean
//	 public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//	  RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
//	  handlerMapping.setUseSuffixPatternMatch(false);
//	  handlerMapping.setUseTrailingSlashMatch(false);
//	  return handlerMapping;
//	 }

    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IpInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new Ip2Interceptor()).addPathPatterns("/**");
        registry.addInterceptor(validationInterceptor).addPathPatterns("/**");
    }



    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new SystemExceptionHandler());
    }

//    /**
//     * spring boot 定时任务
//     */
//    @Scheduled(cron="0 0 22 * * ?")
//    public void reportCurrentTime() {
//    	crawler.getBlogList(1);
//    }

}
