package com.earl.carnet.application.config.shiro;

import java.text.MessageFormat;
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
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.earl.carnet.dao.PrivilegeDao;
import com.earl.carnet.dao.impl.PrivilegeDaoImpl;
import com.earl.carnet.domain.sercurity.privilege.Privilege;
import com.earl.carnet.security.shiro.ShiroAuthorizingRealm;

@Configuration
@EnableTransactionManagement
@AutoConfigureAfter({PrivilegeDao.class})
public class ShiroConfiguration {
    private static Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    public static final String PREMISSION_FORMAT = "perms[\"{0}\"]";

//    @Resource
//    public PrivilegeDao privilegeDao;

//    @Resource
//    private ShiroAuthorizingRealm realm;
   
//    @Bean
//    public PrivilegeDao privilegeDao() {
//        return new PrivilegeDaoImpl();
//    }

    @Bean
    @DependsOn("privilegeDao")
    public ShiroFilterFactoryBean shiroFilter(PrivilegeDao privilegeDao) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setSuccessUrl("/index");
        shiroFilter.setUnauthorizedUrl("/forbidden");
        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();

        List<Privilege> privilegeList = privilegeDao.findAll();

        filterChainDefinitionMapping.put("/api/doLogin", "anon");
        filterChainDefinitionMapping.put("/", "anon");
        filterChainDefinitionMapping.put("/home", "authc,roles[guest]");
        filterChainDefinitionMapping.put("/admin", "authc,roles[admin]");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        shiroFilter.setSecurityManager(securityManager());
        try {
            AbstractShiroFilter aa = (AbstractShiroFilter) shiroFilter.getObject();

            aa.setStaticSecurityManagerEnabled(true);
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) aa.getFilterChainResolver();
            for (Privilege privilege : privilegeList) {
                String privilegeCode = privilege.getPrivilegeCode();
                String matchUrl = privilege.getMatchUrl();
                if (matchUrl.indexOf(';') != -1) {
                    String[] split = matchUrl.split(";");
                    for (String string : split) {
                        filterChainDefinitionMapping.put(string, privilegeCode);
                    }
                } else {
                    filterChainResolver.getFilterChainManager().createChain(matchUrl, MessageFormat.format(PREMISSION_FORMAT, privilegeCode));
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
