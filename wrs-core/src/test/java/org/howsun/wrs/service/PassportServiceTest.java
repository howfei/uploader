package org.howsun.wrs.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.howsun.wrs.BaseTestCaseJunit;
import org.junit.Test;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月16日 上午9:11:13
 */
public class PassportServiceTest extends BaseTestCaseJunit {

	@Resource
	private PassportService passportService;
	
	@Test
	public void testReg() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testEncryptOriginalPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStringBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsAccountExist() {
		boolean result = passportService.isAccountExist("zhangjihao@sohu.com", true);
		System.out.println(result);
	}

}
