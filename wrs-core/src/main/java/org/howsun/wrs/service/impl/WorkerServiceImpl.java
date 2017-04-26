/**
 * 
 */
package org.howsun.wrs.service.impl;

import org.howsun.wrs.domain.Worker;
import org.howsun.wrs.service.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午8:25:15
 */
@Service("workerService")
@Transactional
public class WorkerServiceImpl extends ServiceImpl<Worker> implements WorkerService{

	/*
	 * @see org.howsun.wrs.service.WorkerService#get(java.lang.String, boolean)
	 */
	public Worker get(String account, boolean isEmailNotMobile){
		return this.get(isEmailNotMobile ? "email=?" : "phone=?", new Object[]{account});
	}
	
	/*
	 * 
	 * @see org.howsun.wrs.service.WorkerService#isAccountExist(java.lang.String, boolean)
	 */
	public boolean isAccountExist(String account, boolean isEmailNotMobile){
		long count = this.count(isEmailNotMobile ? "email=?" : "phone=?", new Object[]{account});
		return count > 0;
	}
	
}
