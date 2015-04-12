<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee List</title>
<link rel="stylesheet" href="styles/main.css" type="text/css"/>
<%
	//check if session exists to ensure the user has gone throuh the sign-in page	
	if (session.getAttribute("employee") == null) {
		response.sendRedirect("index.html");
	}
%>
</head>
<body>
	<h1>Employee List</h1>
	<h2><c:out value="${topmessage}"/></h2>
	<table>
		<tr>
			<th>Employee ID</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Phone Number</th>
			<th>Hire Date</th>
			<th>Job ID</th>
			<th>Salary</th>
			<th>Commission Pct</th>
			<th>Manager ID</th>
			<th>Department ID</th>										
		</tr>
		<c:forEach var="emp" items="${employeeList}" >
		   <tr>
		   	 <td><a href="<c:url value='/HRM_EMP/${emp.employee_id}'/>"><c:out value="${emp.employee_id}"/></a></td>
		     <td><c:out value="${emp.first_name}"/></td>
		     <td><c:out value="${emp.last_name}"/></td>		     
		     <td><c:out value="${emp.email}"/></td>
			 <td><c:out value="${emp.phone_number}"/></td>
		     <td><c:out value="${emp.hire_date}"/></td>
		     <td><c:out value="${emp.job_id}"/></td>		     
		     <td><c:out value="${emp.salary}"/></td>		     
			 <td><c:out value="${emp.comm_pct}"/></td>
		     <td><c:out value="${emp.manager_id}"/></td>
		     <td><c:out value="${emp.dept_id}"/></td>		         
		   </tr> 
		</c:forEach>
	</table>
	<p>To return the employee list page, click on the Back button in your
		browser or the Return button shown below.</p>
	<form action="EmployeeList.jsp" method="get">
		<input type="submit" value="Return">
	</form>
</body>
</html>