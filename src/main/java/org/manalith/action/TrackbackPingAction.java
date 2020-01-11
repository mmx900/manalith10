package org.manalith.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.manalith.model.dao.ArticleDAO;
import org.manalith.model.dao.BlogDAO;
import org.manalith.model.dao.TrackbackDAO;
import org.manalith.resource.Article;
import org.manalith.resource.Blog;

public class TrackbackPingAction extends Action {

	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm commentForm = (DynaActionForm) form;

		int articleId = ((Integer) commentForm.get("articleId")).intValue();
		Article article = null;
		String url = (String) commentForm.get("url");
		String encoding = (String) commentForm.get("encoding");

		Blog blogInfo = BlogDAO.instance().getBlogInfo(request.getParameter("id"));

		article = ArticleDAO.instance().getArticle(articleId);

		TrackbackDAO.instance().sendPing(blogInfo, article, url, encoding);

		//FIXME FIXME FIXME
		return mapping.findForward("pingForm");
	}
}
