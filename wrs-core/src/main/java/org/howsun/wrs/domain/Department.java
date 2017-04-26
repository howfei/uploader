/**
 * 
 */
package org.howsun.wrs.domain;

import javax.persistence.Column;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:27:24
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "wrs_department")
public class Department extends Entity{

	private static final long serialVersionUID = -878248879456962380L;

	@Column(length = 32)
	private String name;
	
	@Column(length = 128)
	private String explains;

	
	//-------------------------------------------------------------------------
	
	public Department() {
		super();
	}

	public Department(Integer id) {
		super(id);
	}

	
	//-------------------------------------------------------------------------
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}
	
	
}
