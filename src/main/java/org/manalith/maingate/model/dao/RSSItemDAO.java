package org.manalith.maingate.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.maingate.resource.RSSSource;
import org.manalith.maingate.resource.RSSSourceItem;
import org.manalith.util.rss.RSSChannelItemObject;
import org.manalith.util.rss.RSSChannelObject;
import org.manalith.util.rss.RSSObject;
import org.manalith.util.rss.parser.RSSDOMParser;

public class RSSItemDAO {
	private Connection conn;
	private static RSSItemDAO manager;
	private static Logger logger = LoggerFactory.getLogger(RSSItemDAO.class);

	private RSSItemDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}

	public static RSSItemDAO instance() {
		if (manager == null) {
			manager = new RSSItemDAO();
		}
		return manager;
	}

	public List<RSSSourceItem> getItems() {
		List<RSSSourceItem> items = new ArrayList<RSSSourceItem>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RSSSourceItem item = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, source, rssVersion, rssCharset, channelTitle, channelDescription, ");
		sb.append("channelLanguage, channelCopyright, channelGenerator, channelPubDate, channelLink, ");
		sb.append("itemTitle, itemDescription, itemCategory, itemPubDate, itemLink ");
		sb.append("FROM manalith_maingate_rss_item ");
		sb.append("ORDER BY itemPubDate DESC");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new RSSSourceItem();
				item.setId(rs.getInt("id"));
				item.setSource(rs.getInt("source"));
				item.setRssVersion(rs.getString("rssVersion"));
				item.setRssCharset(rs.getString("rssCharset"));
				item.setChannelTitle(rs.getString("channelTitle"));
				item.setChannelDescription(rs.getString("channelDescription"));
				item.setChannelLanguage(rs.getString("channelLanguage"));
				item.setChannelCopyright(rs.getString("channelCopyright"));
				item.setChannelGenerator(rs.getString("channelGenerator"));
				if (rs.getTimestamp("channelPubDate") != null)
					item.setChannelPubDate(new Date(rs.getTimestamp("channelPubDate").getTime()));
				item.setChannelLink(rs.getString("channelLink"));
				item.setItemTitle(rs.getString("itemTitle"));
				item.setItemDescription(rs.getString("itemDescription"));
				item.setItemCategory(rs.getString("itemCategory"));
				if (rs.getTimestamp("itemPubDate") != null)
					item.setItemPubDate(new Date(rs.getTimestamp("itemPubDate").getTime()));
				item.setItemLink(rs.getString("itemLink"));
				items.add(item);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
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

		return items;
	}

	public List<RSSSourceItem> getItems(int limitation) {
		List<RSSSourceItem> items = new ArrayList<RSSSourceItem>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RSSSourceItem item = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, source, rssVersion, rssCharset, channelTitle, channelDescription, ");
		sb.append("channelLanguage, channelCopyright, channelGenerator, channelPubDate, channelLink, ");
		sb.append("itemTitle, itemDescription, itemCategory, itemPubDate, itemLink ");
		sb.append("FROM manalith_maingate_rss_item ");
		sb.append("ORDER BY itemPubDate DESC LIMIT ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, limitation);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new RSSSourceItem();
				item.setId(rs.getInt("id"));
				item.setSource(rs.getInt("source"));
				item.setRssVersion(rs.getString("rssVersion"));
				item.setRssCharset(rs.getString("rssCharset"));
				item.setChannelTitle(rs.getString("channelTitle"));
				item.setChannelDescription(rs.getString("channelDescription"));
				item.setChannelLanguage(rs.getString("channelLanguage"));
				item.setChannelCopyright(rs.getString("channelCopyright"));
				item.setChannelGenerator(rs.getString("channelGenerator"));
				if (rs.getTimestamp("channelPubDate") != null)
					item.setChannelPubDate(new Date(rs.getTimestamp("channelPubDate").getTime()));
				item.setChannelLink(rs.getString("channelLink"));
				item.setItemTitle(rs.getString("itemTitle"));
				item.setItemDescription(rs.getString("itemDescription"));
				item.setItemCategory(rs.getString("itemCategory"));
				if (rs.getTimestamp("itemPubDate") != null)
					item.setItemPubDate(new Date(rs.getTimestamp("itemPubDate").getTime()));
				item.setItemLink(rs.getString("itemLink"));
				items.add(item);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
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

		return items;
	}

	public void storeFromSources() {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO manalith_maingate_rss_item(");
		sb.append("source, rssVersion, rssCharset, channelTitle, channelDescription, ");
		sb.append("channelLanguage, channelCopyright, channelGenerator, channelPubDate, channelLink, ");
		sb.append("itemTitle, itemDescription, itemCategory, itemPubDate, itemLink");
		sb.append(") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		List<RSSSource> sources = RSSSourceDAO.instance().getSources();
		List<RSSChannelObject> channels = null;
		List<RSSChannelItemObject> items = null;

		RSSObject rss = null;
		for (RSSSource source : sources) {
			rss = RSSDOMParser.instance().parse(source.getRssUrl());
			try {
				pstmt = conn.prepareStatement(sb.toString());

				channels = rss.getChannels();
				for (RSSChannelObject channel : channels) {
					items = channel.getItems();
					for (RSSChannelItemObject item : items) {
						pstmt.setInt(1, source.getId());
						pstmt.setString(2, rss.getVersion());
						pstmt.setString(3, rss.getCharset());

						pstmt.setString(4, channel.getTitle());
						pstmt.setString(5, channel.getDescription());
						pstmt.setString(6, channel.getLanguage());
						pstmt.setString(7, channel.getCopyright());
						pstmt.setString(8, channel.getGenerator());
						if (channel.getPubDate() != null)
							pstmt.setTimestamp(9, new Timestamp(channel.getPubDate().getTime()));
						else
							pstmt.setTimestamp(9, null);
						pstmt.setString(10, channel.getLink());
						pstmt.setString(11, item.getTitle());
						pstmt.setString(12, item.getDescription());
						pstmt.setString(13, item.getCategory());
						if (item.getPubDate() != null)
							pstmt.setTimestamp(14, new Timestamp(item.getPubDate().getTime()));
						else
							pstmt.setTimestamp(14, null);
						pstmt.setString(15, item.getLink());

						pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} catch (NullPointerException e) {
				logger.error(e.getMessage(), e);
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

	public void restoreFromSources() {
		clearSourceList();
		storeFromSources();
	}

	public void clearSourceList() {
		PreparedStatement pstmt = null;
		StringBuffer clearQuery = new StringBuffer();
		clearQuery.append("DELETE FROM manalith_maingate_rss_item");

		try {
			pstmt = conn.prepareStatement(clearQuery.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
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

	public void deleteItemsBySource(int sourceId) {
		PreparedStatement pstmt = null;
		StringBuffer clearQuery = new StringBuffer();
		clearQuery.append("DELETE FROM manalith_maingate_rss_item ");
		clearQuery.append("WHERE source=?");

		try {
			pstmt = conn.prepareStatement(clearQuery.toString());
			pstmt.setInt(1, sourceId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
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
