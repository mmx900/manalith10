package org.manalith.maingate.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.maingate.model.dao.MaingateManager;
import org.manalith.maingate.model.dao.RSSItemDAO;
import org.manalith.maingate.resource.MaingateOption;
import org.manalith.maingate.resource.RSSSourceItem;
import org.manalith.model.dao.UserDAO;
import org.manalith.resource.User;

public class MainAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(MainAction.class);

	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		List<User> users = UserDAO.instance().getUsers();
		RSSItemDAO.instance().restoreFromSources();
		List<RSSSourceItem> recentEntries = RSSItemDAO.instance().getItems(10);
		MaingateOption option = MaingateManager.getOption();

		request.setAttribute("userList", users);
		request.setAttribute("recentEntries", recentEntries);
		request.setAttribute("option", option);

		return mapping.findForward("main");
	}
}
