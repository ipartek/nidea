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
				<input type="text" value="${usuario.nombre}" class="form-control" id="nombre" placeholder="Introduce nombre de usuario" onkeyup="if (this.value.length > 4) comprobarNombre(event)">
			</div>
			<span id="avisoNombre"></span>
		</div>
		<div class="form-group row">
			<label for="email" class="col-sm-2 col-form-label">Email</label>
			<div class="col-sm-5">
				<input type="text" value="${usuario.email}" class="form-control" id="email" placeholder="Introduce email">
			</div>
			<span id="avisoEmail"></span>
		</div>
		<div class="form-group row">
			<label for="pass" class="col-sm-2 col-form-label">Contraseña</label>
			<div class="col-sm-5">
				<input type="password" value="${usuario.pass}" class="form-control"  id="pass" name="pass" placeholder="Introduce contraseña" onblur="validar(event)">
			</div>
			</div><span id="avisoPass"></span>
		</div>
		<div class="form-group row">
			<label for="pass2" class="col-sm-2 col-form-label">Repite Contraseña</label>
			<div class="col-sm-5">
				<input type="password" value="${usuario.pass2}" class="form-control" id="pass2" name="pass2" placeholder="Repite contraseña" onblur="validar(event)">
			</div>
		</div>
		<button type="submit" id="btnRegistrar" class="btn btn-success btn-lg btn-block" disabled>Registrar</button>
	</form>
</div>

<script src="js/ajax.js"></script>
<script src="js/registro.js"></script>

<%@include file="/templates/footer.jsp" %>