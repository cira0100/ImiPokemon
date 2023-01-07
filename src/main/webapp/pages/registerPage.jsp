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
h1{
	display:inline;
}
</style>
<body>
<h1>Register</h1><div class="tooltip">(?)
  <span class="tooltiptext">Klasicna stranica za registraciju. Korisnicko ime i sifra moraju da budu alfanumericki string i polja ne smeju biti prazna. Ukoliko je registracija uspensa korisnik se redirektuje na login stranicu ,a ukoliko je neuspesna korisnik biva obavesten o tome.
  </span>
</div>
<br>
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
<br><br>

</body>
</html>