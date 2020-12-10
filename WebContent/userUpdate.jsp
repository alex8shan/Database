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
<title>Update a User</title>
</head>
<body style="background-color: gray">
<nav class="navbar navbar-expand-md navbar-dark" style="background-color: maroon;">
  <a class="navbar-brand" href="#">YouBook</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#target_collapse">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="target_collapse" >
    <ul class="nav navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="findtopbooks">Home </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="userupdate">User Info</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="rentalview?username=<c:out value="${username}"/>" >Rentals</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="wishlistview?username=<c:out value="${username}"/>">Wishlist</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="recommendation?username=<c:out value="${username}"/>">Recommendation</a>
      </li>
      <li class="nav-item">
      	 <form class="form-inline my-2 my-lg-0" action="findbooks" method="post" style="padding-left: 50px">
			<input class="form-control mr-sm-2" id="firstname" name="firstname" placeholder="Search for title" value="${fn:escapeXml(param.title)}">
			<button class="btn btn-outline-light btn-md my-2 my-sm-0" type="submit">Search</button>
		</form>
      </li>
    </ul>
    <ul class ="nav navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="login" style="color: white;">Logout</a>
      </li>
    </ul>
  </div>
</nav>
<div class="container">
	<div class="card">
      <article class="card-body mx-auto">
      	<h4 class="card-title mt-3 text-center">Update User Information</h4>
		<form action="userupdate" method="post">
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
				  <input type="text" name="firstname" placeholder="FirstName" id="firstname"  data-error="Please enter your first name." style="width: 300px">
				</div>
				<div class="form-group input-group input-group-lg">
					<div class="input-group-prepend">
		    			<span class="input-group-text"> <i class="fa fa-user"></i> </span>
		 			</div>
				  <input type="text" name="lastname" placeholder="LastName" id="lastname"  data-error="Please enter your last name." style="width: 300px">
				</div>
				<div class="form-group input-group input-group-lg">
    				<div class="input-group-prepend">
		    			<span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
		 			</div>
  					<input type="text" name="email" placeholder="Email" id="emailid"  data-error="Please enter your email." style="width: 300px">
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
  						<input type="text" name="paypalid" placeholder="Paypal" id="paypalid"  data-error="Please enter your paypal ID." style="width: 300px">
				</div>
			</div>
			<div class="form-group" align="center">
  				<button type="submit" class="btn"> Submit</button>
			</div>
	</form>
	</article>
	</div>
	</div>
</body>
</html>