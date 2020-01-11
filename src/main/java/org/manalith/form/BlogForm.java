package org.manalith.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class BlogForm extends ActionForm {
	private String title;
	private String description;
	private String template;
	private String owner;
	private String url;
	private boolean allowRSS;
	private int pageSize;
	private FormFile titleImage;
	private FormFile backgroundImage;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isAllowRSS() {
		return allowRSS;
	}

	public boolean getAllowRSS() {
		return this.isAllowRSS();
	}

	public void setAllowRSS(boolean allowRSS) {
		this.allowRSS = allowRSS;
	}

	public FormFile getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(FormFile backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public FormFile getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(FormFile titleImage) {
		this.titleImage = titleImage;
	}
}
