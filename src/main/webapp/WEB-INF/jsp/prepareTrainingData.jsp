
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Prepare Training Data</title>

</head>
<body>
<h1>Prepare Training Data</h1><br>
   <form action="xlsToCsv.htm" method="post"
		enctype="multipart/form-data"><input name="excelfile" type="file"> <br> <br> <input
			type="submit" value="Convert CSV">
		
	</form>

	
	
</body>
</html>
