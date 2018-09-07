<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save Model</title>
<script language="javascript" type="text/javascript">
</script>
<!-- <script  type="text/javascript" src = "javascript/buttonDisable.js"></script> -->
<script type="text/javascript"
	src="javascript/arffextensionvalidation.js"></script>

<script>

	$(document).ready(function(){
		var inputs = $(".denied");
		var init = [];
		for (var i = 0; i < inputs.length; i++) {
			init.push($(inputs[i]).val());
		}
		
		if(init.length==0){
			$('#btn_convert').attr("disabled", true);
			document.getElementById("btn_convert").style.color = "black";
    		//document.getElementById("submit").style.background = "#000";
    		document.getElementById("btn_convert").style.background = null;
    		document.getElementById("btn_convert").style.opacity = ".5";
		}
		 $(document).ajaxSend(function(event, request, settings) {
			  $('#loading-indicator').show();
			});

			$(document).ajaxComplete(function(event, request, settings) {
			  $('#loading-indicator').hide();
			}); 
			
			
	$('#arffTable').DataTable();
	$('#csvTable').DataTable();
	
	
	    $('#csvTable').on('click', '.convert', function(){
		   butId = $(this).attr('id');
          var lastChar = butId.substr(7);
          var selectId = "select_"+lastChar;
          var language = 'Python';
          var id= $("#"+butId).val();
          var init = [];
      
		init[0]=id;
      
          var id2 =JSON.stringify(init);
        
          $.ajax({
              type: "POST",
              url: "executeeModel.htm",
             dataType: 'text',
  			   data:{id:id2,
  				language : language
  			},
              success: function (result) {
            	 /*  $("#div1")
					.fadeTo(1000, 100)
					.slideUp(
							350,
							function() {
								$("#div1")
										.slideUp(
												350);
							}); */
							$("#div3").hide();
							$("#div2").hide();
							$("#div4").hide();
							
			$("#div1").show();
              },
              error: function (result) {
            	  /* $("#div2")
					.fadeTo(1000, 100)
					.slideUp(
							350,
							function() {
								$("#div2")
										.slideUp(
										
												350);
							}); */
							$("#div1").hide();
							$("#div3").hide();
							$("#div4").hide();
							
			$("#div2").show();
              }
          });
		});
	    
	 $("#btn_convert").click(function() {
 		
			var inputs = $(".denied");
			var init = [];
			for (var i = 0; i < inputs.length; i++) {
				init.push($(inputs[i]).val());
			}
			var id = JSON.stringify(init);
			//alert(id);
		
		//	$.post( "executeeModel.htm");
			 $.ajax({
                 type: "POST",
                 url: "executeeModel.htm",
                dataType: 'text',
     			   data:{id:id,
     				 
     			},
                 success: function (result) {
							/* $("#div1")
									.fadeTo(1000, 100)
									.slideUp(
											350,
											function() {
												$("#div1")
														.slideUp(
																350);
											}); */$("#div3").hide();
											$("#div2").hide();
											$("#div4").hide();
											
							$("#div1").show();
                 },
                 error: function (result) {
                	 /* $("#div2")
						.fadeTo(1000, 100)
						.slideUp(
								350,
								function() {
									$("#div2")
											.slideUp(
													350);
								}); */
								$("#div1").hide();
								$("#div3").hide();
								$("#div4").hide();
								
				$("#div2").show();
  },
                
             
			//window.location.href = 'executeeModel.htm?id='+ id+ '';
	 });
	
	
 });
	 $(".delete").click(function(){
		 $("#var1").val(this.value);
		 $("#form").submit();
	 });
	});
/* 	    $('#csvTable').on('click', '.delete', function(){
			   butId = $(this).attr('id');
	          var lastChar = butId.substr(7);
	          var selectId = "select_"+lastChar;
	          var language = 'Python';
	          var id= $("#"+butId).val();
	          var init = [];
	      
			init[0]=id;
	      
	          var id2 =JSON.stringify(init);
	        
	          $.ajax({
	              type: "POST",
	              url: "deletecsv.htm",
	             dataType: 'text',
	  			   data:{id:id2,
	  				language : language
	  			},
	              success: function (result) {
	            	  $("#div1")
						.fadeTo(1000, 100)
						.slideUp(
								350,
								function() {
									$("#div1")
											.slideUp(
													350);
								});
				$("#div1").show();
	              },
	              error: function (result) {
	            	  $("#div2")
						.fadeTo(1000, 100)
						.slideUp(
								350,
								function() {
									$("#div2")
											.slideUp(
													350);
								});
				$("#div2").show();
	              }
	          });
			});
	     */
	    
	

 </script>
<c:if test="${delete == 'error'}">
	<script>
		alert("Model file is saved to DB.,so cannot delete ARFF..");
	</script>
</c:if>
<c:if test="${deleteCsv == 'error'}">
	<script>
	$(document).ready(function(){
	 /* $("#div4")
		.fadeTo(1000, 100)
		.slideUp(
				350,
				function() {
					$("#div4")
							.slideUp(
									350);
				}); */
				$("#div1").hide();
				$("#div2").hide();
				$("#div3").hide();
				
$("#div4").show();
	});
	</script>
</c:if>
<c:if test="${deleteCsv == 'success'}">
	<script>
	$(document).ready(function(){
/* 	 $("#div3")
		.fadeTo(1000, 100)
		.slideUp(
				350,
				function() {
					$("#div3")
							.slideUp(
									350);
				}); */
				$("#div1").hide();
				$("#div2").hide();
				$("#div4").hide();
				
$("#div3").show();
	});
	</script>
</c:if>
<c:if test="${message == 'SuccessTrain'}">
	<script>
		alert("Model is trained and Saved Successfully for the file");
	</script>
</c:if>

</head>
<body>


	<h1>Train And Save Model</h1>
	<br>
	<div>
		<center>
			<img src="images/ajax-loader (1).gif" id="loading-indicator"
				style="display: none" />
		</center>

	</div>

	<div id="div1" class="alert alert-success" style="display: none;">
		<strong>Model is trained and Saved Successfully for the file</strong>
	</div>

	<div id="div2" class="alert alert-warning" style="display: none;">
		<strong>Model failed to be trained and saved for the file</strong>
	</div>
	<div id="div3" class="alert alert-success" style="display: none;">
		<strong>Successfully deleted</strong>
	</div>
	<div id="div4" class="alert alert-warning" style="display: none;">
		<strong>Model file is saved to DB.,so cannot delete CSV</strong>
	</div>


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
	<%-- <div>
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
		<tbody style="color:black;">
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
	</div> --%>
	<div>

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
			<tbody style="color: black;">
				<c:forEach items="${csvFiles}" var="csvFiles" varStatus="loop">
					<tr>
						<td scope="row"><c:out value="${loop.count }"></c:out></td>
						<td><c:out value="${csvFiles.fileName}" /></td>
						<td><c:out value="${csvFiles.rowCount}" /></td>
						<td><c:out value="${csvFiles.columnCount}" /></td>

						<td><a href="downloadCsv.htm?id=${csvFiles.id}"> Download

						</a></td>


						<%-- <td>
					<a href="executeeModel.htm?id=[${csvFiles.id}]">Train and Save Model</a>
				</td> --%>

						<td><Button class="convert" id="button_${loop.count}"
								value="${csvFiles.id}" onkeypress="">Train and Save Model</Button></td>




						<td><%-- <a
							href="deletecsv.htm?id=${csvFiles.id}&page=trainSaveModel"> <img
								src="images/delete.png" alt="delete"
								style="width: 30px; height: 28px; border: 0;">
						</a> --%> <input id="delete1_${loop.count}" class="delete" class="denied" type="image" src="images/delete.png" alt="delete" value="${csvFiles.id}" style="width: 30px; height: 28px; border: 0;">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:forEach items="${csvFiles}" var="csvFiles" varStatus="loop">
			<input type="hidden" name="excelid" value="${csvFiles.id}"
				class="denied">
		</c:forEach>
		<center>
			<Button style="color: black" class="convert" id="btn_convert"
				class="btn btn-primary">Train and Save All Model</Button>
		</center>
<form style="display: hidden" action="deletecsv.htm" method="POST" id="form">
  <input type="hidden" id="var1" name="var1" value=""/>
   <input type="hidden" id="page" name="page" value="trainSaveModel"/>

</form>
	</div>
</body>
</html>
