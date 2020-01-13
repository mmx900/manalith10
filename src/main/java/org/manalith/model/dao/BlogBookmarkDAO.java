package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.resource.BlogBookmark;

public class BlogBookmarkDAO {
	private Connection conn;
	private static BlogBookmarkDAO manager = null;
	private static Logger logger = LoggerFactory.getLogger(BlogBookmarkDAO.class);

	private BlogBookmarkDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}

	public static BlogBookmarkDAO instance() {
		if (manager == null) {
			manager = new BlogBookmarkDAO();
		}

		return manager;
	}

	/**
	 * 블로그의 북마크를 생성합니다.
	 *
	 * @param b 생성할 북마크
	 */
	public void createBookmark(BlogBookmark b) {
		String query = "INSERT INTO manalith_blog_bookmark(blogOwnerId,category,title,url,rssUrl,imageUrl,description) " +
				"VALUES(?,?,?,?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, b.getBlogOwnerId());
			ps.setString(2, b.getCategory());
			ps.setString(3, b.getTitle());
			ps.setString(4, b.getUrl());
			ps.setString(5, b.getRssUrl());
			ps.setString(6, b.getImageUrl());
			ps.setString(7, b.getDescription());

			ps.executeUpdate();
		} catch (SQLException | ClassCastException e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 블로그의 북마크를 변경합니다.
	 *
	 * @param b 변경할 북마크
	 */
	public void updateBookmark(BlogBookmark b) {
		String query = "UPDATE manalith_blog_bookmark " +
				"SET category=?, title=?, url=?, rssUrl=?, imageUrl=?, description=? " +
				"WHERE id=? ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, b.getCategory());
			ps.setString(2, b.getTitle());
			ps.setString(3, b.getUrl());
			ps.setString(4, b.getRssUrl());
			ps.setString(5, b.getImageUrl());
			ps.setString(6, b.getDescription());
			ps.setInt(7, b.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 블로그의 북마크를 삭제합니다.
	 *
	 * @param id 삭제할 북마크의 ID
	 */
	public void destroyBookmark(int id) {
		String query = "DELETE FROM manalith_blog_bookmark WHERE id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 특정 블로그의 모든 북마크를 가져옵니다.
	 *
	 * @param blogOwnerId 블로그의 ID
	 * @return 북마크의 목록
	 */
	public List<BlogBookmark> getBookmarks(String blogOwnerId) {
		List<BlogBookmark> bookmarks = new ArrayList<BlogBookmark>();

		String query = "SELECT id,category,title,url,rssUrl,imageUrl,description " +
				"FROM manalith_blog_bookmark " +
				"WHERE blogOwnerId=? " +
				"ORDER BY category ASC ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blogOwnerId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BlogBookmark b = new BlogBookmark();
					b.setId(rs.getInt("id"));
					b.setBlogOwnerId(blogOwnerId);
					b.setCategory(rs.getString("category"));
					b.setTitle(rs.getString("title"));
					b.setUrl(rs.getString("url"));
					b.setRssUrl(rs.getString("rssUrl"));
					b.setImageUrl(rs.getString("imageUrl"));
					b.setDescription(rs.getString("description"));

					bookmarks.add(b);
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return bookmarks;
	}

	/**
	 * 특정 블로그의 이미 존재하는 북마크들로부터 모든 카테고리를 가져옵니다.
	 *
	 * @param blogOwnerId 블로그의 ID
	 * @return 가져온 북마크의 목록
	 */
	public List<String> getCategories(String blogOwnerId) {
		List<String> categories = new ArrayList<String>();

		String query = "SELECT category " +
				"FROM manalith_blog_bookmark " +
				"WHERE blogOwnerId=? " +
				"GROUP BY category";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blogOwnerId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					categories.add(rs.getString("category"));
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}

		return categories;
	}
}
