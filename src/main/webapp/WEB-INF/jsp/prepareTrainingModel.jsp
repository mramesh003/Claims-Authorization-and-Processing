<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Prepare Training Model</title>
<!-- <script type="text/javascript" src = "javascript/buttonDisable.js"></script> -->
<script type="text/javascript" src = "javascript/csvFileExtensionValidation.js"></script>
<c:if test="${message == 'successUpload'}">
	<script>
		alert("Csv File Saved Successfully");
	</script>
</c:if>
<c:if test="${flag == true}">
	<script>
		alert("Csv File converted to ARFF Successfully");
	</script>
</c:if>
<c:if test="${flag == false}">
	<script>
		alert("Error in conversion..Please refer LOG file for more info..");
	</script>
</c:if>
<c:if test="${delete == 'error'}">
	<script>
		alert("Cannot delete CSV..");
	</script>
</c:if>

</head>
<body>
<script>
	$(document).ready(function() {
		$('#csvTable').DataTable();
	});
</script>
	<h1>Prepare Training Model</h1><br>
	<!-- <form action="uploadCsv.htm" method="post" enctype="multipart/form-data">
		<label>Select CSV file:</label><input id = "file" name="file" type="file"> <br> <br> 
		<input id = "submit" type="submit" value="Upload CSV" disabled>
	</form> -->
	<br>
	<table class="display jqueryDataTable" id="csvTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>
				<th>Data Count</th>
				<th>Attribute Count</th>
				<th>Download</th>
				<th>Convert to ARFF</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody style="color:black">
			<c:forEach items="${csvFiles}" var = "csvFiles" varStatus = "loop">
			<tr>
				<td scope="row"><c:out value = "${loop.count }"></c:out></td>
				<td><c:out value = "${csvFiles.fileName}"/></td>
				<td><c:out value = "${csvFiles.rowCount}"/></td>
				<td><c:out value = "${csvFiles.columnCount}"/></td>
				<td>
					<a href="downloadCsv.htm?id=${csvFiles.id}">
						Download
  						<!-- <img src="css/images/Excel.png" alt="Download" style="width:30px;height:28px;border:0;"> -->
					</a>
				</td> 
				
				<td>
					<a href="csvToArff.htm?id=${csvFiles.id}">
						Convert
  						<!-- <img src="css/images/Excel.png" alt="Download" style="width:30px;height:28px;border:0;"> -->
					</a>
				</td> 
				
				<td><a href="deletecsv.htm?id=${csvFiles.id}&page=prepareTrainingModel">				
				<img src="images/delete.png" alt="delete employee" style="width:30px;height:28px;border:0;">
				</a>
				
				</td>
				
				
				
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
