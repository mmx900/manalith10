/*
 * Created on 2005. 4. 1
 */
package org.manalith.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.manalith.model.dao.TrackbackDAO;


/**
 * @author setzer
 */
public class TrackbackDeleteAction extends Action{
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TrackbackDAO.instance().deleteTrackback(
				Integer.parseInt(request.getParameter("articleId")),
				Integer.parseInt(request.getParameter("trackbackId")));
		
		
		return new ActionForward("/blog.do?id=" + request.getParameter("id"),true);
	}
	
}
