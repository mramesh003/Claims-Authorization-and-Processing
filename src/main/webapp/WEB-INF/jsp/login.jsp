<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/animate.css">
<title>Login</title>
<style type="text/css">
body {
	background: no-repeat center center fixed;
	background-size: cover;
	font-size: 16px;
	font-family: 'Lato', sans-serif;
	font-weight: 300;
	margin: 0;
	color: #222295;
}

h2 {
	text-transform: uppercase;
	color: white;
	font-weight: 400;
	letter-spacing: 1px;
	font-size: 1.4em;
	line-height: 2.8em;
}

a {
	text-decoration: none;
	color: #666;
}

/* Layout */
.container {
	margin: 0;
}

.top {
	margin: 0;
	padding: 0;
	width: 100%;
	background: -moz-linear-gradient(top, rgba(0, 0, 0, 0.6) 0%,
		rgba(0, 0, 0, 0) 100%); /* FF3.6-15 */
	background: -webkit-linear-gradient(top, rgba(0, 0, 0, 0.6) 0%,
		rgba(0, 0, 0, 0) 100%); /* Chrome10-25,Safari5.1-6 */
	background: linear-gradient(to bottom, rgba(0, 0, 0, 0.6) 0%,
		rgba(0, 0, 0, 0) 100%);
	/* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#99000000',
		endColorstr='#00000000', GradientType=0); /* IE6-9 */
}

.login-box {
	background-color: white;
	max-width: 340px;
	margin: 0 auto;
	position: relative;
	top: 80px;
	padding-bottom: 30px;
	border-radius: 5px;
	box-shadow: 0 5px 50px rgba(0, 0, 0, 0.4);
	text-align: center;
}

.login-box .box-header {
	background-color: #064d81;
	margin-top: 0;
	border-radius: 10px 10px 0 0;
}

.login-box label {
	font-weight: 700;
	font-size: .8em;
	color: #064d81;
	letter-spacing: 1px;
	text-transform: uppercase;
	line-height: 2em;
}

.login-box input {
	margin-bottom: 20px;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 2px;
	font-size: .9em;
	color: #064d81;
}

.login-box input:focus {
	outline: none;
	border-color: #dee1e2;
	transition: 0.5s;
	color: black;
}

.button {
	margin-top: 0px;
	border: 0;
	border-radius: 2px;
	padding: 10px;
	text-transform: uppercase;
	font-weight: 400;
	font-size: 0.7em;
	letter-spacing: 1px;
	background-color: #dee1e2;
	cursor: pointer;
	outline: none;
}

.button:hover {
	opacity: 0.7;
	transition: 0.5s;
}

.selected {
	color: #064d81 !important;
	transition: 0.5s;
}

/* Animation Delay */
#logo {
	-webkit-animation-duration: 1s;
	-webkit-animation-delay: 2s;
}

.login-box {
	-webkit-animation-duration: 1s;
	-webkit-animation-delay: 1s;
}
</style>

</head>
<body>
	<div class="container">

		<div class="login-box animated fadeInUp">
			<div class="box-header">
				<h2>Log In</h2>
			</div>
			<form action="login.htm" method="post">
				User Name : <input type="text" name="Username" required /><br>
				<br> Password : <input type="password" name="pw" required /><br>
				<br> <input class="button" type="submit" value="Login" />
			</form>
		</div>
	</div>
</body>
<script>
	$(document).ready(function() {
		$('#logo').addClass('animated fadeInDown');
		$("input:text:visible:first").focus();
	});
	$('#username').focus(function() {
		$('label[for="username"]').addClass('selected');
	});
	$('#username').blur(function() {
		$('label[for="username"]').removeClass('selected');
	});
	$('#password').focus(function() {
		$('label[for="password"]').addClass('selected');
	});
	$('#password').blur(function() {
		$('label[for="password"]').removeClass('selected');
	});
</script>
</html>
