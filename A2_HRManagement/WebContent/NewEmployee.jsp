<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Employee Page</title>
<link rel="stylesheet" href="styles/main.css" media="screen"/>
<jsp:useBean id="employee" class="ca.myseneca.model.Employee" scope="request">
<jsp:setProperty name="employee" property="*"/>
</jsp:useBean>

<%
	//check if session exists to ensure the user has gone throuht the sign-in page	
	if (session.getAttribute("employee") == null) {
		response.sendRedirect("index.jsp");
	}
%>
</head>
<body>
	<form id=logout action="HRM_LOGOUT" method="post">
		<bold>${employee.first_name} ${employee.last_name}</bold>
		<input type="submit" value="Logout">
	</form>
	<br>
	<br>
  <!-- Nav menu -->
    <nav>
      <ul> 
		<li><a href="EmployeeList.jsp">Employee List</a></li>
        <li><a href="HRM_EMP" class="current">New Employee</a></li>
        <li><a href="SearchEmployee.jsp">Search Employees</a></li>  
      </ul>
    </nav>
	
    <section class="main">
    	<header>
          <h1 id="template"><span>New Employee Page</span></h1>
          <form action="HRM_EMPLOYEE" method="post">
          <input type="hidden" name="id" value="${employee.getEmployee_id()}" />
          <table>
          	<% if (employee.getEmployee_id() != 0 ) { %>
          		<tr><td>Employee ID:</td>	<td>${employee.getEmployee_id()}</td></tr>
          	<% } %>
          	<tr><td>First Name:</td>	<td><input type="text" name="fname" required value="${employee.getFirst_name()}" /></td></tr>
          	<tr><td>Last Name:</td>		<td><input type="text" name="lname" required value="${employee.getLast_name()}" /></td></tr>
          	<tr><td>Email:</td>			<td><input type="text" name="email" required value="${employee.getEmail()}" /></td></tr>
          	<tr><td>Phone Number:</td>	<td><input type="text" name="phone" required value="${employee.getPhone_number()}" /></td></tr>
          	<tr><td>Hire Date:</td>		<td><input type="date" name="hiredate" required value="${employee.getHire_date()}" /></td></tr>
          	<tr><td>Job ID:</td>		<td><input type="text" name="jobid" required value="${employee.getJob_id()}" /></td></tr>
          	<tr><td>Salary</td>			<td><input type="number" name="salary" required value="${employee.getSalary()}" /></td></tr>
          	<tr><td>Comm Pct:</td>		<td><input type="number" name="commpct" max="0.5" step="0.01" value="${employee.getComm_pct()}" /></td></tr>
          	<tr><td>Manager ID:</td>	<td><input type="number" name="mgrid" value="${employee.getManager_id()}" /></td></tr>
          	<tr><td>Department ID:</td>	<td>
          		<select name="deptid" required>
          			<c:forEach items="${departments}" var="dept">
          				<option value="${dept.id}" ${dept.getId() == employee.getDept_id() ? 'selected' : ''}>${dept.name}</option>
          			</c:forEach>
          		</select></td></tr>
          </table>
           	<% if (employee.getEmployee_id() != 0) { %>
          		<input type="Submit" name="update" value="Save edits" /><input type="Submit" name="delete" value="Delete record" />
          	<% } else { %>
          		<input type="Submit" name="create" value="Save new employee" />
          	<% } %>
          
          </form>
        </header>
        <!-- HTML5 and normalize.css -->
		<div class="content">
		<p>Insert stuff here</p>
		</div>
    </section>
        <!-- Footer -->
    <footer>
		&copy; 2015 CJV805/DBJ565/DPS904 Seneca College
    </footer>
</body>
</html>