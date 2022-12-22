<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%
long userId=Long.parseLong(request.getParameter("userId"));
IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
service.deleteUser(userId);

response.sendRedirect("adminIndex.jsp");

%>