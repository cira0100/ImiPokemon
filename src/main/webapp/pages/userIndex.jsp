<%@page import="models.Monster"%>
<%@page import="java.util.ArrayList"%>
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
<title>User</title>
<style>
	table, th, td {
	  border: 1px solid black;
	  border-collapse: collapse;
	  padding:2px;
	}
</style>
</head>
<body>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(isAdmin){
	response.sendRedirect("../index.jsp");
}
%>
<a href="logout.jsp">
	<button>Log out</button>
</a>
<h1>Izaberi pokemona</h1>
<br>
<%
	IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
	ArrayList<Monster> monsters=service.getMonsters();
	String username=(String)session.getAttribute("username");
	request.setAttribute("monsters", monsters);
	Monster myMonster=service.getUserMonster(username);
	request.setAttribute("myMonster", myMonster);
%>
Trenutno izabran pokemon: 
<%
	if(myMonster==null)
	{
		%>
		<p style="color:red">Nemate trenutno izabranog pokemon. Morate izabrati pokemona da bi mogli da igrate igru</p>
		<%
	}
	else{
		%>
		<jsp:include page="pokemonViewModel.jsp">
			<jsp:param value="${myMonster.id}" name="monsterId"/>
		</jsp:include>
		<% 
	}
%>
<br><br><br>
<h1>Svi pokemoni</h1>
<table>
<tr>
<th>Ime</th>
<th>Opis</th>
<th>Hp</th>
<th>Slika</th>
<th></th>
</tr>
<c:forEach items="${monsters}" var="item">
<tr>
	<td>
	<c:out value="${item.name}"/>
	</td>
	<td>
	<c:out value="${item.description}"/>
	</td>
	<td>
	<c:out value="${item.hp}"/>
	</td>
	<td>
	<img style="height: 50px" src="data:image/*;base64, ${item.base64Image }" />
	</td>
	<td>
	<a href="choosePokemon.jsp?monsterId=${item.id}">
	<button>Izaberi</button>
</a>
	</td>
</tr> 
</c:forEach>

</table>
<br><br><br>
<h1>Istorija</h1>
<jsp:include page="history.jsp"/>


</body>
</html>