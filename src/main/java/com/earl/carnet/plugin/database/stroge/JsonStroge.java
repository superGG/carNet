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
	public String tableTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dataTemplate(List<String> valueList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dataHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tableHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dropTable() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
