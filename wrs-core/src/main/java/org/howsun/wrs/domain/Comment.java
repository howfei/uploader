/**
 * 
 */
package org.howsun.wrs.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:53:23
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "wrs_comment", indexes = @Index(name = "idx_comment_work", columnList = "work"))
public class Comment extends Entity {

	private static final long serialVersionUID = -8906463741431907910L;

	/**评论哪份报告，不允许空**/
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "work")
	private Work work;
	
	/**评论哪个评论**/
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "comment")
	private Comment comment;
	
	/**作者，不允许空**/
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "author")
	private Worker author;
	
	/**评论内容引子**/
	@Column(length = 140)
	private String intro;
	
	/**评论时间**/
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	
	//-------------------------------------------------------------------------
	
	public Comment() {
		super();
	}

	public Comment(Integer id) {
		super(id);
	}

	//-------------------------------------------------------------------------
	
	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Worker getAuthor() {
		return author;
	}

	public void setAuthor(Worker author) {
		this.author = author;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	

}
