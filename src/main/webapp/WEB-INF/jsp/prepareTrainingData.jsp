
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Prepare Training Data</title>
	<script  type="text/javascript" src = "javascript/buttonDisable.js"></script>
	<c:if test="${message == 'success'}">
		<script>
			alert("File Conversion Successful");
		</script>
	</c:if>
</head>	
<body>
<h1>Prepare Training Data</h1><br>
	<form action="xlToCsv.htm" method="post" enctype="multipart/form-data">
		<input id = "file" name="file" type="file"> <br> <br> 
	
		<input id = "submit" type="submit" value="Convert CSV" disabled>

	</form>
	<table class="display jqueryDataTable" id="csvTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>
				<th>Row Count</th>
				<th>Download</th>
			</tr>
		</thead>
		<tbody>
	</table>
</body>
</html>


