<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API</title>
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
    <th>action</th>
    <th>method</th>		
    <th>ulazi</th>
    <th>format odgovora</th>
    <th> kratki opis </th>
  </tr>
  <tr>
    <td> / </td>
    <td>get</td>		
    <td> - </td>
    <td> text/html </td>
    <td> Dohvaća sadržaj glavne stranice </td>
  </tr>
</table>
	</jsp:body>
</t:userpage>
