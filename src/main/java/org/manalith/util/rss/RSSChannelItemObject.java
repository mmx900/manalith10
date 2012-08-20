/*
 * Created on 2005. 4. 21
 */
package org.manalith.util.rss;

import java.util.Date;

/**
 * @author setzer
 */
public class RSSChannelItemObject {

	private String title;
	private String link;
	// ex) Fri, 08 Apr 2005 16:14:57 +0900
	private Date pubDate;
	private String category;
	private String description;

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the link.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            The link to set.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return Returns the pubDate.
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * @param pubDate
	 *            The pubDate to set.
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
