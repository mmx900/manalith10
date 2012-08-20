/*
 * Created on 2005. 4. 6
 */
package org.manalith.admin.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.manalith.model.dao.BlogBookmarkDAO;
import org.manalith.resource.BlogBookmark;


/**
 * @author setzer
 */
public class BookmarkAction extends DispatchAction {

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

        request.setAttribute("bookmarks", BlogBookmarkDAO.instance()
                .getBookmarks(blogOwnerId));
        
        request.setAttribute("categories", BlogBookmarkDAO.instance()
                .getCategories(blogOwnerId));

        return mapping.findForward("bookmarks");
    }
    
    public ActionForward create(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DynaActionForm bForm = (DynaActionForm) form;
        BlogBookmark b = new BlogBookmark();
        PropertyUtils.copyProperties(b,bForm);
        b.setBlogOwnerId(blogOwnerId);
        
        BlogBookmarkDAO.instance().createBookmark(b);

        return new ActionForward("/blog/admin/bookmark.do?id=" + blogOwnerId,true);
    }
    
    public ActionForward destroy(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(new String(request.getParameter("bookmarkId")));
        
        BlogBookmarkDAO.instance().destroyBookmark(id);

        return new ActionForward("/blog/admin/bookmark.do?id=" + blogOwnerId,true);
    }
}
