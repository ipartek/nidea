<%@page import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeUsuariosController"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeMaterialesController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.nidea.pojo.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>



<h1>Backoffice Usuarios</h1>
<hr>

<%@include file="/templates/alert.jsp"%>
<form action="backoffice/usuarios" method="get" class="form-inline">

	<input type="hidden" name="op"
		value="<%=BackofficeUsuariosController.OP_BUSQUEDA%>"> <input
		type="search" name="search" class="form-control mr-sm-2" required
		placeholder="Nombre del Usuario" aria-label="Search"> <input
		class="btn btn-primary my-2 my-sm-0" type="submit" value="BUSCAR">
	<a class="btn btn-warning my-2 my-sm-0"
		href="backoffice/usuarios?op=-1">LIMPIAR</a>

</form>
<hr>

<a
	href="backoffice/usuarios?id=-1&op=<%=BackofficeUsuariosController.OP_MOSTRAR_FORMULARIO%>"
	class="btn btn-success my-2 my-sm-0">Nuevo Usuario</a>
<hr>

<table id="table-usuarios" class="display">
	<thead>
		<tr>
			<th>Id Usuario</th>
			<th>Nombre Usuario</th>
			<th>Id Rol</th>
			<th>Nombre Rol</th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${usuarios}" var="usuario">

			<tr>
				<td>${usuario.id}</td>
				<td><a href="backoffice/usuarios?id=${usuario.id}&op=<%=BackofficeUsuariosController.OP_MOSTRAR_FORMULARIO %>">${usuario.nombre}</a></td>
				<td>${usuario.rol.id}</td>
				<td>${usuario.rol.nombre}</td>
			</tr>

		</c:forEach>

	</tbody>
</table>

<script type="text/javascript">
	window.history.pushState({}, "hidden", "backoffice/usuarios");
</script>




<%@include file="/templates/footer.jsp"%>
