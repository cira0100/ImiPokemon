<%@page import="java.util.ArrayList"%>
<%@page import="pokemon.IService"%>
<%@page import="models.History"%>
<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
<% 
	long userId=(long)session.getAttribute("id");
	IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
	ArrayList<History> history=service.getUserHistory(userId);
	request.setAttribute("history", history);
	

%>
<table>
<tr>
	<th>Ime
	</th>
	<th>Slika
	</th>
	<th>Datun
	</th>
	<th>Rezultat
	</th>
</tr>
<c:forEach items="${history}" var="item">
			<tr bgcolor="${item.result  eq 1 ? 'LawnGreen': 'IndianRed'}">
				<td><c:out value="${item.pokemon.name}"/></td>
				<td><img style="height: 50px" src="data:image/*;base64, ${item.pokemon.base64Image}" /></td>
				<td><c:out value="${item.time}"/></td>
				<td><c:out value="${item.result  eq 1 ? 'Pobeda': 'Poraz'}"/></td>
			</tr>
			</c:forEach>
</table>
</body>
</html>