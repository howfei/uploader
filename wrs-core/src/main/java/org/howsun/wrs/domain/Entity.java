/**
 * 
 */
package org.howsun.wrs.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 说明:实体类父类
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月13日 下午9:27:59
 */
@MappedSuperclass
public class Entity implements Serializable {


	private static final long serialVersionUID = 3390929717815952968L;

	/**ID**/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	
	
	//----------------------------------------------------------------------------
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	//----------------------------------------------------------------------------
	
	public Entity() {
		super();
	}
	
	public Entity(Integer id) {
		super();
		this.id = id;
	}
	
	//----------------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
