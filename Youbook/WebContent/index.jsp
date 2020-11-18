<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="findbooks" method="post">
		<h1>Search for a Book by title</h1>
		<p>
			<label for="firstname">Title</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.title)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="wishlistCreate"><a href="wishlistcreate">CreateWishlist</a></div>
	<div id=""><a href="findtopbooks">TopBooks</a></div>
	<br/>
	<h1>Matching Books</h1>
        <table border="1">
            <tr>
                <th>BookId</th>
                <th>Title</th>
                <th>Publisher Name</th>
                <th>Publisher Year</th>
                <th>DeleteWishlist</th>
                <th>UpdateWishlist</th>
            </tr>
            <c:forEach items="${books}" var="books" >
                <tr>
                    <td><c:out value="${book.getBookId()}" /></td>
                    <td><c:out value="${book.getTitle()}" /></td>
                    <td><c:out value="${book.getPublisherName()}" /></td>
                    <td><c:out value="${book.getPublicationYear()}" /></td>
                    <td><a href="bookIddelete?bookId=<c:out value="${book.getBookId()}"/>">Delete</a></td>
                    <td><a href="bookupdate?bookId=<c:out value="${book.getBookId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
