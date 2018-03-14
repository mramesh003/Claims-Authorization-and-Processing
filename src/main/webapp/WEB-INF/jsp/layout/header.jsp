<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#headerContainer {
	-moz-box-shadow: inset 0px 1px 0px 0px #97c4fe;
	-webkit-box-shadow: inset 0px 1px 0px 0px #97c4fe;
	box-shadow: inset 0px 1px 0px 0px #97c4fe;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #064d81
		), color-stop(1, #064d81));
	background: -moz-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: -webkit-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: -o-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: -ms-linear-gradient(top, #064d81 5%, #064d81 100%);
	background: linear-gradient(to bottom, #064d81 5%, #064d81 100%);
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
	padding: 6px 24px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #1570cd;
	margin: 0px;
}
</style>

<div id="headerContainer">
	<table width="100%">
		<tr>
			<td width="33%" style="vertical-align: middle;"><img height="60"
				src="css/images/accenture-blue.jpg" id="logo" /></td>
			<td width="33%" align="center" style="font-size: 24px"><b>Claims
					Authorization And Processing</b></td>
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
					alt="update employee" style="width: 30px; height: 28px; border: 0;">
			</a></td>
		</tr>
	</table>

</div>