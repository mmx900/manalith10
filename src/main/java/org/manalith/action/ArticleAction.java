/*
 * Created on 2005. 3. 23
 */
package org.manalith.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.manalith.form.ArticleForm;
import org.manalith.maingate.model.dao.MaingateManager;
import org.manalith.maingate.resource.MaingateOption;
import org.manalith.model.ArticleManager;
import org.manalith.model.FileManager;
import org.manalith.model.dao.ArticleDAO;
import org.manalith.model.dao.BlogDAO;
import org.manalith.model.dao.FileDAO;
import org.manalith.resource.Article;
import org.manalith.resource.ArticleFile;
import org.manalith.resource.ArticleFormat;
import org.manalith.resource.Blog;
import org.manalith.resource.BlogAuthor;
import org.manalith.resource.User;


/**
 * @author setzer
 */
public class ArticleAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(ArticleAction.class);
	private String blogOwnerId = null;
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		blogOwnerId = request.getParameter("id");
		
		if(blogOwnerId == null){
			throw new ServletException("블로그의 ID가 지정되지 않았습니다.");
		}
		
		MaingateOption option = MaingateManager.getOption();
		request.setAttribute("option",option);
		
		return dispatchMethod(
				mapping,
				form,
				request,
				response,
				getMethodName(mapping,form,request,response,"method"));
	}
	
	
	//TODO author인지 확인하는 작업
	public ActionForward create(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ArticleForm articleForm = (ArticleForm) form;
		Article article = new Article();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss",Locale.KOREA);
		
		article.setBlogOwnerId(articleForm.getBlogOwnerId());
		article.setTitle(articleForm.getTitle());
		article.setAuthor(new User(articleForm.getAuthor()));
		article.setCategory(articleForm.getCategory());
		article.setDate(sdf.parse(articleForm.getDate()));
		article.setContents(articleForm.getContents());
		article.setFormat(new ArticleFormat(articleForm.getFormat()));
		article.setFiles(articleForm.getFiles());
		
		ArticleManager.instance().createArticle(article, articleForm.getFiles());
		
		return new ActionForward("/blog.do?id=" + blogOwnerId,true);
	}
	
	
	//FILE만을 업로드하고 갱신된 정보를 createForm으로 돌려보낸다.
	public ActionForward upload(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ArticleForm articleForm = (ArticleForm) form;
		Article article = new Article();
		String mode = request.getParameter("mode");
		
		if(!StringUtils.isEmpty(request.getParameter("articleId")))
			article.setId(Integer.parseInt((String)request.getParameter("articleId")));
		article.setBlogOwnerId(articleForm.getBlogOwnerId());
		article.setTitle(articleForm.getTitle());
		article.setAuthor(new User(articleForm.getAuthor()));
		article.setCategory(articleForm.getCategory());
		article.setContents(articleForm.getContents());
		article.setFormat(new ArticleFormat(articleForm.getFormat()));
		if(articleForm.getFiles() != null)
			article.setFiles(articleForm.getFiles());
		
		if(mode.equals(ArticleManager.ARTICLE_CREATE_MODE)){
			FileDAO.instance().setUnconnectedFile(
					blogOwnerId,
					articleForm.getTmpFile());
		}else{
			FileDAO.instance().setConnectedFile(
					blogOwnerId,
					article.getId(),
					articleForm.getTmpFile());
		}
		
		List<String> categories = ArticleDAO.instance().getCategories(blogOwnerId);
		List<ArticleFile> files = null;
		if(mode.equals(ArticleManager.ARTICLE_CREATE_MODE)){
			files = FileDAO.instance().getUnconnectedFiles(blogOwnerId);
		}else{
			files = FileDAO.instance().getConnectedFiles(article.getId());
		}
		article.setFiles(files);
		
		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);
		
		request.setAttribute("id",blogOwnerId);
		request.setAttribute("article",article);
		request.setAttribute("categories",categories);
		request.setAttribute("articleDate",articleForm.getDate());
		request.setAttribute("targetAction",request.getParameter("mode"));
		request.setAttribute("blog",blog);
		return mapping.findForward("postForm");
	}
	
	//업로드된 FILE만을 삭제하고 원본 페이지를 refresh
	public ActionForward uploadCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ArticleForm articleForm = (ArticleForm) form;
		Article article = new Article();
		String mode = request.getParameter("mode");
		
		if(!StringUtils.isEmpty(request.getParameter("articleId")))
			article.setId(Integer.parseInt((String)request.getParameter("articleId")));
		article.setBlogOwnerId(articleForm.getBlogOwnerId());
		article.setTitle(articleForm.getTitle());
		article.setAuthor(new User(articleForm.getAuthor()));
		article.setCategory(articleForm.getCategory());
		article.setContents(articleForm.getContents());
		article.setFormat(new ArticleFormat(articleForm.getFormat()));
		if(articleForm.getFiles() != null)
			article.setFiles(articleForm.getFiles());
		
		FileManager.instance().deleteFile(
				Integer.parseInt(request.getParameter("fileId")),
				request.getParameter("fileName"));
		
		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);
		List<String> categories = ArticleDAO.instance().getCategories(blogOwnerId);
		List<ArticleFile> files = null;
		if(mode.equals(ArticleManager.ARTICLE_CREATE_MODE)){
			files = FileDAO.instance().getUnconnectedFiles(blogOwnerId);
		}else{
			files = FileDAO.instance().getConnectedFiles(article.getId());
		}
		article.setFiles(files);
		
		request.setAttribute("id",blogOwnerId);
		request.setAttribute("article",article);
		request.setAttribute("categories",categories);
		request.setAttribute("articleDate",articleForm.getDate());
		request.setAttribute("targetAction",request.getParameter("mode"));
		request.setAttribute("blog",blog);
		return mapping.findForward("postForm");
	}
	
	
	//TODO author인지 확인하는 작업
	public ActionForward createForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		String userId = (String)request.getSession().getAttribute("userId");
		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);
		
		if(userId == null){
			String msg = "블로그 소유주나 저자로 로그인하지 않았습니다.";
			throw new ServletException(msg);
		}else if(!(checkOwner(userId) || checkAuthor(userId, blog.getAuthors()))){
			String msg = "블로그 소유주나 저자가 아닙니다.";
			throw new ServletException(msg);
		}
		
		Article article = new Article();
		String today = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss",Locale.KOREA).format(new Date());
		List<String> categories = ArticleDAO.instance().getCategories(blogOwnerId);
		article.setFiles(FileDAO.instance().getUnconnectedFiles(blogOwnerId));
		
		request.setAttribute("id",blogOwnerId);
		request.setAttribute("article",article);
		request.setAttribute("categories",categories);
		request.setAttribute("articleDate",today);
		request.setAttribute("targetAction","create");
		request.setAttribute("blog",blog);
		return mapping.findForward("postForm");
	}
	
	
	public ActionForward updateForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		Article article = ArticleDAO.instance().getArticle(
				blogOwnerId,Integer.parseInt((String)request.getParameter("articleId")));
		
		String date = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss",Locale.KOREA).format(article.getDate());
		List<String> categories = ArticleDAO.instance().getCategories(blogOwnerId);
		article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);
		
		request.setAttribute("id",blogOwnerId);
		request.setAttribute("article",article);
		request.setAttribute("categories",categories);
		request.setAttribute("articleDate",date);
		request.setAttribute("targetAction","update");
		request.setAttribute("blog",blog);
		return mapping.findForward("postForm");
	}
	
	
	public ActionForward update(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ArticleForm articleForm = (ArticleForm) form;
		Article article = new Article();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss",Locale.KOREA);
		
		article.setId(Integer.parseInt((String)request.getParameter("articleId")));
		article.setBlogOwnerId(articleForm.getBlogOwnerId());
		article.setTitle(articleForm.getTitle());
		article.setAuthor(new User(articleForm.getAuthor()));
		article.setCategory(articleForm.getCategory());
		article.setDate(sdf.parse(articleForm.getDate()));
		article.setContents(articleForm.getContents());
		article.setFormat(new ArticleFormat(articleForm.getFormat()));

		ArticleManager.instance().updateArticle(article, articleForm.getFiles());
		
		return new ActionForward("/blog.do?id=" + blogOwnerId,true);
	}
	
	public ActionForward destroy(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		int articleId = Integer.parseInt((String)request.getParameter("articleId"));
		
		ArticleManager.instance().destroyArticle(blogOwnerId, articleId);
		
		return new ActionForward("/blog.do?id=" + blogOwnerId,true);
	}
	
	private boolean checkOwner(String userId){
		return userId.equals(blogOwnerId) ? true : false;
	}
	
	private boolean checkAuthor(String userId, List authors){
		boolean result = false;
		for(int i=0; i<authors.size();i++)
			if(((BlogAuthor)authors.get(i)).getUserId().equals(userId)){
				result = true;
				break;
			}
		
		return result;
	}
}
