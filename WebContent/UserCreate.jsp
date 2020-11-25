<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  <script src="https://kit.fontawesome.com/c0c58502d4.js" crossorigin="anonymous"></script>
  <link rel = "stylesheet" type = "text/css" href = "css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
<div class="login container">
	<h1 class = "text-center">YouBook</h1>
    <h2 class = text-center"> Registration Form</h2>
    <form action = "usercreate" method="post">
		<p class = "text-center">
			<div class="col-md-10 form-group">
  				<input type="text" name="username" placeholder="Username" id="username" required="required" data-error="Please enter your user name.">
			</div>
			<div class="col-md-10 form-group">
  				<input type="text" name="firstname" placeholder="FirstName" id="firstname" required="required" data-error="Please enter your first name.">
			</div>
			<div class="col-md-10 form-group">
  				<input type="text" name="lastname" placeholder="LastName" id="lastname" required="required" data-error="Please enter your last name.">
			</div>
			<div class="col-md-10 form-group">
  				<input type="text" name="email" placeholder="Email" id="emailid" required="required" data-error="Please enter your email.">
			</div>
			<div class="col-md-10 form-group">
  				<input type="text" name="phonenumber" placeholder="Phonenumber" id="phonenumber">
			</div>
			<div class="col-md-10 form-group">
  				<input type="text" name="paypalid" placeholder="Paypal" id="paypalid" required="required" data-error="Please enter your paypal ID.">
			</div>
			<div class="col-md-10 form-group" align="center">
  				<button type="submit" class="btn"> Register</button>
			</div>
			<div class="col-md-10 form-group" align="center">
  				<button type="reset" class="btn btn-new"> <a href = "login">Cancel</a></button>
			</div>
		</p>
	</form>
</div>
</body>
</html>