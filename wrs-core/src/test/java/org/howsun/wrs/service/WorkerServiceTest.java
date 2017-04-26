/**
 * 
 */
package org.howsun.wrs.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.howsun.wrs.BaseTestCaseJunit;
import org.howsun.wrs.domain.Worker;
import org.junit.Test;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午9:01:44
 */
public class WorkerServiceTest extends BaseTestCaseJunit{

	@Resource
	WorkerService workerService;
	
	@Test
	public void testIsAccountExist(){
		boolean exist = workerService.isAccountExist("zhangjihao@sohu.com", true);
		System.out.println(exist);
	}
	
	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#save(java.io.Serializable)}.
	 */
	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#delete(int)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#update(int, java.util.Map)}.
	 */
	@Test
	public void testUpdateIntMapOfStringObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#update(java.io.Serializable)}.
	 */
	@Test
	public void testUpdateEntity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#updates(java.lang.String, java.lang.Object[], java.util.Map)}.
	 */
	@Test
	public void testUpdates() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#get(int)}.
	 */
	@Test
	public void testGetInt() {
		Worker worker = workerService.get(1);
		System.out.println(worker);
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#get(int, java.lang.String)}.
	 */
	@Test
	public void testGetIntString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#get(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testGetStringObjectArray() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#excuting(org.howsun.wrs.dao.ExtendExecutant)}.
	 */
	@Test
	public void testExcuting() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#get(java.lang.String, java.lang.Object[], java.lang.String)}.
	 */
	@Test
	public void testGetStringObjectArrayString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#gets(org.howsun.wrs.dao.Page, org.howsun.wrs.dao.OrderBean)}.
	 */
	@Test
	public void testGetsPageOrderBean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#gets(java.lang.String, java.lang.Object[], java.lang.String, org.howsun.wrs.dao.Page, org.howsun.wrs.dao.OrderBean)}.
	 */
	@Test
	public void testGetsStringObjectArrayStringPageOrderBean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#count()}.
	 */
	@Test
	public void testCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#count(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testCountStringObjectArray() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#getDao()}.
	 */
	@Test
	public void testGetDao() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.howsun.wrs.service.impl.ServiceImpl#setDao(org.howsun.wrs.dao.BaseDao)}.
	 */
	@Test
	public void testSetDao() {
		fail("Not yet implemented");
	}

}
