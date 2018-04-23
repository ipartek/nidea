<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeUsuariosController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>

<a href="backoffice/usuarios" class="btn btn-primary">Volver</a>
<hr>

<h1>Usuarios Form</h1>

<%@include file="/templates/alert.jsp"%>

<form action="backoffice/usuarios" method="get">

	<div class="form-group">
		<label for="id">Id Usuario:</label> <input type="number"
			class="form-control" id="id" name="id" placeholder="Id Usuario"
			readonly value="${usuario.id }">
	</div>
	<div class="form-group">
		<label for="nombre">Nombre Usuario:</label> <input type="text"
			class="form-control" id="nombre" name="nombre"
			placeholder="Nombre Usuario" required value="${usuario.nombre }">
	</div>
	<div class="form-group">
		<label for="password">Password:</label> <input type="password"
			class="form-control" id="password" name="password"
			placeholder="Password" required value="${usuario.pass }">
	</div>
	<div class="form-group">
		<select name="id_rol" class="form-control">
			<option value="-1">Seleccione Rol</option>
			<c:forEach items="${roles }" var="rol">
				<option value="${rol.id }" ${rol.id == usuario.rol.id?'selected':''}>${rol.nombre }</option>
			</c:forEach>
		</select>
	</div>



	<c:if test="${usuario.id == -1}">
		<input type="hidden" name="op"
			value="<%=Operable.OP_GUARDAR%>">
		<button type="submit" class="btn btn-success btn-block">Crear</button>
	</c:if>

	<c:if test="${usuario.id != -1}">
		<input type="hidden" name="op"
			value="<%=Operable.OP_GUARDAR%>">
		<button type="submit" class="btn btn-primary btn-block">Modificar</button>

		<!-- Button trigger modal -->
		<button type="button" class="btn btn-danger btn-block"
			data-toggle="modal" data-target="#exampleModalCenter">Eliminar</button>

		<!-- Modal -->
		<div class="modal fade" id="exampleModalCenter" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalCenterTitle"
			aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLongTitle">Confirmar
							eliminacion de ${usuario.nombre}</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="id">Id Usuario:</label>
							<p>${usuario.id }</p>
						</div>
						<div class="form-group">
							<label for="nombre">Nombre Usuario:</label>
							<p>${usuario.nombre }</p>
						</div>
						<div class="form-group">
							<label for="precio">Rol:</label>
							<p>${usuario.rol.nombre }</p>
						</div>
						<a class="btn btn-danger btn-block"
							href="backoffice/usuarios?id=${usuario.id}&op=<%=Operable.OP_ELIMINAR %>">Eliminar</a>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</form>


<%@include file="/templates/footer.jsp"%>