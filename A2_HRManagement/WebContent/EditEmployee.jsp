<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Employee Page</title>
<link rel="stylesheet" href="../styles/main.css" media="screen"/>
<jsp:useBean id="emp" class="ca.myseneca.model.Employee" scope="request">
<jsp:setProperty name="emp" property="*"/>
</jsp:useBean>

<%
	//check if session exists to ensure the user has gone through the sign-in page	
	if (session.getAttribute("employee") == null) {
		response.sendRedirect("/A2_HRManagement/");
	}
%>
</head>
<body>
	<form id=logout action="../HRM_LOGOUT" method="post">
		<strong>${employee.first_name} ${employee.last_name}</strong>
		<input type="submit" value="Logout">
	</form>
	<br>
	<br>
  <!-- Nav menu -->
    <nav>
      <ul> 
		<li><a href="../EmployeeList.jsp">Employee List</a></li>
        <li><a href="../HRM_EMP/new" class="current">New Employee</a></li>
        <li><a href="../SearchEmployee.jsp">Search Employees</a></li>  
      </ul>
    </nav>
	
    <section class="main">
    	<header>
          <h1 id="template"><span>Employee Detail Page</span></h1>

        </header>
        <!-- HTML5 and normalize.css -->
		<div class="content">
		  ${notice}
		  <c:remove var="notice" scope="session" />
          <form action="../HRM_EMPLOYEE" method="post">
          <input type="hidden" name="id" value="${emp.getEmployee_id()}" />
          <table>
          	<% if (emp.getEmployee_id() != 0 ) { %>
          		<tr><td>Employee ID:</td><td>${emp.getEmployee_id()}</td></tr>
          	<% } %>
          	<tr><td>First Name:</td>	<td><input type="text" name="fname" required value="${emp.getFirst_name()}" /></td></tr>
          	<tr><td>Last Name:</td>		<td><input type="text" name="lname" required value="${emp.getLast_name()}" /></td></tr>
          	<tr><td>Email:</td>			<td><input type="text" name="email" required value="${emp.getEmail()}" /></td></tr>
          	<tr><td>Phone Number:</td>	<td><input type="text" name="phone" required value="${emp.getPhone_number()}" /></td></tr>
          	<tr><td>Hire Date:</td>		<td><input type="date" name="hiredate" required value="${emp.getHire_date()}" /></td></tr>
          	<tr><td>Job ID:</td>		<td><input type="text" name="jobid" required value="${emp.getJob_id()}" /></td></tr>
          	<tr><td>Salary</td>			<td><input type="number" name="salary" required value="${emp.getSalary()}" /></td></tr>
          	<tr><td>Comm Pct:</td>		<td><input type="number" name="commpct" max="0.5" step="0.01" value="${emp.getComm_pct()}" /></td></tr>
          	<tr><td>Manager ID:</td>	<td><input type="number" name="mgrid" value="${emp.getManager_id()}" /></td></tr>
          	<tr><td>Department ID:</td>	<td>
          		<select name="deptid" required>
          			<c:forEach items="${departments}" var="dept">
          				<option value="${dept.id}" ${dept.getId() == emp.getDept_id() ? 'selected' : ''}>${dept.name}</option>
          			</c:forEach>
          		</select></td></tr>
          </table>
           	<% if (emp.getEmployee_id() != 0) { %>
          		<input type="Submit" name="update" value="Save edits" /><input type="Submit" name="delete" value="Delete record" />
          	<% } else { %>
          		<input type="Submit" name="create" value="Save new employee" />
          	<% } %>
          
          </form>		
		</div>
    </section>
        <!-- Footer -->
    <footer>
		&copy; 2015 CJV805/DBJ565/DPS904 Seneca College
    </footer>
</body>
</html>