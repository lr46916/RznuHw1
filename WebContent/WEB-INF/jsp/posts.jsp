<%@page import="java.util.Collections"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Collection"%>
<%@page import="hr.fer.rznu.json.JSONViewFromater"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="format" value = "json" ></c:set>

<c:choose>
<c:when test="${ param.format != null and param.format.equals(format) }">
	<% response.setContentType("application/json"); %>
	<% JSONViewFromater.format(Collections.singletonMap("posts", request.getAttribute("posts")), response); %>
</c:when>
<c:otherwise>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Posts</title>
<style type="text/css">
	td {
		text-align: center;
	}
</style>
<c:if test="${not empty sessionScope.username}">
<script type="text/javascript" src = "/rznu/resources/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src = "/rznu/resources/updates.js"></script>

<script type="text/javascript">
	function deletePost(postId) {
		$.ajax({
			beforeSend: function(xhrObj) {
	            xhrObj.setRequestHeader("Content-Type", "text/plain");
	            xhrObj.setRequestHeader("Accept", "application/json");
	        },
            type: 'DELETE',
            url: "posts/" + postId,
            success: function(result) {
                window.location.reload();
            },
            failure: function(fail) {
            	alert('FAIL');
            }
        })		
	}
	
	function updatePostView(postId) {
		window.location = "/rznu/posts/update?postId=" + postId;
	}
	
	function post(){
		var postData = $("#textData").val();
		updatePost(-1, postData);
	}
</script>
</c:if>
</head>

<t:userpage>
	<jsp:body>
		<table border="1" style="width: 100%">
		  <tr>
		    <th>User</th>
		    <th>Post</th>
		    <c:if test="${not empty sessionScope.username}">
		    <th>Action</th>
		    </c:if>
		  </tr>
		  <c:forEach items="${posts}" var="post">
				<tr>
					<td><c:out value="${ post.username }"></c:out></td>
					<td align = "left"><c:out value="${ post.postText }"></c:out></td>
					<c:if test="${not empty sessionScope.username}">
					<td id = <c:out value="${ post.postId }"/>>
					 <button onclick="deletePost(this.parentNode.id)"> delete </button> 
					 <button onclick="updatePostView(this.parentNode.id)"> edit </button>
					</td>
					</c:if>
				</tr>
		  </c:forEach>
		</table>
		<c:if test="${not disablePosting and not empty sessionScope.username}">
		<div>
			<textarea id = "textData" rows = "2" cols = "50" ></textarea>
			<button onClick = "post()">post</button>
		</div>
		</c:if>
	</jsp:body>
</t:userpage>
</c:otherwise>
</c:choose>