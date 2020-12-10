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
<body style="background-color: purple">
 <div class="container">
	<div class="card">
      <article class="card-body mx-auto">
			<h4 class="card-title mt-3 text-center">Create Account</h4>
			<p class="text-center">Get started with your free account</p>
    		<form action = "usercreate" method="post">
				<div class="form-group input-group input-group-lg">
					<div class="input-group-prepend">
		    			<span class="input-group-text"> <i class="fa fa-user"></i> </span>
		 			</div>
				  <input type="text" name="username" placeholder="Username" id="username" required="required" data-error="Please enter your user name." style="width: 300px">
				</div>
				<div class="form-group input-group input-group-lg">
					<div class="input-group-prepend">
		    			<span class="input-group-text"> <i class="fa fa-user"></i> </span>
		 			</div>
				  <input type="text" name="firstname" placeholder="FirstName" id="firstname" required="required" data-error="Please enter your first name." style="width: 300px">
				</div>
				<div class="form-group input-group input-group-lg">
					<div class="input-group-prepend">
		    			<span class="input-group-text"> <i class="fa fa-user"></i> </span>
		 			</div>
				  <input type="text" name="lastname" placeholder="LastName" id="lastname" required="required" data-error="Please enter your last name." style="width: 300px">
				</div>
				<div class="form-group input-group input-group-lg">
    				<div class="input-group-prepend">
		    			<span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
		 			</div>
  					<input type="text" name="email" placeholder="Email" id="emailid" required="required" data-error="Please enter your email." style="width: 300px">
				</div>
			<div class="form-group input-group input-group-lg">
    			<div class="input-group-prepend">
		    		<span class="input-group-text"> <i class="fa fa-phone"></i> </span>
				</div>
  				<input type="text" name="phonenumber" placeholder="Phonenumber" id="phonenumber" style="width: 300px">
			</div>
			<div class="form-group input-group input-group-lg">
    			<div class="input-group-prepend">
					<span class="input-group-text"><i class="fab fa-paypal"></i></span>
  						<input type="text" name="paypalid" placeholder="Paypal" id="paypalid" required="required" data-error="Please enter your paypal ID." style="width: 300px">
				</div>
			</div>
			<div class="form-group" align="center">
  				<button type="submit" class="btn btn-register"> Register</button>
			</div>
			<div class="form-group" align="center">
  				<button type="reset" class="btn btn-new"> <a href = "login">Cancel</a></button>
			</div>
		</form>
	</article>
  </div>
</div>
</body>
</html>