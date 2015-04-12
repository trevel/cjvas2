<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CJV805 - Assignment #2</title>
<link rel="stylesheet" href="styles/main.css" media="screen"/>
</head>
<body>
<h1>HR Management</h1>
<p>Type your user name and password to login to the system:</p>
	<form action="HRM_LOGIN" method="post">
	    <input type="hidden" name="state" value="signin"> 
		<label>User Name:</label>
		<input type="text" name="username"><br>
		<label>Password:</label>	
		<input type="password" name="password" ><br>
		<br>
		<label></label>
		<input type="submit" value="Login">
	</form>
</body>
</html>