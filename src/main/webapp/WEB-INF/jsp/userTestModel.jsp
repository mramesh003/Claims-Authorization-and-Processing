<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src = "javascript/buttonDisable.js"></script>
<title>User Test Model</title>
</head>
<body>
	<form action="evaluateExcelWithModel.htm" method="post"
		enctype="multipart/form-data">
		<label>Select Excel File :</label> <input id="file" name="file"
			type="file"> <br> <br> <input id="submit"
			type="submit" value="Test" disabled>

	</form>
	<br>
	<Table border="1">
		<tr>
			<th> Actual Class </th>
			<th> Predicted Class </th>
		</tr>
		<c:forEach items="${results}" var="result">
			<c:set var="data" value="${fn:split(result, ',')}" />
			<tr>
				<td><c:out value="${data[0]}" /></td>
				<td><c:out value="${data[1]}" /></td>
			</tr>
		</c:forEach>
	</Table>
</body>
</html>