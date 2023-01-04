<%@page import="models.User"%>
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
<title>Admin</title>
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
if(!isAdmin){
	response.sendRedirect("../index.jsp");
}
%>
<a href="logout.jsp">
	<button>Log out</button>
</a><br><br>
<a href="addNewPokemon.jsp">
	<button>DodajNovogPokemona</button>
</a>

<h1>Lista korisnika</h1>
<%
IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
ArrayList<User>users=service.getAllUsers();
request.setAttribute("users", users);
ArrayList<Monster> monsters=service.getMonsters();
request.setAttribute("monsters", monsters);
%>


<table>
<tr>
<th>Id</th>
<th>Username</th>
<th>admin</th>
<th>pokemonId</th>
<th></th>
</tr>
<c:forEach items="${users}" var="item">
<tr>
	<td>
	<c:out value="${item.id}"/>
	</td>
	<td>
	<c:out value="${item.username}"/>
	</td>
	<td>
	<c:out value="${item.admin}"/>
	</td>
	<td>
	<c:if test="${item.monsterId!=-1}">
	<c:out value="${item.monsterId}"/>
	</c:if>
	</td>
	<td>
	<c:if test="${!item.admin}">
	<a href="deleteUser.jsp?userId=${item.id}">
	<button>Izbrisi</button>
	</a>
	</c:if>
	</td>
</tr> 
</c:forEach>

</table>



<br><br>
<h1>Registracija novog admina</h1>
<form method="post" action="registerAdmin.jsp">
	<input type="text" name="username" pattern="^[A-Za-z0-9]{1,}$" required> Korisnicko ime 
	<br>
	<input type="password" name="password" pattern="^[A-Za-z0-9]{1,}$" required>Sifra
	<br>
	<p style="color:red;">
	<%
		if(request.getParameter("id")!=null &&request.getParameter("id").equals("-1") )
			out.print("Username vec postoji");
	%>
	<%
		if(request.getParameter("id")!=null &&request.getParameter("id").equals("-3") )
			out.print("Username i password moraju biti alfanumericnog tipa");
	%>
	</p>
	<p style="color:green;">
	<%
		if(request.getParameter("id")!=null &&request.getParameter("id").equals("-2") )
			out.print("Uspesna registracija");
	%>
	</p>
	<button type="submit">Registruj admina</button>
</form>
<h1>
</h1>


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
	<a href="deletePokemon.jsp?monsterId=${item.id}">
	<button>Izbrisi</button>
</a>
	</td>
</tr> 
</c:forEach>

</table>




</body>
</html>