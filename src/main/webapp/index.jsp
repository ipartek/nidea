<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<%
	// Scriplet < %  ...   % >
	// varias sentencias 
	String nombre = "pepe";
	String hora = "10:78";
	
	//Lanza adrede un NullPointerException y nos mostrara la pagina error.jsp
	//Hemos configurado este comportamiento en web.xml con < error-page >
	String nulo = null;
	nulo.length();
		

%>

<h2>Hello <%=nombre%></h2>
<p><%=hora%></p>

<a href="generar-mesa"> ¿Quieres Comprar una Mesa ?</a>


<%@include file="templates/footer.jsp" %>