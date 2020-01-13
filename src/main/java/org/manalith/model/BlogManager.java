package org.manalith.model;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.struts.upload.FormFile;
import org.manalith.maingate.model.dao.MaingateManager;
import org.manalith.model.dao.ArticleCommentDAO;
import org.manalith.model.dao.ArticleDAO;
import org.manalith.model.dao.BlogBookmarkDAO;
import org.manalith.model.dao.BlogDAO;
import org.manalith.model.dao.FileDAO;
import org.manalith.model.dao.TemplateDAO;
import org.manalith.model.dao.TrackbackDAO;
import org.manalith.resource.Article;
import org.manalith.resource.ArticleComment;
import org.manalith.resource.ArticleTrackback;
import org.manalith.resource.Blog;
import org.manalith.resource.User;
import org.manalith.util.EscapeUtil;

public class BlogManager {
	private BlogManager() {
	}

	public static BlogManager instance() {
		return new BlogManager();
	}

	/**
	 * 새로운 블로그를 생성한다.
	 */
	public void createBlog(Blog blog, FormFile titleImage, FormFile backgroundImage) throws Exception {
		BlogDAO.instance().createBlog(blog);

		if (titleImage != null) {
			FileDAO.instance().setBlogTitleImage(blog.getOwner(), titleImage);
		}

		if (backgroundImage != null) {
			FileDAO.instance().setBlogBackgroundImage(blog.getOwner(), backgroundImage);
		}
	}

	/**
	 * 블로그 정보를 업데이트한다.
	 */
	public void updateBlog(Blog blog, FormFile titleImage, FormFile backgroundImage) throws Exception {
		BlogDAO.instance().updateBlogInfo(blog);

		if (titleImage != null && titleImage.getFileName().equals("")) {
			FileDAO.instance().setBlogTitleImage(blog.getOwner(), titleImage);
		}

		if (backgroundImage != null && !backgroundImage.getFileName().equals("")) {
			FileDAO.instance().setBlogBackgroundImage(blog.getOwner(), backgroundImage);
		}
	}

	/**
	 * 블로그를 보여준다.
	 */
	public Blog showBlog(String blogOwnerId, String category, String author, int page, int articleId, Date date) throws Exception {
		Blog blog = null;
		BlogDAO bm = BlogDAO.instance();

		if (category != null)
			blog = bm.getBlog(blogOwnerId, category);
		else if (author != null)
			blog = bm.getBlog(blogOwnerId, new User(author));
		else if (page != 0) {
			blog = bm.getBlog(blogOwnerId, page);
		} else if (articleId != 0) {
			Article article = new Article();
			article.setId(articleId);
			blog = bm.getBlog(blogOwnerId, article);
		} else if (date != null)
			blog = bm.getBlog(blogOwnerId, date);
		else
			blog = bm.getBlog(blogOwnerId, 1);

		if (blog != null) {
			List<Article> articles = blog.getArticles();

			for (Article article : articles) {
				if (article.getFormat().toString().equals("text"))
					article.setContents(EscapeUtil.escapeHtml(article.getContents()));

				article.setContents(EscapeUtil.convertLineFeed(article.getContents()));
				List<ArticleComment> comments = article.getComments();

				for (ArticleComment comment : comments) {
					comment.setContents(EscapeUtil.escapeHtml(comment.getContents()));
					comment.setContents(EscapeUtil.convertLineFeed(comment.getContents()));
				}

				article.setComments(comments);
			}

			blog.setArticles(articles);

			blog.setCalendar(ArticleDAO.instance().getArticleCalendar(blogOwnerId));
		}

		blog.setTemplate(
				TemplateDAO.instance().getTemplate(
						//FIXME 다른 방식으로 절대 경로 저장할 것
						MaingateManager.instance().getServletContext().getRealPath("/"),
						blog.getTemplate().toString()
				)
		);

		return blog;
	}

	/**
	 * RSS를 보여준다.
	 */
	public Blog showRSS(String blogOwnerId) throws Exception {
		Blog blog = BlogDAO.instance().getBlog(blogOwnerId);
		if (blog.getAllowRSS() == false) {
			String msg = "RSS 구독이 금지된 블로그입니다.";
			throw new ServletException(msg);
		}

		List<Article> articles = blog.getArticles();

		for (Article article : articles) {
			article.setContents(EscapeUtil.escapeXml(article.getContents()));
		}

		blog.setArticles(articles);

		return blog;
	}

	public List<Article> showRecentArticles(String blogOwnerId, int limitation) throws Exception {
		return ArticleDAO.instance().getRecentArticles(blogOwnerId, limitation);
	}

	public List<ArticleTrackback> showRecentTrackbacks(String blogOwnerId, int limitation) throws Exception {
		return TrackbackDAO.instance().getRecentTrackbacks(blogOwnerId, limitation);
	}

	public List<ArticleComment> showRecentComments(String blogOwnerId, int limitation) throws Exception {
		return ArticleCommentDAO.instance().getRecentComments(blogOwnerId, limitation);
	}

	public List<String> showBookmarkCategories(String blogOwnerId) throws Exception {
		return BlogBookmarkDAO.instance().getCategories(blogOwnerId);
	}
}
