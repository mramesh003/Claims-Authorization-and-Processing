
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Accordian - Prototype</title>

</head>
<body>
	<div class="panel-group" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapse1"><span
							class="glyphicon glyphicon-folder-close"> </span>&nbsp;Menus</a>
					</h4>
				</div>

				<div id="collapse1" class="panel-collapse collapse">
					<div class="panel-body">
						<table class="table">
									<tr>
										<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
											href="<c:url value="prepareTrainData.htm"/>">&nbsp;Prepare Training Data</a></td>
									</tr>
									
									<tr>
										<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
											href="<c:url value="prepareTrainingModel.htm"/>">&nbsp;Prepare Training Model</a></td>
									</tr>
									
									<tr>
										<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
											href="<c:url value="trainSaveModel.htm"/>">&nbsp;Train & Save Model</a></td>
									</tr>
									
									<tr>
										<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
											href="<c:url value="testModel.htm"/>">&nbsp;Test Model</a></td>
									</tr>
									
									<tr>
										<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
											href="<c:url value="executeData.htm"/>">&nbsp;Execute Data</a></td>
									</tr>
									
									<tr>
										<td><span class="glyphicon glyphicon-pencil text-primary"></span><a
											href="<c:url value="executeBatchData.htm"/>">&nbsp;Execute Batch Data</a></td>
									</tr>
						</table>
					</div>
				</div>
			</div>
	</div>

</body>
</html>