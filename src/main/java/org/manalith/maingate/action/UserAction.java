/*
 * Created on 2005. 3. 22
 */
package org.manalith.maingate.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.manalith.maingate.model.dao.MaingateManager;
import org.manalith.model.BlogManager;
import org.manalith.model.UserManager;
import org.manalith.model.dao.UserDAO;
import org.manalith.resource.Blog;
import org.manalith.resource.User;

/**
 * @author setzer
 */
public class UserAction extends DispatchAction {
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (request.getSession().getAttribute("admin") == null) {
			return new ActionForward("/adminAuth.do", true);
		} else if (!(request.getSession().getAttribute("admin").equals("true"))) {
			return new ActionForward("/adminAuth.do", true);
		}

		ActionErrors errors = form.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("addForm");
		}

		register(mapping, form, request, response);
		return new ActionForward(request.getHeader("referer"), true);
	}

	public ActionForward register(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (MaingateManager.getOption().isAllowRegister()
				|| request.getSession().getAttribute("admin") != null) {
			DynaActionForm userForm = (DynaActionForm) form;
			User user = new User();
			PropertyUtils.copyProperties(user, userForm);

			UserManager.instance().createUser(user);

			if (request.getSession().getAttribute("admin") == null) {
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getId());
				session.setAttribute("userName", user.getName());
			}

			Blog blog = new Blog();
			blog.setTitle("Untitled");
			blog.setDescription("");
			blog.setTemplate("default");
			blog.setOwner(user);
			blog.setUrl("http://localhost:8080");

			BlogManager.instance().createBlog(blog, null, null);
		} else {
			String msg = "가입이 허용되지 않은 사이트입니다.";
			throw new ServletException(msg);
		}

		return mapping.findForward("main");
	}

	/**
	 * 아이디(uid)와 비밀번호(password)를 받아 사용자를 로그인한다.
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm userForm = (DynaActionForm) form;
		User user = new User();
		PropertyUtils.copyProperties(user, userForm);

		UserDAO manager = UserDAO.instance();

		if (manager.validUser(user)) {
			user = manager.getUser(user.getId());
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
		}
		return new ActionForward(request.getHeader("referer"), true);
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().invalidate();
		return new ActionForward(request.getHeader("referer"), true);
	}
}
