<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>


<a href="frontoffice/materiales">Materiales</a>


<script>
setTimeout(function () { location.reload(1); }, 5000);	
</script>

YA ESTAS LOGUEADO EN EL FRONTOFFICE <br>


applicationScope (JSP ) == ServletContext ( servlet ) <br>

<h2>Usuarios Conectados</h2>




<hr>

<ul>
	<c:forEach var="usuario" items="${applicationScope.usuarios_conectados}">
		<li>${usuario.key} - ${usuario.value}</li>
	</c:forEach>
</ul>







<%@include file="/templates/footer.jsp" %>