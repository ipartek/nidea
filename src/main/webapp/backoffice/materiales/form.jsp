<%@page
	import="com.ipartek.formacion.nidea.controller.backoffice.MaterialesController"%>
<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>
<%@include file="/templates/alert.jsp"%>
<!-- ${material}-->
<hr>
<!--  ${usuarios}-->
<div class="container">
	<div class="form-group row">
		<a class="btn btn-outline-dark btn-lg" href="backoffice/materiales">Volver</a>
	</div>
	<form action="backoffice/materiales" method="post">
		<div class="form-group row">
			<label for="id" class="col-sm-2 col-form-label">ID:</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="id" readonly	value="${material.id}">
			</div>
		</div>
		<div class="form-group row">
			<label for="nombre" class="col-sm-2 col-form-label">Material</label>
			<div class="col-sm-5">
				<input type="text" value="${material.nombre}" class="form-control"
					name="nombre" placeholder="Introduce el nombre del material">
			</div>
		</div>
		<div class="input-group row">
			<label for="precio" class="col-sm-2 col-form-label">Precio</label>
			<div class="input-group-append">
				<input type="text" class="form-control" value="${material.precio}"
					name="precio" placeholder="Introduce el precio"> <span
					class="input-group-text">&euro;</span>
			</div>
		</div>
		<div class="input-group row">
			<div class="col">
				<label for="usuario" class="col-sm-4 col-form-label">Usuarios:</label>
				<!--  <select name="usuario">
					<c:forEach items= "${usuarios}" var="item">
						<option value= "${item.id}" ${(item.id== material.usuario.id)?"selected":""}>${item.nombre}</option>
					</c:forEach>
				</select>-->
				<input type="text" value ="${material.usuario.nombre}" disabled readonly />
			</div>
			<div class="col">
				<p>Cambiar usuario</p>
				<input type ="search" id="search" placeholder ="Nombre usuario" onkeyup="buscarUsuario(event)">
				<input type="hidden" name="id_usuario" value ="${material.usuario.id}">
				<select id="sUsuarios" name="id_usuario_cambio"></select>
			</div>
		</div>
			<script src="js/buscarUsuario.js"></script>
				
		
		<c:if test="${material.id == -1}">
			<div class="form-group row">
				<div class="col-sm-12">
					<input type="hidden" name="op" value="<%=MaterialesController.OP_GUARDAR%>">
					<button type="submit" class="btn btn-primary btn-lg btn-block">Crear</button>
				</div>
			</div>
		</c:if>
		<c:if test="${material.id > -1}">
			<div class="form-group row">
				<div class="col-sm-6">
					<input type="hidden" name="op" value="<%=MaterialesController.OP_GUARDAR%>">
					<button type="submit" class="btn btn-success btn-lg btn-block">Modificar</button>
				</div>
				<div class="col-sm-6">
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-danger btn-lg btn-block"
				data-toggle="modal" data-target="#exampleModal">Cuidado</button>

					<!-- Modal -->
					<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Eliminar</h5>
									<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">¿ Estas seguro que deseas Eliminar ?
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Cancelar</button>
									<a
								href="backoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_ELIMINAR%>">
										<button type="button" class="btn btn-primary">Aceptar</button>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
		<!-- "col-sm-6"  -->
			</div>
		</c:if>
	</form>
</div>


<jsp:include page="/templates/footer.jsp"></jsp:include>