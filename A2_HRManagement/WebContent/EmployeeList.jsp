<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee List Page</title>
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
          <h1 id="template"><span>Employee List Page</span></h1>
        </header>
        <!-- HTML5 and normalize.css -->
		<div class="content">
		<p>Show employees in a department by typing in the 
		department id and click on the button next, or click on the 
		Show All Employees button for all employees in the company:</p>
		<form action="HRM_DEPTEMP" method="post">
			<label>Department ID:</label>
			<input type="number" name="dept_id" min="0" max="10000">
			<br>
			<br>
			<label></label>
			<input type="submit" value="Show Department Employees">
			<br>
			<br>
			<br>
		</form>
		<form action="HRM_ALLEMP" method="post">
			<label></label>
			<input type="submit" value="Show All Employees">
		</form>
		</div>
    </section>
        <!-- Footer -->
    <footer>
		&copy; 2015 CJV805/DBJ565/DPS904 Seneca College
    </footer>
</body>
</html>