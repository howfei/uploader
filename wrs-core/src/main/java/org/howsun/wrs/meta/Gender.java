/**
 * 
 */
package org.howsun.wrs.meta;

/**
 * 说明:性别
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:35:58
 */
public enum Gender {

	FEMALE("女士"),
	MALE("先生");
	
	String displayName;
	Gender(String displayName){this.displayName = displayName;}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getName(){
		return name();
	}
}
