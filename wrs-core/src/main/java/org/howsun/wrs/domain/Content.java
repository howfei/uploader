/**
 * 
 */
package org.howsun.wrs.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.howsun.wrs.meta.ContentType;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:53:23
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "wrs_content", indexes = {
		@Index(name = "idx_content_object", columnList = "object"),
		@Index(name = "idx_content_type", columnList = "type")
})
public class Content extends Entity {

	private static final long serialVersionUID = -8906463741431907910L;

	/**内容对象**/
	@Column(nullable = false)
	private Integer object;
	
	/**类型：工作报告内容、评论内容**/
	@Enumerated(EnumType.ORDINAL)
	private ContentType type;
	
	/**内容**/
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String content;
	
	/**评论时间**/
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	
	//------------------------------------------------------------------------------
	
	public Content() {
		super();
	}

	public Content(Integer id) {
		super(id);
	}

	
	//------------------------------------------------------------------------------
	
	public Integer getObject() {
		return object;
	}

	public void setObject(Integer object) {
		this.object = object;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
