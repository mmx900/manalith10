/*
 * Created on 2005. 4. 21
 */
package org.manalith.maingate.resource;


/**
 * @author setzer
 */
public class MaingateOption {
	//각 유저 블로그의 TITLE 태그 뒤에 자동으로 삽입될 내용
	private String wideTitle;
	//사이트의 URL
	private String url;
	//유저를 가입 가능하게 할 것인가?
	private boolean allowRegister;
	//RSS 사용을 허가할 것인가?
	private boolean allowRSS;
	//maingate의 템플릿(blog와는 별개, /pages/maingate/template)
	private String template;
	/**
	 * @return Returns the allowRSS.
	 */
	public boolean getAllowRSS() {
		return allowRSS;
	}
	/**
	 * @return Returns the allowRSS.
	 */
	public boolean isAllowRSS() {
		return allowRSS;
	}
	/**
	 * @param allowRSS The allowRSS to set.
	 */
	public void setAllowRSS(boolean allowRSS) {
		this.allowRSS = allowRSS;
	}
	/**
	 * @return Returns the allowUserRegister.
	 */
	public boolean getAllowRegister() {
		return allowRegister;
	}
	/**
	 * @return Returns the allowUserRegister.
	 */
	public boolean isAllowRegister() {
		return allowRegister;
	}
	/**
	 * @param allowUserRegister The allowUserRegister to set.
	 */
	public void setAllowRegister(boolean allowRegister) {
		this.allowRegister = allowRegister;
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
	 * @return Returns the wideTitle.
	 */
	public String getWideTitle() {
		return wideTitle;
	}
	/**
	 * @param wideTitle The wideTitle to set.
	 */
	public void setWideTitle(String wideTitle) {
		this.wideTitle = wideTitle;
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
}
