<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save Model</title>
<script  type="text/javascript" src = "javascript/buttonDisable.js"></script>
<c:if test="${message == 'successUpload'}">
<script>
	alert("Arff File Saved Successfully");
</script>
</c:if>


</head>
<body>
	<h1>Train And Save Model</h1><br>
   <form action="uploadArff.htm" method="post" enctype="multipart/form-data">
		<label>Select ARFF File: </label><input id = "file" name="file" type="file"> <br> <br> 
		<input id = "submit" type="submit" value="save ARFF" disabled>
	</form>
	<br>
	<table class="display jqueryDataTable" id="csvTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>				
				<th>Download</th>
				<th>Train and save model</th>
				<th>Delete</th>
			</tr>
		</thead>

	</table>
</body>
</html>
