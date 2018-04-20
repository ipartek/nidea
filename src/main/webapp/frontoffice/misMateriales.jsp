<%@page import="com.ipartek.formacion.nidea.controller.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Materiales</h1>

<div class="col-md-6">
	<a class="btn btn-outline-primari" href="materiales?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
</div> 

<ol>
	<c:forEach items="${materiales}" var="material">
		<c:set var="clase" value="" />	
		<c:choose>
			<c:when test="${material.precio > 25}">
				<c:set var="clase" value="text-danger" />
			</c:when>
			<c:when test="${material.precio > 6}">
				<c:set var="clase" value="text-primary" />
			</c:when>
		</c:choose>	
		<li>
			<a href="materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a> - 
			<span class="${clase}">${material.precio} &euro;</span>
		</li>
			
			
	</c:forEach>
</ol>

<jsp:include page="/templates/footer.jsp"></jsp:include>