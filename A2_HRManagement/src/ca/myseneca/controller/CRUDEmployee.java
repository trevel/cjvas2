package ca.myseneca.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.myseneca.data.DBAccessHelper;
import ca.myseneca.model.Employee;

/**
 * Servlet implementation class CRUDEmployee
 * 
 * Controller for the EditEmployee.jsp view. Provides Create, Update and Delete mechanisms for 
 * the employee records. 
 */
@WebServlet({"/HRM_EMPLOYEE", "/HRM_EMP/HRM_EMPLOYEE"})
public class CRUDEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDEmployee() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Should never be called; forwards the request do the default index page. 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/index.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * Takes an employee object, creates a employee record and CUDs it based on button pressed. 
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getSession(false) == null || request.getSession(false).getAttribute("employee") == null) {
				getServletContext().getRequestDispatcher("/").forward(request, response);
				return;
			}
			Employee emp = new Employee();
			emp.setFirst_name(request.getParameter("fname"));
			emp.setLast_name(request.getParameter("lname"));
			emp.setEmail(request.getParameter("email"));
			emp.setPhone_number(request.getParameter("phone"));
			java.util.Date hire = null;
			try {
				hire = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("hiredate"));
			} catch (ParseException e) {
				// If the date is bad, we abort...  
				getServletContext().getRequestDispatcher("/EditEmployee.jsp").forward(request, response);
			}
			emp.setHire_date(new java.sql.Date(hire.getTime()));
			emp.setJob_id(request.getParameter("jobid"));
			emp.setSalary(new BigDecimal(request.getParameter("salary")));
			if (request.getParameter("commpct") == null || request.getParameter("commpct") == "") {
				emp.setComm_pct(null);
			} else {
				emp.setComm_pct(new BigDecimal(request.getParameter("commpct")));
			}
			emp.setManager_id(Integer.parseInt(request.getParameter("mgrid")));
			emp.setDept_id(Integer.parseInt(request.getParameter("deptid")));

			int result = 0;

			// Create button
			if(request.getParameter("create") != null) {
				emp.setEmployee_id(0); // need to make sure it's initialized before creating
				result = DBAccessHelper.addNewEmployee(emp);

				if (result == 0 ) {
					request.setAttribute("statusmessage", "Could not create record; check fields");
					this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
					return;
				} else {
					emp.setEmployee_id(result); // populate emp with the new id.
					request.getSession(false).setAttribute("notice", "Record successfully created!");
					response.sendRedirect(response.encodeRedirectURL("/A2_HRManagement/HRM_EMP/" + emp.getEmployee_id()));
					return;
				}

				// Update button
			} else if(request.getParameter("update") != null) {
				emp.setEmployee_id(Integer.parseInt(request.getParameter("id")));
				result = DBAccessHelper.updateEmployee(emp);
				if (result == 0) {
					request.setAttribute("statusmessage", "Could not update record; check fields");
					this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
				} else {
					request.getSession(false).setAttribute("notice", "Record successfully updated!");
					response.sendRedirect(response.encodeRedirectURL("/A2_HRManagement/HRM_EMP/" + emp.getEmployee_id()));
					return;
				}
				return;
				// Delete button	
			} else if(request.getParameter("delete") != null) {
				result = DBAccessHelper.deleteEmployeeByID(Integer.parseInt(request.getParameter("id"))); 
				if (result == 0 ) {
					request.setAttribute("statusmessage", "Could not delete record; check fields");
					this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
				} else {
					request.getSession(false).setAttribute("notice", "Record deleted! ");
					response.sendRedirect(response.encodeRedirectURL("/A2_HRManagement/HRM_EMP/new"));
					return;
				}
				return;
			}
			request.setAttribute("statusmessage", "I have no idea what you were trying to do.");
			this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
		} catch (Exception ex) {
			request.setAttribute("statusmessage", "Server error; try again later.");
			this.getServletContext().getRequestDispatcher("/statusPage.jsp").forward(request, response);
			return;
		}
	}
}
