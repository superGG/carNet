package com.earl.carnet.plugin.database;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

/**
 * 获取数据库元素据
 * 
 * @author Administrator
 *
 */
public class DataBaseMetaData { //单例，得到元数据

	private static Logger logger = getLogger(DataBaseMetaData.class);

//	DataSource primaryDataSource;
	

	public static void main(String[] args) {
		try {
			
			DataBaseBackUpPlugin dataBasePlugin= new DataBaseBackUpPlugin();
			dataBasePlugin.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
