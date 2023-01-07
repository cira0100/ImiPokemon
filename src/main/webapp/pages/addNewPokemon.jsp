<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
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
</style>
<body>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(!isAdmin){
	response.sendRedirect("../index.jsp");
}
%>

<br>
<div class="tooltip">(?)
  <span class="tooltiptext">Stranica za dodavanje novog pokemona.
  Sva polja su required. Za polje hp unos mora biti tipa int, polje snaga mora biti tipa float , sva ostala polja su tipa string. Polje ImageString64 je polje sa sliku encodiranu pomocu base64 encodera maxlenght je 15000.
  Redirekcija na uspesnom dodavanju.</span>
</div>
<form method="post" action="addNewPokemonHelper.jsp">
<input type="text" name="name" required> Ime <br>
<input type="text" name="description" required> Opis <br>
<input type="number" step="1" name="hp" required> Hp <br>
<input type="text" name="base64Image" maxlength="15000" required> ImageString64 <br>

<h2>Abilities</h2>
<h3>ATTACK</h3>
<input type="text" name="abilitiesName" required> Ime <br>
<input type="text" name="abilitiesDesc" required> Opis <br>
<input type="hidden" name="abilitiesType" value="0" >
<input type="number" step="0.01" name="abilitiesPower" required> Snaga <br>
<h3>SPECIAL</h3>
<input type="text" name="abilitiesName" required> Ime <br>
<input type="text" name="abilitiesDesc" required> Opis <br>
<input type="hidden" name="abilitiesType" value="1">
<input type="number" step="0.01" name="abilitiesPower" required> Snaga <br>
<h3>HEAL</h3>
<input type="text" name="abilitiesName" required> Ime <br>
<input type="text" name="abilitiesDesc" required> Opis <br>
<input type="hidden" name="abilitiesType" value="2">
<input type="number" step="0.01" name="abilitiesPower" required> Snaga <br>
<h3>SHIELD</h3>
<input type="text" name="abilitiesName" required> Ime <br>
<input type="text" name="abilitiesDesc" required> Opis <br>
<input type="hidden" name="abilitiesType" value="3">
<input type="number" step="0.01" name="abilitiesPower" required> Snaga <br>

<button type="submit">Dodaj</button>



</form>


</body>
</html>