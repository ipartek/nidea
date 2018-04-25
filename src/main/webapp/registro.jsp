<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<div class="container">
	<div class="form-group row">
		<a class="btn btn-outline-dark btn-lg" href="registro">Volver</a>
	</div>
	<form action="registro" method="post">
		<div class="form-group row">
			<label for="nombre" class="col-sm-2 col-form-label">Nombre</label>
			<div class="col-sm-5">
				<input type="text" value="${usuario.nombre}" class="form-control" name="nombre" placeholder="Introduce nombre de usuario" onblur="buscarUsuario(event)">
			</div>
			<span id="avisoNombre"></span>
		</div>
		<div class="form-group row">
			<label for="pass" class="col-sm-2 col-form-label">Contraseña</label>
			<div class="col-sm-5">
				<input type="password" value="${usuario.pass}" class="form-control"  id="pass" name="pass" placeholder="Introduce contraseña" onblur="buscarUsuario(event)">
			</div>
			</div><span id="avisoPass"></span>
		</div>
		<div class="form-group row">
			<label for="pass2" class="col-sm-2 col-form-label">Repite Contraseña</label>
			<div class="col-sm-5">
				<input type="password" value="${usuario.pass2}" class="form-control" id="pass2" name="pass2" placeholder="Repite contraseña" onblur="buscarUsuario(event)">
			</div>
		</div>
		<button type="submit" id="btnRegistrar" class="btn btn-success btn-lg btn-block" disabled>Registrar</button>
	</form>
</div>

<script src="js/registro.js"></script>

<%@include file="/templates/footer.jsp" %>