/*
 * Created on 2005. 3. 31
 */
package org.manalith.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.resource.Article;
import org.manalith.resource.ArticleTrackback;
import org.manalith.resource.Blog;


/**
 * @author setzer
 */
public class TrackbackDAO {
	private Connection conn;
	private static TrackbackDAO manager = null;
	private static Logger logger = LoggerFactory.getLogger(TrackbackDAO.class);
	
	private TrackbackDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage()); 
			logger.error("SQLState: " + ex.getSQLState()); 
			logger.error("VendorError: " + ex.getErrorCode()); 
		}
	}
	
	public static TrackbackDAO instance(){
		if(manager == null){
			manager = new TrackbackDAO();
		}
		return manager;
	}
	
	public int sendPing(Blog blogInfo, Article article, String targetUrl, String encoding) throws HttpException, IOException{
		int result = -1;
		
		HttpClient client = new DefaultHttpClient();
		
		try{
		HttpPost post = new HttpPost(targetUrl);
		
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 8000);
		
		post.setHeader(
				"Content-type",
				"application/x-www-form-urlencoded; charset=" + encoding);
		//post.setParameter("title",article.getTitle());
		//post.setParameter("excerpt",article.getContents());
		//post.setParameter("url",blogInfo.getUrl());
		//post.setParameter("blog_name",blogInfo.getTitle());
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("title",article.getTitle()));
		nvps.add(new BasicNameValuePair("excerpt",article.getContents()));
		nvps.add(new BasicNameValuePair("url",blogInfo.getUrl()));
		nvps.add(new BasicNameValuePair("blog_name",blogInfo.getTitle()));
		
		post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		
		HttpResponse response = client.execute(post);
		HttpEntity entity = post.getEntity();
		
		//logger.error(result + "");
		logger.error(targetUrl);
		logger.error(encoding);
		logger.error(article.getTitle());
		logger.error(article.getContents());
		logger.error(blogInfo.getUrl());
		logger.error(blogInfo.getTitle());
		//logger.error(post.getRequestBodyAsString());
		//logger.error(post.getResponseBodyAsString());
		logger.error(response.getStatusLine().toString());
		EntityUtils.consume(entity);
		result = 0;
		}finally{
		client.getConnectionManager().shutdown();
		}
		
		return result;
	}
	
	
	public ArticleTrackback getTrackback(int trackbackId){
		ArticleTrackback trackback = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT blogOwnerId, articleId, title, excerpt, url, blog_name, date, inetAddress ");
		sb.append("FROM manalith_blog_article_trackback ");
		sb.append("WHERE id=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, trackbackId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				trackback = new ArticleTrackback();
				trackback.setId(trackbackId);
				trackback.setBlogOwnerId(rs.getString("blogOwnerId"));
				trackback.setArticleId(rs.getInt("articleId"));
				trackback.setTitle(rs.getString("title"));
				trackback.setExcerpt(rs.getString("excerpt"));
				trackback.setUrl(rs.getString("url"));
				trackback.setBlog_name(rs.getString("blog_name"));
				trackback.setDate(new Date(rs.getTimestamp("date").getTime()));
				trackback.setInetAddress(rs.getString("inetAddress"));
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
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
		
		return trackback;
	}
	
	public List<ArticleTrackback> getTrackbacks(int articleId){
		ArticleTrackback trackback = null;
		List<ArticleTrackback> trackbacks = new ArrayList<ArticleTrackback>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT id, blogOwnerId, title, excerpt, url, blog_name, date, inetAddress ");
		sb.append("FROM manalith_blog_article_trackback ");
		sb.append("WHERE articleId=? ");
		sb.append("ORDER BY id DESC");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, articleId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				trackback = new ArticleTrackback();
				trackback.setId(rs.getInt("id"));
				trackback.setBlogOwnerId(rs.getString("blogOwnerId"));
				trackback.setArticleId(articleId);
				trackback.setTitle(rs.getString("title"));
				trackback.setExcerpt(rs.getString("excerpt"));
				trackback.setUrl(rs.getString("url"));
				trackback.setBlog_name(rs.getString("blog_name"));
				trackback.setDate(new Date(rs.getTimestamp("date").getTime()));
				trackback.setInetAddress(rs.getString("inetAddress"));
				trackbacks.add(trackback);
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
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
		
		return trackbacks;
	}
	
	public List<ArticleTrackback> getRecentTrackbacks(String blogOwnerId, int limitation){
		ArticleTrackback trackback = null;
		List<ArticleTrackback> trackbacks = new ArrayList<ArticleTrackback>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT id, articleId, title, excerpt, url, blog_name, date, inetAddress ");
		sb.append("FROM manalith_blog_article_trackback ");
		sb.append("WHERE blogOwnerId=? ");
		sb.append("ORDER BY id DESC LIMIT ?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, blogOwnerId);
			pstmt.setInt(2, limitation);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				trackback = new ArticleTrackback();
				trackback.setId(rs.getInt("id"));
				trackback.setBlogOwnerId(blogOwnerId);
				trackback.setArticleId(rs.getInt("articleId"));
				trackback.setTitle(rs.getString("title"));
				trackback.setExcerpt(rs.getString("excerpt"));
				trackback.setUrl(rs.getString("url"));
				trackback.setBlog_name(rs.getString("blog_name"));
				trackback.setDate(new Date(rs.getTimestamp("date").getTime()));
				trackback.setInetAddress(rs.getString("inetAddress"));
				trackbacks.add(trackback);
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
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
		
		return trackbacks;
	}
	
	public int recievePing(ArticleTrackback trackback){
		int result = 0;
		
		if(StringUtils.isEmpty(trackback.getBlogOwnerId())
				|| trackback.getArticleId() == 0
				|| StringUtils.isEmpty(trackback.getUrl())
				|| StringUtils.isEmpty(trackback.getInetAddress())){
			result = 1;
		}else{
			this.addTrackback(trackback);
		}
		
		return result;
	}
	
	private void addTrackback(ArticleTrackback trackback){
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO manalith_blog_article_trackback(blogOwnerId, articleId, title, excerpt, url, blog_name, inetAddress) ");
		sb.append("VALUES(?,?,?,?,?,?,?)");
		
		try{
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1,trackback.getBlogOwnerId());
			pstmt.setInt(2,trackback.getArticleId());
			pstmt.setString(3,trackback.getTitle());
			pstmt.setString(4,trackback.getExcerpt());
			pstmt.setString(5,trackback.getUrl());
			pstmt.setString(6,trackback.getBlog_name());
			
			//FIXME inet형을 pgsql7.4대의 jdbc 드라이버처럼 string 으로 입력하도록
			PGobject inet = new PGobject();
			inet.setType("inet");
			inet.setValue(trackback.getInetAddress());
			
			pstmt.setObject(7,inet);
			
			pstmt.executeUpdate();
			
			this.increaseTrackbackCount(trackback.getArticleId());
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			if (pstmt != null) {
				try {
					pstmt.close(); 
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null; 
			} 
		}
	}
	
	public void deleteTrackback(int articleId, int trackbackId){
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM manalith_blog_article_trackback ");
		sb.append("WHERE id = ?");
		
		try{
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1,trackbackId);
			
			pstmt.executeUpdate();
			
			this.decreaseTrackbackCount(articleId);
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			if (pstmt != null) {
				try {
					pstmt.close(); 
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null; 
			} 
		}
	}
	
	public void deleteTrackbacks(int articleId){
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM manalith_blog_article_trackback ");
		sb.append("WHERE articleId = ?");
		
		try{
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1,articleId);
			
			pstmt.executeUpdate();
			
			this.decreaseTrackbackCount(articleId);
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}finally{
			if (pstmt != null) {
				try {
					pstmt.close(); 
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null; 
			} 
		}
	}
	
	
	private void increaseTrackbackCount(int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_blog_article ");
		sb.append("SET totalTrackbackCount = totalTrackbackCount + 1 ");
		sb.append("WHERE id = ?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, articleId);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
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
	
	private void decreaseTrackbackCount(int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_blog_article ");
		sb.append("SET totalTrackbackCount = totalTrackbackCount - 1 ");
		sb.append("WHERE id = ?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, articleId);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
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
	
	private void restoreTrackbackCount(int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_blog_article ");
		sb.append("SET totalTrackbackCount = 0 ");
		sb.append("WHERE id = ?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, articleId);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
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
}
