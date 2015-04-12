<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search For Employee</title>
<link rel="stylesheet" href="styles/main.css" type="text/css"/>
<%
	//check if session exists to ensure the user has gone throuh the sign-in page	
	if (session.getAttribute("employee") == null) {
		response.sendRedirect("index.jsp");
	}
%>
</head>
<body>
	<h1>Search for Employee</h1>
	<h2><c:out value="${topmessage}"/></h2>
	<table>
		<tr>
			<th>Name</th>
			<th>Department</th>
			<th>Job ID</th>
			<th>Salary</th>
			<th>Email</th>
			<th>Phone Number</th>									
		</tr>
		<c:forEach var="emp" items="${employeeList}" >
		   <tr>
		     <td><c:out value="${emp.first_name}${' '}${emp.last_name}"/></td>		 
		     <td><c:out value="${emp.dept_name}"/></td>	
		     <td><c:out value="${emp.job_id}"/></td>	
		     <td><c:out value="${emp.salary}"/></td>	
		     <td><a href="<c:url value='mailto:${emp.email}@myseneca.ca'/>"><c:out value="${emp.email}${'@myseneca.ca'}"/></a></td>
			 <td><c:out value="${emp.phone_number}"/></td>	         
		   </tr> 
		</c:forEach>
	</table>
	<p>To return the Search for Employee page, click on the Back button in your
		browser or the Return button shown below.</p>
	<form action="SearchEmployee.jsp" method="get">
		<input type="submit" value="Return">
	</form>
</body>
</html>