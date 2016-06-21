package com.earl.carnet.application.config.shiro;

import static org.slf4j.LoggerFactory.getLogger;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.earl.carnet.dao.PrivilegeDao;
import com.earl.carnet.domain.sercurity.privilege.Privilege;
import com.earl.carnet.security.shiro.ShiroAuthorizingRealm;

@Configuration
//@EnableTransactionManagement
//@AutoConfigureAfter({BeetlSqlConfiguration.class})
public class ShiroConfiguration implements EnvironmentAware {
    private static Logger logger = getLogger(ShiroConfiguration.class);

    public static final String PREMISSION_FORMAT = "perms[\"{0}\"]";

    private Environment env;
    
    private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
		this.propertyResolver = new RelaxedPropertyResolver(env, "security.");
	}
    
    @Bean
    @Resource
    public ShiroFilterFactoryBean shiroFilter(PrivilegeDao privilegeDao) {
    	logger.debug("构造shiroFilter实体");
    	logger.debug("配置权限系统");
		if (propertyResolver.getProperty("enable") == null) {
			logger.error("未指定是否启用权限控制,请配置secutiry.enable属性");
			Arrays.toString(env.getActiveProfiles());
			throw new ApplicationContextException(
					"Security System is not configured correctly");
		}
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setSuccessUrl("/index");
        shiroFilter.setUnauthorizedUrl("/forbidden");
        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();

        List<Privilege> privilegeList = privilegeDao.findAll();
        
        if(propertyResolver.getProperty("enable", Boolean.class)){
        filterChainDefinitionMapping.put("/api/doLogin", "anon");
        filterChainDefinitionMapping.put("/", "anon");
        filterChainDefinitionMapping.put("/index.html", "authc,roles[guest]");
        filterChainDefinitionMapping.put("/admin", "authc,roles[admin]");
        }
        
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        shiroFilter.setSecurityManager(securityManager());
        try {
            AbstractShiroFilter aa = (AbstractShiroFilter) shiroFilter.getObject();

            aa.setStaticSecurityManagerEnabled(true);
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) aa.getFilterChainResolver();
            
        	
            if(propertyResolver.getProperty("enable", Boolean.class)){
                for (Privilege privilege : privilegeList) {
                    String privilegeCode = privilege.getPrivilegeCode();
                    String matchUrl = privilege.getMatchUrl();
                    if (matchUrl.indexOf(';') != -1) {
                        String[] split = matchUrl.split(";");
                        for (String string : split) {
                        	logger.debug("控制-->"+string+ "<--的访问权限");
                            filterChainDefinitionMapping.put(string, privilegeCode);
                        }
                    } else {
                    	logger.debug("控制-->"+matchUrl+ "<--的访问权限");
                        filterChainResolver.getFilterChainManager().createChain(matchUrl, MessageFormat.format(PREMISSION_FORMAT, privilegeCode));
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("anon", new AnonymousFilter());
        filters.put("authc", new FormAuthenticationFilter());
        filters.put("logout", new LogoutFilter());
        filters.put("roles", new RolesAuthorizationFilter());
        filters.put("user", new UserFilter());
        shiroFilter.setFilters(filters);

        System.out.println(shiroFilter.getFilters().size());
        return shiroFilter;
    }

    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroAuthorizingRealm realm() {
        ShiroAuthorizingRealm propertiesRealm = new ShiroAuthorizingRealm();
        propertiesRealm.init();
        return propertiesRealm;
    }
    
    @Bean(name = "securityManager")
    @DependsOn("realm")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
