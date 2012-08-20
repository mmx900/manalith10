/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;


/**
 * @author setzer
 */
public class ArticleFormat {
	private String format;
	private static String[] totalFormats = {"text","html"};
	
	public ArticleFormat(String formatName) throws IllegalArgumentException{
		if(formatValidate(formatName)){
			this.format = formatName;
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public String toString(){
		return format;
	}
	
	public boolean formatValidate(String str){
		for (String name: totalFormats)
			if(str.equals(name)) return true;
		return false;
	}
}
