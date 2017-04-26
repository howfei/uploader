/**
 * 
 */
package org.howsun.wrs.service.impl;

import org.howsun.wrs.util.Asserts;
import org.howsun.wrs.util.BCrypt;
import org.howsun.wrs.util.Codings;
import org.howsun.wrs.util.Strings;

/**
 * 说明:密码加密器
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2015年5月10日 下午8:25:44
 */
public abstract class PassWordEncoder {

	static final String sugar = "";
	
	/**
	 * 
	 * @param rawPass
	 * @param sugar
	 * @return
	 * String
	 * 
	 * @see com.yifangming.person.PassportService#encodePassword(Long objectId, String rawPass, String sugar)
	 */
	public static String encodePassword(String rawMD5Passwork){
		Asserts.validate(Strings.hasText(rawMD5Passwork), "原码不能空");
		rawMD5Passwork = reverse(Codings.MD5Encoding(sugar + rawMD5Passwork));
		return BCrypt.hashpw(rawMD5Passwork, BCrypt.gensalt(12));
	}


	/**
	 * 
	 * @param rawPass
	 * @param encPass
	 * @param sugar
	 * @return
	 * boolean
	 * 
	 * @see com.yifangming.person.PassportService#check(Long objectId, String rawPass, String encPass)
	 */
	public static boolean check(String rawPass, String encPass) {
		Asserts.validate(Strings.hasText(rawPass), 	"原码不能空");
		Asserts.validate(Strings.hasText(encPass), 	"hash码不能空");
		rawPass = reverse(Codings.MD5Encoding(sugar + rawPass));
		return BCrypt.checkpw(rawPass, encPass);
	}

	/**
	 * 反转字符串
	 * @param p
	 * @return
	 * String
	 */
	public static String reverse(String p){
		return new StringBuffer(p).reverse().toString();
	}


	public static void main(String[] args) {
		String hashed1 = encodePassword("123456");
		String hashed2 = encodePassword("123456");
		System.out.println(hashed1);
		System.out.println(hashed2);
		
		System.out.println("----------------");
		String hashed = "$2a$12$FhXRITVLrb.1qzzVW87H9u4tI4r0bY2InHoh0uelRISH1VY3/ax9.";
		//System.out.println(hashed);
		System.out.println(check("23456", hashed));
		System.out.println(check("12345", hashed));
		System.out.println(check("123456", hashed));
	}
}
