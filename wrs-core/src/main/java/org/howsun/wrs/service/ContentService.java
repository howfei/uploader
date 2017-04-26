/**
 * 
 */
package org.howsun.wrs.service;

import org.howsun.wrs.domain.Content;
import org.howsun.wrs.meta.ContentType;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午8:53:45
 */
public interface ContentService extends Service<Content> {
	
	public Content get(int objectId, ContentType type);
}
