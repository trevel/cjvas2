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

	/**
	 * private constructor
	 */
	private DBConnPool() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/oracle_cjv805_151a21");
		} catch (NamingException e) {
			System.out.println(e);
		}
	}

	/**
	 * get an instance of the connection pool
	 * 
	 * @return the connection pool
	 */
	public static synchronized DBConnPool getInstance() {
		if (pool == null) {
			pool = new DBConnPool();
		}
		return pool;
	}

	/**
	 * get a connection from the pool
	 * 
	 * @return a connection, null on failure
	 */
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * close a connection 
	 * 
	 * @param c the connection to close
	 */
	public void freeConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}