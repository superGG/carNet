#carNet

##添加数据库备份功能
	定时保存数据库
		1.定时任务
		2.数据库解析脚本 
		3.完善外键信息 TODO
		4.添加持久化功能 TODO
		5.多数据库持久化支持 TODO
		6.增量备份
		
		并发，多线程存储数据
		
		配置策略：
			1.配置备份服务器地址，自动上传数据库信息 //服务器传输方式
			2.配置信息	
				全信息转成大写
		
		数据库备份类型工厂
		数据持久化类型工厂
		数据持久化策略 ： 全大写，全小写，关键字大写，其他小写
		定时器策略
##添加访问记录回溯功能
	添加访问记录的回溯
		*思路* ： 将访问记录日志独立出来，保存在数据库2  // 多数据源操作
		
		*回溯* ： 通过访问url,参数，组装http请求，查看返回数据
		
##plugin包
	plugin 包提供

	test