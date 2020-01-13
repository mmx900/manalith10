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
		String query =
				"INSERT INTO manalith_maingate_rss_source(title,webUrl,rssUrl,category,description) " +
						"VALUES(?,?,?,?,?) ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, source.getTitle());
			ps.setString(2, source.getWebUrl());
			ps.setString(3, source.getRssUrl());
			ps.setString(4, source.getCategory());
			ps.setString(5, source.getDescription());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	public void delete(int sourceId) {
		RSSItemDAO.instance().deleteItemsBySource(sourceId);

		String query = "DELETE FROM manalith_maingate_rss_source WHERE id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, sourceId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	public void update(RSSSource source) {
		String query = "UPDATE manalith_maingate_rss_source " +
				"SET title=?,webUrl=?,rssUrl=?,category=?,description=? " +
				"WHERE id=? ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, source.getTitle());
			ps.setString(2, source.getWebUrl());
			ps.setString(3, source.getRssUrl());
			ps.setString(4, source.getCategory());
			ps.setString(5, source.getDescription());
			ps.setInt(6, source.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	public RSSSource get(int sourceId) {
		RSSSource source = null;
		String query = "SELECT title, description, webURL, rssUrl, category " +
				"FROM manalith_maingate_rss_source " +
				"WHERE id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, sourceId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					source = new RSSSource();
					source.setId(rs.getInt("id"));
					source.setTitle(rs.getString("title"));
					source.setWebUrl(rs.getString("webUrl"));
					source.setRssUrl(rs.getString("rssUrl"));
					source.setCategory(rs.getString("category"));
					source.setDescription(rs.getString("description"));
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return source;
	}

	public List<RSSSource> getSources() {
		List<RSSSource> sources = new ArrayList<RSSSource>();
		String query = "SELECT id, title, description, webURL, rssUrl, category " +
				"FROM manalith_maingate_rss_source";

		try (PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				RSSSource source = new RSSSource();
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
		}

		return sources;
	}

	public List<String> getCategories() {
		List<String> categories = new ArrayList<String>();

		String query = "SELECT category FROM manalith_maingate_rss_source GROUP BY category";

		try (PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return categories;
	}
}
