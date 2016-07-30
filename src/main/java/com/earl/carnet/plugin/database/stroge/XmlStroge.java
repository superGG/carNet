package com.earl.carnet.plugin.database.stroge;

import java.io.Writer;
import java.util.List;

import com.earl.carnet.plugin.database.core.Table;

/**
 * xml 风格的数据库数据
 * @author Administrator
 *
 */
public class XmlStroge extends BaseStroge implements IStroge{

	public XmlStroge(Writer writer, Table table) {
		super(writer, table);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void strogeTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String tableTemplate() {
		// TODO Auto-generated method stub
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
