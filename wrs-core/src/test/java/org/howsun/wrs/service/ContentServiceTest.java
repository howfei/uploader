package org.howsun.wrs.service;

import javax.annotation.Resource;

import org.howsun.wrs.BaseTestCaseJunit;
import org.howsun.wrs.domain.Content;
import org.howsun.wrs.meta.ContentType;
import org.junit.Test;

public class ContentServiceTest extends BaseTestCaseJunit{

	@Resource
	private ContentService contentService;
	
	@Test
	public void testGetIntContentType() {
		Content content = contentService.get(1, ContentType.WORK);
		System.out.println(content);
	}

}
