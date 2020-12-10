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

<div class = "container">
  <div class="card">
	<h4 class="text-center" style="padding-top: 20px; padding-bottom: 20px">Your Wishlist</h4>
	<div style="padding-bottom: 50px">
	<c:if test="${fn:length(wishlists) > 0}">    
		<p class="text-center"> Check out the books that you wish for! </p>
		<table class="table table-bordered table-hover table-condensed" style="width: 400px; margin: 0px auto;">
            <tr>
                <th>Book Title</th>
                <th>Delete from WishList</th>
            </tr>
            <c:forEach items="${wishlists}" var="wishlist" >
                <tr>
                    <td><c:out value="${wishlist.getBook().getTitle()}" /></td>
                    <td><a href="wishlistdelete?bookId=<c:out value="${wishlist.getBook().getBookId()}"/>&username=<c:out value="${username}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
	</c:if>
	<c:if test="${fn:length(wishlists) == 0}"> 
	<div class="text-center" style="padding-top: 20px; padding-bottom: 20px"> Oops... this wishlist is not yet filled. Fill in with your favorites!</div>
	</c:if>
	</div>   
   </div>
 </div>
</body>
</html>