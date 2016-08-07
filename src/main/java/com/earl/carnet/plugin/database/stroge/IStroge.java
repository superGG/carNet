package com.earl.carnet.plugin.database.stroge;

import java.io.IOException;
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

	void strogeFunction() throws IOException;

	void strogeDataHeader(String dataHeader);

	String tableTemplate();

	String dataTemplate(List<String> valueList);

	String dataHeader();

	String tableHeader();

	String dropTable();



	
}
