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
		boolean bFailed = false;
		ArrayList<Employee> empList = null;
		try {
			empList = DBAccessHelper.getAllEmployees();
		} catch (Exception e) {
			bFailed = true;
			e.printStackTrace();
		} 
		if (bFailed == true) {
			request.setAttribute("statusmessage", "An error has occurred!");
			this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
		} else {
			request.setAttribute("topmessage", "Here is the list of all employees");
			request.setAttribute("employeeList", empList);
			this.getServletContext().getRequestDispatcher("/ShowEmployees.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
