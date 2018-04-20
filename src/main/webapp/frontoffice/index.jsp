<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<script>
setTimeout(function () { location.reload(1); }, 5000);	
</script>

<h1>Backofice</h1>


applicationScope (JSP ) == ServletContext ( servlet ) <br>

<h2>Usuarios Conectados</h2>

<a href="frontoffice/materiales">Tus Materiales</a>

<hr>

<ul>
	<c:forEach var="usuario" items="${applicationScope.usuarios_conectados}">
		<li>${usuario.key} - ${usuario.value}</li>
	</c:forEach>
</ul>







<%@include file="/templates/footer.jsp" %>