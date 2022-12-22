<%
	session.removeAttribute("id");
	session.removeAttribute("username");
	session.removeAttribute("admin");
	response.sendRedirect("../index.jsp");
%>