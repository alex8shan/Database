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
<title>Create a User</title>
</head>
<body>
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
        <a class="nav-link" href="findbooks">FindBooks</a>
      </li>
    </ul>
    <ul class ="nav navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="login" style="color: white;">Logout</a>
      </li>
    </ul>
  </div>
</nav>
	<h1>Create Review</h1>
	<form action="reviewcreate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="">
		</p>
		<p>
			<label for="bookId">BookId</label>
			<input id="bookId" name="bookId" value="">
		</p>
		<p>
			<label for="rating">Rating</label>
			<input id="rating" name="rating" value="">
		</p>
		<p>
			<label for="comment">Comment</label>
			<input id="comment" name="comment" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
</body>
</html>