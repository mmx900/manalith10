/*
 * Created on 2005. 4. 21
 */
package org.manalith.maingate.resource;

import java.util.Date;


/**
 * @author setzer
 */
public class RSSSource {
	private int id;
	private String title;
	private String description;
	private String webUrl;
	private String rssUrl;
	private String category;
	//가장 최근에 엑세스한(아이템들을 가져온) 날짜
	private Date lastUpdate;
	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
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
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the lastUpdate.
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate The lastUpdate to set.
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return Returns the rssUrl.
	 */
	public String getRssUrl() {
		return rssUrl;
	}
	/**
	 * @param rssUrl The rssUrl to set.
	 */
	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the webUrl.
	 */
	public String getWebUrl() {
		return webUrl;
	}
	/**
	 * @param webUrl The webUrl to set.
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
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
}
