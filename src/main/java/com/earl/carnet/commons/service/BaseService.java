package com.earl.carnet.commons.service;

import java.io.Serializable;
import java.util.List;

import com.earl.carnet.commons.domain.AbstractEntity;

/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
*/
public interface BaseService<T extends AbstractEntity<?>, E> {
	
	int updateByPrimaryKeySelective(T entity);

	List<T> update(List<T> entities);

	int save(T entity);

	int  insertBackId(T entity);

	List<T> save(List<T> entities);

	T findOne(Object id);

	boolean exists(Serializable Id);

	long count();

	void delete(Serializable id);

	void deleteByQuery(T entity);

	void delete(List<T> entities);

	void deleteAll();

	List<T> findAll();
	
//	List<T> search(E criteria);
	
	List<T> searchQuery(E userQuery);
/*
	List<T> findAll(Sort sort) throws Exception;

	Page<T> findAll(Pageable pageable) throws Exception;
	
	List<T> search(E criteria, Sort sort);

	Page<T> search(E criteria, Pageable pageable);
*/

	/**
	 * 精确搜索
	 * @author 黄祥谦.
	 * @param queryObject
	 * @return
	 */
	List<T> searchAccurate(T queryObject);
	
	/**
	 * 分页精确搜索
	 * @author 黄祥谦.
	 * @param user
	 * @param i
	 * @param j
	 * @return
	 */
	List<T> searchAccurate(T user, int i, int j);
	
	/**
	 * 模糊查询
	 * @author 黄祥谦.
	 * @param queryObject
	 * @return
	 */
	List<T> searchSimilarity(T queryObject);
	
	List<T> searchSimilarity(T queryObject, int start, int size);

}
