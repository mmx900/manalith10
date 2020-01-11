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
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO manalith_blog_article_comment(blogOwnerId,articleId,name,password,homepage,contents,inetAddress) ");
		sb.append("VALUES(?,?,?,?,?,?,?)");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, comment.getBlogOwnerId());
			pstmt.setInt(2, comment.getArticleId());
			pstmt.setString(3, comment.getName());
			pstmt.setString(4, comment.getPassword());
			pstmt.setString(5, comment.getHomepage());
			pstmt.setString(6, comment.getContents());


			//FIXME inet형을 pgsql7.4대의 jdbc 드라이버처럼 string 으로 입력하도록
			PGobject inet = new PGobject();
			inet.setType("inet");
			inet.setValue(comment.getInetAddress());

			pstmt.setObject(7, inet);

			pstmt.executeUpdate();

			increaseCommentCount(comment.getArticleId());
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassCastException e) {
			logger.error(e.toString());
		} finally {
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

	public ArticleComment getComment(int id) {
		ArticleComment comment = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT id, blogOwnerId, name, date, homepage, contents, inetAddress ");
		sb.append("FROM manalith_blog_article_comment ");
		sb.append("WHERE id=?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

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

		return comment;
	}

	public List<ArticleComment> getComments(int articleId) {
		ArticleComment comment = null;
		List<ArticleComment> commentList = new ArrayList<ArticleComment>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT id, blogOwnerId, name, date, homepage, contents, inetAddress ");
		sb.append("FROM manalith_blog_article_comment ");
		sb.append("WHERE articleId=? ");
		sb.append("ORDER BY id DESC");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, articleId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				comment = new ArticleComment();
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

		return commentList;
	}

	public List<ArticleComment> getRecentComments(String blogOwnerId, int limitation) {
		ArticleComment comment = null;
		List<ArticleComment> commentList = new ArrayList<ArticleComment>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT id, articleId, name, date, homepage, contents, inetAddress ");
		sb.append("FROM manalith_blog_article_comment ");
		sb.append("WHERE blogOwnerId=? ");
		sb.append("ORDER BY id DESC LIMIT ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, blogOwnerId);
			pstmt.setInt(2, limitation);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				comment = new ArticleComment();
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

		return commentList;
	}

	public boolean isValidPassword(int id, String password) {
		boolean result = false;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id FROM manalith_blog_article_comment ");
		sb.append("WHERE id = ? AND password = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, id);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
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
		return result;
	}

	public void destroyComment(int articleId, int id) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM manalith_blog_article_comment ");
		sb.append("WHERE id = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, id);

			pstmt.executeUpdate();

			decreaseCommentCount(articleId);
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally {
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

	public void destroyAllComments(int articleId) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM manalith_blog_article_comment ");
		sb.append("WHERE articleId = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, articleId);

			pstmt.executeUpdate();

			restoreCommentCount(articleId);
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally {
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

	private void increaseCommentCount(int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_blog_article ");
		sb.append("SET totalCommentCount = totalCommentCount + 1 ");
		sb.append("WHERE id = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, articleId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
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

	private void decreaseCommentCount(int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_blog_article ");
		sb.append("SET totalCommentCount = totalCommentCount - 1 ");
		sb.append("WHERE id = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, articleId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
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

	private void restoreCommentCount(int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_blog_article ");
		sb.append("SET totalCommentCount = 0 ");
		sb.append("WHERE id = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, articleId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
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
