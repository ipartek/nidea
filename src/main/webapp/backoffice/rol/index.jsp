<%@page import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeRolesController"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>



<h1>Backoffice Roles</h1>
<hr>

<%@include file="/templates/alert.jsp"%>
<form action="backoffice/roles" method="get" class="form-inline">

	<input type="hidden" name="op"
		value="<%=BackofficeRolesController.OP_BUSQUEDA%>"> <input
		type="search" name="search" class="form-control mr-sm-2" required
		placeholder="Nombre del Rol" aria-label="Search"> <input
		class="btn btn-primary my-2 my-sm-0" type="submit" value="BUSCAR">
	<a class="btn btn-warning my-2 my-sm-0"
		href="backoffice/roles?op=-1">LIMPIAR</a>

</form>
<hr>

<a
	href="backoffice/roles?id=-1&op=<%=BackofficeRolesController.OP_MOSTRAR_FORMULARIO%>"
	class="btn btn-success my-2 my-sm-0">Nuevo Rol</a>
<hr>

<table id="table-roles" class="display">
	<thead>
		<tr>
			<th>Id</th>
			<th>Nombre</th>			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${roles}" var="rol">

			<tr>
				<td>${rol.id}</td>
				<td><a href="backoffice/roles?id=${rol.id}&op=<%=BackofficeRolesController.OP_MOSTRAR_FORMULARIO %>">${rol.nombre}</a></td>
			</tr>

		</c:forEach>

	</tbody>
</table>

<script type="text/javascript">
	window.history.pushState({}, "hidden", "backoffice/roles");
</script>




<%@include file="/templates/footer.jsp"%>