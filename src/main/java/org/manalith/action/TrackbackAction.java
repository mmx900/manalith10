/*
 * Created on 2005. 3. 31
 */
package org.manalith.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.model.dao.TrackbackDAO;
import org.manalith.resource.ArticleTrackback;


/**
 * @author setzer
 */
public class TrackbackAction extends Action{
	
	private static Logger logger = LoggerFactory.getLogger(TrackbackAction.class);
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm trackbackForm = (DynaActionForm) form;
		ArticleTrackback trackback = new ArticleTrackback();
		PropertyUtils.copyProperties(trackback,trackbackForm);
		
		trackback.setBlogOwnerId(request.getParameter("id"));
		trackback.setArticleId(Integer.parseInt(request.getParameter("articleId")));
		trackback.setInetAddress(request.getRemoteHost());
		
		int error = TrackbackDAO.instance().recievePing(trackback);
		String msg = null;
		if(error == 1){
			msg = "Invalid Format";
		}
		
		request.setAttribute("error",error+"");
		request.setAttribute("msg",msg);
		
		return mapping.findForward("result");
	}
	
}
