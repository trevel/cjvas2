<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search For employee</title>
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
		<li><a href="EmployeeList.jsp">Employee List</a></li>
        <li><a href="HRM_EMP/new">New Employee</a></li>
        <li><a href="SearchEmployee.jsp" class="current">Search Employees</a></li>  
      </ul>
    </nav>
	
    <section class="main">
    	<header>
          <h1 id="template"><span>Search For Employee</span></h1>
        </header>
        <!-- HTML5 and normalize.css -->
		<div class="content">
			<p>Search for an Employee by typing in any part of a name, email address, phone number 
			or department:</p>
			<form action="HRM_SEARCH" method="post">
				<label>Search String:</label>
				<input type="text" name="searchStr">
				<input type="submit" value="Go">
				<br>
			</form>
		</div>
    </section>
        <!-- Footer -->
    <footer>
		&copy; 2015 CJV805/DBJ565/DPS904 Seneca College
    </footer>
</body>
</html>