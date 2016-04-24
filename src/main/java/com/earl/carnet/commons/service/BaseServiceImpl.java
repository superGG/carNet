package com.earl.carnet.commons.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.domain.AbstractEntity;

@Transactional
public abstract class BaseServiceImpl<T extends AbstractEntity<?>, E>
		implements BaseService<T, E> {

	protected abstract BaseDao<T> getDao();

	protected Class<T> entityClazz;

	protected Class<E> criteriaClazz;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClazz = (Class<T>) params[0];
		criteriaClazz = (Class<E>) params[1];
	}

	
	@Override
	public T save(T entity) {
		// TODO 未测试.
		getDao().insert(entity);
		return null;
	}
	
	
	
	@Override
	public List<T> save(List<T> entities) {
		Assert.notEmpty(entities);
		List<T> list = new ArrayList<T>();
		for (T t : entities) {
			list.add(save(t));
		}
		return list;
	}

	@Override
	public List<T> update(List<T> entities) {
		// TODO 未测试.
		return null;
	}
	
//	public T create(T entity) {
//		Assert.notNull(entity);
//		T id = entity.getId() == null ? IdGen.uuid() : entity.getId()
//				.trim();
//		entity.setId(id);
//		getDao().insertSelective(entity);
//		return entity;
//	}

//	@Override
//	public List<T> create(List<T> entities) {
//		Assert.notEmpty(entities);
//		List<T> list = new ArrayList<T>();
//		for (T t : entities) {
//			list.add(create(t));
//		}
//		return list;
//	}
	
	@Override
	public int updateByPrimaryKeySelective(T entity) {
		return getDao().updateByPrimaryKeySelective(entity);
	}

//	@Override
//	public List<T> search(E criteria) {
//		// TODO 未测试.
//		return null;
//	}

	@Override
	public List<T> searchAccurate(T queryObject){
		return getDao().searchAccurate(queryObject);
	}
	
	/*
	@Override
	@Transactional(readOnly = true)
	public List<T> findAll(Sort sort) throws Exception {
		Assert.notNull(sort);
		String orderByClause = "";
		for (Order o : sort) {
			orderByClause += " " + o.getProperty() + " "
					+ o.getDirection().toString() + " ";
		}
		E criteria = criteriaClazz.newInstance();
		criteria.setOrderByClause(orderByClause);
		return getDao().selectByExample(criteria);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) throws Exception {
		Assert.notNull(pageable);
		E criteria = null;
		if (pageable.getSort() != null) {
			String orderByClause = "";
			for (Order o : pageable.getSort()) {
				orderByClause += " " + o.getProperty() + " "
						+ o.getDirection().toString() + " ";
			}
			criteria = criteriaClazz.newInstance();
			criteria.setOrderByClause(orderByClause);
		}
		RowBounds rowBounds = new RowBounds(pageable.getPageNumber(),
				pageable.getPageSize());
		List<T> list = getDao().selectByExampleWithRowbounds(criteria,
				rowBounds);
		long count = count();
		return new PageImpl<T>(list, pageable, count);
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<T> search(E criteria, Sort sort) {
		Assert.notNull(criteria);
		Assert.notNull(sort);
		if (sort != null) {
			String orderByClause = "";
			for (Order o : sort) {
				orderByClause += " " + o.getProperty() + " "
						+ o.getDirection().toString() + " ";
			}
			criteria.setOrderByClause(orderByClause);
		}
		return getDao().selectByExample(criteria);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<T> search(E criteria, Pageable pageable) {
		Assert.notNull(criteria);
		Assert.notNull(pageable);
		if (pageable.getSort() != null) {
			String orderByClause = "";
			for (Order o : pageable.getSort()) {
				orderByClause += " " + o.getProperty() + " "
						+ o.getDirection().toString() + " ";
			}
			criteria.setOrderByClause(orderByClause);
		}
		RowBounds rowBounds = new RowBounds(pageable.getPageNumber(),
				pageable.getPageSize());
		List<T> list = getDao().selectByExampleWithRowbounds(criteria,
				rowBounds);
		long count = count();
		return new PageImpl<T>(list, pageable, count);
	}*/
	@Override
	public List<T> searchAccurate(T object, int start, int size) {
		// TODO 未测试.
		List<T> objectList =  getDao().pageSearchAccurate(object,start,size);
		return objectList;
	}

	@Override
	public List<T> findAll() {
		// TODO 未测试.
		return getDao().findAll();
	}
	
	public List<T> findAll(int start,int size){
		return getDao().findAll(start,size);
	}
	
	@Override
	public void deleteByQuery(T entity) {
		// TODO 未测试.
		getDao().deleteByQuery(entity);
	}
	
	@Override
	public void delete(Serializable id) {
		// TODO 未测试.
		getDao().delete(id);
	}

	@Override
	public long count() {
		// TODO 未测试.
		return getDao().countAll();
		 
	}
	
	@Override
	public T findOne(Object id) {
		// TODO 未测试.
		
		return getDao().findOneById(id);
	}
	
	@Override
	public boolean exists(Serializable id) {
		Assert.notNull(id);
		return findOne(id) != null;
	}

	@Override
	public void delete(List<T> entities) {
		Assert.notEmpty(entities);
		for (T t : entities) {
			deleteByQuery(t);
		}
	}

	@Override
	public void deleteAll() {
		delete(findAll());
	}
	
	@Override
	public List<T> searchSimilarity(T queryObject) {
		// TODO 未测试.
		return getDao().searchSimilarity(queryObject);
	}
	
	@Override
	public List<T> searchSimilarity(T queryObject, int start, int size) {
		// TODO 未测试.
		return getDao().searchSimilarity(queryObject, start, size);
	}
	
	@Override
	public List<T> searchQuery(E userQuery) {
		// TODO 未测试.
		return getDao().searchQuery(userQuery);
	}
}
