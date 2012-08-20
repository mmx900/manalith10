/*
 * Created on 2005. 3. 22
 */
package org.manalith.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.manalith.form.BlogForm;
import org.manalith.maingate.model.dao.MaingateManager;
import org.manalith.maingate.resource.MaingateOption;
import org.manalith.model.BlogManager;
import org.manalith.model.VisitorLogManager;
import org.manalith.resource.Blog;
import org.manalith.resource.VisitorLog;

/**
 * @author setzer
 */
public class BlogAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(BlogAction.class);
	
	private String blogOwnerId;
	private String category;
	private String author;
	private int articleId;
	private Date date;
	private int page;
	
	//TODO : 예외처리
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		blogOwnerId = request.getParameter("id");
		category = request.getParameter("category");
		author = request.getParameter("author");
		String articleId = request.getParameter("articleId");
		String date = request.getParameter("date");
		String page = request.getParameter("page");

		if(page != null){
			try{
				this.page = Integer.parseInt(page);
			}catch(NumberFormatException e){
				String msg = "페이지 번호가 잘못되었습니다.";
				throw new ServletException(msg);
			}
		}		
		if(articleId != null){
			try{
				this.articleId = Integer.parseInt(articleId);
			}catch(NumberFormatException e){
				String msg = "게시물 번호가 잘못되었습니다.";
				throw new ServletException(msg);
			}
		}
		if(date != null){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				this.date = sdf.parse(date);
			}catch(ParseException e){
				String msg = "날짜의 형식이 잘못되었습니다.";
				throw new ServletException(msg);
			}
		}
		
		MaingateOption option = MaingateManager.getOption();
		request.setAttribute("option",option);
		
		if (request.getParameter("method") == null) {
			return main(mapping,form,request,response);
		} else {
			return dispatchMethod(
					mapping,
					form,
					request,
					response,
					getMethodName(mapping, form, request, response, "method"));
		}
	}
	
	public ActionForward main(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BlogManager bm = BlogManager.instance();
		Blog blog = bm.showBlog(blogOwnerId, category, author, page, articleId, date);
		
		VisitorLogManager.instance().addVisitLog(new VisitorLog(blogOwnerId, request));
		//FIXME main 메서드가 두번씩 실행되는 문제(로그가 두번 기록됨)
		
		request.setAttribute("blog", blog);
		
		if(blog != null){
			request.setAttribute("recentArticles", bm.showRecentArticles(blogOwnerId, 5));
			request.setAttribute("recentTrackbacks", bm.showRecentTrackbacks(blogOwnerId, 5));
			request.setAttribute("recentComments", bm.showRecentComments(blogOwnerId, 5));
			request.setAttribute("bookmarkCategories", bm.showBookmarkCategories(blogOwnerId));
		}
		
		return mapping.findForward("main");
	}
	
	public ActionForward rss(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Blog blog = BlogManager.instance().showRSS(blogOwnerId);
		
		request.setAttribute("blog", blog);
		
		return mapping.findForward("rss");
	}
	
	//	 블로그 생성
	public ActionForward create(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BlogForm blogForm = (BlogForm) form;
		Blog blog = new Blog();
		
		blog.setTitle(blogForm.getTitle());
		blog.setDescription(blogForm.getDescription());
		blog.setPageSize(blogForm.getPageSize());
		blog.setTemplate(blogForm.getTemplate());
		blog.setOwner(blogForm.getOwner());
		blog.setUrl(blogForm.getUrl());
		blog.setAllowRSS(blogForm.getAllowRSS());
		
		BlogManager.instance().createBlog(blog, blogForm.getTitleImage(), blogForm.getBackgroundImage());
		
		return new ActionForward("/blog.do?id=" + blogOwnerId, true);
	}
	
	public ActionForward update(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BlogForm blogForm = (BlogForm) form;
		Blog blog = new Blog();
		
		blog.setTitle(blogForm.getTitle());
		blog.setDescription(blogForm.getDescription());
		blog.setPageSize(blogForm.getPageSize());
		blog.setTemplate(blogForm.getTemplate());
		blog.setOwner(blogForm.getOwner());
		blog.setUrl(blogForm.getUrl());
		blog.setAllowRSS(blogForm.getAllowRSS());
		
		BlogManager.instance().updateBlog(blog, blogForm.getTitleImage(), blogForm.getBackgroundImage());
		
		return new ActionForward("/blog.do?id=" + blogOwnerId, true);
	}
}
