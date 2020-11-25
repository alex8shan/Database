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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<h2 align = "center" > YouBook Login </h2>
	<div class="container login-container">
        <div class="col-lg-4">
            <form action="login" method="post">
		<div class="form-group" align = "center">
			<input id="username" name="username" placeholder="Username" class="form-control"  value="${fn:escapeXml(param.username)}">
		</div>
		
		<div class="form-group" align = "center">
        <button type="submit" class="btn-login btn">Login</button>
      </div>
      <div class="form-group" align = "center">
        <button type="submit" class="btn btn-new"> <a href = "usercreate">Register</a></button>
      </div>
	</form>
        </div>
    </div>
</div>
	
</body>
</html>