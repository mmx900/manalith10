package org.manalith.model.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.resource.ArticleFile;
import org.manalith.resource.User;

public class FileDAO {

	private Connection conn;
	private static FileDAO manager;
	private static Logger logger = LoggerFactory.getLogger(FileDAO.class);
	private static String ARTICLE_FILE_UPLOAD_PATH = "blog/upload/article/";
	private static String BLOG_TITLE_IMAGE_PATH = "blog/upload/title/";
	private static String BLOG_BACKGROUND_IMAGE_PATH = "blog/upload/background/";
	private static String CONTEXT_REAL_PATH;

	private FileDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			logger.error("detail :");
			logger.error(ex.getMessage(), ex);
		}
	}

	public static FileDAO instance() {
		if (manager == null) {
			manager = new FileDAO();
		}
		return manager;
	}

	public void setContextRootPath(String path) {
		CONTEXT_REAL_PATH = path;
	}

	private void writeFile(String fileName, FormFile formFile, String path) {
		try {
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStream bos = new FileOutputStream(path + fileName);
			InputStream stream = formFile.getInputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				//bos.write(buffer, 0, 8192);
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			//baos.close();
			stream.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setBlogBackgroundImage(User blogOwner, FormFile formFile) {
		setBlogBackgroundImage(blogOwner.toString(), formFile);
	}

	public void setBlogBackgroundImage(String blogOwnerId, FormFile formFile) {
		String path = CONTEXT_REAL_PATH + BLOG_BACKGROUND_IMAGE_PATH;
		String fileName = blogOwnerId + "_" + formFile.getFileName();

		String query1 = "SELECT backgroundImage FROM manalith_blog WHERE owner = ?";

		String query2 = "UPDATE manalith_blog SET backgroundImage = ? WHERE owner = ?";

		try {
			try (PreparedStatement ps = conn.prepareStatement(query1)) {
				ps.setString(1, blogOwnerId);

				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					if (rs.getString("backgroundImage") != null) {
						if (!rs.getString("backgroundImage").equals("")) {
							File file = new File(path + blogOwnerId + "_" + rs.getString("backgroundImage"));
							file.delete();
						}
					}
				}
			}

			try (PreparedStatement ps = conn.prepareStatement(query2)) {
				ps.setString(1, formFile.getFileName());
				ps.setString(2, blogOwnerId);
				ps.executeUpdate();
			}

			writeFile(fileName, formFile, path);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteBlogBackgroundImage(String blogOwnerId) {
		String path = CONTEXT_REAL_PATH + BLOG_BACKGROUND_IMAGE_PATH;

		String query1 = "SELECT backgroundImage FROM manalith_blog WHERE owner = ?";

		String query2 = "UPDATE manalith_blog SET backgroundImage = ? WHERE owner = ?";

		try (PreparedStatement ps = conn.prepareStatement(query1)) {
			ps.setString(1, blogOwnerId);

			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				if (rs.getString("backgroundImage") != null) {
					if (!rs.getString("backgroundImage").equals("")) {
						File file = new File(path + blogOwnerId + "_" + rs.getString("backgroundImage"));
						file.delete();
					}
				}
			}

			try (PreparedStatement ps2 = conn.prepareStatement(query2)) {
				ps2.setString(1, null);
				ps2.setString(2, blogOwnerId);
				ps2.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}


	public void setBlogTitleImage(User blogOwner, FormFile formFile) {
		setBlogTitleImage(blogOwner.toString(), formFile);
	}

	public void setBlogTitleImage(String blogOwnerId, FormFile formFile) {
		String path = CONTEXT_REAL_PATH + BLOG_TITLE_IMAGE_PATH;
		String fileName = blogOwnerId + "_" + formFile.getFileName();

		String query1 = "SELECT titleImage FROM manalith_blog WHERE owner = ?";

		String query2 = "UPDATE manalith_blog SET titleImage = ? WHERE owner = ?";

		try {
			try (PreparedStatement ps = conn.prepareStatement(query1)) {
				ps.setString(1, blogOwnerId);

				try (ResultSet rs = ps.executeQuery()) {
					rs.next();

					if (rs.getString("titleImage") != null) {
						if (!rs.getString("titleImage").equals("")) {
							File file = new File(path + blogOwnerId + "_" + rs.getString("titleImage"));
							file.delete();
						}
					}
				}
			}

			try (PreparedStatement ps = conn.prepareStatement(query2)) {
				ps.setString(1, formFile.getFileName());
				ps.setString(2, blogOwnerId);
				ps.executeUpdate();
			}

			writeFile(fileName, formFile, path);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteBlogTitleImage(String blogOwnerId) {
		String path = CONTEXT_REAL_PATH + BLOG_TITLE_IMAGE_PATH;

		String query1 = "SELECT titleImage FROM manalith_blog WHERE owner = ?";

		String query2 = "UPDATE manalith_blog SET titleImage = ? WHERE owner = ?";

		try {
			try (PreparedStatement ps = conn.prepareStatement(query1)) {
				ps.setString(1, blogOwnerId);

				try (ResultSet rs = ps.executeQuery()) {
					rs.next();

					if (rs.getString("titleImage") != null) {
						if (!rs.getString("titleImage").equals("")) {
							File file = new File(path + blogOwnerId + "_" + rs.getString("titleImage"));
							file.delete();
						}
					}
				}
			}

			try (PreparedStatement ps = conn.prepareStatement(query2)) {
				ps.setString(1, null);
				ps.setString(2, blogOwnerId);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public int setUnconnectedFile(
			String blogOwnerId, FormFile formFile) {
		int fileNum = setUnconnectedFileInfo(blogOwnerId, formFile);
		String fileName = fileNum + "_" + formFile.getFileName();
		long fileSize = formFile.getFileSize();
		String path = CONTEXT_REAL_PATH + ARTICLE_FILE_UPLOAD_PATH;

		writeFile(fileName, formFile, path);

		return fileNum;
	}

	private int setUnconnectedFileInfo(String blogOwnerId, FormFile file) {
		int fileNum = -1;
		Timestamp date = null;

		try {
			try (PreparedStatement ps = conn.prepareStatement("SELECT NOW() AS now");
				 ResultSet rs = ps.executeQuery()) {
				rs.next();
				rs.getTimestamp("now");
			}

			String query = "INSERT INTO manalith_blog_file(blogOwnerId,name,size,date) " +
					"VALUES(?,?,?,?)";

			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, blogOwnerId);
				ps.setString(2, file.getFileName());
				ps.setInt(3, file.getFileSize());
				ps.setTimestamp(4, date);
				ps.executeUpdate();
			}

			String query2 = "SELECT id FROM manalith_blog_file WHERE date = ?";
			try (PreparedStatement ps = conn.prepareStatement(query2)) {
				ps.setTimestamp(1, date);

				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					fileNum = rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return fileNum;
	}

	//처음부터 특정 Article과 연결된 파일을 만든다.
	public void setConnectedFile(
			String blogOwnerId, int articleId, FormFile formFile) {
		int fileId = setUnconnectedFile(blogOwnerId, formFile);
		setConnectedFile(fileId, articleId);
	}


	//이미 업로드된 파일을 특정 Article과 연결시킨다.
	public void setConnectedFiles(int[] fileId, int articleId) {
		try {
			for (int i : fileId) {
				setConnectedFile(fileId[i], articleId);
			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	//이미 업로드된 파일을 특정 Article과 연결시킨다.
	public void setConnectedFile(int fileId, int articleId) {
		String query = "UPDATE manalith_blog_file " +
				"SET articleId = ? " +
				"WHERE id = ? ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);
			ps.setInt(2, fileId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<ArticleFile> getConnectedFiles(int articleId) {
		List<ArticleFile> files = new ArrayList<ArticleFile>();
		ArticleFile file = null;

		String query = "SELECT id, name, size, date " +
				"FROM manalith_blog_file " +
				"WHERE articleId = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, articleId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					file = new ArticleFile();
					file.setId(rs.getInt("id"));
					file.setArticleId(articleId);
					file.setName(rs.getInt("id") + "_" + rs.getString("name"));
					file.setSize(rs.getInt("size"));
					files.add(file);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return files;
	}

	public List<ArticleFile> getUnconnectedFiles(String blogOwnerId) {
		List<ArticleFile> files = new ArrayList<ArticleFile>();

		String query = "SELECT id, name, size, date " +
				"FROM manalith_blog_file " +
				"WHERE blogOwnerId=? AND articleId=0";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, blogOwnerId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ArticleFile file = new ArticleFile();
					file.setId(rs.getInt("id"));
					file.setBlogOwnerId(blogOwnerId);
					file.setName(rs.getInt("id") + "_" + rs.getString("name"));
					file.setSize(rs.getInt("size"));
					files.add(file);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return files;
	}

	//FIXME: 미완, 파일 삭제 부분 미처리
	public void deleteFiles(int articleId) {
		deleteFiles(getConnectedFiles(articleId));
	}

	public void deleteFiles(List files) {
		if (files != null) {
			for (Object o : files) {
				ArticleFile file = (ArticleFile) o;
				deleteFile(file.getId(), file.getName());
			}
		}
	}

	//DELETE articleFile
	public void deleteFile(int fileId, String fileName) {
		File file = new File(CONTEXT_REAL_PATH + ARTICLE_FILE_UPLOAD_PATH + fileName);
		file.delete();
		deleteFileInfo(fileId);
	}

	private void deleteFileInfo(int fileId) {
		String query = "DELETE FROM manalith_blog_file WHERE id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, fileId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
