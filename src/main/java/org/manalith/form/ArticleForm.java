package org.manalith.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBlogOwnerId() {
		return blogOwnerId;
	}

	public void setBlogOwnerId(String blogOwnerId) {
		this.blogOwnerId = blogOwnerId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int[] getFiles() {
		return files;
	}

	public void setFiles(int[] files) {
		this.files = files;
	}

	public FormFile getTmpFile() {
		return tmpFile;
	}

	public void setTmpFile(FormFile tmpFile) {
		this.tmpFile = tmpFile;
	}
}
