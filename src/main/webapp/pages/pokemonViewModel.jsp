<%@page import="models.MonsterViewModel"%>
<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
	long monsterId=Long.parseLong(request.getParameter("monsterId"));
	IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
	MonsterViewModel monster=service.getMonsterViewModel(monsterId);
	request.setAttribute("monster", monster);
	

%>
<table>
			<tr>
				<th>Ime</th>
				<th>Opis</th>
				<th>Hp</th>
				<th>Slika</th>
			</tr>
			<tr>
				<td><c:out value="${monster.name}"/></td>
				<td><c:out value="${monster.description}"/></td>
				<td><c:out value="${monster.hp}"/></td>
				<td><img style="height: 50px" src="data:image/*;base64, ${monster.base64Image }" /></td>
			</tr>
</table>
<table>
			<tr>
				<th>Ime</th>
				<th>Opis</th>
				<th>Type</th>
				<th>Snaga</th>
			</tr>
			<c:forEach items="${monster.abilities}" var="item">
			<tr>
				<td><c:out value="${item.name}"/></td>
				<td><c:out value="${item.description}"/></td>
				<td><c:out value="${item.type}"/></td>
				<td><c:out value="${item.power}"/></td>
			</tr>
			</c:forEach>
			
</table>

</body>
</html>