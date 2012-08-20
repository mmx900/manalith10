/*
 * Created on 2005. 4. 19
 */
package org.manalith.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.log4j.Logger;
import org.manalith.resource.Template;


/**
 * @author setzer
 */
public class TemplateDAO {
	private static TemplateDAO manager = null;
	private static Logger logger = Logger.getLogger(TemplateDAO.class);
	private static String BLOG_TEMPLATE_PATH = "blog/templates/";
	private static String BLOG_TEMPLATE_PROPERTIES = "template.properties";
	
	private TemplateDAO() {
	}
	
	public static TemplateDAO instance() {
		if(manager == null){
			manager = new TemplateDAO();
		}
		return manager;
	}
	
	public static String[] listTemplates(String contextRealPath){
		String[] templates = new File(contextRealPath + BLOG_TEMPLATE_PATH).list();
		return templates;
	}
	
	public List<Template> getTemplates(String contextRealPath) throws Exception{
		List<Template> templates = new ArrayList<Template>();
		String[] t = listTemplates(contextRealPath);
		for(String template : t){
			templates.add(getTemplate(contextRealPath,template));
		}
		return templates;
	}
	
	public Template getTemplate(String contextRealPath, String templateName) throws Exception{
		Template t = new Template(templateName);
		String templatePath = contextRealPath + BLOG_TEMPLATE_PATH + templateName + "/";
		File templateDirectory = new File(templatePath);
		File templateInfo = new File(templatePath + BLOG_TEMPLATE_PROPERTIES);
		
		if(templateDirectory.exists() && templateDirectory.isDirectory()){
			if(templateInfo.exists() && templateInfo.canRead()){
				InputStream in = new FileInputStream(templateInfo); 
				Properties props = new Properties();
				props.load(in);
				in.close();
				
				t.setFullName(props.getProperty("template.fullName"));
				t.setAuthor(props.getProperty("template.author"));
				t.setAuthorURL(props.getProperty("template.authorURL"));
				t.setVersion(props.getProperty("template.version"));
				t.setLicense(props.getProperty("template.license"));
				t.setLicenseURL(props.getProperty("template.licenseURL"));
				t.setTitleImageWidth(props.getProperty("template.titleImageWidth"));
				t.setTitleImageHeight(props.getProperty("template.titleImageHeight"));
				t.setHasLoginPage(Boolean.parseBoolean(props.getProperty("template.hasLoginPage")));
				t.setHasPostPage(Boolean.parseBoolean(props.getProperty("template.hasPostPage")));
			}else{
				//ignore
			}
		}else{
			String msg = "템플릿을 찾을 수 없습니다.";
			throw new Exception(msg);
		}
		return t;
	}
}
