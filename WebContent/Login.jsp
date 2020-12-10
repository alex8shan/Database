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
<title>Login</title>
</head>
<body id ="login-cover">

<form class="text-center" action="login" method="post">
<p class="h4 mb-4" style="padding-top:50px;">YouBook Sign in</p>
	<div class="md-8" align = "center">
		<div class="form-group col-sm-4" align = "center">
			<input id="username" name="username" placeholder="Username" class="form-control mb-4"  value="${fn:escapeXml(param.username)}">
		</div>
		
		<div class="form-group" align = "center">
        <button type="submit" class="btn btn-register btn-info btn-md">Login</button>
      </div>
      <div class="form-group" align = "center">
      <p>Not a member?
       <a href = "usercreate">Register </a>
       </p>
      </div>
      </div>
</form>

</body>
</html>