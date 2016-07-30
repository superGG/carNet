package com.earl.carnet.plugin.database.stroge;

import java.util.List;

/**
 * @author Administrator
 *
 */

//持久化 表结构，表数据
public interface IStroge {

//	需要一个ResuleSet
	
//	或者一组ResultSet //内存消耗大
	
	public void strogeTable();
	
	void strogeData(String data);

	String tableTemplate();

	String dataTemplate(List<String> map);

	
}
