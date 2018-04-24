
<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>
<%@include file="/templates/alert.jsp"%>

<div class="container">
	<div class="form-group row">
		<a class="btn btn-outline-dark btn-lg" href="backoffice/materiales">Volver</a>
	</div>
	<form action="backoffice/materiales" method="post">
		<div class="form-group row">
			<label for="id" class="col-sm-2 col-form-label">ID:</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="id" readonly
					value="${material.id}">
			</div>
		</div>
		<div class="form-group row">
			<label for="nombre" class="col-sm-2 col-form-label">Material</label>
			<div class="col-sm-5">
				<input type="text" value="${material.nombre}" class="form-control"
					name="nombre" placeholder="Introduce el nombre del material">
			</div>
		</div>
		<div class="input-group ">
			<label for="precio" class="col-sm-2 col-form-label">Precio</label>
			<div class="input-group-append">
				<input type="text" class="form-control" value="${material.precio}"
					name="precio" placeholder="Introduce el precio"> <span
					class="input-group-text">&euro;</span>
			</div>
		</div>

		<div class="input-group">
			<div class="col">
				<label for="id_usuario" class="col-sm-2 col-form-label">Usuario</label>
				<input type="text" class="form-control"
					value="${material.usuario.nombre}" readonly />
			</div>
			<div class="col">
				<p>Cambiar propietario</p>
				<input type="search" id="search" class="form-control"
					placeholder="Buscar por nombre de usuario" onkeyup="buscarUsuario(event)">
				<input type="hidden" name="id_usuario" value="${material.usuario.id}"> 
				<select id="sUsuarios"
					name="id_usuario_cambio" class="form-control">
					<option value="-1">Seleccionar usuario</option>
				</select>
			</div>
		</div>

		<script>
			function buscarUsuario(event) {
				//console.log('buscarUsuario: click %o', event);
				var nombreBuscar = event.target.value;
				var select = document.getElementById('sUsuarios');
				if (nombreBuscar !== "") {
					console.log('nombre %s', nombreBuscar);
					var url = "api/usuario?nombre=" + nombreBuscar;
					var options = '';
					
					//eliminar opstions antiguas
					select.innerHTML = "";
			
					//llamada Ajax
					var xhttp = new XMLHttpRequest();
					xhttp.onreadystatechange = function() {
						//llamada terminada y correcta
						if (this.readyState == 4 && this.status == 200) {
							var data = JSON.parse(this.responseText);
							console.log('retorna datos %o', data);
							data.forEach( el => {
								options += '<option value="' + el.id + '">' + el.nombre + '</option>';
							});
			
						} else if (this.readyState == 4 && this.status == 204) {
							console.log('no existen coincidencias');
							options += '<option value="-1">No existen coincidencias</option>';
						}
						select.innerHTML = options;
					};
					xhttp.open("GET", url, true);
					xhttp.send();
				} else {
					select.innerHTML = '<option value="-1">Seleccionar usuario</option>';
				}
			}
		</script>
</div>
<br>

<c:if test="${material.id == -1}">
	<div class="form-group row">
		<div class="col-sm-12">
			<input type="hidden" name="op" value="<%=Operable.OP_GUARDAR%>">
			<button type="submit" class="btn btn-primary btn-lg btn-block">Crear</button>
		</div>
	</div>
</c:if>

<c:if test="${material.id > -1}">
	<div class="form-group row">
		<div class="col-sm-6">
			<input type="hidden" name="op" value="<%=Operable.OP_GUARDAR%>">
			<button type="submit" class="btn btn-success btn-lg btn-block">Modificar</button>
		</div>
		<div class="col-sm-6">

			<!-- Button trigger modal -->
			<button type="button" class="btn btn-danger btn-lg btn-block"
				data-toggle="modal" data-target="#exampleModal">Eliminar</button>

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
								href="backoffice/materiales?id=${material.id}&op=<%=Operable.OP_ELIMINAR%>">
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