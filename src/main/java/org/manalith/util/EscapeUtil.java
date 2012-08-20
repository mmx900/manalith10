/*
 * Created on 2005. 3. 31
 */
package org.manalith.util;


/**
 * @author setzer
 * @version 0.1
 */
public class EscapeUtil {
    public static String escapeXml(String tmp){
        tmp = tmp.replaceAll("&","&amp;");
        // tmp = tmp.replaceAll("\"","&quot;");
        tmp = tmp.replaceAll("'","&#039;");
        tmp = tmp.replaceAll("\"","&#034;");
        tmp = tmp.replaceAll("<","&lt;");
        tmp = tmp.replaceAll(">","&gt;");
        return tmp;
    } 
    
    public static String escapeHtml(String tmp){
        tmp = escapeXml(tmp);
        return tmp;
    }
    
    public static String convertLineFeed(String tmp){
        tmp = tmp.replaceAll("\r\n","<br />");
        tmp = tmp.replaceAll("\n","<br />");
        return tmp;
    }
}
