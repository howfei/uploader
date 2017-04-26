/**
 *
 */
package org.howsun.wrs.util;


/**
 * 说明:<br>
 *
 * @author 张纪豪
 * @version
 */
public abstract class Htmls {


	/**
	 * 将过滤掉Html标签
	 */
	public static String cleanHtmlTag(String surText) {
		return Strings.toString(surText).replaceAll("<[.[^<]]*>", "").replace("\n", "<br/>");
	}
	
	/**
	 * 转义所有的Html标签
	 * @param text
	 * @return
	 */
	public static String htmlEscape(String text){
		if(text == null || "".equals(text))
            return text;
		return text.replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace(" ", "&nbsp;")
        .replace("\"", "&quot;")
        .replace("\'", "&apos;")
        .replace("\n", "<br/>");
	}


	/**
	 * 反转码：将文本转成Html格式
	 * @param text
	 * @return
	 */
	public static String stringToHtml(String text){
		if(text == null || "".equals(text))
            return text;
		return text.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&nbsp;", " ")
				.replace("&quot;", "\"")
				.replace("&apos;", "\'")
				.replace("<br/>", "\n");
	}
}