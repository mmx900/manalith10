/*
 * Created on 2005. 3. 22
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

/**
 * @author setzer
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "manalith_member")
@javax.persistence.SequenceGenerator(name = "SEQ_GEN", sequenceName = "manalith_member_idx_seq")
public class User {

	private int idx;
	private String id;
	private String password;
	private String name;
	private String email;
	private Date date;

	public User() {

	}

	public User(String id) {
		this.id = id;
	}

	public String toString() {
		return id;
	}

	/**
	 * @return Returns the id.
	 */
	@Column(updatable = false, name = "id", nullable = false, length = 12)
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the idx.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx The idx to set.
	 */
	public void setIdx(int idx) {
		this.idx = idx;
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
}
