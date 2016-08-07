package com.earl.carnet.plugin.database.columntype;

import com.earl.carnet.plugin.database.core.Column;

public class BitRule extends BaseRule implements IColumnTypeRule {

	public String valueType(String value) {
		if(value!= null){
			if("1".equals(value.trim())) return "";
			
			if("0".equals(value.trim())) return "";
		}
		return value;
	}
	
	@Override
	public String showColumnType(Column column) {
		// TODO Auto-generated method stub
		return null;
	}
}
