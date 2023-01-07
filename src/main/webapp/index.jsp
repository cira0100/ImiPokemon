<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Monster"%>
<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="database.Database"%>
<!DOCTYPE html>
<% %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
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
h1{
display:inline
}
</style>
<body>
<h1>Login</h1><div class="tooltip">(?)
  <span class="tooltiptext">Stranica za prijavu. U zavistinosti da li je korisnik koji se prijavljuje admin ili obican korisnik bice preusmeran na svoju stranicu.
  ukoliko su unesu pogresne informacije za prijavu korisnik ce biti obavesten o tome.
  </span>
</div>
<br>

<form method="post" action="pages/login.jsp">
	<input type="text" name="username" required> Korisnicko ime 
	<br>
	<input type="password" name="password" required>Sifra
	<br>
	<p style="color:red;">
	<%
		if(request.getParameter("id")!=null &&request.getParameter("id").equals("-1") )
			out.print("Pogresan login");
	%>
	</p>
	<p style="color:green;">
	<%
		if(request.getParameter("id")!=null &&request.getParameter("id").equals("-2") )
			out.print("Uspesna registracija");
	%>
	</p>
	<button type="submit">Prijava</button>
</form>

<br>
<a href="pages/registerPage.jsp">
	<button>Registracija</button>
</a>
<br><br>





</body>
</html>