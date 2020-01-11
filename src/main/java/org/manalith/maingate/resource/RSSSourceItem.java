package org.manalith.maingate.resource;

import java.util.Date;

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

	public String getChannelCopyright() {
		return channelCopyright;
	}

	public void setChannelCopyright(String channelCopyright) {
		this.channelCopyright = channelCopyright;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public String getChannelGenerator() {
		return channelGenerator;
	}

	public void setChannelGenerator(String channelGenerator) {
		this.channelGenerator = channelGenerator;
	}

	public String getChannelLanguage() {
		return channelLanguage;
	}

	public void setChannelLanguage(String channelLanguage) {
		this.channelLanguage = channelLanguage;
	}

	public Date getChannelPubDate() {
		return channelPubDate;
	}

	public void setChannelPubDate(Date channelPubDate) {
		this.channelPubDate = channelPubDate;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemLink() {
		return itemLink;
	}

	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}

	public Date getItemPubDate() {
		return itemPubDate;
	}

	public void setItemPubDate(Date itemPubDate) {
		this.itemPubDate = itemPubDate;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getRssCharset() {
		return rssCharset;
	}

	public void setRssCharset(String rssCharset) {
		this.rssCharset = rssCharset;
	}

	public String getRssVersion() {
		return rssVersion;
	}

	public void setRssVersion(String rssVersion) {
		this.rssVersion = rssVersion;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getChannelLink() {
		return channelLink;
	}

	public void setChannelLink(String channelLink) {
		this.channelLink = channelLink;
	}
}
