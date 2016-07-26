package com.earl.carnet.plugin.database;

import static org.slf4j.LoggerFactory.getLogger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.earl.carnet.plugin.IPlugin;
import com.earl.carnet.plugin.database.core.Table;

public class DataBaseBackUpPlugin implements IPlugin {

	private static Logger logger = getLogger(DataBaseBackUpPlugin.class);
	
	public static Connection connection;

	public static DatabaseMetaData metaData;

	public static Connection getConnection() throws Exception {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
//				String url = "jdbc:mysql://localhost:3306/carnet?useUnicode=true&amp;charaterEncoding=utf-8";
//				String user = "root";
//				String pass = "root";
				
				String url = "jdbc:mysql://localhost:3306/vc_camp?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round&transformedBitIsBoolean=true";
				String user = "root";
				String pass = "root";
				connection = DriverManager.getConnection(url, user, pass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connection;
		} else {
			return connection;
		}
	}
	
	public static DatabaseMetaData getDatabaseMetaData() throws SQLException, Exception{
		if(metaData == null){
			return getConnection().getMetaData();
		}
		return metaData;
	}
	
	//启动数据库备份插件
	@Override
	public void start() throws Exception{
		// TODO Auto-generated method stub
		DatabaseMetaData metaData = getDatabaseMetaData();
		logger.info("数据库产品名: " + metaData.getDatabaseProductName());
		logger.info("数据库是否支持事务: " + metaData.supportsTransactions());
		logger.info("数据库产品的版本号:" + metaData.getDatabaseProductVersion());
		logger.info("数据库的默认事务隔离级别:" + metaData.getDefaultTransactionIsolation());
		logger.info("支持批量更新:" + metaData.supportsBatchUpdates());
		logger.info("DBMS 的 URL:" + metaData.getURL());
		logger.info("数据库的已知的用户名称:" + metaData.getUserName());
		logger.info("数据库是否处于只读模式:" + metaData.isReadOnly());
		logger.info("数据库是否支持为列提供别名:" + metaData.supportsColumnAliasing());
		logger.info("是否支持指定 LIKE 转义子句:" + metaData.supportsLikeEscapeClause());
		logger.info("是否为外连接提供受限制的支持:" + metaData.supportsLimitedOuterJoins());
		logger.info("是否允许一次打开多个事务:" + metaData.supportsMultipleTransactions());
		logger.info("是否支持 EXISTS 表达式中的子查询:" + metaData.supportsSubqueriesInExists());
		logger.info("是否支持 IN 表达式中的子查询:" + metaData.supportsSubqueriesInIns());
		logger.info("是否支持给定事务隔离级别:" + metaData.supportsTransactionIsolationLevel(1));
		logger.info("此数据库是否支持事务:" + metaData.supportsTransactions());
		logger.info("此数据库是否支持 SQL UNION:" + metaData.supportsUnion());
		logger.info("此数据库是否支持 SQL UNION ALL:" + metaData.supportsUnionAll());
		logger.info("此数据库是否为每个表使用一个文件:" + metaData.usesLocalFilePerTable());
		logger.info("此数据库是否将表存储在本地文件中:" + metaData.usesLocalFiles());
		logger.info("底层数据库的主版本号:" + metaData.getDatabaseMajorVersion());
		logger.info("底层数据库的次版本号:" + metaData.getDatabaseMinorVersion());

		logger.info("JDBC 驱动程序的主版本号:" + metaData.getJDBCMajorVersion());
		logger.info("JDBC 驱动程序的次版本号:" + metaData.getJDBCMinorVersion());
		logger.info("JDBC 驱动程序的名称:" + metaData.getDriverName());
		logger.info("JDBC 驱动程序的 String 形式的版本号:" + metaData.getDriverVersion());

		logger.info("可以在不带引号的标识符名称中使用的所有“额外”字符:" + metaData.getExtraNameCharacters());
		logger.info("用于引用 SQL 标识符的字符串:" + metaData.getIdentifierQuoteString());
		logger.info("允许用于类别名称的最大字符数:" + metaData.getMaxCatalogNameLength());
		logger.info("允许用于列名称的最大字符数:" + metaData.getMaxColumnNameLength());
		logger.info("允许在 GROUP BY 子句中使用的最大列数:" + metaData.getMaxColumnsInGroupBy());
		logger.info("允许在 SELECT 列表中使用的最大列数:" + metaData.getMaxColumnsInSelect());
		logger.info("允许在表中使用的最大列数:" + metaData.getMaxColumnsInTable());
		logger.info("数据库的并发连接的可能最大数:" + metaData.getMaxConnections());
		logger.info("允许用于游标名称的最大字符数:" + metaData.getMaxCursorNameLength());
		logger.info("在同一时间内可处于开放状态的最大活动语句数:" + metaData.getMaxStatements());

		logger.info("---------------------------------");
		logger.info("---------------------------------");
		logger.info("---------------------------------");
		logger.info("---------------------------------");

		
		logger.info("###### 获取当前数据库所支持的SQL数据类型");
		ResultSet tableType = metaData.getTypeInfo();
		while (tableType.next()) {
			logger.info("数据类型名:" + tableType.getString(1) + ",短整型的数:" + tableType.getString(2) + ",整型的数:"
					+ tableType.getString(3) + ",最小精度:" + tableType.getString(14) + ",最大精度:" + tableType.getString(15));
			// 数据类型名:TIMESTAMP,短整型的数:93,整型的数:23,最小精度:0,最大精度:10
			// 数据类型名:VARCHAR,短整型的数:12,整型的数:2147483647,最小精度:0,最大精度:0
		}

		logger.info("###### 获取数据库中允许存在的表类型");
		ResultSet tableTypes = metaData.getTableTypes();
		while (tableTypes.next()) {
			logger.info("类型名:" + tableTypes.getString(1));
			/**
			 * H2 类型名:SYSTEM TABLE 类型名:TABLE 类型名:TABLE LINK 类型名:VIEW
			 */
		}
		
		logger.info("---------------------------------");
		logger.info("---------------------------------");
		logger.info("---------------------------------");
		logger.info("---------------------------------");
		
		logger.info("###### 获取表的信息");
		ResultSet tSet = metaData.getTables(null, "%", "%", new String[] { "TABLE", "VIEW" }); //TODO 多线程构造表数据

		while (tSet.next()) {
			logger.info(tSet.getRow() + "_表类别:" + tSet.getString("TABLE_CAT") + "_表模式:" + tSet.getString("TABLE_SCHEM")
					+ "_表名称:" + tSet.getString("TABLE_NAME") + "_表类型:" + tSet.getString("TABLE_TYPE")
			// +"\n_表的解释性注释:"+tSet.getString("REMARKS")+"_类型的类别:"+tSet.getString("TYPE_CAT")
			// +"\n_类型模式:"+tSet.getString("TYPE_SCHEM")+"_类型名称:"+tSet.getString("TYPE_NAME")
			// +"\n_有类型表的指定'identifier'列的名称:"+tSet.getString("SELF_REFERENCING_COL_NAME")
			// +"\n_指定在 SELF_REFERENCING_COL_NAME
			// 中创建值的方式:"+tSet.getString("REF_GENERATION")
			);
			// 2_表类别:MANOR_表模式:PUBLIC_表名称:SYS_RESOURCE_表类型:TABLE
			
			Table table = new Table(tSet, connection,metaData,null); //构建表对象
			//下面构建表结构
			table.initTable();

		}
		tSet.close();
		connection.close();
		
	}

	//执行插件业务
	@Override
	public void service() {
		// TODO Auto-generated method stub

	}

	//插件进入暂停状态
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	//停止插件
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
}
