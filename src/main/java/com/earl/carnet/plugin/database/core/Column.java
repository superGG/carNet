package com.earl.carnet.plugin.database.core;

public class Column {
	
	private String columnName;
	private String columnLabel;
	private int columnType; //int 类型标识的数据库类型
	private String columnTypeName;//数据库使用的数据类型
	private String columnClassName;//java对应的数据类型
	private int precision;//列宽度
	private boolean caseSensitive;
	private boolean readOnly;
	private boolean autoIncrement;
	private int nullable;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnLabel() {
		return columnLabel;
	}
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	public String getColumnClassName() {
		return columnClassName;
	}
	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}
	
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	public void setNullable(int nullable) {
		// TODO Auto-generated method stub
		
	}
	public int getNullable() {
		return nullable;
	}
	@Override
	public String toString() {
		return "Column [columnName=" + columnName + ", columnLabel=" + columnLabel + ", columnType=" + columnType
				+ ", columnTypeName=" + columnTypeName + ", columnClassName=" + columnClassName + ", precision="
				+ precision + ", caseSensitive=" + caseSensitive + ", readOnly=" + readOnly + ", autoIncrement="
				+ autoIncrement + ", nullable=" + nullable + "]";
	}
	
}
