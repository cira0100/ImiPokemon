<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="pokemon.Database"%>
<!DOCTYPE html>
<% %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%

Database d=Database.getInstance();

out.print(d.getUsername());

%>

</body>
</html>