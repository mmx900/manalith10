/*
 * Created on 2005. 4. 3
 */
package org.manalith.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author setzer
 */
public class ConnectionFactory extends HttpServlet {

	private static DataSource datasource = null;
	private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	private static ConnectionFactory instance = null;

	public void init() throws ServletException {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			datasource = (DataSource) envContext.lookup("jdbc/manalith");
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		instance = this;
	}

	public void destroy() {
		try {
			shutdownDataSource();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static ConnectionFactory getInstance() {
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}

	public static Connection getConnection() throws SQLException {
		if (datasource == null) {
			throw new SQLException();
		}
		return datasource.getConnection();
	}

	private static void shutdownDataSource() throws SQLException {
		datasource.getConnection().close();
	}
}
