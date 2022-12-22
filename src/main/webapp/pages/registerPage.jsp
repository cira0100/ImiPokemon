<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="register.jsp">
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
	<button type="submit">Registracija</button>
</form>
</body>
</html>