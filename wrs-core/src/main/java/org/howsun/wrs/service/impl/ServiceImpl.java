/**
 * 
 */
package org.howsun.wrs.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.howsun.wrs.dao.BaseDao;
import org.howsun.wrs.dao.ExtendExecutant;
import org.howsun.wrs.dao.OrderBean;
import org.howsun.wrs.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 说明:通用接口
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2015年12月2日 9:35:12
 */
@Transactional
@SuppressWarnings("unchecked")
public abstract class ServiceImpl<Entity extends Serializable> implements Service<Entity> {

	protected static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);
	
	@Resource
	protected BaseDao dao;
	

	protected Class<Entity> entityClass;
	
	{
		try {
			Type type = this.getClass().getGenericSuperclass();
			
			//解决两层以上继承问题
			while(!(type instanceof ParameterizedType)){	
				type = ((Class<?>)type).getGenericSuperclass();
				
				//为了避免写错代码出现死循环，加上这个保险。
				if(type == null || "java.lang.Object".equals(type.getClass())){
					break;
				}
			}
			
			//取出真实的类型
			entityClass = (Class<Entity>)((ParameterizedType) type).getActualTypeArguments()[0];
			
		} catch (Exception e) {
			log.error(String.format("提取实体类型失败->[消息:%s]\t[类名:%s]", e.getMessage(), getClass()));
			e.printStackTrace();
		}
	}
	
	/* 
	 * @see com.kuaiche.core.service.base.Service#save(Entity)
	 */
	@Override
	public void save(Entity entity){
		dao.save(entity);
	}

	/* 
	 * @see com.kuaiche.core.service.base.Service#delete(long)
	 */
	@Override
	public void delete(int id){
		dao.delete(entityClass, id);
	}
	

	/* 
	 * @see com.kuaiche.core.service.base.Service#update(long, java.util.Map)
	 */
	@Override
	public int update(int id, Map<String, Object> values){
		return dao.update(entityClass, values, id);
	}
	
	
	@Override
	public void update(Entity entity) {
		dao.update(entity);
	}

	
	@Override
	public int updates(String condition, Object[] params, Map<String, Object> values) {
		return dao.updates(entityClass, values, condition, params);
	}

	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Entity get(int id) {
		return dao.findOne(entityClass, id);
	}

	/* 
	 * @see com.kuaiche.core.service.base.Service#get(long, java.util.Set)
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Entity get(int id, String fields){
		return dao.findOne(entityClass, fields);
	}
	

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Entity get(String condition, Object[] params) {
		return dao.findOne(entityClass, condition, params);
	}

	public void excuting(ExtendExecutant<Session> extendExecutant){
		dao.execute(extendExecutant);
	}


	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Entity get(String condition, Object[] params, String fields) {
		return dao.findOne(entityClass, fields, condition, params);
	}


	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Entity> gets(org.howsun.wrs.dao.Page page, OrderBean order) {
		return dao.finds(entityClass, page, order);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Entity> gets(String condition, Object[] params, String fields, org.howsun.wrs.dao.Page page, OrderBean order) {
		return dao.finds(entityClass, fields, page, condition, params, order);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public long count() {
		return dao.getCount(entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public long count(String condition, Object[] params) {
		return dao.getCount(entityClass, condition, params);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	protected BaseDao getDao() {
		return dao;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	protected void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
}
