/*
 * Created on 2005. 3. 26
 */
package org.manalith.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.manalith.model.ArticleCommentManager;
import org.manalith.resource.ArticleComment;


/**
 * @author setzer
 */
public class ArticleCommentAction extends DispatchAction{
	private String blogOwnerId = null;
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		blogOwnerId = request.getParameter("id");
		
		if(blogOwnerId == null)
			throw new ServletException("블로그의 ID가 지정되지 않았습니다.");
		
		return dispatchMethod(
				mapping,
				form,
				request,
				response,
				getMethodName(mapping,form,request,response,"method"));
	}
	
	public ActionForward create(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		DynaActionForm commentForm = (DynaActionForm) form;
		ArticleComment comment = new ArticleComment();
		
		PropertyUtils.copyProperties(comment,commentForm);
		comment.setBlogOwnerId(blogOwnerId);
		comment.setInetAddress(request.getRemoteHost());
		
		ArticleCommentManager.instance().createComment(comment);
		
		return new ActionForward("/blog.do?id=" + blogOwnerId,true);
	}
	
	
	public ActionForward destroyForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		return mapping.findForward("destroyForm");
	}
	
	public ActionForward destroy(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		DynaActionForm commentForm = (DynaActionForm) form;
		ArticleComment comment = new ArticleComment();
		
		PropertyUtils.copyProperties(comment,commentForm);

		String commentId = request.getParameter("commentId");
		try{
			comment.setId(Integer.parseInt(commentId));
		}catch(NumberFormatException e){
			String msg = "comment id가 잘못되었습니다.";
			throw new ServletException(msg);
		}
		
		ArticleCommentManager.instance().destroyComment(comment);
		
		return new ActionForward("/blog.do?id=" + blogOwnerId,true);
	}
}
