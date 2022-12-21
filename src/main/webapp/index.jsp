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
<title>Insert title here</title>
</head>
<body>
<%

Database db=Database.getInstance();

ArrayList<Monster> m=db.getMonsters();

for(Monster mon:m){
	out.println(mon+"<br>");
}


%>

</body>
</html>