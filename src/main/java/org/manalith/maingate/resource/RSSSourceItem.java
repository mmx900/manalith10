/*
 * Created on 2005. 4. 22
 */
package org.manalith.maingate.resource;

import java.util.Date;


/**
 * @author setzer
 */
public class RSSSourceItem {
	private int id;
	private int source;
	private String rssVersion;
	private String rssCharset;
	private String channelTitle;
	private String channelDescription;
	private String channelLanguage;
	private String channelCopyright;
	private String channelGenerator;
	private Date channelPubDate;
	private String channelLink;
	private String itemTitle;
	private String itemDescription;
	private String itemCategory;
	private Date itemPubDate;
	private String itemLink;
	/**
	 * @return Returns the channelCopyright.
	 */
	public String getChannelCopyright() {
		return channelCopyright;
	}
	/**
	 * @param channelCopyright The channelCopyright to set.
	 */
	public void setChannelCopyright(String channelCopyright) {
		this.channelCopyright = channelCopyright;
	}
	/**
	 * @return Returns the channelDescription.
	 */
	public String getChannelDescription() {
		return channelDescription;
	}
	/**
	 * @param channelDescription The channelDescription to set.
	 */
	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}
	/**
	 * @return Returns the channelGenerator.
	 */
	public String getChannelGenerator() {
		return channelGenerator;
	}
	/**
	 * @param channelGenerator The channelGenerator to set.
	 */
	public void setChannelGenerator(String channelGenerator) {
		this.channelGenerator = channelGenerator;
	}
	/**
	 * @return Returns the channelLanguage.
	 */
	public String getChannelLanguage() {
		return channelLanguage;
	}
	/**
	 * @param channelLanguage The channelLanguage to set.
	 */
	public void setChannelLanguage(String channelLanguage) {
		this.channelLanguage = channelLanguage;
	}
	/**
	 * @return Returns the channelPubDate.
	 */
	public Date getChannelPubDate() {
		return channelPubDate;
	}
	/**
	 * @param channelPubDate The channelPubDate to set.
	 */
	public void setChannelPubDate(Date channelPubDate) {
		this.channelPubDate = channelPubDate;
	}
	/**
	 * @return Returns the channelTitle.
	 */
	public String getChannelTitle() {
		return channelTitle;
	}
	/**
	 * @param channelTitle The channelTitle to set.
	 */
	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
	/**
	 * @return Returns the id.
	 */
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
	 * @return Returns the itemCategory.
	 */
	public String getItemCategory() {
		return itemCategory;
	}
	/**
	 * @param itemCategory The itemCategory to set.
	 */
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	/**
	 * @return Returns the itemDescription.
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription The itemDescription to set.
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 * @return Returns the itemLink.
	 */
	public String getItemLink() {
		return itemLink;
	}
	/**
	 * @param itemLink The itemLink to set.
	 */
	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}
	/**
	 * @return Returns the itemPubDate.
	 */
	public Date getItemPubDate() {
		return itemPubDate;
	}
	/**
	 * @param itemPubDate The itemPubDate to set.
	 */
	public void setItemPubDate(Date itemPubDate) {
		this.itemPubDate = itemPubDate;
	}
	/**
	 * @return Returns the itemTitle.
	 */
	public String getItemTitle() {
		return itemTitle;
	}
	/**
	 * @param itemTitle The itemTitle to set.
	 */
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	/**
	 * @return Returns the rssCharset.
	 */
	public String getRssCharset() {
		return rssCharset;
	}
	/**
	 * @param rssCharset The rssCharset to set.
	 */
	public void setRssCharset(String rssCharset) {
		this.rssCharset = rssCharset;
	}
	/**
	 * @return Returns the rssVersion.
	 */
	public String getRssVersion() {
		return rssVersion;
	}
	/**
	 * @param rssVersion The rssVersion to set.
	 */
	public void setRssVersion(String rssVersion) {
		this.rssVersion = rssVersion;
	}
	/**
	 * @return Returns the source.
	 */
	public int getSource() {
		return source;
	}
	/**
	 * @param source The source to set.
	 */
	public void setSource(int source) {
		this.source = source;
	}
	/**
	 * @return Returns the channelLink.
	 */
	public String getChannelLink() {
		return channelLink;
	}
	/**
	 * @param channelLink The channelLink to set.
	 */
	public void setChannelLink(String channelLink) {
		this.channelLink = channelLink;
	}
}
