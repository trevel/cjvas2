package ca.myseneca.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.myseneca.data.DBAccessHelper;
import ca.myseneca.model.Employee;

/**
 * Servlet implementation class Employee
 */
@WebServlet({"/HRM_EMP", "/HRM_EMP/*"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = null;
		url = (String) request.getAttribute("javax.servlet.forward.request_uri");
		url = url == null ? ((HttpServletRequest) request).getRequestURI() : url;
		url = url.split("/")[url.split("/").length - 1];
		int id;
		Employee employee = new Employee();
		System.out.println(url);
		if (url != "HRM_EMP") {
			try {
				id = Integer.parseInt(url);
				employee = DBAccessHelper.getEmployeeByID(id);
			} catch (NumberFormatException e) {
				
			}
		}
		request.setAttribute("employee", employee);
		System.out.println(employee);
		
		getServletContext().getRequestDispatcher("/NewEmployee.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
