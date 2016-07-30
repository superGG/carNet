package com.earl.carnet.plugin.database.config;

public class DefaultConfig implements IConfig{

	
	public boolean additionBackUp; //是否增量备份
	
	public String genFilePath; // 保存的文件路径
	
	public String rootFilePath; // 增量策略中，需要添加的增量文件
	
	public String server;
	
	public String username;
	
	public String password;
	
	public String driverClassName;
	
	public String url;
	
	public String appendFilePath;
	
	
	public DefaultConfig myConfig;
	
	public String getAppendFilePath() {
		return appendFilePath;
	}

	public void setAppendFilePath(String appendFilePath) {
		this.appendFilePath = appendFilePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public DefaultConfig getMyConfig() {
		return myConfig;
	}

	public void setMyConfig(DefaultConfig myConfig) {
		this.myConfig = myConfig;
	}

	public boolean isAdditionBackUp() {
		return additionBackUp;
	}

	public void setAdditionBackUp(boolean additionBackUp) {
		this.additionBackUp = additionBackUp;
	}

	public String getGenFilePath() {
		return genFilePath;
	}

	public void setGenFilePath(String genFilePath) {
		this.genFilePath = genFilePath;
	}

	public String getRootFilePath() {
		return rootFilePath;
	}

	public void setRootFilePath(String rootFilePath) {
		this.rootFilePath = rootFilePath;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	
	
}
