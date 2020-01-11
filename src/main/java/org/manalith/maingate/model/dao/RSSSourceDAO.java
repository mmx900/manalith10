package org.manalith.maingate.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.maingate.resource.RSSSource;

public class RSSSourceDAO {
	private Connection conn;
	private static RSSSourceDAO manager;
	private static Logger logger = LoggerFactory.getLogger(RSSSourceDAO.class);

	private RSSSourceDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}

	public static RSSSourceDAO instance() {
		if (manager == null) {
			manager = new RSSSourceDAO();
		}
		return manager;
	}

	public void add(RSSSource source) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO manalith_maingate_rss_source(title,webUrl,rssUrl,category,description) ");
		sb.append("VALUES(?,?,?,?,?) ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, source.getTitle());
			pstmt.setString(2, source.getWebUrl());
			pstmt.setString(3, source.getRssUrl());
			pstmt.setString(4, source.getCategory());
			pstmt.setString(5, source.getDescription());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (Exception e) {
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

	public void delete(int sourceId) {
		RSSItemDAO.instance().deleteItemsBySource(sourceId);

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM manalith_maingate_rss_source ");
		sb.append("WHERE id=? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, sourceId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (Exception e) {
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

	public void update(RSSSource source) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE manalith_maingate_rss_source ");
		sb.append("SET title=?,webUrl=?,rssUrl=?,category=?,description=? ");
		sb.append("WHERE id=? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, source.getTitle());
			pstmt.setString(2, source.getWebUrl());
			pstmt.setString(3, source.getRssUrl());
			pstmt.setString(4, source.getCategory());
			pstmt.setString(5, source.getDescription());
			pstmt.setInt(6, source.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (Exception e) {
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

	public RSSSource get(int sourceId) {
		PreparedStatement pstmt = null;
		RSSSource source = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		sb.append("SELECT title, description, webURL, rssUrl, category ");
		sb.append("FROM manalith_maingate_rss_source ");
		sb.append("WHERE id=?");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, sourceId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				source = new RSSSource();
				source.setId(rs.getInt("id"));
				source.setTitle(rs.getString("title"));
				source.setWebUrl(rs.getString("webUrl"));
				source.setRssUrl(rs.getString("rssUrl"));
				source.setCategory(rs.getString("category"));
				source.setDescription(rs.getString("description"));
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
		return source;
	}

	public List<RSSSource> getSources() {
		PreparedStatement pstmt = null;
		List<RSSSource> sources = new ArrayList<RSSSource>();
		RSSSource source = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		sb.append("SELECT id, title, description, webURL, rssUrl, category ");
		sb.append("FROM manalith_maingate_rss_source");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				source = new RSSSource();
				source.setId(rs.getInt("id"));
				source.setTitle(rs.getString("title"));
				source.setWebUrl(rs.getString("webUrl"));
				source.setRssUrl(rs.getString("rssUrl"));
				source.setCategory(rs.getString("category"));
				source.setDescription(rs.getString("description"));
				sources.add(source);
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
		return sources;
	}

	public List<String> getCategories() {
		List<String> categories = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT category ");
		sb.append("FROM manalith_maingate_rss_source ");
		sb.append("GROUP BY category");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				categories.add(rs.getString("category"));
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
		return categories;
	}
}
