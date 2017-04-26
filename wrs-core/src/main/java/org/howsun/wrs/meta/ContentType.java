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
public enum ContentType {

	WORK("工作报告内容"),
	COMMENT("评论内容"),
	;
	
	String displayName;
	ContentType(String displayName){this.displayName = displayName;}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getName(){
		return name();
	}
}
