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
<title>Find a User</title>
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
        <a class="nav-link" href="#">Home </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="userupdate">Update User</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="rentalview?username=<c:out value="${username}"/>" style="color: white;">Rentals</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="reviewcreate">Write Reviews</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="wishlistcreate">Create Wishlist</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="findtopbooks">TopBooks</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="userdelete">Delete User</a>
      </li>
    </ul>
    <ul class ="nav navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="logout" style="color: white;">Logout</a>
      </li>
    </ul>
  </div>
</nav>
	<form action="findbooks" method="post">
		<h1>Search for a Book by title</h1>
		<p>
			<label for="firstname">Title</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.title)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
		</p>
	</form>
	
	<h1>Matching Books</h1>
        <table border="1">
            <tr>
                <th>BookId</th>
                <th>Title</th>
                <th>Publisher Name</th>
                <th>Publisher Year</th>
                <th> User </th>
                <th>DeleteWishlist</th>
                <th>UpdateWishlist</th>
                <th>Rent</th>
            </tr>
            <c:forEach items="${books}" var="book" >
                <tr>
                    <td><c:out value="${book.getBookId()}" /></td>
                    <td><c:out value="${book.getTitle()}" /></td>
                    <td><c:out value="${book.getPublisherName()}" /></td>
                    <td><c:out value="${book.getPublicationYear()}" /></td>
                    <td><c:out value="${username}" /></td>
                    <td><a href="bookIddelete?bookId=<c:out value="${book.getBookId()}"/>">Delete</a></td>
                    <td><a href="bookupdate?bookId=<c:out value="${book.getBookId()}"/>">Update</a></td>
                    <td><a href="rentalcreate?bookId=<c:out value="${book.getBookId()}"/>&username=<c:out value="${username}"/>">Rent</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
