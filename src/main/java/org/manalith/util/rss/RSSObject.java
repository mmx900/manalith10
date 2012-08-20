/*
 * Created on 2005. 4. 21
 */
package org.manalith.util.rss;

import java.util.List;


/**
 * @author setzer
 */
public class RSSObject {
	private String version;
	private String charset;
	private List<RSSChannelObject> channels;
	
	/**
	 * @return Returns the channels.
	 */
	public List<RSSChannelObject> getChannels() {
		return channels;
	}
	/**
	 * @param channels The channels to set.
	 */
	public void setChannels(List<RSSChannelObject> channels) {
		this.channels = channels;
	}
	
	public void addChannel(RSSChannelObject obj){
		this.channels.add(obj);
	}
	/**
	 * @return Returns the charset.
	 */
	public String getCharset() {
		return charset;
	}
	/**
	 * @param charset The charset to set.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
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
}
