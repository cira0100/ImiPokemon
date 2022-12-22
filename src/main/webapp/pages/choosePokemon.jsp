<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(isAdmin){
	response.sendRedirect("../index.jsp");
}

IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
long userId=(Long)session.getAttribute("id");
long monsterId=Long.parseLong(request.getParameter("monsterId"));

service.addMonsterToUser(userId, monsterId);

response.sendRedirect("userIndex.jsp");
%>