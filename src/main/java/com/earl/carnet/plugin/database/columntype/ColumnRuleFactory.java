package com.earl.carnet.plugin.database.columntype;

import java.util.HashMap;
import java.util.Map;

public class ColumnRuleFactory {

	public static Map<String,IColumnTypeRule> columnRule = new HashMap<>();
	
	static{
		columnRule.put("datetime", new DateTimeRule());
		columnRule.put("decimal", new DecimalRule());
		columnRule.put("text", new TextRule());
	}
	
	
}
