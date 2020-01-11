package org.manalith.model;

import org.manalith.model.dao.FileDAO;

public class FileManager {

	private FileManager() {
	}

	public static FileManager instance() {
		return new FileManager();
	}

	/**
	 * 업로드된 파일을 삭제한다.
	 */
	public void deleteFile(int fileId, String fileName) throws Exception {
		FileDAO.instance().deleteFile(fileId, fileName);
	}
}
