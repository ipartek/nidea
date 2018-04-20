<%@page import="com.ipartek.formacion.nidea.controller.MaterialesController"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeMaterialesController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>

<a href="materiales" class="btn btn-primary">Volver</a>
<hr>

<h1>Materiales Form</h1>

<%@include file="/templates/alert.jsp"%>

<form action="materiales" method="get">
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


	<c:if test="${material.id == -1}">
		<input type="hidden" name="op"
			value="<%=MaterialesController.OP_GUARDAR%>">
		<button type="submit" class="btn btn-success btn-block">Crear</button>
	</c:if>

	<c:if test="${material.id != -1}">
		<input type="hidden" name="op"
			value="<%=MaterialesController.OP_GUARDAR%>">
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
						<a class="btn btn-danger btn-block"
							href="materiales?id=${material.id}&op=<%=MaterialesController.OP_ELIMINAR %>">Eliminar</a>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</form>


<%@include file="/templates/footer.jsp"%>