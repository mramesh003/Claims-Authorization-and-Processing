<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connect To Python</title>

</head>
<body>
	<h1>Connect To Python</h1>
	<br>
	<form action="executeeModel.htm?id=1" method="post"
		enctype="multipart/form-data">

		<input id="submit" type="submit" value="Connect to Python">
	</form>
	<div id="result">
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
