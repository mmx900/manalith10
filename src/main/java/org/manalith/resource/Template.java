/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;


/**
 * @author setzer
 */
public class Template {
	private String name;
	private String author;
	private String authorURL;
	private String version;
	private String fullName;
	private String license;
	private String licenseURL;
	private String titleImageWidth;
	private String titleImageHeight;
	private boolean hasLoginPage;
	private boolean hasPostPage;
	
	public Template(){
		
	}
	
	public Template(String str){
		this.name = str;
	}
	
	public String toString(){
		return this.name;
	}
	
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version The version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return Returns the fullName.
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName The fullName to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return Returns the authorURL.
	 */
	public String getAuthorURL() {
		return authorURL;
	}
	/**
	 * @param authorURL The authorURL to set.
	 */
	public void setAuthorURL(String authorURL) {
		this.authorURL = authorURL;
	}
	/**
	 * @return Returns the licenseURL.
	 */
	public String getLicenseURL() {
		return licenseURL;
	}
	/**
	 * @param licenseURL The licenseURL to set.
	 */
	public void setLicenseURL(String licenseURL) {
		this.licenseURL = licenseURL;
	}
	/**
	 * @return Returns the license.
	 */
	public String getLicense() {
		return license;
	}
	/**
	 * @param license The license to set.
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	/**
	 * @return Returns the titleImageHeight.
	 */
	public String getTitleImageHeight() {
		return titleImageHeight;
	}
	/**
	 * @param titleImageHeight The titleImageHeight to set.
	 */
	public void setTitleImageHeight(String titleImageHeight) {
		this.titleImageHeight = titleImageHeight;
	}
	/**
	 * @return Returns the titleImageWidth.
	 */
	public String getTitleImageWidth() {
		return titleImageWidth;
	}
	/**
	 * @param titleImageWidth The titleImageWidth to set.
	 */
	public void setTitleImageWidth(String titleImageWidth) {
		this.titleImageWidth = titleImageWidth;
	}
	/**
	 * @return Returns the hasLoginPage.
	 */
	public boolean isHasLoginPage() {
		return hasLoginPage;
	}
	/**
	 * @return Returns the hasLoginPage.
	 */
	public boolean getHasLoginPage() {
		return hasLoginPage;
	}
	/**
	 * @param hasLoginPage The hasLoginPage to set.
	 */
	public void setHasLoginPage(boolean hasLoginPage) {
		this.hasLoginPage = hasLoginPage;
	}
	/**
	 * @return Returns the hasPostPage.
	 */
	public boolean isHasPostPage() {
		return hasPostPage;
	}
	/**
	 * @return Returns the hasPostPage.
	 */
	public boolean getHasPostPage() {
		return hasPostPage;
	}
	/**
	 * @param hasPostPage The hasPostPage to set.
	 */
	public void setHasPostPage(boolean hasPostPage) {
		this.hasPostPage = hasPostPage;
	}
}
