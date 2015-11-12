<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update post</title>
<script type="text/javascript" src = "/rznu/resources/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src = "/rznu/resources/updates.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		$(":button").click(
			function(){
				var text = $("#textData").val();
				updatePost(this.id, text);				
			}		
		)
});
</script>
</head>
<t:userpage>
	<jsp:body>
		<table border="1">
  <tr>
    <th>username</th>
    <th> post </th>
  </tr>
  <tr>
    <td> <c:out value="${ post.username }"></c:out> </td>
    <td> 
    	<textarea id = "textData"><c:out value="${post.postText}"></c:out></textarea> 
    </td>
  </tr>
</table>
	<button id="<c:out value="${ post.postId }"></c:out>"> Update </button>
	</jsp:body>
</t:userpage>