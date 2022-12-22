<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(!isAdmin){
	response.sendRedirect("../index.jsp");
}
%>


<form method="post" action="addNewPokemonHelper.jsp">
<input type="text" name="name"> Ime <br>
<input type="text" name="description"> Opis <br>
<input type="text" name="hp"> Hp <br>
<input type="text" name="base64Image"> ImageString64 <br>

<h2>Abilities</h2>
<h3>ATTACK</h3>
<input type="text" name="abilitiesName"> Ime <br>
<input type="text" name="abilitiesDesc"> Opis <br>
<input type="hidden" name="abilitiesType" value="0">
<input type="text" name="abilitiesPower"> Snaga <br>
<h3>SPECIAL</h3>
<input type="text" name="abilitiesName"> Ime <br>
<input type="text" name="abilitiesDesc"> Opis <br>
<input type="hidden" name="abilitiesType" value="1">
<input type="text" name="abilitiesPower"> Snaga <br>
<h3>HEAL</h3>
<input type="text" name="abilitiesName"> Ime <br>
<input type="text" name="abilitiesDesc"> Opis <br>
<input type="hidden" name="abilitiesType" value="2">
<input type="text" name="abilitiesPower"> Snaga <br>
<h3>SHIELD</h3>
<input type="text" name="abilitiesName"> Ime <br>
<input type="text" name="abilitiesDesc"> Opis <br>
<input type="hidden" name="abilitiesType" value="3">
<input type="text" name="abilitiesPower"> Snaga <br>

<button type="submit">Dodaj</button>



</form>


</body>
</html>