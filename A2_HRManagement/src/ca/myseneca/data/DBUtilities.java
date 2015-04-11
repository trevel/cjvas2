package ca.myseneca.data;

import java.sql.*;


/**
 * @author Laurie Shields (034448142), Mark Lindan (063336143)
 *
 */
public final class DBUtilities {
	
	/**
	 * Print out the details of the SQL Exception
	 * 
	 * @param ex The exception object
	 */
	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("\n---SQLException---\n");
				System.err.println("Message: " + e.getMessage());
				System.err.println("SQLState: "	+ ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				Throwable t = ex.getCause();
				while (t != null) {
					System.err.println("Cause: " + t);
					t = t.getCause();
				}
				System.err.println("");
			}
		}
	}
	
	/**
	 * Prints out the details from the SQLWarning
	 * 
	 * @param warning The SQLWarning object
	 */
	public static void printWarnings(SQLWarning warning) {
		if (warning == null) {
			return;
		}
		System.err.println("\n---SQL Warning---\n");
		while (warning != null) {
			System.err.println("Message: " + warning.getMessage());
			System.err.println("SQLState: " + warning.getSQLState());
			System.err.print("Error Code: ");
			System.err.println(warning.getErrorCode());
			System.err.println("");
			warning = warning.getNextWarning();
		}
	}

	/**
	 * Prints the details of the exception from the attempted batch update
	 * 
	 * @param b The BatchUpdateException
	 */
	public static void printBatchUpdateException(BatchUpdateException b) {
		System.err.println("----BatchUpdateException----");
		System.err.println("Message:  " + b.getMessage());
		System.err.println("SQLState:  " + b.getSQLState());
		System.err.println("Error Code:  " + b.getErrorCode());
		System.err.print("Update counts:  ");
		int[] updateCounts = b.getUpdateCounts();
		for (int i = 0; i < updateCounts.length; i++) {
			System.err.print(updateCounts[i] + "   ");
		}
		System.err.println("");
	}
}
