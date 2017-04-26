package org.howsun.wrs;

import org.howsun.wrs.domain.Worker;
import org.howsun.wrs.service.WorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 功能描述：
 *
 * @author howfei[13720056627@163.com]
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service.xml" })
@Rollback
public class BaseTestCaseJunit extends AbstractTransactionalJUnit4SpringContextTests{

	protected static Logger log = LoggerFactory.getLogger(BaseTestCaseJunit.class);
	
	@Test
	public void testStartup(){
		
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("service.xml");
		WorkerService workerService = ctx.getBean(WorkerService.class);
		Worker worker = workerService.get(1);
		System.out.println(worker != null);
		long count = workerService.count();
		System.out.println(count);
		log.info(ctx.getDisplayName());
		ctx.close();
	}
}
