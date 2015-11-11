<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Posts</title>
<style type="text/css">
	td {
		text-align: center;
	}
</style>
</head>

<t:userpage>
	<jsp:body>
		<table border="1" style="width: 100%">
		  <tr>
		    <th>User</th>
		    <th>Post</th>
		    <th>Action</th>
		  </tr>
		  <c:forEach items="${posts}" var="post">
				<tr>
					<td><c:out value="${ post.username }"></c:out></td>
					<td align = "left"><c:out value="${ post.postText }"></c:out></td>
					<td> - </td>
				</tr>
		  </c:forEach>
		</table>
	</jsp:body>
</t:userpage>