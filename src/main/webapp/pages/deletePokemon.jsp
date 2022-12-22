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
long monsterId=Long.parseLong(request.getParameter("monsterId"));
IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
service.deleteMonster(monsterId);
response.sendRedirect("adminIndex.jsp");

%>