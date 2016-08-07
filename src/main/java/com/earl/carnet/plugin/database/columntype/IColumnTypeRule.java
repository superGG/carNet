package com.earl.carnet.plugin.database.columntype;

import com.earl.carnet.plugin.database.core.Column;

public interface IColumnTypeRule {

	String showColumnType(Column column);

	String valueType(String value);

}
