<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Prepare Training Model</title>
<script type="text/javascript" src = "javascript/buttonDisable.js"></script>
<c:if test="${message == 'successUpload'}">
		<script>
			alert("Csv File Saved Successfully");
		</script>
	</c:if>
	<script>
	$(document).ready(function(){
	$('#csvTable').DataTable();
 });
 </script>
</head>
<body>
	<h1>Prepare Training Model</h1><br>
	<form action="csvToArff.htm" method="post" enctype="multipart/form-data">
		<label>Select CSV file:</label><input id = "file" name="file" type="file"> <br> <br> 
		<input id = "submit" type="submit" value="Convert ARFF" disabled>
	</form>
	<br>
	<table class="display jqueryDataTable" id="csvTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>
				<th>Row Count</th>
				<th>Download</th>
				<th>Convert to CSV</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${CsvFiles}" var = "CsvFiles" varStatus = "loop">
			<tr>
				<td scope="row"><c:out value = "${loop.count }"></c:out></td>
				<td><c:out value = "${CsvFiles.fileName}"/></td>
				<td></td>
				<td>
					<a href="downloadCsv.htm?id=${CsvFiles.id}">
						Download
  						<!-- <img src="css/images/Excel.png" alt="Download" style="width:30px;height:28px;border:0;"> -->
					</a>
				</td> 
				
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
