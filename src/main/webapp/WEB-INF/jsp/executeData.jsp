<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Execute Model</title>
<script  type="text/javascript" src = "javascript/buttonDisable.js"></script>
</head>
<body>
	<h1>Execute Data</h1><br>
   <form action="executeData.htm" method="post" enctype="multipart/form-data">
		<input id = "file" name="file" type="file"> <br> <br> 
		<input id = "submit" type="submit" value="Execute Model" disabled>
	</form>
</body>
</html>
