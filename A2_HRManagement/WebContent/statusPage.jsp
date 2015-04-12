<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HR Management Status</title>
<link rel="stylesheet" href="styles/main.css" media="screen"/>
<%
	//check if session exists to ensure the user has gone throuht the sign-in page	
	if (session.getAttribute("employee") == null) {
		response.sendRedirect("index.html");
	}
%>
</head>
<body>
	<form id=logout action="HRM_LOGOUT" method="post">
		<b>${employee.first_name} ${employee.last_name}</b>
		<input type="submit" value="Logout">
	</form>
	<br>
	<br>
    <!-- Nav menu -->
    <nav>
      <ul> 
		<li><a href="EmployeeList.jsp"  class="current">Employee List</a></li>
        <li><a href="HRM_EMP/new">New Employee</a></li>
        <li><a href="SearchEmployee.jsp">Search Employees</a></li>  
      </ul>
    </nav>
	<section class="main">
    	<header>
        	<h1 id="template"><span>HR Management - Status</span></h1>
        </header>
        <!-- HTML5 and normalize.css -->
		<div class="content">
			<p><c:out value="${statusmessage}"/></p>
		</div>
	</section>
    <!-- Footer -->
    <footer>
		&copy; 2015 CJV805/DBJ565/DPS904 Seneca College
    </footer>
</body>
</html>