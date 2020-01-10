/*
 * Created on 2005. 4. 20
 */
package org.manalith.resource;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Type;

/**
 * @author setzer
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "manalith_blog_log")
@javax.persistence.SequenceGenerator(name = "SEQ_GEN", sequenceName = "manalith_blog_log_id_seq")
public class VisitorLog {

	private int id;
	private String blogOwnerId;
	private String referer;
	private String ip;
	private String agent;
	private Date date;

	public VisitorLog(
			String blogOwnerId,
			String referer,
			String ip,
			String agent,
			Date date) {
		setBlogOwnerId(blogOwnerId);
		setAgent(referer);
		setIp(ip);
		setAgent(agent);
		setDate(date);
	}

	public VisitorLog(String blogOwnerId, HttpServletRequest request) {
		setBlogOwnerId(blogOwnerId);
		setAgent(request.getHeader("User-Agent"));
		setIp(request.getRemoteAddr());
		// setReferer(request.getRequestURI());
		setReferer(request.getHeader("referer"));
		setDate(new Date());
	}

	/**
	 * @return Returns the agent.
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent The agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
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
	 * @return Returns the ip.
	 */
	@Type(type = "org.manalith.db.datatype.InetType")
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip The ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return Returns the referer.
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer The referer to set.
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @return Returns the blogOwnerId.
	 */
	@Column(updatable = false, name = "blogOwnerId", nullable = false, length = 12)
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
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	public int getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
