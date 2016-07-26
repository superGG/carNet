package com.earl.carnet.plugin;

public interface IPlugin {

	
	/**
	 * 启动插件
	 * @throws Exception 
	 */
	public void start() throws Exception;
	
	/**
	 * 插件服务
	 */
	public void service();
	
	
	/**
	 * 插件暂停
	 */
	public void pause();
	
	
	/**
	 * 插件停止
	 */
	public void stop();
	
}
