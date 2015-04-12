package ca.myseneca.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.myseneca.data.DBAccessHelper;
import ca.myseneca.model.Department;
import ca.myseneca.model.Employee;

/**
 * Servlet implementation class Employee
 */
@WebServlet("/HRM_EMP/*")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * Takes a URL HRM_EMP/{id} and forwards to EditEmployee. If /{id} is a valid record,
	 * it loads that record in the form, otherwise it gives a new record interface.
	 * 
	 * @param id (optional, from the URL) 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getSession(false) == null || request.getSession(false).getAttribute("employee") == null) {
				getServletContext().getRequestDispatcher("/").forward(request, response);
				return;
			}
			// To make more RESTful, grabs the number following the URL
			String url = null;
			url = (String) request.getAttribute("javax.servlet.forward.request_uri");
			url = url == null ? ((HttpServletRequest) request).getRequestURI() : url;
			url = url.split("/")[url.split("/").length - 1];
			int id;
			Employee employee = new Employee();
			if (url.equals("HRM_EMP")) {
				//getServletContext().getRequestDispatcher("/HRM_EMP/new").forward(request, response);
				response.sendRedirect(response.encodeRedirectURL("/A2_HRManagement/HRM_EMP/new"));
				return;
			} else {
				if(!url.equals("new")) {
					try {
						id = Integer.parseInt(url);
						employee = DBAccessHelper.getEmployeeByID(id);
					} catch (NumberFormatException e) {
						// if it's not a valid integer, we treat it as a new employee. 
						// Could alternately forward to a list, or some such. 
					}
				}
			}
			request.setAttribute("emp", employee);

			// ArrayList<Employee> managers = DBAccessHelper.getAllEmployees();
			ArrayList<Department> departments = DBAccessHelper.getAllDepartments();
			request.setAttribute("departments", departments);
			getServletContext().getRequestDispatcher("/EditEmployee.jsp").forward(request, response);
		} catch (Exception ex) {
			request.setAttribute("statusmessage", "Server error; try again later.");
			this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
