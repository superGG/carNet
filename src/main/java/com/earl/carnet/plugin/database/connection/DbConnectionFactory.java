package com.earl.carnet.plugin.database.connection;

import java.sql.Connection;

import com.earl.carnet.plugin.database.config.DefaultConfig;

public class DbConnectionFactory implements IConnectionFactory {

	
	private DefaultConfig config;

	public DbConnectionFactory(DefaultConfig config) {
		// TODO Auto-generated constructor stub
		this.config = config;
	}
	
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		
		if(config.getDriverClassName().toLowerCase().indexOf("mysql")!= -1){
			
		return	new MySqlConnection().configBy(config);
		}

		return null; //IConnection
	}

}
