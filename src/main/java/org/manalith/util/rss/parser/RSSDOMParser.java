/*
 * Created on 2005. 9. 25
 */
package org.manalith.util.rss.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.manalith.util.rss.RSSChannelItemObject;
import org.manalith.util.rss.RSSChannelObject;
import org.manalith.util.rss.RSSObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class RSSDOMParser implements RSSParser{
	private static RSSDOMParser util = null;
	private static Logger logger = Logger.getLogger(RSSParser.class);
	
	private RSSDOMParser(){}
	public static RSSDOMParser instance(){
		if(util == null){
			util = new RSSDOMParser();
		}
		
		return util;
	}
	
	public RSSObject parse(String url){
		return parse(url, "UTF-8");
	}
	
	public RSSObject parse(String url, String charset){
		RSSDateFormatter df = RSSDateFormatter.instance();
		RSSObject ro = null;
		
		try {
			ro = new RSSObject();
			RSSChannelObject roc = null;
			List<RSSChannelObject> channels = new ArrayList<RSSChannelObject>();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domParser = factory.newDocumentBuilder();
			Document document = domParser.parse(url);
			
			Node rss = document.getDocumentElement();
			ro.setVersion(rss.getAttributes().getNamedItem("version").getNodeValue());
			
			NodeList nChannels = document.getElementsByTagName("channel");
			
			for(int i=0;i<nChannels.getLength();i++){
				roc = new RSSChannelObject();
				List<RSSChannelItemObject> items = new ArrayList<RSSChannelItemObject>();
				RSSChannelItemObject roci = null;
				
				NodeList nChannelValues = nChannels.item(i).getChildNodes();
				
				for(int x=0;x<nChannelValues.getLength();x++){
					Node node = nChannelValues.item(x);
					String name = node.getNodeName();
					String value = node.getTextContent();
					
					if(name.equals("title")){
						roc.setTitle(value);
					}else if(name.equals("link")){
						roc.setLink(value);
					}else if(name.equals("language")){
						roc.setLanguage(value);
					}else if(name.equals("dc:language")){
						roc.setLanguage(value);
					}else if(name.equals("copyright")){
						roc.setCopyright(value);
					}else if(name.equals("pubDate")){
						try{
							roc.setPubDate(df.parsePubDate(value));
						}catch(ParseException e){
							logger.error("parse fail : " + value);
						}
					}else if(name.equals("dc:date")){
						try{
							roc.setPubDate(df.parseDcDate(value));
						}catch(ParseException e){
							logger.error("parse fail : " + value);
						}
					}else if(name.equals("generator")){
						roc.setGenerator(value);
					}else if(name.equals("item")){
						NodeList nItemValues = node.getChildNodes();
						roci = new RSSChannelItemObject(); 
						
						for(int y=0;y<nItemValues.getLength();y++){
							Node itemValue = nItemValues.item(y);
							
							String iName = itemValue.getNodeName();
							String iValue = itemValue.getTextContent();
							
							if(iName.equals("title")){
								roci.setTitle(iValue);
							}else if(iName.equals("link")){
								roci.setLink(iValue);
							}else if(iName.equals("pubDate")){
								try{
									roci.setPubDate(df.parsePubDate(iValue));
								}catch(ParseException e){
									logger.error("parse fail : " + iValue);
								}
							}else if(iName.equals("dc:date")){
								try{
									roci.setPubDate(df.parseDcDate(iValue));
								}catch(ParseException e){
									logger.error("parse fail : " + iValue);
								}
							}else if(iName.equals("category")){
								roci.setCategory(iValue);
							}else if(iName.equals("description")){
								roci.setDescription(iValue);
							}
							
						}
						items.add(roci);
					}
				}
				roc.setItems(items);
				channels.add(roc);
				ro.setChannels(channels);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ro;
	}
}
