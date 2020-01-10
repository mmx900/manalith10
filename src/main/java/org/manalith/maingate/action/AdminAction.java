/*
 * Created on 2005. 4. 20
 */
package org.manalith.maingate.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.manalith.maingate.model.dao.RSSSourceDAO;
import org.manalith.maingate.resource.RSSSource;
import org.manalith.model.dao.UserDAO;
import org.manalith.resource.User;


/**
 * @author setzer
 */
public class AdminAction extends DispatchAction {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (request.getSession().getAttribute("admin") == null) {
			return new ActionForward("/adminAuth.do", true);
		} else if (!(request.getSession().getAttribute("admin").equals("true"))) {
			return new ActionForward("/adminAuth.do", true);
		}

		if (request.getParameter("method") == null) {
			return mapping.findForward("menu");
		}

		return dispatchMethod(mapping, form, request, response, getMethodName(
				mapping,
				form,
				request,
				response,
				"method"));
	}
	
	/*public ActionForward options(
	 ActionMapping mapping,
	 ActionForm form,
	 HttpServletRequest request,
	 HttpServletResponse response) throws Exception {
	 
	 MaingateManager.instance().getOptions();
	 
	 return mapping.findForward("options");
	 }*/

	public ActionForward users(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<User> userList = UserDAO.instance().getUsers();

		request.setAttribute("userList", userList);

		return mapping.findForward("users");
	}

	public ActionForward rss(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<RSSSource> sources = RSSSourceDAO.instance().getSources();
		request.setAttribute("sources", sources);

		List<String> categories = RSSSourceDAO.instance().getCategories();
		request.setAttribute("categories", categories);

		return mapping.findForward("rss");
	}
	
	/*public ActionForward updateOption(
	 ActionMapping mapping,
	 ActionForm form,
	 HttpServletRequest request,
	 HttpServletResponse response) throws Exception {
	 
	 return mapping.findForward("/");
	 }*/
}
