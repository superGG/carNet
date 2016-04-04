package com.earl.carnet.commons.dao.impl;

import static org.beetl.sql.core.kit.Constants.SELECT_BY_TEMPLATE;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanMap;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLScript;
import org.beetl.sql.core.db.AbstractDBStyle;
import org.beetl.sql.core.db.KeyHolder;
import org.springframework.beans.factory.annotation.Autowired;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.domain.AbstractEntity;
import com.earl.carnet.commons.domain.DateQuery;
import com.earl.carnet.commons.util.Assert;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


public class BaseDaoImpl<T extends AbstractEntity<?>> implements BaseDao<T>{

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
	public int insert(T record) {
		return sqlManager.insert(record);
	}
	
	@Override
	public int insertBackId(T record){
		KeyHolder keyHolder = new KeyHolder();
		sqlManager.insert(entityClazz, record, keyHolder);
		return keyHolder.getInt();
	}

	@Override
	public void delete(Object id) {
		sqlManager.deleteById(entityClazz, id);//咋们暂时不支持联合主键，beetlsql支持联合主键
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return sqlManager.updateTemplateById(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return sqlManager.all(entityClazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(int start, int size) {
		// TODO 未测试.
		return sqlManager.all(entityClazz, start, size);
	}

	@Override
	public long countAll() {
		return sqlManager.allCount(entityClazz);
	}

	@Override
	public int insertSelective(T record) {
		return sqlManager.insert(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO 未测试.
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOneById(Object Id) {
		return (T)sqlManager.unique(entityClazz, Id);
	}
	
	@Override
	public List<T> searchAccurate(T queryObject){
		return sqlManager.template(queryObject);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchSimilarity(T queryObject){
		Map<String, Object> notNullProperties = getNotNullProperties(queryObject);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("notNullProperties",notNullProperties);
		param.put("tableName",sqlManager.getNc().getTableName(queryObject.getClass()));
		
		return sqlManager.select("base.searchSimilarity", entityClazz,param,null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
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
		System.out.println(sql);
		
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
	

	@Override
	public List<T> pageSearchAccurate(T queryObject, int start, int size) {
		return sqlManager.template(queryObject, start, size);
	}

	@Override
	public long countMatch(T queryObject) {
		return sqlManager.templateCount(queryObject);
	}
	
	
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
		System.out.println(script.getSql());
		
		sqlManager.executeUpdate(templatesql,notNullProperties);
	}

}
