package com.earl.carnet.plugin.database.config;

import java.io.InputStream;

/**
 * 读取配置文件,支持文件类型JSON,yml,xml,properties
 * @author Administrator
 *
 */
public interface IConfigReader {

	/**
	 * 通过字符串读取配置文件
	 * @param resource 资源路径 
	 * @return TODO
	 */
	public IConfig readResource(String resource);

	/**
	 * 通过输入流读取配置文件
	 * @param inputStream 资源输入流
	 * @return TODO
	 */
	public IConfig readResource(InputStream inputStream);
	
	
	
	
	
}
