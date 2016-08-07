package com.earl.carnet.exception;

/**
 * Created by Administrator on 2016/4/16.
 * 系统应用层面的异常
 *
 */
public class ApplicationException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message){
        super(message);
    }

}
