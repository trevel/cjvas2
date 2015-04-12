package ca.myseneca.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.myseneca.data.DBAccessHelper;
import ca.myseneca.model.Employee;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Servlet for performing user login validation", urlPatterns = { "/HRM_LOGIN" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/index.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String state = request.getParameter("state");
		Employee emp = null;
		String url = null;

		if (state == null) {
			state = "clear"; // default state
		}
		// check current state and set URL to appropriate page
		if ("clear".equals(state)) {
			url = "/index.html"; // the sign-in page
		} else if ("signin".equals(state)) {
			try {
				int result = DBAccessHelper.getEmployeeID(username, password);
				if (result > 0) {
					HttpSession session = request.getSession();
					emp = DBAccessHelper.getEmployeeByID(result);
					session.setAttribute("employee", emp);
					session.setMaxInactiveInterval(30 * 60);
					response.sendRedirect("EmployeeList.jsp");
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher(
							"/index.html");
					PrintWriter out = response.getWriter();
					out.println("<p style=\"color:red\">Either email or password is wrong. " 
							+ "Please try again.</p>");
					rd.include(request, response);
					return;		 
				}
			} catch (Exception ex) {

			} finally {
				
			}
		}
	}

}
