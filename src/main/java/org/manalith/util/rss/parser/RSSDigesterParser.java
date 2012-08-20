/*
 * Created on 2005. 9. 25
 */
package org.manalith.util.rss.parser;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.manalith.util.rss.RSSChannelItemObject;
import org.manalith.util.rss.RSSChannelObject;
import org.manalith.util.rss.RSSObject;
import org.xml.sax.SAXException;


public class RSSDigesterParser implements RSSParser{
	private static RSSDigesterParser util = null;
	private static Logger logger = Logger.getLogger(RSSParser.class);
	
	private RSSDigesterParser(){}
	public static RSSDigesterParser instance(){
		if(util == null){
			util = new RSSDigesterParser();
		}
		
		return util;
	}
	
	public RSSObject parse(String url){
		return parse(url, "UTF-8");
	}
	
	public RSSObject parse(String url, String charset){
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("rss",RSSObject.class);
		digester.addBeanPropertySetter("rss/version", "version");
		digester.addObjectCreate("rss/channel", RSSChannelObject.class);
		digester.addSetNext("rss/channel", "addChannel");
		digester.addBeanPropertySetter("rss/channel/title", "title");
		digester.addBeanPropertySetter("rss/channel/link", "link");
		digester.addBeanPropertySetter("rss/channel/language", "language");
		digester.addBeanPropertySetter("rss/channel/dc:language", "language");
		//digester.addBeanPropertySetter("rss/channel/pubDate", "pubDate");
		//digester.addBeanPropertySetter("rss/channel/dc:date", "pubDate");
		digester.addBeanPropertySetter("rss/channel/copyright", "copyright");
		digester.addBeanPropertySetter("rss/channel/generator", "generator");
		digester.addObjectCreate("rss/channel/item", RSSChannelItemObject.class);
		digester.addSetNext("rss/channel/item", "addItem");
		digester.addBeanPropertySetter("rss/channel/item/title", "title");
		digester.addBeanPropertySetter("rss/channel/item/link", "link");
		digester.addBeanPropertySetter("rss/channel/category", "category");
		//digester.addBeanPropertySetter("rss/channel/item/pubDate", "pubDate");
		//digester.addBeanPropertySetter("rss/channel/item/dc:date", "pubDate");
		digester.addBeanPropertySetter("rss/channel/item/description", "description");
		
		RSSObject ro = null;
		
		try {
			ro = (RSSObject) digester.parse(url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return ro;
	}
}
