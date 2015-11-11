<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log in</title>
</head>
<body>
	<form class="centered" action="/rznu/logInRequest" method="POST">
		<input id="username" name="username" type="text"><br>
		<input name="password" type="password"><br>
		<input value="Login" type="submit">
		<h6><font color="red"><c:out value="${message}"></c:out></font></h6>
	</form>
</body>
</html>