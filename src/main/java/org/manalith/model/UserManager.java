package org.manalith.model;

import java.util.Date;

import org.manalith.model.dao.UserDAO;
import org.manalith.resource.User;

public class UserManager {

	private UserManager() {
	}

	public static UserManager instance() {
		return new UserManager();
	}

	/**
	 * 새로운 유저를 생성한다.
	 */
	public void createUser(User user) throws Exception {
		UserDAO manager = UserDAO.instance();
		if (!manager.existUser(user.getId())) {
			user.setDate(new Date());
			manager.addUser(user);
		} else {
			String msg = "이미 같은 아이디의 유저가 존재합니다.";
			throw new Exception(msg);
		}
	}
}
