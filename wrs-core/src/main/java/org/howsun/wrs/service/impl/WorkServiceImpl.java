/**
 * 
 */
package org.howsun.wrs.service.impl;

import java.util.Date;

import org.howsun.wrs.domain.Content;
import org.howsun.wrs.domain.Work;
import org.howsun.wrs.meta.ContentType;
import org.howsun.wrs.service.WorkService;
import org.howsun.wrs.util.Asserts;
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
public class WorkServiceImpl extends ServiceImpl<Work> implements WorkService{

	@Override
	public void save(Work work) {
		if(work.getContent() == null || work.getContent().trim().length() < 10){
			Asserts.isTrue(false, "没有报告内容");
		}
		Asserts.notNull(work.getWorker(), "请指定报告人");
		Asserts.notNull(work.getType(), "请指定报告类型");
		Asserts.notNull(work.getReported(), "请指定报告日期");
		super.save(work);
		
		Content content = new Content();
		content.setObject(work.getId());
		content.setType(ContentType.WORK);
		content.setContent(work.getContent());
		content.setCreated(new Date());
		dao.save(content);
	}

	
}
