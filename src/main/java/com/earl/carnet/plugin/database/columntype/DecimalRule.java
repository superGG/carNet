package com.earl.carnet.plugin.database.columntype;

import com.earl.carnet.plugin.database.core.Column;

public class DecimalRule extends BaseRule implements IColumnTypeRule {

	@Override
	public String showColumnType(Column column) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append('(').append(column.getPrecision()).append(',').append('0').append(')');
		return stringBuilder.toString();
	}
}
