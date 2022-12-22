<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%

IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
out.print(session.getAttribute("id"));
long userId=(Long)session.getAttribute("id");
long monsterId=Long.parseLong(request.getParameter("monsterId"));

service.addMonsterToUser(userId, monsterId);

response.sendRedirect("userIndex.jsp");
%>