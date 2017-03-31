package org.chenmin.auto.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBUtils {
	public static String DRIVER = "com.mysql.jdbc.Driver";
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private DBUtils() {
	}

	public static Connection getConnection(String url, String user, String password) {
		Connection conn;
			try {
				System.out.println(url);
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		 
		return conn;
	}

	public static void closeResources(Connection conn, Statement pstmt,
			ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				if (null != pstmt) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
						if (null != conn) {
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
					}
				}
			}
		}
	}
}
