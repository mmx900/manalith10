package org.manalith.maingate.resource;

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

	public boolean getAllowRSS() {
		return allowRSS;
	}

	public boolean isAllowRSS() {
		return allowRSS;
	}

	public void setAllowRSS(boolean allowRSS) {
		this.allowRSS = allowRSS;
	}

	public boolean getAllowRegister() {
		return allowRegister;
	}

	public boolean isAllowRegister() {
		return allowRegister;
	}

	public void setAllowRegister(boolean allowRegister) {
		this.allowRegister = allowRegister;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWideTitle() {
		return wideTitle;
	}

	public void setWideTitle(String wideTitle) {
		this.wideTitle = wideTitle;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
