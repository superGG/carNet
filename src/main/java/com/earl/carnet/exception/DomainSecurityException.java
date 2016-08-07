package com.earl.carnet.exception;

/**
 * Created by Administrator on 2016/4/16.
 * 系统安全方面的异常
 *
 * 1，登录失败
 *
 * 2，账号重复注册
 *
 *
 */
public class DomainSecurityException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DomainSecurityException(String message){
        super(message);
    }
}
