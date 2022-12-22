<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(!isAdmin){
	response.sendRedirect("../index.jsp");
}
%>
<%
long userId=Long.parseLong(request.getParameter("userId"));
IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
service.deleteUser(userId);

response.sendRedirect("adminIndex.jsp");

%>