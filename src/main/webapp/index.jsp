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
<body>
<h1>Login</h1>

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




</body>
</html>