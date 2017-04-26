/**
 * 
 */
package org.howsun.wrs.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.howsun.wrs.meta.Gender;
import org.howsun.wrs.util.Ips;

/**
 * 说明:员工
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:34:57
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "wrs_worker", indexes = {
		@Index(name = "idx_worker_department", columnList = "department"),//部门索引
		@Index(name = "idx_worker_phone", columnList = "phone"),//电话索引
		@Index(name = "idx_worker_email", columnList = "email"),//邮箱索引
})
public class Worker extends Entity {

	private static final long serialVersionUID = 5731499748781523049L;

	/**名称**/
	@Column(length = 32)
	private String name;
	
	/**性别**/
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;
	
	/**电话：可作登录账号**/
	@Column(length = 11, unique = true)
	private String phone;
	
	/**邮箱：可作登录账号**/
	@Column(length = 64, unique = true)
	private String email;
	
	/**密码：加密存储**/
	@Column(length = 64, nullable = false)
	private String password;
	
	/**登录次数**/
	private Integer logins;
	
	@Column(name = "last_login_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;
	
	@Column(name = "last_login_ip")
	private Long lastLongIp;
	
	/**岗位、职位**/
	@Column(length = 16)
	private String position;
	
	/**属性部门**/
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "department")
	private Department department;

	
	//---------------------------------------------------------------------
	
	public Worker() {
		super();
	}

	public Worker(Integer id) {
		super(id);
	}

	
	//---------------------------------------------------------------------
	public String getLastLongIpToString(){
		return this.lastLongIp != null ? Ips.long2ip(this.lastLongIp) : null;
	}
	//---------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getLogins() {
		return logins;
	}

	public void setLogins(Integer logins) {
		this.logins = logins;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getLastLongIp() {
		return lastLongIp;
	}

	public void setLastLongIp(Long lastLongIp) {
		this.lastLongIp = lastLongIp;
	}
	
}
