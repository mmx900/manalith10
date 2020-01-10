/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;


/**
 * @author setzer
 */
public class BlogBookmark {
	private int id;
	private String blogOwnerId;
	private String category;
	private String title;
	private String url;
	private String rssUrl;
	private String imageUrl;
	private String description;

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
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
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

	/**
	 * @return Returns the bookmarkId.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param bookmarkId The bookmarkId to set.
	 */
	public void setId(int bookmarkId) {
		this.id = bookmarkId;
	}

	/**
	 * @return Returns the imageUrl.
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl The imageUrl to set.
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
}
