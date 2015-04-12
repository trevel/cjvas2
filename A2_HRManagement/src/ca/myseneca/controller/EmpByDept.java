package ca.myseneca.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.myseneca.data.DBAccessHelper;
import ca.myseneca.model.Employee;

/**
 * Servlet implementation class EmpByDept
 */
@WebServlet("/HRM_DEPTEMP")
public class EmpByDept extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpByDept() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Employee> empList = null;
		int dept = 0;
		String deptName = null;
		String deptStr = request.getParameter("dept_id");
		try {
			if(deptStr !=null && !deptStr.isEmpty()) {
			     dept = Integer.parseInt(deptStr.trim());
			}
			deptName = DBAccessHelper.getDepartmentNameByID(dept);
			if (deptName != null) {
				request.setAttribute("topmessage", "Here is the information of employees from department of " + deptName);
				empList = DBAccessHelper.getEmployeesByDepartmentID(dept);
			} else {
				// LAURIE::TODO error page? we got invalid department
			}
		} catch (Exception e) {
			// LAURIE:: TODO
			e.printStackTrace();
		} finally {
			// LAURIE:: TODO - need code here
		}
		// LAURIE:: TODO error page on failure
		request.setAttribute("employeeList", empList);
		this.getServletContext().getRequestDispatcher("/ShowEmployees.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
