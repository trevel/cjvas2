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
 * Servlet implementation class EmpAll
 */
@WebServlet({"/HRM_ALLEMP", "/HRM_EMP"})
public class EmpAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpAll() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Employee> empList = null;
		try {
			empList = DBAccessHelper.getAllEmployees();
		} catch (Exception e) {
			// LAURIE:: TODO
			e.printStackTrace();
		} finally {
			// LAURIE:: TODO - need code here
		}
		// LAURIE:: TODO - error page for failure
		request.setAttribute("topmessage", "Here is the list of all employees");
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
