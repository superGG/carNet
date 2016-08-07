package com.earl.carnet.plugin.database.stroge;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.earl.carnet.plugin.database.columntype.ColumnRuleFactory;
import com.earl.carnet.plugin.database.columntype.IColumnTypeRule;
import com.earl.carnet.plugin.database.config.DefaultConfig;
import com.earl.carnet.plugin.database.core.Column;
import com.earl.carnet.plugin.database.core.Function;
import com.earl.carnet.plugin.database.core.Table;

public abstract class BaseStroge implements IStroge {

	Writer writer;
	Table table;
	Function function;
	private ResultSet tableData;

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public BaseStroge() {

	}

	public BaseStroge(Writer writer, Table table) {
		this.writer = writer;
		this.table = table;
	}

	public void doStroge(DefaultConfig config) {
		
		//TODO 配置表存储策略 //是否存储表信息
		
		if(tableTemplate()!= null){
			strogeTableHeader(tableHeader());
			strogeDropTable(dropTable());
			strogeTable(); // 存储表结构
		}

		int size = table.getColumns().size();
		List<Column> columns = table.getColumns();
		tableData = table.getTableData();
		if(tableData != null){
			//TODO 配置数据存储策略
			strogeDataHeader(dataHeader());
			List<String> valueList = new ArrayList<String>();
			try {
				while (tableData.next()) {
					for (int i = 1; i <= size; i++) {

						IColumnTypeRule columnRule =  ColumnRuleFactory.columnRule
								.get(columns.get(i - 1).getColumnTypeName().toLowerCase());
						String string = tableData.getString(i);
						if(string != null){
							if (columnRule != null) {
								valueList.add(columnRule.valueType(tableData.getString(i).replace("\r", "\\r").replace("\n", "\\n")));
							} else {
								valueList.add(tableData.getString(i).replace("\r", "\\r").replace("\n", "\\n"));
							}
						}else{
							valueList.add(string);
						}
						
					}
					strogeData(dataTemplate(valueList)); // 存储表数据
					valueList.clear();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void strogeDropTable(String dropTable) {
		try {
			this.writer.append(dropTable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void strogeTableHeader(String tableHeader) {
		try {
			this.writer.append(tableHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void strogeTable() {
		try {
			this.writer.append(tableTemplate());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void strogeDataHeader(String dataHeader) {
		try {
			this.writer.append(dataHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void strogeData(String data) {
		try {
			this.writer.append(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void strogeFunction() throws IOException {
		// TODO Auto-generated method stub
		this.writer.append(function.getFunctionStr());
	}
	
	public void finishStroge() {
		try {
			this.writer.flush();
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
