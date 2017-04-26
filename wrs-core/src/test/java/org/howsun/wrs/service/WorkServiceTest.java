/**
 * 
 */
package org.howsun.wrs.service;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.howsun.wrs.BaseTestCaseJunit;
import org.junit.Test;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午9:42:07
 */
public class WorkServiceTest extends BaseTestCaseJunit{

	@Test
	public void testGet(){
		
	}
	
	public static void main(String[] args) {
		Class<?> entity = org.howsun.wrs.domain.Work.class;
		try {
			System.out.println(entity.getSuperclass() instanceof Serializable);
			System.out.println(entity.getSuperclass().getDeclaredField("id"));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		for(Field field : entity.getDeclaredFields()){
			System.out.println(field);
		}
	}
}
