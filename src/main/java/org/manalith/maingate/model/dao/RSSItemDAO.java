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
		String query = "SELECT id, source, rssVersion, rssCharset, channelTitle, channelDescription, " +
				"channelLanguage, channelCopyright, channelGenerator, channelPubDate, channelLink, " +
				"itemTitle, itemDescription, itemCategory, itemPubDate, itemLink " +
				"FROM manalith_maingate_rss_item " +
				"ORDER BY itemPubDate DESC";

		try (PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				RSSSourceItem item = new RSSSourceItem();
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
		}

		return items;
	}

	public List<RSSSourceItem> getItems(int limitation) {
		List<RSSSourceItem> items = new ArrayList<RSSSourceItem>();

		String query = "SELECT id, source, rssVersion, rssCharset, channelTitle, channelDescription, " +
				"channelLanguage, channelCopyright, channelGenerator, channelPubDate, channelLink, " +
				"itemTitle, itemDescription, itemCategory, itemPubDate, itemLink " +
				"FROM manalith_maingate_rss_item " +
				"ORDER BY itemPubDate DESC LIMIT ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, limitation);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					RSSSourceItem item = new RSSSourceItem();
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
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return items;
	}

	public void storeFromSources() {
		String query = "INSERT INTO manalith_maingate_rss_item(" +
				"source, rssVersion, rssCharset, channelTitle, channelDescription, " +
				"channelLanguage, channelCopyright, channelGenerator, channelPubDate, channelLink, " +
				"itemTitle, itemDescription, itemCategory, itemPubDate, itemLink" +
				") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		List<RSSSource> sources = RSSSourceDAO.instance().getSources();

		for (RSSSource source : sources) {
			RSSObject rss = RSSDOMParser.instance().parse(source.getRssUrl());

			try (PreparedStatement ps = conn.prepareStatement(query)) {
				List<RSSChannelObject> channels = rss.getChannels();
				for (RSSChannelObject channel : channels) {
					List<RSSChannelItemObject> items = channel.getItems();
					for (RSSChannelItemObject item : items) {
						ps.setInt(1, source.getId());
						ps.setString(2, rss.getVersion());
						ps.setString(3, rss.getCharset());

						ps.setString(4, channel.getTitle());
						ps.setString(5, channel.getDescription());
						ps.setString(6, channel.getLanguage());
						ps.setString(7, channel.getCopyright());
						ps.setString(8, channel.getGenerator());
						if (channel.getPubDate() != null)
							ps.setTimestamp(9, new Timestamp(channel.getPubDate().getTime()));
						else
							ps.setTimestamp(9, null);
						ps.setString(10, channel.getLink());
						ps.setString(11, item.getTitle());
						ps.setString(12, item.getDescription());
						ps.setString(13, item.getCategory());
						if (item.getPubDate() != null)
							ps.setTimestamp(14, new Timestamp(item.getPubDate().getTime()));
						else
							ps.setTimestamp(14, null);
						ps.setString(15, item.getLink());

						ps.executeUpdate();
					}
				}
			} catch (SQLException | NullPointerException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void restoreFromSources() {
		clearSourceList();
		storeFromSources();
	}

	public void clearSourceList() {
		String query = "DELETE FROM manalith_maingate_rss_item";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteItemsBySource(int sourceId) {
		String query = "DELETE FROM manalith_maingate_rss_item WHERE source=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, sourceId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
