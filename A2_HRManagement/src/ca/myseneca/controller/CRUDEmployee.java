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
 */
@WebServlet({"/HRM_EMPLOYEE", "/HRM_EMP/HRM_EMPLOYEE"})
public class CRUDEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Employee emp = new Employee();
		emp.setFirst_name(request.getParameter("fname"));
		emp.setLast_name(request.getParameter("lname"));
		emp.setEmail(request.getParameter("email"));
		emp.setPhone_number(request.getParameter("phone"));
		java.util.Date hire = null;
		try {
			hire = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("hiredate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
		
		if(request.getParameter("create") != null) {
			emp.setEmployee_id(0);
			DBAccessHelper.addNewEmployee(emp);
			
		} else if(request.getParameter("update") != null) {
			System.out.println(request.getParameter("id"));
			emp.setEmployee_id(Integer.parseInt(request.getParameter("id")));
			DBAccessHelper.updateEmployee(emp);
		} else if(request.getParameter("delete") != null) {
			DBAccessHelper.deleteEmployeeByID(Integer.parseInt(request.getParameter("id")));
		}
		getServletContext().getRequestDispatcher("/HRM_EMP" ).forward(request, response);
		
	}

}
