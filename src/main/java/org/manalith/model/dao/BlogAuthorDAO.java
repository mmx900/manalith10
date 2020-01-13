package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.db.HibernateUtil;
import org.manalith.exception.ExistAuthorException;
import org.manalith.resource.BlogAuthor;
import org.manalith.resource.User;

public class BlogAuthorDAO {
	private Connection conn;
	private static BlogAuthorDAO manager = null;
	private static Logger logger = LoggerFactory.getLogger(BlogAuthorDAO.class);

	private BlogAuthorDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}

	public static BlogAuthorDAO instance() {
		if (manager == null) {
			manager = new BlogAuthorDAO();
		}
		return manager;
	}

	public void addAuthor(BlogAuthor user) throws ExistAuthorException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query1 = "SELECT userId FROM manalith_blog_author " +
				"WHERE blogOwnerId = ? AND userId = ?";

		String query2 = "INSERT INTO manalith_blog_author(blogOwnerId,userId) " +
				"VALUES(?,?)";

		try {
			pstmt = conn.prepareStatement(query1);
			pstmt.setString(1, user.getBlogOwnerId());
			pstmt.setString(2, user.getUserId());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				throw new ExistAuthorException();
			}

			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, user.getBlogOwnerId());
			pstmt.setString(2, user.getUserId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassCastException e) {
			logger.error(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.toString());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.toString());
				}
			}
		}
	}

	public void deleteAuthor(BlogAuthor user) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"delete BlogAuthor where blogOwnerId = :blogOwnerId and userId = :userId")
					.setParameter("blogOwnerId", user.getBlogOwnerId())
					.setParameter("userId", user.getUserId())
					.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List getAuthors(String blogOwnerId) {
		/*
		 Session session = HibernateUtil.currentSession();
		 List authors = null;
		 
		 try{
		 authors = session.createQuery(
		 "from User user, Author author where author.blogOwnerId = ? and author.userId = user.id")
		 .setString(0, blogOwnerId)
		 .list();
		 
		 }catch(HibernateException e){
		 logger.error(e.getMessage(), e);
		 }finally{
		 HibernateUtil.closeSession();
		 }
		 
		 return authors;
		 */

		List authors = null;
		User user = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "SELECT a.userId,b.name,b.email FROM manalith_blog_author a " +
				"INNER JOIN manalith_member b ON a.userId = b.id " +
				"WHERE a.blogOwnerId=? ";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, blogOwnerId);

			rs = pstmt.executeQuery();

			authors = new ArrayList();

			while (rs.next()) {
				user = new User();
				user.setId(rs.getString("userId"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				authors.add(user);
			}

		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassCastException e) {
			logger.error(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.toString());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.toString());
				}
			}
		}

		return authors;
	}

	public void deleteAllAuthors(String blogOwnerId) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"delete BlogAuthor where blogOwnerId = :id")
					.setParameter("id", blogOwnerId)
					.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
