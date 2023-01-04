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
	<button type="submit">Registracija</button>
</form>
</body>
</html>