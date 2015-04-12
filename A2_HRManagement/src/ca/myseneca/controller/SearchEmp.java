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
 * Servlet implementation class SearchEmp
 */
@WebServlet("/HRM_SEARCH")
public class SearchEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchEmp() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = null;
		boolean bFailed = false;
		ArrayList<Employee> empList = null;
		try {
			search = request.getParameter("searchStr");
			empList = DBAccessHelper.searchEmployeesByStr(search);
		} catch (Exception e) {
			bFailed = true;
			request.setAttribute("statusmessage", "An error has occurred!");
			e.printStackTrace();
		}
		if (bFailed == true) {
			this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
		} else {
			request.setAttribute("topmessage", "Employee search results for input: " + search);
			request.setAttribute("employeeList", empList);
			this.getServletContext().getRequestDispatcher("/ShowSearch.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
