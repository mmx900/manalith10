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

	public static TrackbackDAO instance() {
		if (manager == null) {
			manager = new TrackbackDAO();
		}
		return manager;
	}

	public int sendPing(Blog blogInfo, Article article, String targetUrl, String encoding) throws HttpException, IOException {
		int result = -1;

		HttpClient client = new DefaultHttpClient();

		try {
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
			nvps.add(new BasicNameValuePair("title", article.getTitle()));
			nvps.add(new BasicNameValuePair("excerpt", article.getContents()));
			nvps.add(new BasicNameValuePair("url", blogInfo.getUrl()));
			nvps.add(new BasicNameValuePair("blog_name", blogInfo.getTitle()));

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
		} finally {
			client.getConnectionManager().shutdown();
		}

		return result;
	}


	public ArticleTrackback getTrackback(int trackbackId) {
		ArticleTrackback trackback = null;

		String query = "SELECT blogOwnerId, articleId, title, excerpt, url, blog_name, date, inetAddress " +
				"FROM manalith_blog_article_trackback " +
				"WHERE id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, trackbackId);

			try (ResultSet rs = ps.executeQuery()) {
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
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return trackback;
	}

	public List<ArticleTrackback> getTrackbacks(int articleId) {
		List<ArticleTrackback> trackbacks = new ArrayList<ArticleTrackback>();

		String query = "SELECT id, blogOwnerId, title, excerpt, url, blog_name, date, inetAddress " +
				"FROM manalith_blog_article_trackback " +
				"WHERE articleId=? " +
				"ORDER BY id DESC";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ArticleTrackback trackback = new ArticleTrackback();
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
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return trackbacks;
	}

	public List<ArticleTrackback> getRecentTrackbacks(String blogOwnerId, int limitation) {
		List<ArticleTrackback> trackbacks = new ArrayList<ArticleTrackback>();

		String query = "SELECT id, articleId, title, excerpt, url, blog_name, date, inetAddress " +
				"FROM manalith_blog_article_trackback " +
				"WHERE blogOwnerId=? " +
				"ORDER BY id DESC LIMIT ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blogOwnerId);
			ps.setInt(2, limitation);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ArticleTrackback trackback = new ArticleTrackback();
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
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return trackbacks;
	}

	public int recievePing(ArticleTrackback trackback) {
		int result = 0;

		if (StringUtils.isEmpty(trackback.getBlogOwnerId())
				|| trackback.getArticleId() == 0
				|| StringUtils.isEmpty(trackback.getUrl())
				|| StringUtils.isEmpty(trackback.getInetAddress())) {
			result = 1;
		} else {
			this.addTrackback(trackback);
		}

		return result;
	}

	private void addTrackback(ArticleTrackback trackback) {
		String query = "INSERT INTO manalith_blog_article_trackback(blogOwnerId, articleId, title, excerpt, url, blog_name, inetAddress) " +
				"VALUES(?,?,?,?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, trackback.getBlogOwnerId());
			ps.setInt(2, trackback.getArticleId());
			ps.setString(3, trackback.getTitle());
			ps.setString(4, trackback.getExcerpt());
			ps.setString(5, trackback.getUrl());
			ps.setString(6, trackback.getBlog_name());

			//FIXME inet형을 pgsql7.4대의 jdbc 드라이버처럼 string 으로 입력하도록
			PGobject inet = new PGobject();
			inet.setType("inet");
			inet.setValue(trackback.getInetAddress());

			ps.setObject(7, inet);

			ps.executeUpdate();

			this.increaseTrackbackCount(trackback.getArticleId());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteTrackback(int articleId, int trackbackId) {
		String query = "DELETE FROM manalith_blog_article_trackback WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, trackbackId);

			ps.executeUpdate();

			this.decreaseTrackbackCount(articleId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteTrackbacks(int articleId) {
		String query = "DELETE FROM manalith_blog_article_trackback " +
				"WHERE articleId = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();

			this.decreaseTrackbackCount(articleId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}


	private void increaseTrackbackCount(int articleId) throws SQLException {
		String query = "UPDATE manalith_blog_article " +
				"SET totalTrackbackCount = totalTrackbackCount + 1 " +
				"WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void decreaseTrackbackCount(int articleId) throws SQLException {
		String query = "UPDATE manalith_blog_article " +
				"SET totalTrackbackCount = totalTrackbackCount - 1 " +
				"WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void restoreTrackbackCount(int articleId) throws SQLException {
		String query = "UPDATE manalith_blog_article " +
				"SET totalTrackbackCount = 0 " +
				"WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}
}
