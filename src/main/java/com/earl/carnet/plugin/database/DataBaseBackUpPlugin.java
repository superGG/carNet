package com.earl.carnet.plugin.database;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.earl.carnet.plugin.IPlugin;
import com.earl.carnet.plugin.database.config.DefaultConfig;
import com.earl.carnet.plugin.database.config.YmlConfigReader;
import com.earl.carnet.plugin.database.connection.DbConnectionFactory;
import com.earl.carnet.plugin.database.core.Function;
import com.earl.carnet.plugin.database.core.Table;
import com.earl.carnet.plugin.database.stroge.NavicatStroge;

public class DataBaseBackUpPlugin implements IPlugin {

	private static Logger logger = getLogger(DataBaseBackUpPlugin.class);

	public static Connection connection;

	public static DatabaseMetaData metaData;

	public void baseDBInfo() throws SQLException {
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

	}

	// +"\n_表的解释性注释:"+tSet.getString("REMARKS")+"_类型的类别:"+tSet.getString("TYPE_CAT")
	// +"\n_类型模式:"+tSet.getString("TYPE_SCHEM")+"_类型名称:"+tSet.getString("TYPE_NAME")
	// +"\n_有类型表的指定'identifier'列的名称:"+tSet.getString("SELF_REFERENCING_COL_NAME")
	// +"\n_指定在 SELF_REFERENCING_COL_NAME
	// 中创建值的方式:"+tSet.getString("REF_GENERATION")

	// 启动数据库备份插件
	@Override
	public void start() throws Exception {

		DefaultConfig config = new YmlConfigReader().readResource("backup/backup-config.yml");

		initConnection(config);

		baseDBInfo();
		// TODO 增量备份策略扩展
		File file = new File("D:/aa" + new Date().getTime() + ".sql");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter writer = new FileWriter(file, true);

		writer.append("SET FOREIGN_KEY_CHECKS=0;\n"); // 如果是增量备份，不涉及到表备份，则不需要这个东西

		NavicatStroge navicatStroge = new NavicatStroge();
		navicatStroge.setWriter(writer);

		logger.info("###### 获取表的信息");
		// SHOW FULL TABLES FROM `vc_camp` LIKE '%'
		// 构造表结构
		List<Table> arrayList = constructTables(metaData);

		for (Table table2 : arrayList) {

			table2.initTable(config);

			navicatStroge.setTable(table2);

			navicatStroge.doStroge(config);
			
		}
		
		if(config.getBooleanFunction()){
			
			List<Function> constructFunction = constructFunction(metaData);
			
			for (Function table2 : constructFunction) {

				table2.genSql();
				navicatStroge.setFunction(table2);
				navicatStroge.strogeFunction();
				
			}
		}
		
		navicatStroge.finishStroge();

		connection.close();
		Thread.sleep(100000);
	}

	private List<Table> constructTables(DatabaseMetaData metaData2) throws SQLException {
		ResultSet singalTable = null;
		List<Table> arrayList = new ArrayList<>();
		try {
			singalTable = metaData.getTables(null, "%", "%", new String[] { "TABLE", "VIEW" }); // TODO
			while (singalTable.next()) {
				// 下面构建表结构
				Table table = new Table(singalTable, connection, metaData); // 构建表对象
				table.setSchem(singalTable.getString("TABLE_SCHEM"));
				table.setName(singalTable.getString("TABLE_NAME"));
				table.setType(singalTable.getString("TABLE_TYPE"));
				table.setCatalog(singalTable.getString("TABLE_CAT"));
				table.initColumn(); // 初始化列信息
				arrayList.add(table);
				//TODO 多线程构造表数据
				logger.info(singalTable.getRow() + "_表类别:" + singalTable.getString("TABLE_CAT") + "_表模式:"
						+ singalTable.getString("TABLE_SCHEM") + "_表名称:" + singalTable.getString("TABLE_NAME") + "_表类型:"
						+ singalTable.getString("TABLE_TYPE"));
				// 2_表类别:MANOR_表模式:PUBLIC_表名称:SYS_RESOURCE_表类型:TABLE
			}
		} finally {
			if (singalTable != null) {
				singalTable.close();
			}
		}
		return arrayList;
	}

	private List<Function> constructFunction(DatabaseMetaData metaData2) {
		ResultSet singalTable = null;
		List<Function> arrayList = new ArrayList<>();
		try {
		singalTable = metaData2.getFunctions(null, "%", "%");
			ResultSetMetaData metaData3 = singalTable.getMetaData();
			int columnCount = metaData3.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				System.out.println(metaData3.getColumnName(i));
			}
			while (singalTable.next()) {
				// 下面构建表结构
				Function function = new Function(connection,metaData);
//				function.setName(singalTable.get;
				
				function.setName(singalTable.getString("FUNCTION_NAME"));
				function.setRemarks(singalTable.getString("REMARKS"));
				function.setFunctionType(singalTable.getString("FUNCTION_TYPE"));
				function.genSql();
				arrayList.add(function);
				// 多线程构造表数据
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (singalTable != null) {
				try {
					singalTable.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return arrayList;
	}

	private void initConnection(DefaultConfig config) {

		connection = new DbConnectionFactory(config).getConnection();
		try {
			metaData = connection.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 执行插件业务
	@Override
	public void service() {
		// TODO Auto-generated method stub

	}

	// 插件进入暂停状态
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	// 停止插件
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
}
