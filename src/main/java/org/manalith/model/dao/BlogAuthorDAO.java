/*
 * Created on 2005. 3. 22
 */
package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.manalith.db.ConnectionFactory;
import org.manalith.db.HibernateUtil;
import org.manalith.exception.ExistAuthorException;
import org.manalith.resource.BlogAuthor;
import org.manalith.resource.User;


/**
 * @author setzer
 */
public class BlogAuthorDAO {
	private Connection conn;
	private static BlogAuthorDAO manager = null;
	private static Logger logger = Logger.getLogger(BlogAuthorDAO.class);
	
	private BlogAuthorDAO(){
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage()); 
			logger.error("SQLState: " + ex.getSQLState()); 
			logger.error("VendorError: " + ex.getErrorCode()); 
		}
	}
	
	public static BlogAuthorDAO instance(){
		if(manager == null){
			manager = new BlogAuthorDAO();
		}
		return manager;
	}
	
	public void addAuthor(BlogAuthor user) throws ExistAuthorException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT userId FROM manalith_blog_author ");
		sb1.append("WHERE blogOwnerId = ? AND userId = ?");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("INSERT INTO manalith_blog_author(blogOwnerId,userId) ");
		sb2.append("VALUES(?,?)");
		
		try{
			pstmt = conn.prepareStatement(sb1.toString());
			pstmt.setString(1,user.getBlogOwnerId());
			pstmt.setString(2,user.getUserId());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				throw new ExistAuthorException();
			}
			
			pstmt = conn.prepareStatement(sb2.toString());
			pstmt.setString(1,user.getBlogOwnerId());
			pstmt.setString(2,user.getUserId());
			
			pstmt.executeUpdate();
		}catch(SQLException e){
			logger.error(e.toString());
		}catch(ClassCastException e){
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
	}
	
	public void deleteAuthor(BlogAuthor user){
		Session session = HibernateUtil.currentSession();
		
		try{
			session.createQuery(
			"delete BlogAuthor where blogOwnerId = ? and userId = ?")
			.setString(0, user.getBlogOwnerId())
			.setString(1, user.getUserId())
			.executeUpdate();
			
		}catch(HibernateException e){
			logger.error(e);
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	public List getAuthors(String blogOwnerId){
		/*
		 Session session = HibernateUtil.currentSession();
		 List authors = null;
		 
		 try{
		 authors = session.createQuery(
		 "from User user, Author author where author.blogOwnerId = ? and author.userId = user.id")
		 .setString(0, blogOwnerId)
		 .list();
		 
		 }catch(HibernateException e){
		 logger.error(e);
		 }finally{
		 HibernateUtil.closeSession();
		 }
		 
		 return authors;
		 */
		
		List authors = null;
		User user = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.userId,b.name,b.email FROM manalith_blog_author a ");
		sb.append("INNER JOIN manalith_member b ON a.userId = b.id ");
		sb.append("WHERE a.blogOwnerId=? ");
		
		try{
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1,blogOwnerId);
			
			rs = pstmt.executeQuery();
			
			authors = new ArrayList();
			
			while(rs.next()){
				user = new User();
				user.setId(rs.getString("userId"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				authors.add(user);
			}
			
		}catch(SQLException e){
			logger.error(e.toString());
		}catch(ClassCastException e){
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
		return authors;
	}
	
	public void deleteAllAuthors(String blogOwnerId){
		Session session = HibernateUtil.currentSession();
		
		try{
			session.createQuery(
			"delete BlogAuthor where blogOwnerId = ?")
			.setString(0, blogOwnerId)
			.executeUpdate();
			
		}catch(HibernateException e){
			logger.error(e);
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
}
