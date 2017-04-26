/**
 * 
 */
package org.howsun.wrs.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.howsun.wrs.meta.WorkType;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:46:44
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "wrs_work", indexes = {
		@Index(name = "idx_work_type", columnList = "type"),//部门索引
		@Index(name = "idx_work_worker", columnList = "worker"),//电话索引
		@Index(name = "idx_work_reported", columnList = "reported"),//邮箱索引
})
public class Work extends Entity {

	private static final long serialVersionUID = -5206168699135536545L;
	
	/**报告人**/
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "worker")
	private Worker worker;
	
	/**报告类型**/
	@Enumerated(EnumType.ORDINAL)
	private WorkType type;
	
	/**报告日期**/
	@NotNull(message = "报告日期不能空")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reported;
	
	/**创建日期**/
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	/**
	 * 报告内容(临时，需要在Content查询)
	 * @see Content
	 */
	@Transient
	private String content;
	
	//------------------------------------------------------------------
	
	public Work() {
		super();
	}

	public Work(Integer id) {
		super(id);
	}
	
	
	//------------------------------------------------------------------
	
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public WorkType getType() {
		return type;
	}
	public void setType(WorkType type) {
		this.type = type;
	}
	public Date getReported() {
		return reported;
	}
	public void setReported(Date reported) {
		this.reported = reported;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
}
