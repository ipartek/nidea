<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeMaterialesController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.nidea.pojo.Material"%>
<%@page import="java.util.ArrayList"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>



<h1>Backoffice Materiales</h1>
<hr>

<%@include file="/templates/alert.jsp"%>
<form action="backoffice/materiales" method="get" class="form-inline">

	<input type="hidden" name="op"
		value="<%=Operable.OP_BUSQUEDA%>"> <input
		type="search" name="search" class="form-control mr-sm-2" required
		placeholder="Nombre del material" aria-label="Search"> <input
		class="btn btn-primary my-2 my-sm-0" type="submit" value="BUSCAR">
	<a class="btn btn-warning my-2 my-sm-0"
		href="backoffice/materiales?op=-1">LIMPIAR</a>

</form>
<hr>

<a
	href="backoffice/materiales?id=-1&op=<%=Operable.OP_MOSTRAR_FORMULARIO%>"
	class="btn btn-success my-2 my-sm-0">Nuevo Material</a>



<hr>

<table class="tabla table table-striped table-bordered display" style="width:100%" id="table-materiales">
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

			<tr>
				<td>${material.id}</td>
				<td><a
					href="backoffice/materiales?id=${material.id}&op=<%=Operable.OP_MOSTRAR_FORMULARIO %>">${material.nombre}</a></td>
				<td>${material.precio}€</td>
				<td>${material.usuario.nombre}</td>
			</tr>

		</c:forEach>

	</tbody>
</table>

<script type="text/javascript">
	window.history.pushState({}, "hidden", "backoffice/materiales");
</script>




<%@include file="/templates/footer.jsp"%>
