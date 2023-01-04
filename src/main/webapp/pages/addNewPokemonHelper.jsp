<%@page import="models.AbilityType"%>
<%@page import="models.Ability"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.CONSTS"%>
<%@page import="java.rmi.Naming"%>
<%@page import="pokemon.IService"%>
<%
boolean isAdmin=(Boolean)session.getAttribute("admin");
if(!isAdmin){
	response.sendRedirect("../index.jsp");
}
%>
<jsp:useBean id="pokemon" class="models.PokemonAddModel"></jsp:useBean>

<jsp:setProperty property="*" name="pokemon"/>
<%
IService service=(IService)Naming.lookup(CONSTS.rmiUrl);
List<String> names=Arrays.asList(request.getParameterValues("abilitiesName"));
List<String> desc=Arrays.asList(request.getParameterValues("abilitiesDesc"));
List<String> type=Arrays.asList(request.getParameterValues("abilitiesType"));
List<String> power=Arrays.asList(request.getParameterValues("abilitiesPower"));
pokemon.abilities=new ArrayList<Ability>();

for (int i = 0; i < 4; i++) {
		Ability tempA=new Ability();
		tempA.setName(names.get(i));
		tempA.setDescription(desc.get(i));
		int tempType=Integer.parseInt(type.get(i));
		tempA.setType(AbilityType.values()[tempType]);
		float tempPower=Float.valueOf(power.get(i));
		tempA.setPower(tempPower);
		pokemon.abilities.add(tempA);	
	}
service.addPokemonWithAbilities(pokemon);
response.sendRedirect("adminIndex.jsp");


%>

