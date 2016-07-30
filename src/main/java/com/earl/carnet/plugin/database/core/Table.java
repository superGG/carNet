package com.earl.carnet.plugin.database.core;

import static org.slf4j.LoggerFactory.getLogger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

public class Table implements IBeSql{

	private static Logger logger = getLogger(Table.class);
	
	private String name;
	
	private String catalog;
	
	private String type;//标记当前类型是表还是视图
	
	private List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();
	
	private List<Column> columns = new ArrayList<Column>();// 表拥有的字段,字段属性

	private List<ForeinKey> forginKey = new ArrayList<ForeinKey>();
	
	
	private Connection connection;

	private String tableSql;
	
	private DatabaseMetaData metaData;
	
	private ResultSet tableData; //表数据
	
	
	public String getTableSql() {
		return tableSql;
	}

	public void setTableSql(String tableSql) {
		this.tableSql = tableSql;
	}

	public ResultSet getTableData() {
		return tableData;
	}

	public void setTableData(ResultSet tableData) {
		this.tableData = tableData;
	}

	public Table(ResultSet tSet,Connection connection, DatabaseMetaData metaData) throws SQLException {
		// TODO Auto-generated constructor stub
		this.connection = connection;
		this.metaData = metaData;
		
		this.catalog = tSet.getString(1);
		this.name = tSet.getString(3);
		
	}

	public void initPrimaryKey() throws SQLException{//依赖DatabaseMetaData metaData
		logger.info("###### 表的主键列信息");
		ResultSet primaryKey = metaData.getPrimaryKeys(null, this.catalog, this.name);
		while (primaryKey.next()) {
			
			PrimaryKey tmpPrimaryKey= new PrimaryKey();
			tmpPrimaryKey.setColumnName(primaryKey.getString("COLUMN_NAME"));
			tmpPrimaryKey.setPkName(primaryKey.getString("PK_NAME"));
			logger.info("表名:" + primaryKey.getString("TABLE_NAME") + ",列名:" + primaryKey.getString("COLUMN_NAME")
					+ " 主键名:" + primaryKey.getString("PK_NAME"));
			// 表名:SYS_ROLE_RES,列名:SYS_RES_ID 主键名:CONSTRAINT_9
			// 表名:SYS_ROLE_RES,列名:SYS_ROLE_ID 主键名:CONSTRAINT_9
			primaryKeys.add(tmpPrimaryKey);
		}
	}

	public void initForeinKey() throws SQLException{// 依赖DatabaseMetaData metaData
		logger.info("###### 表的外键列信息");
		ResultSet foreinKey = metaData.getImportedKeys(null, this.catalog,this.name);
		while (foreinKey.next()) {
			
			ForeinKey tmpForeinKey = new ForeinKey();
			tmpForeinKey.setPkName(foreinKey.getString("PK_NAME"));
			tmpForeinKey.setFkColumnName(foreinKey.getString("FKCOLUMN_NAME"));
			tmpForeinKey.setPkTableName(foreinKey.getString("PKTABLE_NAME"));
			tmpForeinKey.setFkTableName(foreinKey.getString("FKTABLE_NAME"));
			tmpForeinKey.setPkColumnName(foreinKey.getString("PKCOLUMN_NAME"));
			tmpForeinKey.setKeySeq(foreinKey.getString("KEY_SEQ"));
			
			logger.info("主键名:" + foreinKey.getString("PK_NAME") + ",外键名:" + foreinKey.getString("FKCOLUMN_NAME")
					+ ",主键表名:" + foreinKey.getString("PKTABLE_NAME") + ",外键表名:" + foreinKey.getString("FKTABLE_NAME")
					+ ",外键列名:" + foreinKey.getString("PKCOLUMN_NAME") + ",外键序号:" + foreinKey.getString("KEY_SEQ"));
			// 主键名:PRIMARY_KEY_95,外键名:SYS_RES_ID,主键表名:SYS_RESOURCE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
			// 主键名:PRIMARY_KEY_A,外键名:SYS_ROLE_ID,主键表名:SYS_ROLE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
		}
	}
	
	public void initColumn(ResultSet rsSet) throws SQLException{
		  ResultSet rs = metaData.getColumns(null, null, this.name, "%");
	ResultSetMetaData rsData = rsSet.getMetaData();
		
		rsData.getColumnCount();
	
		for (int i = 1; i <= rsData.getColumnCount(); i++) {
			rs.next();
			Column column = new Column();
			column.setCatalogName(rsData.getCatalogName(i));
			column.setScale(rsData.getScale(i));
			column.setSchemaName(rsData.getSchemaName(i));
			column.setDisplaySize(rsData.getColumnDisplaySize(i));
			column.setCaseSensitive( rsData.isCaseSensitive(i));
			column.setColumnClassName(rsData.getColumnClassName(i));
			column.setColumnLabel(rsData.getColumnLabel(i));
			column.setColumnName(rsData.getColumnName(i));//sql 列名
			column.setColumnTypeName(rsData.getColumnTypeName(i));
			column.setColumnType(rsData.getColumnType(i));
			column.setPrecision(rsData.getPrecision(i));
			column.setReadOnly(rsData.isReadOnly(i));
			column.setAutoIncrement(rsData.isAutoIncrement(i));//字段是否自增
			column.setNullable(rsData.isNullable(i));//改字段是否为空
			column.setRemarks(rs.getString("REMARKS"));//列描述  
			column.setDefValue(rs.getString("COLUMN_DEF"));//默认值  
			column.checkAndModify(); //数据库类型出现偏差，自动修正
			columns.add(column);//将这个列添加入表结构中
			
			logger.info(column.toString());
		}
		
		logger.info(gendTableSql());

	}
	
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<ForeinKey> getForginKey() {
		return forginKey;
	}

	public void setForginKey(List<ForeinKey> forginKey) {
		this.forginKey = forginKey;
	}
	
	public List<PrimaryKey> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	@Override
	public String gendTableSql() throws SQLException{
		String sql = "show create table "+ name;
		ResultSet executeQuery = connection.createStatement().executeQuery(sql);
		String tableSql = null;
		while(executeQuery.next()){
			String tableName = executeQuery.getString("Table");
			tableSql = executeQuery.getString("Create Table");
			System.out.println(tableSql);
		}
		this.tableSql= tableSql+";\n";
		return tableSql;
	}

	public ResultSet prepareData() throws SQLException {
		// TODO Auto-generated method stub
		//TableData and Struts 表数据与表结构
		String sql = "select * from `" + this.name+"`";
		return connection.createStatement().executeQuery(sql);
	}
	
	public void initTable() throws SQLException{
		initPrimaryKey();
		initForeinKey();
		this.tableData = prepareData();
		
		initColumn(tableData);
	}
	
	
	
}
