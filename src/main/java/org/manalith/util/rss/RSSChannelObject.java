/*
 * Created on 2005. 4. 21
 */
package org.manalith.util.rss;

import java.util.Date;
import java.util.List;


/**
 * @author setzer
 */
public class RSSChannelObject {
	private String title;
	private String link;
	private String description;
	//언어. 예) ko, en
	private String language;
	//본 내용들의 저작권.
	private String copyright;
	//RSS 문서가 생성된 시각.
	private Date pubDate;
	//RSS를 생성한 프로그램. 예) Manalith v0.1
	private String generator;
	private List<RSSChannelItemObject> items;
	
	/**
	 * @return Returns the copyright.
	 */
	public String getCopyright() {
		return copyright;
	}
	/**
	 * @param copyright The copyright to set.
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
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
	 * @return Returns the generator.
	 */
	public String getGenerator() {
		return generator;
	}
	/**
	 * @param generator The generator to set.
	 */
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	/**
	 * @return Returns the items.
	 */
	public List<RSSChannelItemObject> getItems() {
		return items;
	}
	/**
	 * @param items The items to set.
	 */
	public void setItems(List<RSSChannelItemObject> items) {
		this.items = items;
	}
	
	public void addItem(RSSChannelItemObject obj){
		this.items.add(obj);
	}
	
	public RSSChannelItemObject getItemByTitle(String title){
		RSSChannelItemObject obj = null;
		RSSChannelItemObject tmp = null;
		for(int i=0;i<items.size();i++){
			tmp = (RSSChannelItemObject) items.get(i);
			if(tmp.getTitle().equals(title)){
				obj = tmp;
				break;
			}
		}
		return obj;
	}
	/**
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language The language to set.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return Returns the link.
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link The link to set.
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return Returns the pubDate.
	 */
	public Date getPubDate() {
		return pubDate;
	}
	/**
	 * @param pubDate The pubDate to set.
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
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
}
