/*
 * Created on 2005. 3. 22
 */
package org.manalith.admin.action;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.manalith.maingate.model.dao.MaingateManager;
import org.manalith.maingate.resource.MaingateOption;
import org.manalith.model.dao.BlogDAO;
import org.manalith.model.dao.FileDAO;
import org.manalith.model.dao.TemplateDAO;
import org.manalith.resource.Blog;
import org.manalith.resource.Template;

/**
 * @author setzer
 */
public class AdminAction extends DispatchAction {

	private String blogOwnerId = null;

	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		blogOwnerId = request.getParameter("id");

		if (blogOwnerId == null) {
			throw new ServletException("블로그의 ID가 지정되지 않았습니다.");
		}

		if (!blogOwnerId.equals(request.getSession().getAttribute("userId"))) {
			throw new ServletException("블로그의 소유주가 아닙니다.");
		}

		if (request.getParameter("method") == null) {
			return main(mapping, form, request, response);
		}

		MaingateOption option = MaingateManager.getOption();
		request.setAttribute("option", option);

		return dispatchMethod(mapping, form, request, response, getMethodName(
				mapping,
				form,
				request,
				response,
				"method"));
	}

	// 관리자 메뉴 메인.
	public ActionForward main(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);
		request.setAttribute("blog", blog);

		return mapping.findForward("main");
	}

	public ActionForward options(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getParameter("del") != null) {
			if (request.getParameter("del").equals("titleImage")) {
				FileDAO.instance().deleteBlogTitleImage(blogOwnerId);
			} else if (request.getParameter("del").equals("backgroundImage")) {
				FileDAO.instance().deleteBlogBackgroundImage(blogOwnerId);
			}
		}

		List<Template> templates = TemplateDAO.instance().getTemplates(getServlet().getServletContext().getRealPath("/"));
		request.setAttribute("templates", templates);

		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);

		blog.setTemplate(
				TemplateDAO.instance().getTemplate(
						getServlet().getServletContext().getRealPath("/"),
						blog.getTemplate().toString()
				)
		);

		request.setAttribute("blog", blog);

		return mapping.findForward("options");
	}

	//FIXME ??? 이 메서드 왜 만들었는지 모르겠다 ...;
	public ActionForward updateOption(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//ignore

		return mapping.findForward("main");
	}

	public ActionForward createForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Template> templates = TemplateDAO.instance().getTemplates(getServlet().getServletContext().getRealPath("/"));
		request.setAttribute("templates", templates);

		return mapping.findForward("createForm");
	}

	public ActionForward categories(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("categories");
	}
}
