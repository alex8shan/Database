<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  <script src="https://kit.fontawesome.com/c0c58502d4.js" crossorigin="anonymous"></script>
  <link rel = "stylesheet" type = "text/css" href = "css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="UTF-8">
<title>Wishlists</title>
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
        <a class="nav-link" href="rentalview?username=<c:out value="${username}"/>" >Rentals</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="reviewcreate">Write Reviews</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="findtopbooks">TopBooks</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="wishlistcreate">Create Wishlist</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="wishlistdelete">Delete Wishlist</a>
      </li>
    </ul>
    <ul class ="nav navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="logout" style="color: white;">Logout</a>
      </li>
    </ul>
  </div>
</nav>
<h1>WishList of User</h1>
        <table border="1">
            <tr>
                <th>BookId</th>
            </tr>
            <c:forEach items="${wishlist}" var="wishlist" >
                <tr>
                    <td><c:out value="${wishlist.getBook().getBookId()}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>