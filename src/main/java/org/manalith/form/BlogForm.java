/*
 * Created on 2005. 3. 23
 */
package org.manalith.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


/**
 * @author setzer
 */
public class BlogForm extends ActionForm{
	private String title;
	private String description;
	private String template;
	private String owner;
	private String url;
	private boolean allowRSS;
	private int pageSize;
	private FormFile titleImage;
	private FormFile backgroundImage;
	
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
	 * @return Returns the owner.
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner The owner to set.
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return Returns the template.
	 */
	public String getTemplate() {
		return template;
	}
	/**
	 * @param template The template to set.
	 */
	public void setTemplate(String template) {
		this.template = template;
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
	 * @return Returns the allowRSS.
	 */
	public boolean isAllowRSS() {
		return allowRSS;
	}
	public boolean getAllowRSS() {
		return this.isAllowRSS();
	}
	/**
	 * @param allowRSS The allowRSS to set.
	 */
	public void setAllowRSS(boolean allowRSS) {
		this.allowRSS = allowRSS;
	}
	/**
	 * @return Returns the backgroundImage.
	 */
	public FormFile getBackgroundImage() {
		return backgroundImage;
	}
	/**
	 * @param backgroundImage The backgroundImage to set.
	 */
	public void setBackgroundImage(FormFile backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	/**
	 * @return Returns the titleImage.
	 */
	public FormFile getTitleImage() {
		return titleImage;
	}
	/**
	 * @param titleImage The titleImage to set.
	 */
	public void setTitleImage(FormFile titleImage) {
		this.titleImage = titleImage;
	}
}
