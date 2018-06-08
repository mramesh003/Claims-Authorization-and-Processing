<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ClaimsAuthorizationAndProcessing</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.css"/>
 <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>

<style>
	/* Set height of the grid so .sidenav can be 100% (adjust if needed) */
	.row.content {
		 height: 100%;
	}
	
	/* Set gray background color and 100% height */
	.sidenav {
		min-height: 100%;
		height: 100%;
	} 
	
	/* On small screens, set height to 'auto' for sidenav and grid */
	@media screen and (max-width: 767px) {
		.sidenav {
			height: auto;
		}
		.row.content {
			height: auto;
		}
	} 
</style>
</head>
<body style = "background-image:url(images/blue.jpg);color:white;">
	<div class="container-fluid">
		<div>
			<tiles:insertAttribute name="header" />
		</div>
		<div class="row content">
			<div class="col-sm-3 sidenav">
				<tiles:insertAttribute name="menu" />
			</div>
			<div class="col-sm-9">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>