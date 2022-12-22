<%@page import="models.User"%>
<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<jsp:useBean id="user" class="models.User"></jsp:useBean>

<jsp:setProperty property="*" name="user"/>

<%
	IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
	User s=service.login(user);
	if(s==null)
	{
		response.sendRedirect("../index.jsp?id=-1");
	}else {
		session.setAttribute("id", s.getId());
		session.setAttribute("username", s.getUsername());
		session.setAttribute("admin", s.isAdmin());
		if(s.isAdmin())
			response.sendRedirect("adminIndex.jsp");
		else
			response.sendRedirect("userIndex.jsp");
	}
		

	
	




%>