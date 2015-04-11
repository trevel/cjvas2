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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int dept = Integer.parseInt(request.getParameter("dept_id"));
		// LAURIE:: TODO - need code here
		ArrayList<Employee> empList = DBAccessHelper.getEmployeesByDepartmentID(dept);
		if (empList != null) {
			
		} else {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
