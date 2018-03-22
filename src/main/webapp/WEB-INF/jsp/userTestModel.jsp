<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/buttonDisable.js"></script>
<title>User Test Model</title>

<script>
$(document).ready(function(){
       $("#result").hide();
       $("#result1").hide();
    });

</script>
<c:if test="${resultpage == 'yes' and isjava == 'yes'}">
		<script>
		$(document).ready(function(){
			$("#result").show();
		 });
		</script>
</c:if>
<c:if test="${resultpage == 'yes' and isjava == 'no'}">
		<script>
		$(document).ready(function(){
			$("#result1").show();
		 });
		</script>
	</c:if>	
</head>
<body>
	<form name="evaluate" action="evaluateResults.xls" method="post"
		enctype="multipart/form-data">
		<label>Select Excel File :</label> <input id="file" name="file"
			type="file">
			 <br> <br>
			 <label>Language : </label><input type="radio" name="language" value="python" checked> Python
  										<input type="radio" name="language" value="java"> Java<br>
			  <input id="submit"
			type="submit" value="Test" disabled>

	</form>
	<br>
	<div id="result">
		<div style="float: left; width: 20%">
			<Table border="1">
				<tr>
					<th>Actual Class</th>
					<th>Predicted Class</th>
				</tr>
				<c:forEach items="${results}" var="result">
					<c:set var="data" value="${fn:split(result, ',')}" />
					<tr>
						<td><c:out value="${data[0]}" /></td>
						<td><c:out value="${data[1]}" /></td>
					</tr>
				</c:forEach>
			</Table>
		</div>
		<div style="float: right; width: 70%">
			<Table border="1">
				<c:forEach var="eval" items="${evaluationResult}">
					<tr>
						<td>${eval.key}</td>
						<td>${eval.value}</td>
					</tr>

				</c:forEach>
			</Table>
		</div>
	</div>
	
	<div id="result1">
		<div style="float: left; width: 20%">
			<Table border="1">
				<tr>
					<th>Billing National Provider Identification Number</th>
					<th>Insured Policy Number for Subscriber</th>
					<th>Subscriber State</th>
					<th>Subscriber Postal Code</th>
					<th>Subscriber Birth Date</th>
					<th>Payer Identification</th>
					<th>Patient State</th>
					<th>Patient Zip Code</th>
					<th>Patients Birth Date</th>
					<th>Facility Type Code</th>
					<th>Claim Transaction Type</th>
					<th>Statement From and Statement Through Date</th>
					<th>Principal Diagnosis Code</th>
					<th>Admitting Diagnosis Code</th>
					<th>Attending Provider NPI</th>
					<th>Rendering Provider NPI</th>
					<th>Reffering provider NPI</th>
					<th>Revenue Code</th>
					<th>CPT Procedure Code</th>
					<th>Procedure Modifier 1</th>
					<th>Procedure Modifier 2</th>
					<th>Line Item Charge Amount</th>
					<th>Service Unit Count</th>
					<th>Service Date</th>
					<th>Service Facility Provider ID</th>
				</tr>
				<c:forEach items="${ListOfClaims}" var="Claims">
					<tr>
						<c:forEach items="${Claims}" var="Claim">

							<td><c:out value="${Claim}" /></td>
						</c:forEach>
					</tr>

				</c:forEach>
			</Table>
		</div>
	</div>
</body>
</html>