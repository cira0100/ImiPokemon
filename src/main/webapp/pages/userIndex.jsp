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
	.tooltip {
  position: relative;
  display: inline-block;
  border-bottom: 1px dotted black;
}
.tooltip .tooltiptext {
  visibility: hidden;
  width: 300px;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  position: absolute;
  z-index: 1;
}
.tooltip:hover .tooltiptext {
  visibility: visible;
}
h1{
	display:inline;
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
<br>
<h1>Izaberi pokemona</h1><div class="tooltip">(?)
  <span class="tooltiptext">Korisnik da bi se uopste ulogovan i koristio desktop aplikaciju mora da ima izabranog pokemona!!!
  Prikazan izabran poken sa svim podacima o njemu</span>
</div>
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
<br><br>
<h1>Svi pokemoni</h1><div class="tooltip">(?)
  <span class="tooltiptext">Lista svih dostupnih pokemona odakle korisnik moze da izabere pokemona koga ce koristiti.</span>
</div>
<br>
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
<br><br>
<h1>Istorija</h1><div class="tooltip">(?)
  <span class="tooltiptext">Pregled istorije meceva korisnika. Prikaz imena i slike koriscenog pokemona, datum i rezultat meca.</span>
</div>
<br>
<br>
<jsp:include page="history.jsp"/>


</body>
</html>