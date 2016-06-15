package com.earl.carnet.application.config.mvc;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.earl.carnet.interceptor.Ip2Interceptor;
import com.earl.carnet.interceptor.IpInterceptor;
import com.earl.carnet.interceptor.ValidationInterceptor;

@Configuration
//@AutoConfigureAfter({BeetlSqlConfiguration.class})
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(MvcConfiguration.class);

    String userfilePath = null;
    
    String qrcodefilePath = null;
    
    @Resource
    ValidationInterceptor validationInterceptor;
    
    @Value("#{public[basePath]}" + "#{public.userfilePath}")
	public void setUserFilePath(String filePath) {
		logger.debug("userfilePath=" + filePath);
		this.userfilePath = filePath;
	}
    
    @Value("#{public[basePath]}" + "#{public.qrcodefilePath}")
   	public void setQRCodeFilePath(String filePath) {
    	logger.debug("userfilePath=" + filePath);
   		this.qrcodefilePath = filePath;
   	}

    //	 @Bean
//	 public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//	  RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
//	  handlerMapping.setUseSuffixPatternMatch(false);
//	  handlerMapping.setUseTrailingSlashMatch(false);
//	  return handlerMapping;
//	 }

    //设置允许跨域节点
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/*/api-docs/*").allowedOrigins("*");
    }

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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+userfilePath);
        registry.addResourceHandler("/QRCodeImg/**").addResourceLocations("file:"+qrcodefilePath);
        super.addResourceHandlers(registry);
    }

//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(new SystemExceptionHandler());
//    }

//    /**
//     * spring boot 定时任务
//     */
//    @Scheduled(cron="0 0 22 * * ?")
//    public void reportCurrentTime() {
//    	crawler.getBlogList(1);
//    }

}
