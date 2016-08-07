package com.earl.carnet.plugin.database.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 函数实体
 * @author Administrator
 *
 */
public class Function {

	private String name;
	private String showFunction;
	private String remarks;
	private String functionType;
	private String functionStr;
	
	private Connection connection;
	private DatabaseMetaData metaData;
	
	public Function(Connection connection, DatabaseMetaData metaData) {
		// TODO Auto-generated constructor stub
		this.connection = connection;
		this.metaData = metaData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowFunction() {
		return showFunction;
	}
	public void setShowFunction(String showFunction) {
		this.showFunction = showFunction;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFunctionType() {
		return functionType;
	}
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	public String getFunctionStr() {
		return functionStr;
	}
	public void setFunctionStr(String functionStr) {
		this.functionStr = functionStr;
	}
	
	public void genSql() throws SQLException {
		// TODO Auto-generated method stub
		ResultSet executeQuery = connection.createStatement().executeQuery("SHOW CREATE FUNCTION `"+this.name+"`");
		while(executeQuery.next()){
			executeQuery.getString("Function");
			this.functionStr = executeQuery.getString("Create Function");
			System.out.println(functionStr);
		}
		
		
	}
	
	
	
	
}
