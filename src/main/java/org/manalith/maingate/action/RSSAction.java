package org.manalith.maingate.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.manalith.maingate.model.dao.RSSSourceDAO;
import org.manalith.maingate.resource.RSSSource;

public class RSSAction extends DispatchAction {

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

		return dispatchMethod(mapping, form, request, response, getMethodName(
				mapping,
				form,
				request,
				response,
				"method"));
	}

	public ActionForward sourceAdd(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm f = (DynaActionForm) form;
		RSSSource s = new RSSSource();
		PropertyUtils.copyProperties(s, form);

		RSSSourceDAO.instance().add(s);

		return new ActionForward(request.getHeader("referer"), true);
	}

	public ActionForward sourceDelete(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int sourceId;
		try {
			sourceId = Integer.parseInt(request.getParameter("sourceId"));
		} catch (NumberFormatException e) {
			String msg = "잘못된 인자입니다.";
			throw new ServletException(msg);
		}

		RSSSourceDAO.instance().delete(sourceId);

		return new ActionForward(request.getHeader("referer"), true);
	}

	public ActionForward sourceUpdate(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm f = (DynaActionForm) form;
		RSSSource s = new RSSSource();
		PropertyUtils.copyProperties(s, form);

		RSSSourceDAO.instance().update(s);

		return new ActionForward(request.getHeader("referer"), true);
	}
}
