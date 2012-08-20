/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.manalith.resource.calendar.BlogCalendar;

/**
 * @author setzer
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "manalith_blog")
@javax.persistence.SequenceGenerator(name = "SEQ_GEN", sequenceName = "manalith_blog_idx_seq")
public class Blog {

	private int idx;

	private String title;
	private String description;
	private Template template;
	private User owner;
	private Date created;
	private List<Article> articles;
	private List authors;
	private List categories;
	private List<BlogBookmark> bookmarks;

	// for paging
	private int totalArticleCount;
	private int pageSize;
	private int currentPage;

	// for day render
	private BlogCalendar calendar;

	// options
	private String url;
	private boolean allowRSS;
	private String titleImage;
	private String backgroundImage;

	/**
	 * @return Returns the articles.
	 */
	@Transient
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * @param articles
	 *            The articles to set.
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	/**
	 * @return Returns the bookmarks.
	 */
	@Transient
	public List<BlogBookmark> getBookmarks() {
		return bookmarks;
	}

	/**
	 * @param bookmarks
	 *            The bookmarks to set.
	 */
	public void setBookmarks(List<BlogBookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	/**
	 * @return Returns the categories.
	 */
	@Transient
	public List getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            The categories to set.
	 */
	public void setCategories(List categories) {
		this.categories = categories;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param describe
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the owner.
	 */
	@Column(updatable = false, name = "owner", nullable = false, length = 12)
	@Type(type = "java.lang.String")
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            The owner to set.
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setOwner(String owner) {
		this.owner = new User(owner);
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return Returns the template.
	 */
	@Type(type = "org.manalith.db.datatype.TemplateType")
	@Column(nullable=false)
	public Template getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            The template to set.
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}

	public void setTemplate(String template) {
		this.template = new Template(template);
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

	/**
	 * @return Returns the totalArticleCount.
	 */
	public int getTotalArticleCount() {
		return totalArticleCount;
	}

	/**
	 * @param totalArticleCount
	 *            The totalArticleCount to set.
	 */
	public void setTotalArticleCount(int totalArticleCount) {
		this.totalArticleCount = totalArticleCount;
	}

	/**
	 * @return Returns the created.
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            The created to set.
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return Returns the authors.
	 */
	@Transient
	public List getAuthors() {
		return authors;
	}

	/**
	 * @param authors
	 *            The authors to set.
	 */
	public void setAuthors(List authors) {
		this.authors = authors;
	}

	/**
	 * hibernate mapping에서 isAllowRSS와 getAllowRSS가 각자 처리되어 Transient 처리합니다.
	 * @return Returns the allowRSS.
	 */
	@Transient
	public boolean isAllowRSS() {
		return allowRSS;
	}

	public boolean getAllowRSS() {
		return this.isAllowRSS();
	}

	/**
	 * @param allowRSS
	 *            The allowRSS to set.
	 */
	public void setAllowRSS(boolean allowRSS) {
		this.allowRSS = allowRSS;
	}

	/**
	 * @return Returns the backgroundImage.
	 */
	public String getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @param backgroundImage
	 *            The backgroundImage to set.
	 */
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @return Returns the titleImage.
	 */
	public String getTitleImage() {
		return titleImage;
	}

	/**
	 * @param titleImage
	 *            The titleImage to set.
	 */
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	/**
	 * @return Returns the calendar.
	 */
	@Transient
	public BlogCalendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            The calendar to set.
	 */
	public void setCalendar(BlogCalendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * @return Returns the idx.
	 */
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx
	 *            The idx to set.
	 */
	public void setIdx(int idx) {
		this.idx = idx;
	}
}
