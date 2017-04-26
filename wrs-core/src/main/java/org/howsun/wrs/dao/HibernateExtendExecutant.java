/**
 *
 */
package org.howsun.wrs.dao;

import org.hibernate.Session;

/**
 *  功能描述：Hibernate扩展执行器
 * @author howfei[13720056627@163.com]
 *
 */
public abstract class HibernateExtendExecutant implements ExtendExecutant<Session> {

	/* (non-Javadoc)
	 * @see org.howsun.dao.ExtendExecutant#executing(java.lang.Object)
	 */
	@Override
	public void executing(Session session){
		hibernateCallback(session);
	}

	protected abstract void hibernateCallback(Session session);

}
