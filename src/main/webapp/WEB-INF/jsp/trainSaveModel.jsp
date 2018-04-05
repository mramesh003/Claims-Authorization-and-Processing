<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save Model</title>
<script language="javascript" type="text/javascript" >
</script>
<script  type="text/javascript" src = "javascript/buttonDisable.js"></script>
<script  type="text/javascript" src = "javascript/arffextensionvalidation.js"></script>

<script>
	$(document).ready(function(){
	$('#arffTable').DataTable();
	$('#csvTable').DataTable();
 });
 </script>

</head>
<body>


	<h1>Train And Save Model</h1><br>
  <%--  <form action="uploadArff.htm" method="post" enctype="multipart/form-data" >
   <c:if test="${message == 'successUpload'}">
<div style="color:#556B2F" ><b> ARFF FILE SAVED SUCCESSFULLY</b>
</div>
</c:if>
<c:if test="${message == 'Deleted'}">
<div style="color:#B22222" ><b> ARFF FILE DELETED SUCCESSFULLY</b>
</div>
</c:if>
<br><br>
		<label>Select ARFF File: </label><input id = "file" name="file" type="file" onchange="ValidateSingleInput(this);"/> <br>
		
		 <br> 
		
		<input id = "submit" type="submit" value="save ARFF"  disabled>
	</form>
	<br> --%>
	<div>
	Java
	<table class="display jqueryDataTable" id="arffTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>	
				<th>Data Count</th>
				<th>Attribute Count</th>
				<th>Download</th>
				<th>Train and save model</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arffFiles}" var = "arffFiles" varStatus = "loop">
			<tr>
				<td scope="row"><c:out value = "${loop.count }"></c:out></td>
				<td><c:out value = "${arffFiles.fileName}"/></td>
				<td><c:out value = "${arffFiles.rowCount}"/></td>
				<td><c:out value = "${arffFiles.colCount}"/></td>
				
				<td>
					<a href="downloadArff.htm?id=${arffFiles.id}">
						Download
  						
					</a>
				</td> 
				<td>
					<a href="trainModel.htm?id=${arffFiles.id}">Train and Save Model</a>
				</td>
				<td><a href="deleteArff.htm?id=${arffFiles.id}">				
				<img src="images/delete.png" alt="delete" style="width:30px;height:28px;border:0;">
				</a>
				
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div>
	Python
	<table class="display jqueryDataTable" id="csvTable">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>	
				<th>Data Count</th>
				<th>Attribute Count</th>
				<th>Download</th>
				<th>Train and save model</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${csvFiles}" var = "csvFiles" varStatus = "loop">
			<tr>
				<td scope="row"><c:out value = "${loop.count }"></c:out></td>
				<td><c:out value = "${csvFiles.fileName}"/></td>
				<td><c:out value = "${csvFiles.rowCount}"/></td>
				<td><c:out value = "${csvFiles.columnCount}"/></td>
				
				<td>
					<a href="downloadCsv.htm?id=${csvFiles.id}">
						Download
  						
					</a>
				</td> 
				<td>
					<a href="executeeModel.htm?id=${csvFiles.id}">Train and Save Model</a>
				</td>
				<td><a href="deletecsv.htm?id=${csvFiles.id}&page=trainSaveModel">				
				<img src="images/delete.png" alt="delete" style="width:30px;height:28px;border:0;">
				</a>
				
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>
