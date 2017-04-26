package org.howsun.wrs.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


/**
 * @Description：BaseDao，通用数据访问组件
 *
 * @author howfei[13720056627@163.com]
 * @Date 2007-9-18
 * @version v2.0
 */
public interface BaseDao {
	
	/**
	 * 保存
	 * @param object
	 */
	public abstract void save(Object object);

	
	/**
	 * 更新
	 * @param object
	 */
	public abstract void merge(Object object);
	
	
	/**
	 * 更新
	 * @param object
	 */
	public abstract void update(Object object);

	
	/**
	 * 按需字段更新<br>
	 *
	 * @param <T>
	 * @param entityClass
	 * @param values 字段名对应要更新的值，字段名中不需要=？
	 * @param id
	 */
	public abstract <T> int update(Class<T> entityClass, Map<String, Object> values, Serializable id);
	
	/**
	 * 批量更新<br>
	 * 
	 * @param <T>
	 * @param entityClass 实体名
	 * @param updateValues 字段名对应要更新的值，字段名中不需要=？
	 * @param conditions 
	 * @return
	 */
	public abstract <T> int updates(Class<T> entityClass, Map<String, Object> updateValues, String condition, Object[] params);


	/**
	 * 删除即时对象
	 * @param object
	 * @return
	 */
	public abstract int delete(Object object);
	
	
	/**
	 * 删除，支持批量删除
	 * @param <T>
	 * @param entityClass
	 * @param entityids
	 */
	public abstract <T> int delete(Class<T> entityClass, Serializable ... entityids);

	
	/**
	 * 根据条件删除
	 * @param entityClass
	 * @param condition
	 * @param params
	 * @return
	 */
	public <T> int delete(Class<T> entityClass, String condition, Object[] params);
	
	/**
	 * 单个对象查找
	 * @param <T>
	 * @param entityClass
	 * @param entityid
	 * @return t
	 */
	public abstract <T> T findOne(Class<T> entityClass, Serializable id);

	
	/**
	 * ORM查询语言定制查询
	 * @param <T>
	 * @param entityClass
	 * @param condition
	 * @param params
	 * @return
	 */
	public abstract <T> T findOne(Class<T> entityClass, String condition, Object[] params);
	
	
	/**
	 * 单一实体查询：定制字段、定制条件查询
	 * @param entityClass
	 * @param fields
	 * @param conditions
	 * @return
	 */
	public abstract <T> T findOne(Class<T> entityClass, String fields, String condition, Object[] params);

	
	/**
	 * 用原生SQL语言查询
	 * @param <T>
	 * @param entityClass
	 * @param whereql
	 * @param params
	 * @return
	 */
	public abstract <T> T findOneBySQL(String sql, Object[] params);


	
	/**
	 * 可定制字段查询，对象中需要与可定制字段对应的构造方法
	 * 注意：当有外连接查询时，条件中的字段一定要加别名，例如o.userid=?
	 * @param <T>
	 * @param entityClass
	 * @param fields
	 * @param page
	 * @param whereql
	 * @param params
	 * @param order
	 * @return
	 */
	public abstract <T> List<T> finds(Class<T> entityClass, String fields, Page page, String condition, Object[] params, OrderBean order);


	
	/**
	 * 多结果查询：支持分页、条件参数、排序
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @param whereql
	 * @param params
	 * @param order
	 * @return t
	 */
	public abstract <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order);

	
	/**
	 * 多结果查询二：支持分页、条件参数
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @param whereql
	 * @param params
	 * @return t
	 */
	public abstract <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params);


	/**
	 * 多结果查询三：支持分页、排序
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @param order
	 * @return t
	 */
	public abstract <T> List<T> finds(Class<T> entityClass, Page page, OrderBean order);

	
	/**
	 * 多结果查询四：支持分页
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @return t
	 */
	public abstract <T> List<T> finds(Class<T> entityClass, Page page);


	/**
	 * 多结果查询五：排序不分页
	 * @param <T>
	 * @param entityClass
	 * @return t
	 */
	public abstract <T> List<T> finds(Class<T> entityClass, OrderBean order);

	
	/**
	 * 多结果查询：查询所有数据
	 * @param <T>
	 * @param entityClass
	 * @return t
	 */
	public abstract <T> List<T> finds(Class<T> entityClass);


	
	/**
	 * 多结果查询七：
	 * 利用自定义的SQL语句查询，带分页和条件
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public abstract List<?> findsByXQL(String xql, Object[] params, Page page);

	
	
	/**
	 * 多结果查询七：
	 * 利用自定义的SQL语句查询，带分页和条件
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public abstract List<?> findsBySQL(String sql, Object[] params, Page page);

	
	/**
	 * 多结果查询八：
	 * 利用自定义的SQL语句查询，带条件
	 * @param <T>
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> findsBySQL(String sql, Object[] params);


	
	/**
	 * 统计
	 * @param <T>
	 * @param entityClass
	 * @return t
	 * @throws Exception
	 */
	public abstract <T> long getCount(Class<T> entityClass);

	
	/**
	 * 统计
	 * @param <T>
	 * @param entityClass
	 * @param condition
	 * @param params
	 * @return
	 * 
	 */
	public abstract <T> long getCount(Class<T> entityClass, String condition, Object[] params);
	

	
	/**
	 * 自增长主键
	 */
	public abstract Long nextId(Class<?> entityClass);

	
	/**
	 * 递增某字段值，例如递增点击数
	 * @param entityName
	 * @param fields
	 * @param fieldValues
	 * @param id
	 */
	public abstract <T> void increaseFieldValue(Class<T> entityName, String field, Integer defaultValue, Serializable id);

	

	/**
	 * 清除会话，对象变成游离态
	 */
	public abstract void clear();
	

	/**
	 * 提交
	 */
	public abstract void flush();
	
	
	/**
	 * 扩展执行器
	 * @param extendExecutant
	 */
	public abstract void execute(ExtendExecutant<Session> extendExecutant);
	
	
}