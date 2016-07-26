package com.earl.carnet.security.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

//之前讲了Shiro Security如何结合验证码，这次讲讲如何限制用户登录尝试次数，防止多次尝试，暴力破解密码情况出现。要限制用户登录尝试次数，必然要对用户名密码验证失败做记录，Shiro中用户名密码的验证交给了CredentialsMatcher 
//所以在CredentialsMatcher里面检查，记录登录次数是最简单的做法。Shiro天生和Ehcache是一对好搭档，无论是单机还是集群，都可以在Ehcache中存储登录尝试次数信息。 
//现在介绍一个简单的登录次数验证做法，实现一个RetryLimitCredentialsMatchers继承至HashedCredentialsMatcher，加入缓存，在每次验证用户名密码之前先验证用户名尝试次数，如果超过5次就抛出尝试过多异常，否则验证用户名密码，验证成功把尝试次数清零，不成功则直接退出。这里依靠Ehcache自带的timeToIdleSeconds来保证锁定时间（帐号锁定之后的最后一次尝试间隔timeToIdleSeconds秒之后自动清除）。

public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {
	
	private static Logger logger = LogManager   
            .getLogger(RetryLimitCredentialsMatcher.class);
	
	// 集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
	private Cache<String, AtomicInteger> passwordRetryCache;

	@Resource
	public CacheManager cacheManager;
	
	public RetryLimitCredentialsMatcher(CacheManager cacheManager) {

		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
		this.setHashAlgorithmName("SHA-1");
	}
	
//	@PostConstruct
//	public void init(){
//		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//		this.setHashAlgorithmName("SHA-1");
//		
//	}

	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		// retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (null == retryCount) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		if (retryCount.incrementAndGet() > 5) {

			logger.warn("username: " + username
					+ " tried to login more than 5 times in period");

			throw new ExcessiveAttemptsException("username: " + username
					+ " tried to login more than 5 times in period");
		}
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			// clear retry data
			passwordRetryCache.remove(username);
		}
		return matches;
	}
}