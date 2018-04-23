<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeMaterialesController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>

<a href="backoffice/materiales" class="btn btn-primary">Volver</a>
<hr>

<h1>Materiales Form</h1>

<%@include file="/templates/alert.jsp"%>

<form action="backoffice/materiales" method="get">

	<div class="form-group">
		<label for="id">Id:</label> <input type="number" class="form-control"
			id="id" name="id" placeholder="Id" readonly value="${material.id }">
	</div>
	<div class="form-group">
		<label for="nombre">Nombre Material:</label> <input type="text"
			class="form-control" id="nombre" name="nombre" placeholder="Nombre"
			required value="${material.nombre }">
	</div>
	<div class="form-group">
		<label for="precio">Precio:</label> <input type="number" step="0.01"
			class="form-control" id="precio" name="precio" placeholder="Precio"
			required value="${material.precio }">
	</div>
	<div class="form-group">
		<select name="id_usuario" class="form-control">
			<option value="-1">Seleccione Usuario</option>
			<c:forEach items="${usuarios }" var="usuario">
				<option value="${usuario.id }" ${usuario.id == material.usuario.id?'selected':''}>${usuario.nombre }</option>
			</c:forEach>
		</select>
	</div>

	<c:if test="${material.id == -1}">
		<input type="hidden" name="op"
			value="<%=Operable.OP_GUARDAR%>">
		<button type="submit" class="btn btn-success btn-block">Crear</button>
	</c:if>

	<c:if test="${material.id != -1}">
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
							eliminacion de ${material.nombre}</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="id">Id:</label>
							<p>${material.id }</p>
						</div>
						<div class="form-group">
							<label for="nombre">Nombre Material:</label>
							<p>${material.nombre }</p>
						</div>
						<div class="form-group">
							<label for="precio">Precio:</label>
							<p>${material.precio }</p>
						</div>
						<div class="form-group">
							<label for="usuario">Usuario:</label>
							<p>${material.usuario.nombre }</p>
						</div>
						<a class="btn btn-danger btn-block"
							href="backoffice/materiales?id=${material.id}&op=<%=Operable.OP_ELIMINAR %>">Eliminar</a>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</form>


<%@include file="/templates/footer.jsp"%>