<%@page import="pokemon.RegexHelper"%>
<%@page import="models.User"%>
<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<jsp:useBean id="user" class="models.User"></jsp:useBean>

<jsp:setProperty property="*" name="user"/>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(!isAdmin){
	response.sendRedirect("../index.jsp");
}
%>
<%
	IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
	user.setAdmin(true);
	if(RegexHelper.checkAlphaNumericInput(user.username) && RegexHelper.checkAlphaNumericInput(user.password)){
		boolean s=service.addUser(user);
		if(s)
		{
			response.sendRedirect("adminIndex.jsp?id=-2");
		}
		else{
			response.sendRedirect("adminIndex.jsp?id=-1");
		}
	}
	else{
		response.sendRedirect("adminIndex.jsp?id=-3");
	}
	
%>