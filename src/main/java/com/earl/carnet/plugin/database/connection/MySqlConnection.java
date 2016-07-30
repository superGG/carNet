package com.earl.carnet.plugin.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import com.earl.carnet.plugin.database.config.DefaultConfig;

public class MySqlConnection implements IConnection{

	@Override
	public Connection configBy(DefaultConfig config) {
		try {
			// Driver name
			Class.forName(config.getDriverClassName());
			//connection url
			return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	
	
	
}
