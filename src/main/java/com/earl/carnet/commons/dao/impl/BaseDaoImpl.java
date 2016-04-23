package com.earl.carnet.commons.dao.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.domain.AbstractEntity;
import com.earl.carnet.commons.domain.DateQuery;
import com.earl.carnet.commons.util.Assert;
import org.apache.commons.beanutils.BeanMap;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLScript;
import org.beetl.sql.core.db.AbstractDBStyle;
import org.beetl.sql.core.db.KeyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.beetl.sql.core.kit.Constants.SELECT_BY_TEMPLATE;


public class BaseDaoImpl<T extends AbstractEntity<?>> implements BaseDao<T>{

	private static Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

//	@Resource(name = "sqlManager")
	protected SQLManager sqlManager;

	@Resource
	public void sqlManager(SQLManager sqlManager){

		this.sqlManager = sqlManager;
	}

	@SuppressWarnings("rawtypes")
	private Class entityClazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClazz = (Class<T>) params[0];
	}
	
	@Override
	//添加一条记录
	public int insert(T record) {
		return sqlManager.insert(record);
	}

	@Override
	//添加一条记录
	public int insertSelective(T record) {
		return sqlManager.insert(record);
	}
	
	@Override
	//添加一条新纪录，返回该记录的id
	public int insertBackId(T record){
		KeyHolder keyHolder = new KeyHolder();
		sqlManager.insert(entityClazz, record, keyHolder);
		return keyHolder.getInt();
	}

	@Override
	//根据id删除对象
	public void delete(Object id) {
		sqlManager.deleteById(entityClazz, id);//咋们暂时不支持联合主键，beetlsql支持联合主键
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return sqlManager.updateTemplateById(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	//查询所有
	public List<T> findAll() {
		return sqlManager.all(entityClazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	//分页查询
	public List<T> findAll(int start, int size) {
		// TODO 未测试.
		return sqlManager.all(entityClazz, start, size);
	}

	@Override
	//返回该对象总数
	public long countAll() {
		return sqlManager.allCount(entityClazz);
	}



	@Override
	public int updateByPrimaryKey(T record) {
		// TODO 未测试.
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	//根据id查询
	public T findOneById(Object Id) {
		return (T)sqlManager.unique(entityClazz, Id);
	}
	
	@Override
	//精确查询.
	public List<T> searchAccurate(T queryObject){
		return sqlManager.template(queryObject);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	//相似查询
	public List<T> searchSimilarity(T queryObject){
		Map<String, Object> notNullProperties = getNotNullProperties(queryObject);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("notNullProperties",notNullProperties);
		param.put("tableName",sqlManager.getNc().getTableName(queryObject.getClass()));
		
		return sqlManager.select("base.searchSimilarity", entityClazz,param,null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	//查询
	public List<T> searchQuery(Object query){
		
		Map<String, Object> notNullProperties = getNotNullProperties(query);
		String tableName = sqlManager.getNc().getTableName(entityClazz);
		
		StringBuilder sql = new StringBuilder("select * from "+tableName+" where 1=1 ");
		
		AbstractDBStyle dbStyle = (AbstractDBStyle) sqlManager.getDbStyle();
		String statementstart = dbStyle.STATEMENT_START;
		String statementend = dbStyle.STATEMENT_END;
		String hOLDER_START = dbStyle.HOLDER_START;
		String hOLDER_END = dbStyle.HOLDER_END;
		String lineSeparator = System.getProperty("line.separator", "\n");
		
		for (Entry<String,Object> entry : notNullProperties.entrySet()) {
			
			String colName = sqlManager.getNc().getColName(entry.getKey());
			
			Object value = entry.getValue();
			if(value instanceof DateQuery){
				DateQuery dateQuery = (DateQuery)value;
				Assert.assertTrue(dateQuery.begdate.getTime() < dateQuery.enddate.getTime(),"日期格式不对");
				String connector = " and ";
				String prefix = "";	
				String sql2 =  statementstart + "if(!isEmpty(" + prefix + entry.getKey()+".begdate" + ")){"
				+ statementend + connector + colName+">" +hOLDER_START+entry.getKey()+".begdate" + hOLDER_END+ lineSeparator + statementstart + "}" + statementend;

				sql2 =  sql2+statementstart + "if(!isEmpty(" + prefix+ entry.getKey() +".enddate"+ ")){"
				+ statementend + connector + colName+"<" +hOLDER_START+entry.getKey() +".enddate"+hOLDER_END+ lineSeparator + statementstart + "}" + statementend;
				sql.append(sql2);
				}else{
					sql.append(" and ").append(colName).append(" = ").append(hOLDER_START).append(entry.getKey()).append(hOLDER_END);
				}
			
			}
		System.out.println("sql语句:"+sql);
		
		return sqlManager.execute(sql.toString(), entityClazz,notNullProperties);
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchSimilarity(T queryObject,int start,int size){
		Map<String, Object> notNullProperties = getNotNullProperties(queryObject);
		
		SQLScript pageScript = sqlManager.getPageSqlScript("base.searchSimilarity");
		Map<String, Object> param = new HashMap<String, Object>();
		sqlManager.getDbStyle().initPagePara(param, start, size);
		param.put("notNullProperties",notNullProperties);
		param.put("tableName",sqlManager.getNc().getTableName(queryObject.getClass()));
		
		return (List<T>) pageScript.select(queryObject.getClass(), param,null);
	}
	
	//翻页
	public List<T> pageSearchAccurate(T queryObject, int start, int size) {
		return sqlManager.template(queryObject, start, size);
	}

	//符合条件的个数
	public long countMatch(T queryObject) {
		return sqlManager.templateCount(queryObject);
	}

	/**
	 * 获取非空属性.
	 * @param object
	 * @return
     */
	private Map<String, Object> getNotNullProperties(Object object) {
		Map<String, Object> notNullParam = null;
		BeanMap beanMap = new BeanMap(object);
		notNullParam = new HashMap<String, Object>();
		Iterator<Object> keyIterator = beanMap.keySet().iterator();
		String propertyName = null;
		while (keyIterator.hasNext()) {
			propertyName = (String) keyIterator.next();
			if (!propertyName.equals("class")
					&& beanMap.get(propertyName) != null
					&& !beanMap.get(propertyName).equals("")) {
				notNullParam.put(propertyName, beanMap.get(propertyName));
			}
		}
		return notNullParam;
	}
	
	@Override
	public void deleteByQuery(Object queryObject){
		Map<String, Object> notNullProperties = getNotNullProperties(queryObject);
		SQLScript script = sqlManager.getScript(entityClazz, SELECT_BY_TEMPLATE);
		String sql = script.getSql();
		String templatesql = sql.replaceAll("select *", "delete").replace("*", "");
		log.info("script sql :" + script.getSql());

		sqlManager.executeUpdate(templatesql,notNullProperties);
	}

}
