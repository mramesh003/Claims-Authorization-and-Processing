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
<style>
 #headerContainer {
	-moz-box-shadow: inset 0px 1px 0px 0px #97c4fe;
	-webkit-box-shadow: inset 0px 1px 0px 0px #97c4fe;
	box-shadow: inset 0px 1px 0px 0px #97c4fe;
	background-position: center;-webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
	/* background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #064d81
		), color-stop(1, #064d81));
	background: -moz-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: -webkit-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: -o-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: -ms-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: linear-gradient(to bottom, #064d81 5%, #064d81 100%); */
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#064d81',
		endColorstr='#064d81', GradientType=0);
	background-color: #064d81;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #337fed;
	display: block;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	/* padding: 6px 24px; */
	text-decoration: none;
	text-shadow: 0px 1px 0px #1570cd;
	margin: 0px;
} 
body, html {
    height: 100%;
}
#bg
{
/* The image used */
    background-image:  url(images/robotichand1.jpg);

    /* Full height */
    height: 100%; 

    /* Center and scale the image nicely */
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
}
</style>
</head>
<body >
<div id="headerContainer">
	<table width="100%">
		<tr>
			<td width="33%" style="vertical-align: middle;"><img height="60"
				src="css/images/accenture-blue.jpg" id="logo" /></td>
			<td width="33%" align="center" style="font-size: 24px"><b>Claims
					AI Prediction Engine</b></td>
			<!-- <td width="33%" align="right">
				<table>
					<tr>
						<td align="right" style="font-size: 14px">Employee Id: </td>
						<td align="left" style="font-weight:800;"></td>
					</tr>
					<tr>
						<td align="right" style="font-size: 14px">Role: </td>
						<td align="left" style="font-weight:800;"></td>
					</tr>
					
					
				</table>
			</td> -->
			<%-- <td width="33%" align="right">
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
					
	--%>
	
			<td width="33%" align="right"><a href="logout.htm"> <img src="css/images/logout.jpg"
					 style="width: 30px; height: 28px; border: 0;">
			</a></td>
		</tr>
	</table>

</div>
<div id="bg">
	<br>
	<h3 align="center"><b>Final Result</b></h3>
<br>
	<div >
	<table width="100%" align="center"  cellpadding="2px">
	<tr>
		<td width="4%"></td>
	<td><table width="50%" align="center" class="table table-condensed">
		<tr class="active">
			<td width="25%">Total Number of claims tested</td>
			<td width="15%">${numberOfTestClaims}</td>
			
		<tr class="success">
			<td >Number of claims Accepted</td>
			<td >${acceptCount}</td>
		<tr class="warning">
			<td >Number of claims Pended</td>
			<td>${pendCount}</td>
		<tr class="danger">
			<td>Number of claims Rejected</td>
			<td>${rejectCount}</td>
		</tr>
		<tr class="active">
			<td>Accuracy</td>
			<td>${accScore}</td>
		</tr>
		
		<tr>
		<br>
		<td align="center" colspan="2">
		<a style="background-color:white;color:black;text-decoration: none;" href="generateReport.xls"> ViewReport </a>
		</td></tr>
	</table></td>
	
	<td><table width="50%" align="center">
	<tr>
		<td align="center"><img style="opacity: 0.9" src="data:image/png;base64,${imagefile}" height="400px" width="500px"  /></td>
	</tr>
	</table>
	</td>
	</tr>
	
	</table>
	
	
	</div>
	
	
	
</div>
</body>
</html>