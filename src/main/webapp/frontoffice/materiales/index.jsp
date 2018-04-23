<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.MaterialesController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.nidea.pojo.Material"%>
<%@page import="java.util.ArrayList"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>

<h1>Materiales</h1>

<%@include file="/templates/alert.jsp"%>

<form action="frontoffice/materiales" method="get" class="form-inline">

	<input type="hidden" name="op"
		value="<%=Operable.OP_BUSQUEDA%>"> <input
		type="search" name="search" class="form-control mr-sm-2" required
		placeholder="Nombre del material" aria-label="Search"> <input
		class="btn btn-primary my-2 my-sm-0" type="submit" value="BUSCAR">
	<a class="btn btn-warning my-2 my-sm-0" href="frontoffice/materiales?op=-1">LIMPIAR</a>

</form>
<hr>

<a
	href="frontoffice/materiales?id=-1&op=<%=Operable.OP_MOSTRAR_FORMULARIO%>"
	class="btn btn-success my-2 my-sm-0">Nuevo Material</a>
<hr>

<table id="table-materiales-frontoffice" class="display">
	<thead>
		<tr>
			<th>Id</th>
			<th>Nombre</th>
			<th>Precio</th>
			<th>Usuario</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${materiales}" var="material">

			<c:if test="${material.usuario.id == usuario.id }">
				<tr>
					<td>${material.id}</td>
					<td><a
						href="frontoffice/materiales?id=${material.id}&op=<%=Operable.OP_MOSTRAR_FORMULARIO %>">${material.nombre}</a></td>
					<td>${material.precio}€</td>
					<td>${material.usuario.nombre}</td>
				</tr>
			</c:if>
		</c:forEach>

	</tbody>
</table>

<script type="text/javascript">
	window.history.pushState({}, "hidden", "materiales");
</script>

<%@include file="/templates/footer.jsp"%>

