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
<c:if test="${not empty sessionScope.username}">
<script type="text/javascript" src = "resources/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
	function deletePost(postId) {
		$.ajax({
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
	
	function updatePost(postId) {
		window.location = "posts/update?postId=" + postId;
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
					 <button onclick="updatePost(this.parentNode.id)"> edit </button>
					</td>
					</c:if>
					
				</tr>
		  </c:forEach>
		</table>
	</jsp:body>
</t:userpage>