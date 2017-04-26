/**Collections.java*/
package org.howsun.wrs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 集合工具
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-28
 * @version v0.1
 */
public abstract class Collections {

	/**
	 * 判断集合是否空，
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
	
	/**
	 * @param collection
	 * @return
	 * boolean
	 */
	public static boolean notEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * Return <code>true</code> if the supplied Map is <code>null</code>
	 * or empty. Otherwise, return <code>false</code>.
	 * @param map the Map to check
	 * @return whether the given Map is empty
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * @param map
	 * @return
	 * boolean
	 */
	public static boolean notEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}


	/**
	 * Merge the given Properties instance into the given Map,
	 * copying all properties (key-value pairs) over.
	 * <p>Uses <code>Properties.propertyNames()</code> to even catch
	 * default properties linked into the original Properties instance.
	 * @param props the Properties instance to merge (may be <code>null</code>)
	 * @param map the target Map to merge the properties into
	 */
	@SuppressWarnings("unchecked")
	public static void mergePropertiesIntoMap(Properties props, @SuppressWarnings("rawtypes") Map map) {
		if (map == null) {
			throw new IllegalArgumentException("Map must not be null");
		}
		if (props != null) {
			for (@SuppressWarnings("rawtypes")
			Enumeration en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				Object value = props.getProperty(key);
				if (value == null) {
					// Potentially a non-String value...
					value = props.get(key);
				}
				map.put(key, value);
			}
		}
	}



	/**
	 * Check whether the given Collection contains the given element instance.
	 * <p>Enforces the given instance to be present, rather than returning
	 * <code>true</code> for an equal element as well.
	 * @param collection the Collection to check
	 * @param element the element to look for
	 * @return <code>true</code> if found, <code>false</code> else
	 */
	public static boolean containsInstance(Collection<?> collection, Object element) {
		if (collection != null) {
			for (Object candidate : collection) {
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		List<String> s = new ArrayList<String>();
		s.add("");
		System.out.println(notEmpty(s));
	}
}
