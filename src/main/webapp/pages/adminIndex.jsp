<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
</head>
<body>

<h1>Registracija novog admina</h1>
<form method="post" action="registerAdmin.jsp">
	<input type="text" name="username"> Korisnicko ime 
	<br>
	<input type="password" name="password">Sifra
	<br>
	<p style="color:red;">
	<%
		if(request.getParameter("id")!=null &&request.getParameter("id").equals("-1") )
			out.print("Username vec postoji");
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




</body>
</html>