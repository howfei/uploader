package org.howsun.wrs.service;

import org.howsun.wrs.domain.Worker;
import org.howsun.wrs.util.Codings;


/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月15日 下午6:04:59
 */
public interface PassportService extends WorkerService{

	static final String WEB_MD5_SALT = "li2H543s!6g3";
	
	/**
	 * 注册<br>
	 * 先区分出account是邮箱还是手机，<br>
	 * 再验证是否已注册<br>
	 * 密码是原密码(明码)MD5后的值，如果是移动APP，请在手机端按方式加密再发送到服务端<br>
	 * <br>
	 * @param account
	 * @param md5Password
	 * @return
	 * Worker
	 */
	Worker reg(String account, String md5Password);

	/**
	 * 登录 <br>
	 * 先区分出account是邮箱还是手机，<br>
	 * 再根据邮箱或手机查询<br>
	 * 密码是原密码(明码)MD5后的值，如果是移动APP，请在手机端按方式加密再发送到服务端<br>
	 * 根据
	 * <br>
	 * @param account
	 * @param md5Password
	 * @param lastIp
	 * @return
	 * Worker
	 */
	Worker login(String account, String md5Password, long lastIp);

	/**
	 * 加密表单传来的原始密码为MD5，如果是移动APP，请在手机端按方式加密再发送到服务端
	 * @param originalPassword
	 * @return
	 * String
	 */
	public static String encryptOriginalPassword(String originalPassword){
		return Codings.MD5Encoding(WEB_MD5_SALT + originalPassword + WEB_MD5_SALT);
	}
}