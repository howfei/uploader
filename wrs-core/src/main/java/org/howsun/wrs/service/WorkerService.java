/**
 * 
 */
package org.howsun.wrs.service;

import org.howsun.wrs.domain.Worker;
import org.howsun.wrs.util.Strings;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午8:53:45
 */
public interface WorkerService extends Service<Worker> {

	/**
	 * 自动判断邮箱还是手机，并查询
	 * @param account
	 * @param isEmailNotMobile
	 * @return
	 * Worker
	 */
	public Worker get(String account, boolean isEmailNotMobile);
	
	
	/**
	 * 检查账号是否已注册
	 * @param account
	 * @param isEmailNotMobile true=邮箱,false=手机号
	 * @return
	 * boolean true=存在
	 */
	public boolean isAccountExist(String account, boolean isEmailNotMobile);
	
	
	/**
	 * 判断是邮箱还是手机，为空则什么都不是
	 * @param account
	 * @return
	 * Boolean
	 */
	public static Boolean isEmailNotMobile(String account){
		
		if(!Strings.hasLengthByTrim(account)){
			return null;
		}
		
		Boolean isEmailNotMobile = null;
		
		if(account.contains("@") && account.matches(Strings.REGEX_EMAIL)){
			isEmailNotMobile = true;
		}
		else if(account.matches(Strings.REGEX_MOBILE)){
			isEmailNotMobile = false;
		}
		
		return isEmailNotMobile;
	}
}
