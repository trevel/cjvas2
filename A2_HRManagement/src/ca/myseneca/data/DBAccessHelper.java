package ca.myseneca.data;

import java.sql.*;
import java.util.ArrayList;

import ca.myseneca.data.DBConnPool;
import ca.myseneca.model.Department;
import ca.myseneca.model.Employee;

/**
 * @author Laurie Shields (034448142), Mark Lindan (063336143)
 *
 */
public final class DBAccessHelper {

	/**
	 * Attempt to authorize the user
	 * 
	 * @param user The username for the attempted login
	 * @param password The password for the attempted login
	 * @return 0 if user not authorized, employee_id for user if authorized
	 */
	public static int getEmployeeID(String user, String password) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int retVal = 0;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return 0;
			}
			stmt = conn.prepareStatement("SELECT employee_id FROM Security "
					+ "WHERE SEC_ID = ?  AND SEC_PASSWORD = ? AND SEC_STATUS = 'A'");
			stmt.setString(1, user);
			stmt.setString(2, password);				
			rs = stmt.executeQuery();
			if (rs.next()) {
				retVal = rs.getInt(1);
			} 
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		} // Finally
		return retVal;
	}

	/**
	 * Retrieves a list of all the employees
	 * 
	 * @return An ArrayList of all the Employee objects
	 */
	public static ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> tmpList = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return tmpList;
			}
			tmpList = new ArrayList<Employee>();
			stmt = conn.prepareStatement("SELECT employee_id, first_name, "
					+ "last_name, email, phone_number, hire_date, job_id, "
					+ "salary, commission_pct, manager_id, department_id "
					+ "FROM employees");
			rs = stmt.executeQuery();
			while (rs.next()) {
				// convert the data from the resultSet into Employee object
				Employee emp = populateEmp(rs);
				// store the employee object in the ArrayList
				tmpList.add(emp);
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		} // finally
		return tmpList;
	}

	/**
	 * Retrieves a list of all employees in a specified department
	 * 
	 * @param depid The department ID to get employees for
	 * @return An ArrayList of Employee objects for the department
	 */
	public static ArrayList<Employee> getEmployeesByDepartmentID(int depid) {
		ArrayList<Employee> tmpList = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return tmpList;
			}
			tmpList = new ArrayList<Employee>();
			stmt = conn.prepareStatement("SELECT employee_id, first_name, "
					+ "last_name, email, phone_number, hire_date, job_id, "
					+ "salary, commission_pct, manager_id, department_id "
					+ "FROM employees WHERE department_id=?");
			stmt.setInt(1, depid);  // set the first parameter
			rs = stmt.executeQuery();
			while (rs.next()) {
				// convert the data from the resultSet into Employee object
				Employee emp = populateEmp(rs);
				// store the employee object in the ArrayList
				tmpList.add(emp);
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		} // finally
		return tmpList;
	}
		
	/**
	 * Get the Employee object for a specific employee_id
	 * 
	 * @param empid The employee ID to retrieve
	 * @return An Employee object if found, null if empid not found
	 */
	public static Employee getEmployeeByID(int empid) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Employee emp = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return null;
			}
			stmt = conn.prepareStatement("SELECT employee_id, first_name, "
					+ "last_name, email, phone_number, hire_date, job_id, "
					+ "salary, commission_pct, manager_id, department_id "
					+ "FROM employees WHERE employee_id=?");
			stmt.setInt(1, empid);  // set the first parameter
			rs = stmt.executeQuery();
			if (rs.next()) {
				// convert the data from the resultSet into Employee object
				emp = populateEmp(rs);
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		}
		return emp;
	}

	/**
	 * Adds a new employee to the database
	 * 
	 * @param emp The new Employee to add
	 * @return The new id# if record created; 0 otherwise
	 */
	public static int addNewEmployee(Employee emp) {
		Statement stmt = null;
		ResultSet rs = null;
		int new_id = 0;
		String sqlQuery = "SELECT employee_id, first_name, last_name, email, "
				+ "phone_number, hire_date, job_id, salary, commission_pct, "
				+ "manager_id, department_id FROM employees";
		if (emp == null) {
			// employee object is null...nothing for us to do
			return 0;
		}
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return 0;
			}
			// make the result set updatable
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// Get the highest id, increment.
			rs = stmt.executeQuery("SELECT max(employee_id) FROM employees");
			rs.next();
			new_id = rs.getInt(1) + 1;
			rs.close();
			
			rs = stmt.executeQuery(sqlQuery);
			// make sure the result set we got back is updatable
			if (rs.getConcurrency() == ResultSet.CONCUR_UPDATABLE) {
				// move to where we can insert new row
				rs.moveToInsertRow();
				// set all the fields
				rs.updateInt(1, new_id);
				rs.updateString(2, emp.getFirst_name());
				rs.updateString(3, emp.getLast_name());
				rs.updateString(4, emp.getEmail());
				rs.updateString(5, emp.getPhone_number());
				rs.updateDate(6, emp.getHire_date());
				rs.updateString(7, emp.getJob_id());
				rs.updateBigDecimal(8, emp.getSalary());
				rs.updateBigDecimal(9, emp.getComm_pct());
				rs.updateInt(10, emp.getManager_id());
				rs.updateInt(11, emp.getDept_id());
				// insert the row
				rs.insertRow();
				rs.moveToCurrentRow();
			} else {
				System.out.println("ResultSet is not an updatable result set.");
				return 0;
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			// clean up
			try {
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
				return 0;
			}
		}
		return new_id;
	}

	/**
	 * Update the specified employee
	 * 
	 * @param emp The employee to update
	 * @return 1 if updated, 0 in case of error
	 */
	public static int updateEmployee(Employee emp) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int retVal = 0;
		if (emp == null) {
			// employee object is null...not much more we can do
			return 0;
		}
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return 0;
			}
			// make the result set updatable
			stmt = conn.prepareStatement("SELECT employee_id, first_name, "
					+ "last_name, email, phone_number, hire_date, job_id, "
					+ "salary, commission_pct, manager_id, department_id "
					+ "FROM employees WHERE employee_id=?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, emp.getEmployee_id()); // set the first parameter
			rs = stmt.executeQuery();
			// Check the result set is an updatable result set
			if (rs.getConcurrency() == ResultSet.CONCUR_UPDATABLE) {
				rs.first();
				// go through and update whatever has changed...can't change ID
				if (rs.getString(2) != emp.getFirst_name()) {
					rs.updateString(2, emp.getFirst_name());
				}
				if (rs.getString(3) != emp.getLast_name()) {
					rs.updateString(3, emp.getLast_name());
				}
				if (rs.getString(4) != emp.getEmail()) {
					rs.updateString(4, emp.getEmail());
				}
				if (rs.getString(5) != emp.getPhone_number()) {
					rs.updateString(5, emp.getPhone_number());
				}
				if (rs.getDate(6) != emp.getHire_date()) {
					rs.updateDate(6, emp.getHire_date());
				}
				if (rs.getString(7) != emp.getJob_id()) {
					rs.updateString(7, emp.getJob_id());
				}
				if (rs.getBigDecimal(8) != emp.getSalary()) {
					rs.updateBigDecimal(8, emp.getSalary());
				}
				if (rs.getBigDecimal(9) != emp.getComm_pct()) {
					rs.updateBigDecimal(9, emp.getComm_pct());
				}
				if (rs.getInt(10) != emp.getManager_id()) {
					rs.updateInt(10, emp.getManager_id());
				}
				if (rs.getInt(11) != emp.getDept_id()) {
					rs.updateInt(11, emp.getDept_id());
				}
				rs.updateRow();
				retVal = 1; // no return value from updateRow() call so just use 1
			} else {
				System.out.println("ResultSet is not an updatable result set.");
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		}
		return retVal;
	}

	/**
	 * Delete the employee with ID=empid
	 * 
	 * @param empid The ID of the employee to delete
	 * @return 0 on failure, 1 on success
	 */
	public static int deleteEmployeeByID(int empid) {
		int retVal = 0;
		PreparedStatement stmt = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return 0;
			}
			stmt = conn.prepareStatement(
					"DELETE FROM employees WHERE employee_id=?");
			stmt.setInt(1, empid);
			retVal = stmt.executeUpdate();
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		}
		return retVal;
	}

	/**
	 * Run a batch Update on the database
	 * <p>
	 * The batch update is executed inside a transaction to make sure
	 * that either all updates are executed or none are.
	 * 
	 * @param SQLs A list of SQL strings to process
	 * @return false on failure, true on success
	 */
	public static boolean batchUpdate(String[] SQLs) {
		Statement stmt = null;
		boolean retVal = false;
		if (SQLs.length == 0) {
			// there were no statements to execute
			return false;
		}
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return false;
			}
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			for (int i = 0; i < SQLs.length; i++) {
				stmt.addBatch(SQLs[i]);
			}
			stmt.executeBatch();
			conn.commit();
			retVal = true;
		} catch (BatchUpdateException b) {
			DBUtilities.printBatchUpdateException(b);
			try {
				// something failed...we need to rollback
				conn.rollback();
			} catch (SQLException ex) {
				DBUtilities.printSQLException(ex);
			}
		} catch (SQLException ex) {
			DBUtilities.printSQLException(ex);
			try {
				// something failed...we need to rollback
				conn.rollback();
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		} finally {
			try {
				// clean up
				conn.setAutoCommit(true);
				if (stmt != null) {
					stmt.close();
				}
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		}
		return retVal;
	}
	
	/**
	 * get the name of a department
	 * 
	 * @param deptId
	 * @return the name of the department
	 */
	public static String getDepartmentNameByID(int deptId) {
		String deptName = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return null;
			}
			stmt = conn.prepareStatement("SELECT department_name "
					+ "FROM departments WHERE department_id=?");
			stmt.setInt(1, deptId);  // set the first parameter
			rs = stmt.executeQuery();
			if (rs.next()) {
				// convert the data from the resultSet into Employee object
				deptName = rs.getString(1);
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		}
		return deptName;
	}
	
	/**
	 * Searches for employees by a specified string
	 * 
	 * @param search A string to search for
	 * @return A list of Employees, null if none found
	 */
	public static ArrayList<Employee> searchEmployeesByStr(String search ) {
		ArrayList<Employee> tmpList = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		String pstr = null;

		try {
			conn = pool.getConnection();
			if (conn == null) {
				return tmpList;
			}
			tmpList = new ArrayList<Employee>();
/*			stmt = conn.prepareStatement("SELECT e.first_name, e.last_name, "
					+ "e.email, e.phone_number, e.job_id, e.salary, d.department_name "
					+ "FROM employees e "
					+ "JOIN departments d"
					+ "ON (e.department_id=d.department_id)"
					+ "WHERE first_name LIKE ? "
					+ "OR last_name LIKE ? "
					+ "OR phone_number LIKE ? "
					+ "OR email LIKE ?"
					+ "OR d.department_name LIKE ?");*/
			stmt = conn.prepareStatement(
					"SELECT e.first_name, e.last_name, e.email, e.phone_number, e.job_id, e.salary, d.department_name FROM employees e "
					+ "JOIN departments d "
					+ "ON (e.department_id=d.department_id) "
					+ "WHERE e.first_name LIKE ? "
					+ "OR e.last_name LIKE ? "
					+ "OR e.phone_number LIKE ? "
					+ "OR e.email LIKE ? "
					+ "OR d.department_name LIKE ?"
					+ "ORDER BY e.employee_id");
			pstr = "%" + search +"%";
			stmt.setString(1, pstr);  
			stmt.setString(2, pstr);  
			stmt.setString(3, pstr);  
			stmt.setString(4, pstr);  
			stmt.setString(5, pstr);  
			rs = stmt.executeQuery();
			while (rs.next()) {
				// convert the data from the resultSet into Employee object
				Employee emp = new Employee();
				emp.setFirst_name(rs.getString(1));
				emp.setLast_name(rs.getString(2));
				emp.setEmail(rs.getString(3));
				emp.setPhone_number(rs.getString(4));
				emp.setJob_id(rs.getString(5));
				emp.setSalary(rs.getBigDecimal(6));
				emp.setDept_name(rs.getString(7));
				// store the employee object in the ArrayList
				tmpList.add(emp);
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		} // finally
		return tmpList;
	}

	/**
	 * Helper method to convert from result set into Employee objects
	 * 
	 * @param rs A result set that contains employees
	 * @return An Employee object, null on  failure
	 * @throws SQLException
	 */
	private static Employee populateEmp(ResultSet rs) throws SQLException {
		if (rs != null) {
			Employee emp = new Employee();
			emp.setEmployee_id(rs.getInt(1));
			emp.setFirst_name(rs.getString(2));
			emp.setLast_name(rs.getString(3));
			emp.setEmail(rs.getString(4));
			emp.setPhone_number(rs.getString(5));
			emp.setHire_date(rs.getDate(6));
			emp.setJob_id(rs.getString(7));
			emp.setSalary(rs.getBigDecimal(8));
			emp.setComm_pct(rs.getBigDecimal(9));
			emp.setManager_id(rs.getInt(10));
			emp.setDept_id(rs.getInt(11));
			return emp;
		} else {
			return null;
		}
	}
	
	/**
	 * get a list of all the departments
	 * 
	 * @return a list of all departments. null if there are none
	 */
	public static ArrayList<Department> getAllDepartments() {
		ArrayList<Department> tmpList = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DBConnPool pool = DBConnPool.getInstance();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn == null) {
				return tmpList;
			}
			tmpList = new ArrayList<Department>();
			stmt = conn.prepareStatement("SELECT department_id, department_name "
					+ "FROM departments ORDER BY 1");
			rs = stmt.executeQuery();
			while (rs.next()) {
				// convert the data from the resultSet into Employee object
				Department dept = new Department(rs.getInt(1), rs.getString(2));
				// store the employee object in the ArrayList
				tmpList.add(dept);
			}
		} catch (SQLException e) {
			DBUtilities.printSQLException(e);
		} finally {
			try {
				// clean up
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				pool.freeConnection(conn);
			} catch (SQLException e) {
				DBUtilities.printSQLException(e);
			}
		} // finally
		return tmpList;
	}
}
