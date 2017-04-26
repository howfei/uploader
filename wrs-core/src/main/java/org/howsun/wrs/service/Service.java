package org.howsun.wrs.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.howsun.wrs.dao.ExtendExecutant;
import org.howsun.wrs.dao.OrderBean;
import org.howsun.wrs.dao.Page;


/**
 * 
 * 说明:通用接口
 * 
 * @param <Entity>
 * 
 * @author howfei[13720056627@163.com]
 * 
 */
public interface Service<Entity extends Serializable> {

	
	/**
	 * 保存实体
	 * @param entity
	 * void
	 */
	public abstract void save(Entity entity);
	
	
	/**
	 * 删除
	 * @param id
	 * void
	 */
	public abstract void delete(int id);
	
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 * int
	 */
	public abstract void update(Entity entity);
	
	
	/**
	 * 根据ID更新单个实体
	 * @param id
	 * @param values
	 * void
	 */
	public abstract int update(int id, Map<String, Object> values);

	
	/**
	 * 根据条件更新多个实体
	 * @param conditions
	 * @param values
	 * @return
	 * int
	 */
	public abstract int updates(String condition, Object[] params, Map<String, Object> values);
	
	
	
	/**
	 * 根据ID查询单个实体
	 * @param id
	 * @return
	 * Entity
	 */
	public abstract Entity get(int id);

	
	/**
	 * 根据ID查询单个实体
	 * @param id
	 * @param fields  多个字段用逗号隔开
	 * @return
	 * Entity
	 */
	public abstract Entity get(int id, String fields);
	

	/**
	 * 根据条件查询单个实体
	 * @param conditions 
	 * @return 
	 * Entity
	 */
	public abstract Entity get(String condition, Object[] params);
	

	/**
	 * 根据条件查询单个实体
	 * @param conditions
	 * @param fields 多个字段用逗号隔开
	 * @return
	 * Entity
	 */
	public abstract Entity get(String condition, Object[] params, String fields);
	

	/**
	 * 根据条件查询多个实体
	 * @param conditions
	 * @param fields  多个字段请用逗号隔开
	 * @param page
	 * @return
	 * List<Entity>
	 */
	public abstract List<Entity> gets(Page page, OrderBean order);
	
	
	public abstract List<Entity> gets(String condition, Object[] params, String fields, Page page, OrderBean order);
	
	
	/**
	 * 统计
	 * @return
	 */
	public long count();
	
	/**
	 * 
	 * @param conditions
	 * @return
	 */
	public long count(String condition, Object[] params);


	/**
	 * 执行器
	 * @param daoExcute
	 */
	public void excuting(ExtendExecutant<Session> daoExcute);
	
}