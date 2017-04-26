/**
 * 
 */
package org.howsun.util;

import java.util.Date;

import org.slf4j.LoggerFactory;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 上午10:55:52
 */
public class Log4jTest {

	static org.slf4j.Logger log = LoggerFactory.getLogger(Log4jTest.class);
	
	/**
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		log.info(new Date().toString());
		log.error("error......");
	}

}
