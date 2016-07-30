package com.earl.carnet.plugin.database.stroge;

import java.io.Writer;
import java.util.List;

import com.earl.carnet.plugin.database.core.Table;

/**
 * json风格的数据库备份
 * @author Administrator
 *
 */
public class JsonStroge extends BaseStroge implements IStroge{

	
	
	public JsonStroge(Writer writer, Table table) {
		super(writer, table);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void strogeTable() { //不需要接触Set
		// TODO Auto-generated method stub
		table.toString();
	}

	@Override
	public String tableTemplate(){
		
		return null;
	}

	@Override
	public String dataTemplate(List<String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void strogeData(String data) {
		// TODO Auto-generated method stub
		
	}
	
}
