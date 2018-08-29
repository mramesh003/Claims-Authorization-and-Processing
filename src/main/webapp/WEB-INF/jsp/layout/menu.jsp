
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body >
	<div class="panel-group" id="accordion">
		<div class="panel panel-default" style = "background-image:url(images/blue.jpg);color:white;">
			<div class="panel-heading" style = "background-image:url(images/blue.jpg);color:white;">
				<h4 class="panel-title">
					<span class="glyphicon glyphicon-folder-close"> </span> <b>Menus</b>

				</h4>
			</div>

			<!--  <div id="collapse1" class="panel-collapse collapse"> -->
			<div id="accordion" class="panel-group">
				<div class="panel-body">
					<table class="table">
						<c:set var="User" value="${user}" />
						<c:if test="${User.roleId == 1}">
							<tr>
								<td><span class="glyphicon glyphicon-pencil text-primary"></span><a style="color:white"
									href="<c:url value="prepareTrainData.htm"/>">&nbsp;Prepare
										Training Data</a></td>
							</tr>

							<%-- <tr>
								<td><span class="glyphicon glyphicon-pencil text-primary"></span><a style="color:white"
									href="<c:url value="prepareTrainingModel.htm"/>">&nbsp;Prepare
										Training Model</a></td>
							</tr>
 --%>
							<tr>
								<td><span class="glyphicon glyphicon-pencil text-primary"></span><a style="color:white"
									href="<c:url value="trainSaveModel.htm"/>">&nbsp;Train &
										Save Model</a></td>
							</tr>

							<%-- <tr>
								<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
									href="<c:url value="testModel.htm"/>">&nbsp;Test Model</a></td>
							</tr>

							<tr>
								<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
									href="<c:url value="executeData.htm"/>">&nbsp;Execute Data</a></td>
							</tr>

							<tr>
								<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
									href="<c:url value="executeBatchData.htm"/>">&nbsp;Execute
										Batch Data</a></td>
							</tr> --%>
						</c:if>
						<%-- <tr>
							<td><span class="glyphicon glyphicon-pencil text-primary"></span><a style="color:white"
								href="<c:url value="userTestModel.htm"/>">&nbsp;User Test
									Model</a></td>
						</tr> --%>
					</table>
				</div>
			</div>
		</div>
	</div>

</body>
</html>