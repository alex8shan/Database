<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create a WishList</title>
</head>
<body>
<h1>Create WishList</h1>
<form action="wishlistcreate" method="post">
    <p>
        <label for="bookid">BookID</label>
        <input id="bookid" name="bookid" value="">
    </p>
    <p>
        <label for="username">UserName</label>
        <input id="username" name="username" value="">
    </p>
    <p>
        <input type="submit">
    </p>
</form>
<br/><br/>
<p>
    <span id="successMessage"><b>${messages.success}</b></span>
</p>

<h1>Created wishList</h1>
<table border="1">
    <tr>
        <th>BookId</th>
        <th>UserName</th>
        <th>DeleteWishlist</th>
        <th>UpdateWishlist</th>
    </tr>
    <c:forEach items="${wishlists}" var="wishlists" >
        <tr>
            <td><c:out value="${wishlists.getBook().getBookId()}" /></td>
            <td><c:out value="${wishlists.getUser().getUserName()}" /></td>
<%--            <td><a href="wishlistupdate?=<c:out value="${book.getBookId()}"/>">Update</a></td>--%>
            <td><a href="wishlistdelete?bookId=<c:out value="${wishlists.getBook().getBookId()}"/>&username=<c:out value="${wishlists.getUser().getUserName()}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>