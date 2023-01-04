<%@page import="pokemon.RegexHelper"%>
<%@page import="models.User"%>
<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<jsp:useBean id="user" class="models.User"></jsp:useBean>

<jsp:setProperty property="*" name="user"/>

<%
	IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
	if(RegexHelper.checkAlphaNumericInput(user.username) && RegexHelper.checkAlphaNumericInput(user.password)){
		
		boolean s=service.addUser(user);
		if(s)
		{
			response.sendRedirect("../index.jsp?id=-2");
		}
		else{
			response.sendRedirect("registerPage.jsp?id=-1");
		}
		
	}
	else{
		response.sendRedirect("registerPage.jsp?id=-3");
	}
	
%>