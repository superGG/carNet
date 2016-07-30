package com.earl.carnet.plugin.database.stroge;

import com.earl.carnet.plugin.database.config.IConfig;

/**
 * 简单工厂 构建存储持久化对象
 * @author Administrator
 *
 */
public class DefaultStrogeFactory implements StrogeFactory{

	
	@Override
	public IStroge getStroge(IConfig config){
		
		return null;
	}
	
	
}
