<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty delay}">
	<c:set var="delay" value="3000" />
</c:if>
<c:if test="${empty adress}">
	<c:set var = "adress" value = "http://localhost:8080/api/" />
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
	function redirect() {
		window.location = "<c:out value = "${adress}"/>";
	}
</script>
</head>
<body>
	<h4>
		<c:forEach items="${messages}" var="item">
    		${item}<br>
		</c:forEach>
	</h4>
	<small> You should be automatically redirected from this page
		in few seconds... If you are not getting redirected please follow this
		<a href="${adress}">link</a>
	</small>
	<script type="text/javascript">
		setTimeout(redirect, <c:out value="${delay}"/>);
	</script>
</body>
</html>