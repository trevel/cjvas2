package ca.myseneca.data;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Laurie Shields (034448142), Mark Lindan (063336143)
 *
 */
public class DBConnPool {

	private static DBConnPool pool = null;
	private static DataSource dataSource = null;

	private DBConnPool() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/oracle_cjv805_151a21");
		} catch (NamingException e) {
			System.out.println(e);
		}
	}

	public static synchronized DBConnPool getInstance() {
		if (pool == null) {
			pool = new DBConnPool();
		}
		return pool;
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public void freeConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}