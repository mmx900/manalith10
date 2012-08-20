/*
 * Created on 2005. 4. 21
 */
package org.manalith.maingate.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.manalith.maingate.resource.MaingateOption;
import org.manalith.model.dao.FileDAO;


/**
 * @author setzer
 */
public class MaingateManager extends HttpServlet{
	private static Logger logger = Logger.getLogger(MaingateManager.class);
	private static MaingateManager instance = null;
	private static MaingateOption option = null;
	
	public MaingateManager(){
		/*
		 try{
		 this.init();
		 }catch(ServletException e){
		 logger.error(e);
		 }
		 */
	}
	
	public void init() throws ServletException{
		Properties props = new Properties();
		try{
			//설정들을 가져온다
			InputStream in = getServletContext().getResourceAsStream("/WEB-INF/maingate.properties");
			props.load(in);
			in.close();
			
			setOption(props);
			
			//컨텍스트 경로를 파일 매니저에 추가한다.
			FileDAO.instance().setContextRootPath(getServletContext().getRealPath("/"));
		}catch(IOException e){
			throw new ServletException(e);
		}
		instance = this;
	}
	
	public static MaingateManager instance(){
		return instance;
	}
	
	public static void setOption(Properties props) throws IOException{
		option = new MaingateOption();
		option.setAllowRSS(Boolean.parseBoolean(props.getProperty("maingate.allowRSS")));
		option.setAllowRegister(Boolean.parseBoolean(props.getProperty("maingate.allowRegister")));
		option.setUrl(props.getProperty("maingate.url"));
		option.setWideTitle(props.getProperty("maingate.wideTitle"));
		option.setTemplate(props.getProperty("maingate.template"));
	}
	
	public static MaingateOption getOption(){
		return option;
	}
}
