package com.earl.carnet.plugin.database.stroge;

import java.io.Writer;
import java.util.List;

import com.earl.carnet.plugin.database.core.Function;
import com.earl.carnet.plugin.database.core.Table;

/**
 * navicat 风格的数据库数据
 * 
 * @author Administrator
 *
 */
public class NavicatStroge extends BaseStroge implements IStroge {

	private Function function;

	public NavicatStroge(Writer writer, Table table) {
		super(writer, table);
		// TODO Auto-generated constructor stub
	}

	public NavicatStroge() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String dropTable(){
		StringBuilder builder = new StringBuilder();
		builder.append("DROP TABLE IF EXISTS `").append(table.getName()).append("`;\n");
		return builder.toString();
	}
	
	
	@Override
	public String tableHeader(){
		StringBuilder builder = new StringBuilder();
		builder.append("\n-- ----------------------------\n")
		.append("-- Table structure for `").append(table.getName()).append("`\n")
		.append("-- ----------------------------\n\n");
		return builder.toString();
	}
	
	@Override
	public String dataHeader(){
		StringBuilder builder = new StringBuilder();
		builder.append("\n-- ----------------------------\n")
		.append("-- Records of ").append(table.getName()).append("\n")
		.append("-- ----------------------------\n");
		return builder.toString();
	}
	
	@Override
	public String dataTemplate(List<String> valueList) { // 存储单条数据
		// TODO Auto-generated method stub

		StringBuilder builder = new StringBuilder("INSERT INTO ");
		builder.append('`').append(table.getName()).append('`').append(" VALUES (");
		int size = valueList.size();
		for (int i = 1; i <= size; i++) {
			if (i == 1) {
				builder.append('\'').append(valueList.get(0)).append('\'');
			} else {
				String string = valueList.get(i - 1);
				if (string != null) {
					string = "'" + string.replace("\r\n", "\\r\\n").replace("'", "\\'") + "'";
				} else {
					string = "null";
				
				}
				builder.append(',').append(string);
			}
			// insert into table_name
			// value(String,String,String,String,String,String);
		}

		builder.append(");\n");
		return builder.toString();
	}
	
	@Override
	public String tableTemplate() {
		// TODO Auto-generated method stub
		return table.getTableSql();
	}
}
