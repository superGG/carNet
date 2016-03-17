package com.earl.carnet.application.config.shiro;

import com.earl.carnet.application.config.beetlsql.BeetlSqlConfiguration;
import com.earl.carnet.dao.PrivilegeDao;
import com.earl.carnet.dao.impl.PrivilegeDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@AutoConfigureAfter({BeetlSqlConfiguration.class})
public class SecurityConfiguration {
    private static Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

//    @Bean
//    @ConditionalOnMissingBean
//    public PrivilegeDao privilegeDao() {
//        return new PrivilegeDaoImpl();
//    }
}
