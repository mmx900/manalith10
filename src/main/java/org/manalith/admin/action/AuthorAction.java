/*
 * Created on 2005. 3. 28
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
import org.manalith.exception.ExistAuthorException;
import org.manalith.model.dao.BlogAuthorDAO;
import org.manalith.model.dao.UserDAO;
import org.manalith.resource.BlogAuthor;
import org.manalith.resource.User;

/**
 * @author setzer
 */
public class AuthorAction extends DispatchAction {

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
			return list(mapping, form, request, response);
		}

		return dispatchMethod(mapping, form, request, response, getMethodName(
				mapping,
				form,
				request,
				response,
				"method"));
	}

	public ActionForward list(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("authors", BlogAuthorDAO.instance()
				.getAuthors(blogOwnerId));

		return mapping.findForward("authors");
	}

	public ActionForward add(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BlogAuthor author = new BlogAuthor();
		author.setUserId(request.getParameter("authorId"));
		author.setBlogOwnerId(blogOwnerId);
		try {
			BlogAuthorDAO.instance().addAuthor(author);
		} catch (ExistAuthorException e) {
			throw new ServletException("이미 등록이 되어 있습니다.");
		}

		return new ActionForward("/blog/admin/author.do?id=" + blogOwnerId, true);
	}

	public ActionForward search(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyword = request.getParameter("keyword");
		List<User> users = UserDAO.instance().getMatchedUsersById(keyword, blogOwnerId);
		request.setAttribute("users", users);

		return list(mapping, form, request, response);
	}

	public ActionForward delete(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BlogAuthor author = new BlogAuthor();
		author.setUserId(request.getParameter("authorId"));
		author.setBlogOwnerId(blogOwnerId);
		BlogAuthorDAO.instance().deleteAuthor(author);

		return new ActionForward("/blog/admin/author.do?id=" + blogOwnerId, true);
	}
}
