/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

/**
 * @author setzer
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "manalith_blog_article")
@javax.persistence.SequenceGenerator(name = "SEQ_GEN", sequenceName = "manalith_blog_article_id_seq")
public class Article {

	private int id;
	private int num;
	private String blogOwnerId;

	private String title;
	// Manalith는 1 블로그당 다중 저자를 지원하므로 Article의 Author와 Blog의 Owner는 같지 않을 수 있다.
	// BlogAuthor를 사용하지 않는 것은 Author 그룹에서 제명했을 경우를 고려한 것이다.
	private User author;
	private String category;
	private Date date;
	private String contents;
	private ArticleFormat format;
	private List<ArticleComment> comments;
	private List<ArticleTrackback> trackbacks;
	private List<ArticleFile> files;

	// for paging
	private int totalCommentCount;
	private int totalTrackbackCount;

	/**
	 * @return Returns the articleComment.
	 */
	@Transient
	public List<ArticleComment> getComments() {
		return comments;
	}

	/**
	 * @param articleComment The articleComment to set.
	 */
	public void setComments(List<ArticleComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return Returns the articleTrackback.
	 */
	@Transient
	public List<ArticleTrackback> getTrackbacks() {
		return trackbacks;
	}

	/**
	 * @param articleTrackback The articleTrackback to set.
	 */
	public void setTrackbacks(List<ArticleTrackback> trackbacks) {
		this.trackbacks = trackbacks;
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
	 * @return Returns the format.
	 */
	@Type(type = "org.manalith.db.datatype.ArticleFormatType")
	public ArticleFormat getFormat() {
		return format;
	}

	/**
	 * @param format The format to set.
	 */
	public void setFormat(ArticleFormat format) {
		this.format = format;
	}

	/**
	 * @return Returns the owner.
	 */
	//@ManyToOne(targetEntity=org.manalith.resource.User.class, fetch=FetchType.EAGER)
	@ManyToOne
	@JoinColumn(name = "author", referencedColumnName = "id")
	public User getAuthor() {
		return author;
	}

	/**
	 * @param owner The owner to set.
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	public void setAuthor(String authorName) {
		this.author = new User(authorName);
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
	 * @return Returns the totalCommentCount.
	 */
	public int getTotalCommentCount() {
		return totalCommentCount;
	}

	/**
	 * @param totalCommentCount The totalCommentCount to set.
	 */
	public void setTotalCommentCount(int totalCommentCount) {
		this.totalCommentCount = totalCommentCount;
	}

	/**
	 * @return Returns the totalTrackbackCount.
	 */
	public int getTotalTrackbackCount() {
		return totalTrackbackCount;
	}

	/**
	 * @param totalTrackbackCount The totalTrackbackCount to set.
	 */
	public void setTotalTrackbackCount(int totalTrackbackCount) {
		this.totalTrackbackCount = totalTrackbackCount;
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
	 * @return Returns the files.
	 */
	@Transient
	public List getFiles() {
		return files;
	}

	/**
	 * @param files The files to set.
	 */
	public void setFiles(List<ArticleFile> files) {
		this.files = files;
	}

	public void setFiles(String[] filesArray) {
		try {
			List<ArticleFile> files = new ArrayList<ArticleFile>();
			for (String file : filesArray) {
				files.add(new ArticleFile(file));
			}
			this.files = files;
		} catch (NullPointerException e) {
			this.files = null;
		}
	}

	public void setFiles(int[] filesArray) {
		try {
			List<ArticleFile> files = new ArrayList<ArticleFile>();
			for (int fileIdx : filesArray) {
				files.add(new ArticleFile(fileIdx));
			}
			this.files = files;
		} catch (NullPointerException e) {
			this.files = null;
		}
	}

	/**
	 * @return Returns the num.
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num The num to set.
	 */
	public void setNum(int num) {
		this.num = num;
	}
}
