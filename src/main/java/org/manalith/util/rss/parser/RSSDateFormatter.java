/*
 * Created on 2005. 9. 26
 */
package org.manalith.util.rss.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RSSDateFormatter {

	private static RSSDateFormatter instance;
	private  SimpleDateFormat formatter;
	//Fri, 22 Apr 2005 04:04:58 +0900
	public static final String pubDatePattern = "EEE, d MMM yyyy HH:mm:ss Z";
	//2005-04-21T18:22:22+09:00
	public static final String dcDatePattern = "yyyy-MM-dd'T'HH:mm:ss";

	private RSSDateFormatter() {
		formatter = new SimpleDateFormat(pubDatePattern, Locale.US);
	}

	public static RSSDateFormatter instance() {
		if (instance == null)
			instance = new RSSDateFormatter();
		return instance;
	}
	
	public Date parsePubDate(String date) throws ParseException{
		formatter.applyPattern(pubDatePattern);
		Date result = formatter.parse(date);
		return result;
	}
	
	public Date parseDcDate(String date) throws ParseException{
		formatter.applyPattern(dcDatePattern);
		Date result = formatter.parse(date);
		return result;
	} 

}
