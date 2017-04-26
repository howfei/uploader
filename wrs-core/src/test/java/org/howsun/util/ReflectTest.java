/**
 * 
 */
package org.howsun.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月16日 上午10:26:39
 */
public class ReflectTest {

	protected Class<?> entityClass;
	{
		try {
			Type type = this.getClass().getGenericSuperclass();
			while(!(type instanceof ParameterizedType)){
				type = ((Class<?>)type).getGenericSuperclass();
				/**/
				if(type == null || "java.lang.Object".equals(type.getClass())){//为了避免写错代码出现死循环，加上这个保险。
					break;
				}
				
			}
			if(type != null){
				entityClass = (Class<?>)((ParameterizedType) type).getActualTypeArguments()[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		
	}

}
