<%@page import="java.util.Collections"%>
<%@page import="hr.fer.rznu.json.JSONViewFromater"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty delay}">
	<c:set var="delay" value="3000" />
</c:if>
<c:if test="${empty adress}">
	<c:set var = "adress" value = "/rznu" />
</c:if>

<c:choose>
<c:when test="${ param.format != null and param.format.equals(format) }">
	<% response.setContentType("application/json"); %>
	<% JSONViewFromater.format(Collections.singletonMap("messages", request.getAttribute("posts")), response); %>
</c:when>
<c:otherwise>
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
</c:otherwise>
</c:choose>