/**
 * 
 */
package org.howsun.wrs.service.impl;

import org.howsun.wrs.domain.Content;
import org.howsun.wrs.meta.ContentType;
import org.howsun.wrs.service.ContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午8:25:15
 */
@Service
@Transactional
public class ContentServiceImpl extends ServiceImpl<Content> implements ContentService{

	@Override
	public Content get(int objectId, ContentType type) {
		Content content = dao.findOne(entityClass, "o.object=? AND o.type=?", new Object[]{objectId, type});
		return content;
	}

}
