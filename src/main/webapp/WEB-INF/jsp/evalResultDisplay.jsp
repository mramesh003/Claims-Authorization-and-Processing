<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evaluation Results</title>
</head>
<body>
	<br>
	<h3>Final Result</h3>
	<table class="table table-condensed">
		<tr class="active">
			<td>Total Number of claims tested</td>
			<td>${numberOfTestClaims}</td>
		<tr class="success">
			<td>Number of claims Accepted</td>
			<td>${acceptCount}</td>
		<tr class="warning">
			<td>Number of claims Pended</td>
			<td>${pendCount}</td>
		<tr class="danger">
			<td>Number of claims Rejected</td>
			<td>${rejectCount}</td>
		</tr>
	</table>
	<a href="generateReport.xls"> ViewReport </a>
</body>
</html>