<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Model</title>
<script  type="text/javascript" src = "javascript/buttonDisable.js"></script>
</head>
<body>
	<h1>Test Model</h1><br>
   <form action="testModel.htm" method="post" enctype="multipart/form-data">
		Model : <input id = "file" name="file" type="file"> <br> <br> 
		<input id = "submit" type="submit" value="Test Model" disabled>
	</form>
</body>
</html>
