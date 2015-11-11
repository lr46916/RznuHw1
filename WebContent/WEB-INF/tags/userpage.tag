<%@tag description="User Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<style>
ul#menu li {
	display: inline;
	padding: 3px;
}
ul#menu li a {
    background-color: black;
    color: white;
    padding: 10px 20px;
    text-decoration: none;
    border-radius: 4px 4px 4px 4px;
}
ul#menu li a:hover {
    background-color: orange;
}
</style>
</head>

<t:genericpage>
	<jsp:attribute name="header">
		<ul id="menu">
			<li><a href="/rznu/users"> users </a></li>
			<li><a href="/rznu/posts"> posts </a></li>
			<li><a href="/rznu/api"> api </a> </li>
		<c:choose>
		<c:when test="${empty sessionScope.username}">
			<li><a href="/rznu/logIn"> logIn </a> </li>
			<li><a href="/rznu/register"> register </a></li>
		</c:when>
		<c:otherwise>
			<li><a href="/rznu/logOut"> logOut </a></li>
		</c:otherwise>
		</c:choose>	
		</ul>
	</jsp:attribute>
	<jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:genericpage>