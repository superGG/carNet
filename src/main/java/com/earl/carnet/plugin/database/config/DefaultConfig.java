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
	
	public String backUpStrategy;
	
	public String remoteUrl;
	
	public int backUpInterval;
	
	public DefaultConfig myConfig;
	
	public String targetDataBase;
	
	public Boolean booleanFunction;
	
	public Boolean booleanProcedure;
	
	public Boolean getBooleanProcedure() {
		return booleanProcedure;
	}

	public void setBooleanProcedure(Boolean booleanProcedure) {
		this.booleanProcedure = booleanProcedure;
	}

	public Boolean getBooleanFunction() {
		return booleanFunction;
	}

	public void setBooleanFunction(Boolean booleanFunction) {
		this.booleanFunction = booleanFunction;
	}

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

	public String getBackUpStrategy() {
		return backUpStrategy;
	}

	public void setBackUpStrategy(String backUpStrategy) {
		this.backUpStrategy = backUpStrategy;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public int getBackUpInterval() {
		return backUpInterval;
	}

	public void setBackUpInterval(int backUpInterval) {
		this.backUpInterval = backUpInterval;
	}

	public String getTargetDataBase() {
		return targetDataBase;
	}

	public void setTargetDataBase(String targetDataBase) {
		this.targetDataBase = targetDataBase;
	}

	@Override
	public String toString() {
		return "DefaultConfig [additionBackUp=" + additionBackUp + ", genFilePath=" + genFilePath + ", rootFilePath="
				+ rootFilePath + ", server=" + server + ", username=" + username + ", password=" + password
				+ ", driverClassName=" + driverClassName + ", url=" + url + ", appendFilePath=" + appendFilePath
				+ ", backUpStrategy=" + backUpStrategy + ", remoteUrl=" + remoteUrl + ", backUpInterval="
				+ backUpInterval + ", myConfig=" + myConfig + ", targetDataBase=" + targetDataBase + "]";
	}
	
}
