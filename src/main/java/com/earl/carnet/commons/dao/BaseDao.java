package com.earl.carnet.commons.dao;

import com.earl.carnet.domain.carnet.Message.Message;
import com.earl.carnet.domain.carnet.car.Car;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends Serializable > {
	
	/**
	 * 插入一条记录
	 * @author 黄祥谦.
	 * @param record
	 * @return
	 */
	int insert(T record);
	
	/**
	 * 通过id进行删除，id可以是String类型，或者Long类型，或者Integer类型
	 * @author 黄祥谦.
	 * @param id
	 */
	void delete(Object id);

	/**
	 * 动态增加
	 * @author 黄祥谦.
	 * @param record
	 * @return
	 */
	int insertSelective(T record);
	
	/**
	 * 动态更新
	 * @author 黄祥谦.
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);
	
//	/**
//	 * 通过主键更新
//	 * @author 黄祥谦.
//	 * @param record
//	 * @return
//	 */
//	int updateByPrimaryKey(T record);
	
	/**
	 * 查询所有
	 * @author 黄祥谦.
	 * @return
	 */
	public List<T> findAll();

	/**
	 * findAll with page
	 * @author 黄祥谦.
	 * @param start
	 * @param size
	 * @return
	 */
	List<T> findAll(int start, int size);

	/**
	 * 查询指定对象表的所有记录，
	 * @author 黄祥谦.
	 * @return
	 */
	//TODO 需要被扩展，添加查询条件
	long countAll();

	/**
	 * 满足查询条件的记录个数
	 * @author 黄祥谦.
	 * @param queryObject
	 * @return
	 */
	long countMatch(T queryObject);
	
	T findOneById(Object Id);

	/**
	 * 精确查询.
	 * @author 黄祥谦.
	 * @param queryObject
	 * @return
	 */
	List<T> searchAccurate(T queryObject);
	
	/**
	 * 精确查询分页.
	 * @author 黄祥谦.
	 * @param queryObject
	 * @return
	 */
	List<T> pageSearchAccurate(T queryObject, int start, int size);

	/**
	 * 模糊查询
	 * @author 黄祥谦.
	 * @param queryObject
	 * @return
	 */
	List<T> searchSimilarity(T queryObject);

	/**
	 * 模糊查询，分页
	 * @author 黄祥谦.
	 * @param queryObject
	 * @param start
	 * @param size
	 * @return
	 */
	List<T> searchSimilarity(T queryObject, int start, int size);

	/**
	 * 暂时仅仅支持int类型的id
	 * @author 黄祥谦.
	 * @param record
	 * @return
	 */
	int insertBackId(T record);

	/**
	 * 根据查询对象进行搜索
	 * @author 黄祥谦.
	 * @param query
	 * @return
	 */
	List<T> searchQuery(Object query);

	/**
	 * 通过对象进行删除记录
	 * @author 黄祥谦.
	 * @param queryObject
	 */
	void deleteByQuery(Object queryObject);


	/**
	 * 更新与数据库不相同的属性.
	 * @param model 需要更新的对象.
	 * @param model_data 相应的数据库对象
     * @return
     */
	int updateByNotSameParam(Object model, Object model_data);

}
