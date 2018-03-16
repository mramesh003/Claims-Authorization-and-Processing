
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Prepare Training Data</title>
	<script  type="text/javascript" src = "javascript/buttonDisable.js"></script>
	<script type="text/javascript" src = "javascript/excelFileExtensionValidation.js"></script>
	<c:if test="${message == 'successUpload'}">
		<script>
			alert("Excel File Saved Successfully");
		</script>
	</c:if>
	<c:if test="${message == 'successConversion'}">
		<script>
			alert("Excel File Converted to CSV Successfully");
		</script>
	</c:if>
	<c:if test="${message == 'deletedSuccessfully'}">
		<script>
			alert("Excel File deleted Successfully");
		</script>
	</c:if>
	<c:if test="${message == 'CannotDeleteExcel'}">
		<script>
			alert("Selected Excel file cannot be deleted as it has CSV dependency");
		</script>
	</c:if>
<script>
	$(document).ready(function(){
	$('#csvTable').DataTable();
 });
 </script>
 <script>
 function jsFunction(a)
 {
	 document.getElementById("language").value = a.value;
 }
 </script>
</head>	
<body>
<h1>Prepare Training Data</h1><br>
	<form action="uploadExcel.htm" method="post" enctype="multipart/form-data">
		<label>Select Excel File :</label>
	
		<input id = "file" name="file" type="file"> <br> <br> 
	
		<input id = "submit" type="submit" value="Upload File" disabled>
		
	</form>
	<br>
	<table class="display jqueryDataTable" id="csvTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>
				<th>Data Count</th>
				<th>Attribute Count</th>
				<th>Download</th>
				<th>Java/Python</th>
				<th>Convert to CSV</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${excelFiles}" var = "excelFiles" varStatus = "loop">
			<tr>
				<td scope="row"><c:out value = "${loop.count }"></c:out></td>
				<td><c:out value = "${excelFiles.fileName}"/></td>
				<td><c:out value = "${excelFiles.rowcount}"/></td> 
				<td><c:out value = "${excelFiles.colCount}"/></td> 
				<td>
					<a href="downloadExcel.htm?id=${excelFiles.id}">
						Download
  					</a>
				</td> 
				<td>
				<select  onchange="jsFunction(this);">
					  <option value="python">Python</option>
					  <option value="java">Java</option>
				</select>
				
				</td>
				
				<td>
					<form action="convertToCsv.htm" method="post">
					<input type="hidden" name="language" id="language" value="python"/>
					<input type="hidden" name="id" id="id" value="${excelFiles.id}"/>
					<%-- <a href="convertToCsv.htm?id=${excelFiles.id}&language=${language}"> Convert</a> --%>
					<button type="submit" value="convert" name="convert">Convert</button>
					</form>
				</td>
				<td>
					<a href="deleteExcel.htm?id=${excelFiles.id}">
					<img src="images/delete.png" alt="delete" style="width:30px;height:28px;border:0;"></a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>


