package com.earl.carnet.plugin.database.core;

import java.sql.SQLException;

public interface IBeSql {

	String gendTableSql() throws SQLException;
	
}
