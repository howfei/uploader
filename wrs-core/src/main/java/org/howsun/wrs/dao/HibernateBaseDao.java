package org.howsun.wrs.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.howsun.wrs.util.Asserts;
import org.howsun.wrs.util.Collections;
import org.howsun.wrs.util.Numbers;
import org.howsun.wrs.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 *
* 功能描述：
*
*  20140803：Hibernate4所有动作都需要在事务之中，因此需要在service类名上标注@Transactional("transactionManager")，任何方法上都不再配置该注解
*
*  PROPAGATION_REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。<br>
 * PROPAGATION_SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行。<br>
 * PROPAGATION_MANDATORY：支持当前事务，如果当前没有事务，就抛出异常。<br>
 * PROPAGATION_REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起。<br>
 * PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。<br>
 * PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。<br>
 * PROPAGATION_NESTED：支持当前事务，如果当前事务存在，则执行一个嵌套事务，如果当前没有事务，就新建一个事务。
 * 嵌套事务实现了隔离机制，例如B事务嵌套在A事务中，B失败不会影响A提交。而PROPAGATION_REQUIRED则全部回滚<br>
*
*
* Hibernate 4 例子
* http://www.baeldung.com/hibernate-4-spring
* http://www.baeldung.com/2011/12/13/the-persistence-layer-with-spring-3-1-and-jpa/
* http://java.dzone.com/articles/springmvc4-spring-data-jpa
* 
* Hibernate通用接口
* https://code.google.com/p/hibernate-generic-dao/
* 
* Hibernate 通过 Criteria 查询返回 指定字段
* Criteria cr = session.createCriteria(User.class)
* 	add(Restrictions.eq("id",id))
    .setProjection(Projections.projectionList()
      .add(Projections.property("id"), "id")
      .add(Projections.property("Name"), "Name"))
    .setResultTransformer(Transformers.aliasToBean(User.class));

  List<User> list = cr.list();
* 
* 
* @author howsun(howsun.zhang@google.com)
* @version 2.0
 */
@Repository("baseDao")
public class HibernateBaseDao implements BaseDao {

	protected Logger log = LoggerFactory.getLogger(HibernateBaseDao.class);

	protected static final Map<Class<?>, Field> ID_FIELD = new HashMap<Class<?>, Field>();

	@Resource
	protected SessionFactory sessionFactory;

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	
	//---------------------------------------written---------------------------------------------------


	@Override
	public void save(Object object) {
		sessionFactory.getCurrentSession().save(object);
	}
	
	@Override
	public void merge(Object object) {
		sessionFactory.getCurrentSession().merge(object);
	}

	@Override
	public void update(Object object) {
		sessionFactory.getCurrentSession().update(object);
	}

	
	@Override
	public <T> int update(Class<T> entityClass, Map<String, Object> values, Serializable id){
		Asserts.isTrue(values.size() > 0, "没有需要更新的值，请检查参数");
		String idField = sessionFactory.getClassMetadata(entityClass).getIdentifierPropertyName();
		return updates(entityClass, values, idField + "=?", new Object[]{id});
	}

	
	
	/**
	 * 
	 * @param entityClass
	 * @param updateValues
	 * @param conditions  key必须指定操作符，如:field=?、field>=?等等
	 * @return
	 */
	public <T> int updates(Class<T> entityClass, Map<String, Object> updateValues, String condition, Object[] params){
		
		Asserts.isTrue(updateValues.size() > 0, "无法按要求数据更新实体");
		
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer xQL = new StringBuffer("UPDATE ").append(entityClass.getName()).append(" SET");
		for(Map.Entry<String, ?> value : updateValues.entrySet()){
			String field = value.getKey();
			xQL.append(" ").append(field).append(field.indexOf('=') > -1 ? "," : "=?,");
			Object pValue = value.getValue();
			if(pValue != null){
				values.add(pValue);
			}
		}
		
		xQL.deleteCharAt(xQL.length() - 1);
		
		if(Strings.hasLength(condition)){
			xQL.append(" WHERE ").append(condition);
			for(Object param : params){
				values.add(param);
			}
		}
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery(xQL.toString());
		setParameter(query, values.toArray());
		
		return query.executeUpdate();
	}


	@Override
	public int delete(Object object) {
		sessionFactory.getCurrentSession().delete(object);
		return 1;
	}
	
	@Override
	public <T> int delete(Class<T> entityClass, Serializable... entityids) {
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		for(Serializable id : entityids){
			try {
				session.delete(session.load(entityClass, id));
				i++;
			} catch (Exception e) {
				log.info(e.getMessage(), e);
				continue;
			}
		}
		return i;
	}


	@Override
	public <T> int delete(Class<T> entityClass, String condition, Object[] params){
		if(Strings.hasLength(condition)){
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE " + entityClass.getName() + " WHERE " + condition);
			this.setParameter(query, params);
			return query.executeUpdate();
		}
		return 0;
	}
	
	
	
	//-------------------------------------read-----------------------------------------------

	/*
	 * @see org.howsun.wrs.dao.BaseDao#findOne(java.lang.Class, java.io.Serializable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T findOne(Class<T> entityClass, Serializable entityid) {
		return (T)sessionFactory.getCurrentSession().get(entityClass, entityid);
	}

	
	/*
	 * @see org.howsun.wrs.dao.BaseDao#findOne(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	public <T> T findOne(Class<T> entityClass, String condition, Object[] params){
		return this.findOne(entityClass, null, condition, params);
	}
	
	
	/*
	 * @see org.howsun.wrs.dao.BaseDao#findOne(java.lang.Class, java.util.Set, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public <T> T findOne(Class<T> entityClass, String fields, String condition, Object[] params){
		
		String fs = null;
		if(Strings.hasLength(fields)){
			fs = '(' + fields + ')';
		}
		
		String fieldObject = Strings.hasLengthByTrim(fs) ? ("new " + entityClass.getName() + fs) : "o";
		StringBuffer sql = new StringBuffer("SELECT " + fieldObject + " FROM " + entityClass.getName() +" o");
		
		Session session = sessionFactory.getCurrentSession();
		
		boolean hashWhere = Strings.hasLength(condition);
		if(hashWhere){
			sql.append(" WHERE ").append(condition);
		}
		
		Query query = session.createQuery(sql.toString());
		this.setParameter(query, params);
		
		T t = null;
		
		try {
			t = (T)query.uniqueResult();
		} 
		catch (NonUniqueResultException e){
			List<T> ls = query.list();
			if(Collections.notEmpty(ls)){
				return ls.get(0);
			}
		}
		catch (Exception e) {
			throw e;
		}
		
		return t;
	}
	
	

	@Override
	@SuppressWarnings("unchecked")
	public <T> T findOneBySQL(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		this.setParameter(query, params);
		return (T)query.uniqueResult();
	}

	
	
	
	//----------------------------------------------------------------------------------------------
	
	
	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#finds(java.lang.Class, java.lang.String, org.howsun.dao.page.Page, java.lang.String, java.lang.Object[], org.howsun.dao.OrderBean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> finds(Class<T> entityClass, String fields, Page page, String condition, Object[] params, OrderBean order) {
		String entityName = entityClass.getName();
		String fieldObject = Strings.hasLengthByTrim(fields) ? ("new " + entityName + "(" + fields + ")") : "o";
		StringBuffer sql = new StringBuffer("SELECT " + fieldObject + " FROM " + entityName +" o");
		StringBuffer countSql = new StringBuffer("SELECT count(" + getCountField(entityClass) + ") FROM " + entityName +" o");

		//设置查询条件
		if(condition != null && condition.trim().length() > 0){
			sql.append(" WHERE ").append(condition);
			countSql.append(" WHERE ").append(condition);
		}
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = null;
		if(page != null && page.getTotalCount() == 0){
			query = session.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}

		//设置排序
		if(order != null){
			sql.append(order.toSQL("o"));
		}

		query = session.createQuery(sql.toString());
		this.setParameter(query, params);

		//分页
		if(page != null){
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());
		}

		return query.list();
	}
	
	


	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order) {
		return getScrollData(entityClass, page, condition, params, order);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params) {
		return getScrollData(entityClass, page, condition, params, null);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, OrderBean order) {
		return getScrollData(entityClass, page, null, null, order);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page) {
		return getScrollData(entityClass, page, null, null, null);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, OrderBean order) {
		return getScrollData(entityClass, null, null, null, order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> finds(Class<T> entityClass) {
		return sessionFactory.getCurrentSession().createCriteria(entityClass).list();
	}

	@Override
	public List<?> findsBySQL(String sql, Object[] params, Page page) {
		Session session = sessionFactory.getCurrentSession();
		String sqlCopy = sql.toLowerCase();
		Asserts.isTrue(sqlCopy.contains("select") && sqlCopy.contains("from"), "不合格的SQL语句");

		if(page != null && page.getTotalCount() == 0){
			int start = sqlCopy.indexOf("select") + 6;
			int end = sqlCopy.indexOf("from");
			StringBuffer s = new StringBuffer();
			s.append(sql.substring(0, start)).append(" count(*) ").append(sql.substring(end, sql.length()));
			SQLQuery query = session.createSQLQuery(s.toString());
			setParameter(query, params);
			this.setCount(query, page);
		}

		SQLQuery query = session.createSQLQuery(sql);
		setParameter(query, params);

		return query.list();
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#findsBySQL(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> findsBySQL(String sql, Object[] params) {
		return this.findsBySQL(sql, params, null);
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#findsByXQL(java.lang.String, java.lang.Object[], org.howsun.dao.page.Page)
	 */
	@Override
	public List<?> findsByXQL(String xql, Object[] params, Page page) {

		Query query = sessionFactory.getCurrentSession().createQuery(xql);

		// 分页
		if (page !=null)
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());

		this.setParameter(query, params);

		List<?> s = query.list();

		if(page != null && page.getTotalCount() == 0){
			String countSql = "select count(*) " + xql.substring(xql.indexOf("from"));
			query = sessionFactory.getCurrentSession().createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		return s;
	}



	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#flush()
	 */
	@Override
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#getCount(java.lang.Class)
	 */
	@Override
	public <T> long getCount(Class<T> entityClass) {
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityClass.getName() +" o");
		Query query = sessionFactory.getCurrentSession().createQuery(countSql.toString());
		return getCountOfLong(query);
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#getCount(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <T> long getCount(Class<T> entityClass, String condition, Object[] params) {
		String entityName = entityClass.getName();
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityName +" o");

		//设置查询条件
		if(condition != null && condition.trim().length() > 0){
			countSql.append(" where ").append(condition);
		}

		Query query = sessionFactory.getCurrentSession().createQuery(countSql.toString());
		this.setParameter(query, params);

		return getCountOfLong(query);
	}



	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#nextId(java.lang.Class)
	 */
	@Override
	public Long nextId(Class<?> entityClass) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT MAX(id) FROM " + entityClass.getName());
		return getCountOfLong(query) + 1;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#increaseFieldValue(java.lang.Class, java.lang.String, java.lang.Integer, java.io.Serializable)
	 */
	@Override
	public <T> void increaseFieldValue(Class<T> entityName, String field, Integer defaultValue, Serializable id) {
		if(id == null){
			return;
		}
		if(!Numbers.thanZero(defaultValue)){
			defaultValue = 1;
		}
		String idFieldName = sessionFactory.getClassMetadata(entityName).getIdentifierPropertyName();
		Asserts.notNull(idFieldName, "未找到主键字段");
		Query query = sessionFactory.getCurrentSession().createQuery(String.format("UPDATE %s SET %s=%s+" + defaultValue + " WHERE %s=?",
						entityName.getName(),
						field,
						field,
						idFieldName));
		query.setParameter(0, id);
		query.executeUpdate();
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#execute(org.howsun.dao.ExtendExecutant)
	 */
	@Override
	public void execute(ExtendExecutant<Session> extendExecutant){
		extendExecutant.executing(sessionFactory.getCurrentSession());
	}

	
	
	
	
	
	////////////////////////////////////////////////////////private method////////////////////////////////////////////////

	/**
	 * 为查询对象设定参数，注意：如果是JPA，则索引位要加一
	 * @param query
	 * @param params
	 */
	protected void setParameter(Query query, Object[] params){
		if(params != null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	/**
	 * 得到统计字段
	 * @param <T>
	 * @param entityClass
	 * @return String

	private <T> String getCountField(Class<T> entityClass){
		try {
			PropertyDescriptor[] ps = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : ps){
				Method getter = propertydesc.getReadMethod();
				if(getter != null && getter.isAnnotationPresent(EmbeddedId.class)){
					PropertyDescriptor[] idClassps  = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					return "o." + propertydesc.getName() + "." + (idClassps[0].getName().equals("class") ? idClassps[1].getName() : idClassps[0].getName());
				}
				//需要修改，查找到id字段
				return "o." + sessionFactory.getClassMetadata(entityClass).getIdentifierPropertyName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "o";
	}
	*/

	private <T> String getCountField(Class<T> entityClass){
		sessionFactory.getClassMetadata(entityClass).getIdentifierPropertyName();
		
		Field idField = getIdField(entityClass);
		if(idField.isAnnotationPresent(EmbeddedId.class)){
			return "*";
		}
		return "o." + idField.getName();
		//return "o";
	}

	private <T> Field getIdField(Class<T> entityClass){
		Field idField = ID_FIELD.get(entityClass);
		if(idField != null){
			return idField;
		}

		Class<?> superClass = entityClass.getSuperclass();
		if(superClass instanceof Serializable){
			for(Field f : superClass.getDeclaredFields()){
				if(f.getAnnotation(Id.class) != null || f.getAnnotation(EmbeddedId.class) != null){
					idField = f;
				}
			}
		}
		for(Field f : entityClass.getDeclaredFields()){
			if(f.getAnnotation(Id.class) != null || f.getAnnotation(EmbeddedId.class) != null){
				idField = f;
			}
		}
		
		for(Field f : entityClass.getDeclaredFields()){
			if(f.getAnnotation(Id.class) != null || f.getAnnotation(EmbeddedId.class) != null){
				idField = f;
			}
		}

		ID_FIELD.put(entityClass, idField);
		Asserts.notNull(idField, String.format("%s实体中没有ID字段", entityClass));

		return idField;
	}

	//主方法
	@SuppressWarnings("unchecked")
	private <T> List<T> getScrollData(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order) {
		StringBuffer sql = new StringBuffer("select o from " + entityClass.getName() +" o");

		//设置查询条件
		if(Strings.hasLengthByTrim(condition)){
			sql.append(" where ").append(condition);
		}

		Session session = sessionFactory.getCurrentSession();
		Query query = null;

		if(page != null && page.getTotalCount() == 0){
			StringBuffer countSql = new StringBuffer("select count("+getCountField(entityClass)+") from " + entityClass.getName() +" o");
			//设置查询条件
			if(Strings.hasLengthByTrim(condition)){
				countSql.append(" where ").append(condition);
			}

			if(log.isDebugEnabled()){
				log.debug(countSql.toString());
			}

			query = session.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		//设置排序
		if(order != null){
			sql.append(order.toSQL("o"));//排序
		}
		query = session.createQuery(sql.toString());
		this.setParameter(query, params);

		//分页
		if(page != null){
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());
		}

		log.debug(sql.toString());

		return query.list();
	}

	private void setCount(Query query, Page page){
		int count = getCount(query);
		page.setTotalCount(count);
	}

	private int getCount(Query query){
		return (int)getCountOfLong(query);
	}

	private long getCountOfLong(Query query){
		Object object  = query.uniqueResult();
		if(object != null){
			if(object instanceof BigInteger){
				BigInteger bi = (BigInteger)object;
				return bi.longValue();
			}
			if(object instanceof Long){
				long i = (Long)object;
				return i;
			}
			if(object instanceof Integer){
				Integer i = (Integer)object;
				return i;
			}
		}
		return 0;
	}
	@Override
	public void finalize() throws Throwable {
		if(this.sessionFactory.getCurrentSession() != null){
			sessionFactory.getCurrentSession().close();
		}
		super.finalize();
	}



	////////////////////////////////////////////////////////////////////

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		try {
			return sessionFactory.getCurrentSession();
		}
		catch (Exception e) {
			log.info(e.getMessage());
		}
		return sessionFactory.openSession();
	}

	public static void main(String[] args) {
		String sql = "SELECT * FROM cms_article Where articlegroup=1 order id desc";
		String sqlCopy = sql.toLowerCase();
		Asserts.isTrue(sqlCopy.contains("select") && sqlCopy.contains("from"), "不合格的SQL语句");

		int start = sqlCopy.indexOf("select") + 6;
		int end = sqlCopy.indexOf("from");
		StringBuffer s = new StringBuffer();
		s.append(sql.substring(0, start)).append(" count(*) ").append(sql.substring(end, sql.length()));

		System.out.println(s.toString());
	}
}
