/**
 * 
 */
package org.howsun.wrs.meta;

/**
 * 说明:工作报告类型
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:35:58
 */
public enum WorkType {

	DAILY("日报"),
	WEEKLY("周报"),
	MONTHLY("月报")
	;
	
	String displayName;
	WorkType(String displayName){this.displayName = displayName;}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getName(){
		return name();
	}
}
