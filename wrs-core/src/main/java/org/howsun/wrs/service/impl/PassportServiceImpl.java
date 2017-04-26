/**
 * 
 */
package org.howsun.wrs.service.impl;

import java.util.Date;

import org.howsun.wrs.domain.Worker;
import org.howsun.wrs.service.PassportService;
import org.howsun.wrs.service.WorkerService;
import org.howsun.wrs.util.Asserts;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月15日 下午2:27:10
 */
@Service("passportService")
@Transactional
public class PassportServiceImpl extends WorkerServiceImpl implements PassportService {

	
	/* 
	 * @see org.howsun.wrs.service.impl.PassportService#reg(java.lang.String, java.lang.String)
	 */
	@Override
	public Worker reg(String account, String md5Password){
		
		Asserts.hasText(account, "账号不能空");
		Asserts.hasText(md5Password, "密码不能空");
		
		Worker worker = new Worker();
		Boolean isEmailNotMobile = WorkerService.isEmailNotMobile(account);
		Asserts.notNull(isEmailNotMobile, "请使用合格的邮箱或手机号码注册");
		
		boolean isAccountExist = isAccountExist(account, isEmailNotMobile);
		Asserts.isFalse(isAccountExist, String.format("抱歉！该%s已被别人注册，请更换重试！", isEmailNotMobile ? "邮箱" : "手机号码"));
		
		if(isEmailNotMobile){
			worker.setEmail(account);
		}else{
			worker.setPhone(account);
		}
		
		worker.setPassword(PassWordEncoder.encodePassword(md5Password));
		
		super.save(worker);
		
		Worker result = new Worker();
		BeanUtils.copyProperties(worker, result, "password");
		
		return result;
	}
	
	/* 
	 * @see org.howsun.wrs.service.impl.PassportService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Worker login(String account, String md5Password, long lastIp){
		
		Asserts.hasText(account, "账号不能空");
		Asserts.hasText(md5Password, "密码不能空");
		
		Boolean isEmailNotMobile = WorkerService.isEmailNotMobile(account);
		Asserts.notNull(isEmailNotMobile, "请使用合格的邮箱或手机号码登录");
		
		Worker worker = get(account, isEmailNotMobile);
		Asserts.notNull(worker, "登录失败，请检查账号是否正确");
		
		boolean checked = PassWordEncoder.check(md5Password, worker.getPassword()); 
		Asserts.isTrue(checked, "登录失败，账号或密码错误");
		
		//
		worker.setLastLongIp(lastIp);
		worker.setLastLoginTime(new Date());
		
		Integer count = worker.getLogins();
		if(count == null){
			count = 0;
		}
		worker.setLogins(++count);//这里不能count++
		
		Worker result = new Worker();
		BeanUtils.copyProperties(worker, result, "password");
		
		return result;
		
	}
	
}
