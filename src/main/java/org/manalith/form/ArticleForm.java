/*
 * Created on 2005. 3. 23
 */
package org.manalith.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


/**
 * @author setzer
 */
public class ArticleForm extends ActionForm {
	private String blogOwnerId;
	private String title;
	private String author;
	private String category;
	private String date;
	private String contents;
	private String format;
	private FormFile tmpFile;
	private int[] files;

	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
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
	public String getDate() {
		return date;
	}

	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
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
	 * @return Returns the files.
	 */
	public int[] getFiles() {
		return files;
	}

	/**
	 * @param files The files to set.
	 */
	public void setFiles(int[] files) {
		this.files = files;
	}

	/**
	 * @return Returns the tmpFile.
	 */
	public FormFile getTmpFile() {
		return tmpFile;
	}

	/**
	 * @param tmpFile The tmpFile to set.
	 */
	public void setTmpFile(FormFile tmpFile) {
		this.tmpFile = tmpFile;
	}
}
