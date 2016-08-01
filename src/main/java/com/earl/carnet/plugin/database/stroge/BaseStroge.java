package com.earl.carnet.plugin.database.stroge;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.earl.carnet.plugin.database.core.Column;
import com.earl.carnet.plugin.database.core.Table;

public abstract class BaseStroge implements IStroge{

	Writer writer;
	Table table;
	private ResultSet tableData;
	
	public BaseStroge(Writer writer,Table table){
		this.writer = writer;
		this.table = table;
	}
	
	public void doStroge(){
		strogeTableHeader(tableHeader());
		strogeDropTable(dropTable());
		strogeTable(); //存储表结构
		int size = table.getColumns().size();
		List<Column> columns = table.getColumns();
		tableData = table.getTableData();
		strogeDataHeader(dataHeader());
		List<String> valueList = new ArrayList<String>();
		try {
			while(tableData.next()){
				for (int i = 1; i <= size; i++) {
					
					if(columns.get(i-1).getColumnTypeName().toLowerCase().indexOf("bit") != -1){
						String string = tableData.getString(i);
						if(string != null){
							if(string.equals("0")){
								valueList.add("");
							}else if(string.equals("1")){
								valueList.add("");
							}
						}else{
							valueList.add(null);
						}
					}else{
						valueList.add(tableData.getString(i));
					}
				}
				strogeData(dataTemplate(valueList)); //存储表数据
				valueList.clear();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void strogeDropTable(String dropTable) {
		// TODO Auto-generated method stub
		try {
			this.writer.append(dropTable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void strogeTableHeader(String tableHeader){
		try {
			this.writer.append(tableHeader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void strogeTable() {
		// TODO Auto-generated method stub
		try {
			this.writer.append(tableTemplate());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void strogeDataHeader(String dataHeader){
		try {
			this.writer.append(dataHeader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void strogeData(String data) {
		// TODO Auto-generated method stub
		try {
			this.writer.append(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
