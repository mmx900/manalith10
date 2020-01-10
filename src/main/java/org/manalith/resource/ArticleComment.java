/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;

import java.util.Date;

/**
 * @author setzer
 */
public class ArticleComment {
	private String blogOwnerId;
	private int articleId;

	private int id;
	private String name;
	private String password;
	private Date date;
	private String homepage;
	private String contents;
	private String inetAddress;

	/**
	 * @return Returns the contents.
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents The contents to set.
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return Returns the homepage.
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage The homepage to set.
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
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
	 * @return Returns the articleId.
	 */
	public int getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId The articleId to set.
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return Returns the inetAddress.
	 */
	public String getInetAddress() {
		return inetAddress;
	}

	/**
	 * @param inetAddress The inetAddress to set.
	 */
	public void setInetAddress(String inetAddress) {
		this.inetAddress = inetAddress;
	}

	/**
	 * @return Returns the blogOwnerId.
	 */
	public String getBlogOwnerId() {
		return blogOwnerId;
	}

	/**
	 * @param blogOwnerId The blogOwnerId to set.
	 */
	public void setBlogOwnerId(String blogOwnerId) {
		this.blogOwnerId = blogOwnerId;
	}
}
