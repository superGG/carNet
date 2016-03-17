/*
 *  Copyright 2014-2015 snakerflow.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.earl.carnet.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.earl.carnet.domain.sercurity.user.User;

/**
 * shiro工具类
 * @author yuqs
 * @since 0.1
 */
public class ShiroUtils {
	/**
	 * 返回当前登录的认证实体ID
	 * @return
	 */
	public static Long getUserId() {
		ShiroPrincipal principal = getPrincipal();
		if(principal != null) return principal.getId();
		return -1L;
	}
	
	public static User getUser() {
		ShiroPrincipal principal = getPrincipal();
		if(principal != null) return principal.getUser();
		return null;
	}
	
	/**
	 * 获取当前登录的认证实体
	 * @return
	 */
	public static ShiroPrincipal getPrincipal() {
		Subject subject = SecurityUtils.getSubject();
		return (ShiroPrincipal)subject.getPrincipal();
	}
	
	/**
	 * 获取当前认证实体的中文名称
	 * @return
	 */
	public static String getFullname() {
		ShiroPrincipal principal = getPrincipal();
		if(principal != null) return principal.toString();
		return "";
	}
	
	/**
	 * 获取当前认证实体的登录名称
	 * @return
	 */
	public static String getUsername() {
		ShiroPrincipal principal = getPrincipal();
		if(principal != null) return principal.getUsername();
		throw new RuntimeException("user's name is null.");
	}
	
}
