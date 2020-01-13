package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.resource.ArticleComment;

public class ArticleCommentDAO {
	private Connection conn;
	private static ArticleCommentDAO manager = null;
	private static Logger logger = LoggerFactory.getLogger(ArticleCommentDAO.class);

	private ArticleCommentDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}

	public static ArticleCommentDAO instance() {
		if (manager == null) {
			manager = new ArticleCommentDAO();
		}
		return manager;
	}

	public void createComment(ArticleComment comment) {
		String query = "INSERT INTO manalith_blog_article_comment(blogOwnerId,articleId,name,password,homepage,contents,inetAddress) " +
				"VALUES(?,?,?,?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, comment.getBlogOwnerId());
			ps.setInt(2, comment.getArticleId());
			ps.setString(3, comment.getName());
			ps.setString(4, comment.getPassword());
			ps.setString(5, comment.getHomepage());
			ps.setString(6, comment.getContents());

			//FIXME inet형을 pgsql7.4대의 jdbc 드라이버처럼 string 으로 입력하도록
			PGobject inet = new PGobject();
			inet.setType("inet");
			inet.setValue(comment.getInetAddress());

			ps.setObject(7, inet);

			ps.executeUpdate();

			increaseCommentCount(comment.getArticleId());
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	public ArticleComment getComment(int id) {
		ArticleComment comment = null;

		String query = "SELECT id, blogOwnerId, name, date, homepage, contents, inetAddress " +
				"FROM manalith_blog_article_comment " +
				"WHERE id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					comment = new ArticleComment();
					comment.setId(rs.getInt("id"));
					comment.setBlogOwnerId(rs.getString("blogOwnerId"));
					comment.setName(rs.getString("name"));
					comment.setDate(new Date(rs.getTimestamp("date").getTime()));
					comment.setHomepage(rs.getString("homepage"));
					comment.setContents(rs.getString("contents"));
					comment.setInetAddress(rs.getString("inetAddress"));
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return comment;
	}

	public List<ArticleComment> getComments(int articleId) {
		List<ArticleComment> commentList = new ArrayList<ArticleComment>();

		String query = "SELECT id, blogOwnerId, name, date, homepage, contents, inetAddress " +
				"FROM manalith_blog_article_comment " +
				"WHERE articleId=? " +
				"ORDER BY id DESC";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ArticleComment comment = new ArticleComment();
					comment.setId(rs.getInt("id"));
					comment.setBlogOwnerId(rs.getString("blogOwnerId"));
					comment.setArticleId(articleId);
					comment.setName(rs.getString("name"));
					comment.setDate(new Date(rs.getTimestamp("date").getTime()));
					comment.setHomepage(rs.getString("homepage"));
					comment.setContents(rs.getString("contents"));
					comment.setInetAddress(rs.getString("inetAddress"));
					commentList.add(comment);
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return commentList;
	}

	public List<ArticleComment> getRecentComments(String blogOwnerId, int limitation) {
		List<ArticleComment> commentList = new ArrayList<ArticleComment>();

		String query = "SELECT id, articleId, name, date, homepage, contents, inetAddress " +
				"FROM manalith_blog_article_comment " +
				"WHERE blogOwnerId=? " +
				"ORDER BY id DESC LIMIT ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blogOwnerId);
			ps.setInt(2, limitation);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ArticleComment comment = new ArticleComment();
					comment.setId(rs.getInt("id"));
					comment.setBlogOwnerId(blogOwnerId);
					comment.setArticleId(rs.getInt("articleId"));
					comment.setName(rs.getString("name"));
					comment.setDate(new Date(rs.getTimestamp("date").getTime()));
					comment.setHomepage(rs.getString("homepage"));
					comment.setContents(rs.getString("contents"));
					comment.setInetAddress(rs.getString("inetAddress"));
					commentList.add(comment);
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return commentList;
	}

	public boolean isValidPassword(int id, String password) {
		boolean result = false;
		String query = "SELECT id FROM manalith_blog_article_comment " +
				"WHERE id = ? AND password = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.setString(2, password);

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

	public void destroyComment(int articleId, int id) {
		String query = "DELETE FROM manalith_blog_article_comment WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);

			ps.executeUpdate();

			decreaseCommentCount(articleId);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	public void destroyAllComments(int articleId) {
		String query = "DELETE FROM manalith_blog_article_comment WHERE articleId = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();

			restoreCommentCount(articleId);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	private void increaseCommentCount(int articleId) throws SQLException {
		String query = "UPDATE manalith_blog_article " +
				"SET totalCommentCount = totalCommentCount + 1 " +
				"WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void decreaseCommentCount(int articleId) throws SQLException {
		String query = "UPDATE manalith_blog_article " +
				"SET totalCommentCount = totalCommentCount - 1 " +
				"WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void restoreCommentCount(int articleId) throws SQLException {
		String query = "UPDATE manalith_blog_article " +
				"SET totalCommentCount = 0 " +
				"WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}
}
