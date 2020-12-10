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
<title>Show Top Books</title>
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
<div class = "container">
	<div class="page-header" style="padding-top: 20px">
		<h1 class="text-center"> Welcome to YouBook!</h1>
	</div>
	<div class = "container" style="padding-bottom: 20px" >
		<div style="padding-top: 20px"> YouBook is a Book Rental Service designed to help users discover the joy of reading at affordable prices. Users can easily pick the book of their choice from our enviable collection.</div>
	</div>
	<div>
		<h4 class="text-center" style="padding-bottom: 20px">Top 10 Books</h4>
	</div>
	<div>
		<h6 class="text-center" style="padding-bottom: 20px"> Here's top 10 books for you to get started with:  </h6>
	</div>
        <table class="table table-striped table-bordered table-hover table-condensed">
            <tr>
                <th>BookId</th>
                <th>Title</th>
                <th>Publisher Name</th>
                <th>Publisher Year</th>
                <th>Add to Wishlist</th>
                <th>Rent this book</th>
            </tr>
            <c:forEach items="${books}" var="book" >
                <tr>
                    <td><c:out value="${book.getBookId()}" /></td>
                    <td><c:out value="${book.getTitle()}" /></td>
                    <td><c:out value="${book.getPublisherName()}" /></td>
                    <td><c:out value="${book.getPublicationYear()}" /></td>
                    <td><a href="wishlistcreate?bookId=<c:out value="${book.getBookId()}"/>&username=<c:out value="${username}"/>">Update</a></td>
                    <td><a href="rentalcreate?bookId=<c:out value="${book.getBookId()}"/>&username=<c:out value="${username}"/>">Rent</a></td>
                </tr>
            </c:forEach>
       </table>
 </div>
</body>
</html>
