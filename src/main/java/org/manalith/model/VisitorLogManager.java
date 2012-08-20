/*
 * Created on 2005. 9. 25
 */
package org.manalith.model;

import org.manalith.model.dao.VisitorLogDAO;
import org.manalith.resource.VisitorLog;


public class VisitorLogManager {
	private VisitorLogManager(){}
	public static VisitorLogManager instance(){
		return new VisitorLogManager();
	}
	

	/**
	 * 방문자 로그를 추가한다.
	 */
	public void addVisitLog(VisitorLog log) {
		VisitorLogDAO.instance().addLog(log);
	}
}
