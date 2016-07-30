package com.earl.carnet.plugin.database.stroge;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import com.earl.carnet.plugin.database.columntype.ColumnRuleFactory;
import com.earl.carnet.plugin.database.columntype.IColumnTypeRule;
import com.earl.carnet.plugin.database.core.Column;
import com.earl.carnet.plugin.database.core.PrimaryKey;
import com.earl.carnet.plugin.database.core.Table;

/**
 * navicat 风格的数据库数据
 * 
 * @author Administrator
 *
 */
public class NavicatStroge extends BaseStroge implements IStroge {

	public NavicatStroge(Writer writer, Table table) {
		super(writer, table);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String dataTemplate(List<String> valueList) { // 存储单条数据
		// TODO Auto-generated method stub

		StringBuilder builder = new StringBuilder("INSERT INTO ");
		builder.append('`').append(table.getName()).append('`').append(" VALUE(");
		int size = valueList.size();
		for (int i = 1; i <= size; i++) {
			if (i == 1) {
				builder.append('\'').append(valueList.get(0)).append('\'');
			} else {
				String string = valueList.get(i - 1);
				if (string != null) {
					string = "'" + string + "'";
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
//	StringBuilder tmpBuilder = new StringBuilder();
//	tmpBuilder.append("CREATE TABLE ").append('`').append(table.getName()).append('`').append("(\n");
//	for (Iterator<Column> iterator = table.getColumns().iterator(); iterator.hasNext();) {
//		Column column = iterator.next();
//		tmpBuilder.append('`').append(column.getColumnName()).append('`').append(' ');
//		
//				String tmpString =column.getColumnTypeName().toLowerCase();
//				
//				tmpBuilder.append(tmpString);
//				
//				IColumnTypeRule iColumnTypeRule = ColumnRuleFactory.columnRule.get(tmpString);
//				
//				if(iColumnTypeRule != null){
//					tmpBuilder.append(iColumnTypeRule.showColumnType(column));
//				}else{
//					tmpBuilder.append(" (").append(column.getPrecision())
//					.append(')');
//				}
//				
//				tmpBuilder.append(" DEFAULT ").append(column.getDefValue());
//				if(!column.getRemarks().trim().isEmpty()){
//					tmpBuilder.append(" COMMENT ").append('\'').append(column.getRemarks()).append('\'');
//				}
//				
//				tmpBuilder.append(',').append("\n");
//	}
//	tmpBuilder.append("PRIMARY KEY (");
//	for (PrimaryKey primaryKey : table.getPrimaryKeys()) {
//		// TODO 添加主键展示
//		tmpBuilder.append('`').append(primaryKey.getColumnName()).append('`');
//	}
//	tmpBuilder.append(")\n");
//	tmpBuilder.append(')');
//	tmpBuilder.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;\n");
//	return tmpBuilder.toString();
	@Override
	public String tableTemplate() {
		// TODO Auto-generated method stub
		return table.getTableSql();
	}

}
