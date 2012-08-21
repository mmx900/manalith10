/*
 * Created on 2005. 3. 22
 */
package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.manalith.db.ConnectionFactory;
import org.manalith.db.HibernateUtil;
import org.manalith.resource.Article;
import org.manalith.resource.Blog;
import org.manalith.resource.Template;
import org.manalith.resource.User;

/**
 * @author setzer
 */
public class BlogDAO {
	
	private Connection conn;
	private static BlogDAO manager = null;
	private static Logger logger = Logger.getLogger(BlogDAO.class);
	
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
		if(manager == null){
			manager = new BlogDAO();
		}
		return manager;
	}
	
	public boolean isBlogOwner(User user) {
		return isBlogOwner(user.getId());
	}
	
	public boolean isBlogOwner(String userId) {
		boolean result = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT title FROM manalith_blog WHERE owner=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}finally {
			if (rs != null) {
				try {
					rs.close(); 
				} catch (SQLException e) {
					logger.error(e.toString());
				}
				rs = null; 
			}
			if (pstmt != null) {
				try {
					pstmt.close(); 
				} catch (SQLException e) {
					logger.error(e.toString());
				}
				pstmt = null; 
			} 
		}
		
		return result;
	}
	
	//FIXME : getAuthors() 이후 루핑하여 manalith_blog_author에 집어넣으며,
	// manalith_blog_category와 연결되어 있어야 한다.
	public void createBlog(Blog blog) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO manalith_blog(title, description, template, owner, pageSize, url, allowRSS) ");
		sb.append("VALUES(?,?,?,?,?,?,?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, blog.getTitle());
			pstmt.setString(2, blog.getDescription());
			pstmt.setString(3, blog.getTemplate().toString());
			pstmt.setString(4, blog.getOwner().getId());
			pstmt.setInt(5, blog.getPageSize());
			pstmt.setString(6, blog.getUrl());
			pstmt.setBoolean(7, blog.getAllowRSS());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close(); 
				} catch (SQLException e) {
					logger.error(e.toString());
				}
				pstmt = null; 
			} 
		}
	}
	
	public Blog getBlog(String userId) {
		Blog blog = getBlogInfo(userId);
		if(blog != null){
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
		if(blog != null){
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
		if(blog != null){
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
		if(blog != null){
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
		if(blog != null){
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
		if(blog != null){
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
	 * @param blogOwnerId
	 * @return 가져온 블로그의 정보
	 */
	public Blog getBlogInfo(String blogOwnerId) {
		Blog blog = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT title, description, template, created, totalArticleCount, pageSize, url, allowRSS, titleImage, backgroundImage ");
		sb.append("FROM manalith_blog ");
		sb.append("WHERE owner=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, blogOwnerId);
			
			rs = pstmt.executeQuery();
			
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
		} catch (SQLException e) {
			logger.error(e.toString());
		}finally {
			if (rs != null) {
				try {
					rs.close(); 
				} catch (SQLException e) {
					logger.error(e.toString());
				}
				rs = null; 
			}
			if (pstmt != null) {
				try {
					pstmt.close(); 
				} catch (SQLException e) {
					logger.error(e.toString());
				}
				pstmt = null; 
			} 
		}
		
		return blog;
	}
	
	/**
	 * 블로그의 정보를 변경합니다.
	 * @param blog 변경할 블로그
	 */
	public void updateBlogInfo(Blog blog) {
		Session session = HibernateUtil.currentSession();
		
		try{
			session.createQuery(
			"update Blog set title = ?, description=?, template=?, pageSize=?, url=?, allowRSS=? where owner = ?")
			.setString(0, blog.getTitle())
			.setString(1, blog.getDescription())
			.setString(2, blog.getTemplate().toString())
			.setLong(3, blog.getPageSize())
			.setString(4, blog.getUrl())
			.setBoolean(5, blog.getAllowRSS())
			.setString(6, blog.getOwner().toString())
			.executeUpdate();
			
		}catch(HibernateException e){
			logger.error(e);
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	/**
	 * 블로그를 삭제합니다.
	 * @param blogOwnerId 삭제할 블로그의 ID
	 */
	//FIXME : 관련 테이블 행의 제거 : manalith_blog_category, manalith_blog_author 등
	public void destroyBlog(String blogOwnerId) {
		Session session = HibernateUtil.currentSession();
		
		try{
			session.createQuery(
			"delete Article where blogOwnerId = ?")
			.setString(0, blogOwnerId)
			.executeUpdate();
			
			session.createQuery(
			"delete Blog where owner = ?")
			.setString(0, blogOwnerId)
			.executeUpdate();
			
		}catch(HibernateException e){
			logger.error(e);
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
}