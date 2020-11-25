<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a Review</title>
</head>
<body>
	<h1>Update Review</h1>
	<form action="reviewupdate" method="post">
		<p>
			<label for="reviewId">ReviewID</label>
			<input id="reviewId" name="reviewId" value="${fn:escapeXml(param.reviewId)}">
		</p>
		<p>
			<label for="rating">Rating</label>
			<input id="rating" name="rating" value="${fn:escapeXml(param.rating)}">
		</p>
			<label for="comment">Comment</label>
			<input id="comment" name="comment" value="${fn:escapeXml(param.comment)}">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>