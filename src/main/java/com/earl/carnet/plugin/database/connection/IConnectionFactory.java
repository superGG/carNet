package com.earl.carnet.plugin.database.connection;

import java.sql.Connection;

public interface IConnectionFactory {

	public Connection getConnection();
	
}
