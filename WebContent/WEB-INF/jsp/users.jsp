<%@page import="java.util.Collections"%>
<%@page import="hr.fer.rznu.json.JSONViewFromater"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
<c:when test="${ param.format != null and param.format.equals(\"json\") }">
	<% response.setContentType("application/json"); %>
	<% JSONViewFromater.format(Collections.singletonMap("users", request.getAttribute("users")), response); %>
</c:when>
<c:otherwise>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<t:userpage>
	<jsp:body>
		<h3> Users: </h3>
		<ul>	
		<c:forEach items="${users}" var="user">
				<li><a href = "/rznu/users/<c:out value="${ user.username }" />/posts"><c:out value="${ user.username }" /></a></li>
		</c:forEach>	
		</ul>
	</jsp:body>
</t:userpage>
</c:otherwise>
</c:choose>