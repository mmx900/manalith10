package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.db.HibernateUtil;
import org.manalith.resource.Article;
import org.manalith.resource.Blog;
import org.manalith.resource.Template;
import org.manalith.resource.User;

public class BlogDAO {

	private Connection conn;
	private static BlogDAO manager = null;
	private static Logger logger = LoggerFactory.getLogger(BlogDAO.class);

	private BlogDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}

	public static BlogDAO instance() {
		if (manager == null) {
			manager = new BlogDAO();
		}
		return manager;
	}

	public boolean isBlogOwner(User user) {
		return isBlogOwner(user.getId());
	}

	public boolean isBlogOwner(String userId) {
		boolean result = false;

		String sql = "SELECT title FROM manalith_blog WHERE owner=?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return result;
	}

	//FIXME : getAuthors() 이후 루핑하여 manalith_blog_author에 집어넣으며,
	// manalith_blog_category와 연결되어 있어야 한다.
	public void createBlog(Blog blog) {
		String query = "INSERT INTO manalith_blog(title, description, template, owner, pageSize, url, allowRSS) " +
				"VALUES(?,?,?,?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blog.getTitle());
			ps.setString(2, blog.getDescription());
			ps.setString(3, blog.getTemplate().toString());
			ps.setString(4, blog.getOwner().getId());
			ps.setInt(5, blog.getPageSize());
			ps.setString(6, blog.getUrl());
			ps.setBoolean(7, blog.getAllowRSS());

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	public Blog getBlog(String userId) {
		Blog blog = getBlogInfo(userId);
		if (blog != null) {
			ArticleDAO articleManager = ArticleDAO.instance();
			BlogAuthorDAO authorManager = BlogAuthorDAO.instance();
			BlogBookmarkDAO bookmarkManager = BlogBookmarkDAO.instance();
			blog.setCategories(articleManager.getCategories(userId));
			blog.setArticles(articleManager.getArticles(userId));
			blog.setAuthors(authorManager.getAuthors(userId));
			blog.setBookmarks(bookmarkManager.getBookmarks(userId));
		}
		return blog;
	}

	public Blog getBlog(String userId, String category) {
		Blog blog = getBlogInfo(userId);
		if (blog != null) {
			ArticleDAO articleManager = ArticleDAO.instance();
			BlogAuthorDAO authorManager = BlogAuthorDAO.instance();
			BlogBookmarkDAO bookmarkManager = BlogBookmarkDAO.instance();
			blog.setCategories(articleManager.getCategories(userId));
			blog.setArticles(articleManager.getArticles(userId, category));
			blog.setAuthors(authorManager.getAuthors(userId));
			blog.setBookmarks(bookmarkManager.getBookmarks(userId));
		}
		return blog;
	}

	public Blog getBlog(String userId, int page) {
		Blog blog = getBlogInfo(userId);
		if (blog != null) {
			ArticleDAO articleManager = ArticleDAO.instance();
			BlogAuthorDAO authorManager = BlogAuthorDAO.instance();
			BlogBookmarkDAO bookmarkManager = BlogBookmarkDAO.instance();
			blog.setCategories(articleManager.getCategories(userId));
			blog.setArticles(articleManager.getArticles(userId, page, blog.getTotalArticleCount(), blog.getPageSize()));
			blog.setAuthors(authorManager.getAuthors(userId));
			blog.setBookmarks(bookmarkManager.getBookmarks(userId));
		}
		return blog;
	}

	public Blog getBlog(String userId, Article article) {
		Blog blog = getBlogInfo(userId);
		if (blog != null) {
			ArticleDAO articleManager = ArticleDAO.instance();
			BlogAuthorDAO authorManager = BlogAuthorDAO.instance();
			BlogBookmarkDAO bookmarkManager = BlogBookmarkDAO.instance();
			blog.setCategories(articleManager.getCategories(userId));
			blog.setArticles(articleManager.getArticles(userId, article));
			blog.setAuthors(authorManager.getAuthors(userId));
			blog.setBookmarks(bookmarkManager.getBookmarks(userId));
		}
		return blog;
	}

	public Blog getBlog(String userId, java.util.Date date) {
		Blog blog = getBlogInfo(userId);
		if (blog != null) {
			ArticleDAO articleManager = ArticleDAO.instance();
			BlogAuthorDAO authorManager = BlogAuthorDAO.instance();
			BlogBookmarkDAO bookmarkManager = BlogBookmarkDAO.instance();
			blog.setCategories(articleManager.getCategories(userId));
			blog.setArticles(articleManager.getArticles(userId, date));
			blog.setAuthors(authorManager.getAuthors(userId));
			blog.setBookmarks(bookmarkManager.getBookmarks(userId));
		}
		return blog;
	}

	public Blog getBlog(String userId, User author) {
		Blog blog = getBlogInfo(userId);
		if (blog != null) {
			ArticleDAO articleManager = ArticleDAO.instance();
			BlogAuthorDAO authorManager = BlogAuthorDAO.instance();
			BlogBookmarkDAO bookmarkManager = BlogBookmarkDAO.instance();
			blog.setCategories(articleManager.getCategories(userId));
			blog.setArticles(articleManager.getArticles(userId, author));
			blog.setAuthors(authorManager.getAuthors(userId));
			blog.setBookmarks(bookmarkManager.getBookmarks(userId));
		}
		return blog;
	}

	/**
	 * 블로그의 정보를 가져옵니다.
	 *
	 * @param blogOwnerId
	 * @return 가져온 블로그의 정보
	 */
	public Blog getBlogInfo(String blogOwnerId) {
		Blog blog = null;
		String query = "SELECT title, description, template, created, totalArticleCount, pageSize, url, allowRSS, titleImage, backgroundImage " +
				"FROM manalith_blog " +
				"WHERE owner=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blogOwnerId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					blog = new Blog();
					blog.setTitle(rs.getString("title"));
					blog.setDescription(rs.getString("description"));
					blog.setTemplate(new Template(rs.getString("template")));
					blog.setOwner(new User(blogOwnerId));
					blog.setCreated(new Date(rs.getTimestamp("created").getTime()));
					blog.setTotalArticleCount(rs.getInt("totalArticleCount"));
					blog.setPageSize(rs.getInt("pageSize"));
					blog.setUrl(rs.getString("url"));
					blog.setAllowRSS(rs.getBoolean("allowRSS"));
					blog.setTitleImage(rs.getString("titleImage"));
					blog.setBackgroundImage(rs.getString("backgroundImage"));
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return blog;
	}

	/**
	 * 블로그의 정보를 변경합니다.
	 *
	 * @param blog 변경할 블로그
	 */
	public void updateBlogInfo(Blog blog) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"update Blog set title=:title, description=:desc, template=:template, pageSize=:size, url=:url, allowRSS=:rss where owner=:owner")
					.setParameter("title", blog.getTitle())
					.setParameter("desc", blog.getDescription())
					.setParameter("template", blog.getTemplate().toString())
					.setParameter("size", blog.getPageSize())
					.setParameter("url", blog.getUrl())
					.setParameter("rss", blog.getAllowRSS())
					.setParameter("owner", blog.getOwner().toString())
					.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 블로그를 삭제합니다.
	 *
	 * @param blogOwnerId 삭제할 블로그의 ID
	 */
	//FIXME : 관련 테이블 행의 제거 : manalith_blog_category, manalith_blog_author 등
	public void destroyBlog(String blogOwnerId) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"delete Article where blogOwnerId = :id")
					.setParameter("id", blogOwnerId)
					.executeUpdate();

			session.createQuery(
					"delete Blog where owner = :owner")
					.setParameter("owner", blogOwnerId)
					.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
