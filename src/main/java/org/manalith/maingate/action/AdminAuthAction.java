/*
 * Created on 2005. 4. 21
 */
package org.manalith.maingate.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author setzer
 */
public class AdminAuthAction extends DispatchAction{
	private static Logger logger = LoggerFactory.getLogger(AdminAuthAction.class);
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if (request.getParameter("method") == null){
			return adminForm(mapping, form, request, response);
		}
		
		return dispatchMethod(mapping, form, request, response, getMethodName(
				mapping,
				form,
				request,
				response,
		"method"));
	}
	
	public ActionForward adminForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("authForm");
	}
	
	public ActionForward login(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String password = (String) ((DynaActionForm)form).get("password");
		try{
			Properties props = new Properties();
			InputStream in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/maingate.properties");
			props.load(in);
			in.close();
			
			//logger.error(password);
			//logger.error(props.getProperty("maingate.admin.password"));
			
			if(props.getProperty("maingate.admin.password").equals(password)){
				request.getSession().setAttribute("admin","true");
			}
		}catch(IOException e){
			throw new ServletException(e);
		}
		return new ActionForward("/admin.do",true);
	}
	
	public ActionForward logout(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(request.getSession().getAttribute("admin").equals("true"))
			request.getSession().setAttribute("admin",null);
		
		return new ActionForward("/",true);
	}
}
