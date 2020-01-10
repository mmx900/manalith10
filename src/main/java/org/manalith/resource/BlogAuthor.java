/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author setzer
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "manalith_blog_author")
@javax.persistence.SequenceGenerator(name = "SEQ_GEN", sequenceName = "manalith_blog_author_id_seq")
public class BlogAuthor {

	private String id;
	private String userId;
	private String blogOwnerId;
	private Date date;

	/**
	 * @return Returns the blogOwnerId.
	 */
	@ManyToOne(targetEntity = org.manalith.resource.Blog.class)
	public String getBlogOwnerId() {
		return blogOwnerId;
	}

	/**
	 * @param blogOwnerId The blogOwnerId to set.
	 */
	public void setBlogOwnerId(String blogOwnerId) {
		this.blogOwnerId = blogOwnerId;
	}

	/**
	 * @return Returns the id.
	 */
	@ManyToOne(targetEntity = org.manalith.resource.User.class)
	public String getUserId() {
		return userId;
	}

	/**
	 * @param id The id to set.
	 */
	public void setUserId(String id) {
		this.userId = id;
	}

	/**
	 * @return Returns the date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date The date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Returns the id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
}
