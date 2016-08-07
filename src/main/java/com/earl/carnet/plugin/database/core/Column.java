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
	private int displaySize;
	private String schemaName;
	private int scale;
	private String catalogName;
	private String remarks;
	private String defValue;
	private String catalog;
	private String tableName;
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void checkAndModify() {
		// TODO Auto-generated method stub
		if(this.columnTypeName.toLowerCase().indexOf("varchar")!= -1){
			if(this.precision > 255){
				this.columnTypeName = "TEXT"; //TODO 需要完善
			}
		}
	}
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
	public int getDisplaySize() {
		return displaySize;
	}
	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	@Override
	public String toString() {
		return "Column [columnName=" + columnName + ", columnLabel=" + columnLabel + ", columnType=" + columnType
				+ ", columnTypeName=" + columnTypeName + ", columnClassName=" + columnClassName + ", precision="
				+ precision + ", caseSensitive=" + caseSensitive + ", readOnly=" + readOnly + ", autoIncrement="
				+ autoIncrement + ", nullable=" + nullable + ", displaySize=" + displaySize + ", schemaName="
				+ schemaName + ", scale=" + scale + ", catalogName=" + catalogName + ", remarks=" + remarks
				+ ", defValue=" + defValue + "]";
	}
	
}
