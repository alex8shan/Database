<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Created wishList</h1>
<table border="1">
    <tr>
        <th>BookId</th>
        <th>UserName</th>
        <th>DeleteWishlist</th>
        <th>UpdateWishlist</th>
    </tr>
    <c:forEach items="${wishlistbooks}" var="wishlistbook" >
        <tr>
            <td><c:out value="${wishlistbook.getBook().getBookId()}" /></td>
            <td><c:out value="${wishlistbook.getUser().getUserName()}" /></td>
                <%--            <td><a href="wishlistupdate?=<c:out value="${book.getBookId()}"/>">Update</a></td>--%>
            <td><a href="wishlistdelete?bookId=<c:out value="${wishlistbook.getBookId()}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
</body>
</html>
