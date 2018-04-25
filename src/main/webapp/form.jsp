<%@page import="com.ipartek.formacion.nidea.controller.frontoffice.FrontofficeMaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>




<div class="container">
	<div class="form-group row">
		<a class="btn btn-outline-dark btn-lg" href="login">Volver</a>
	</div>
	<h2 align="center">REGISTRO DE UN NUEVO USUARIO</h2>
	<hr>
	<form name="formRegistro" action="registro" method="post">  
		<div class="form-group row">
			<label for="usuario" class="col-sm-2 col-form-label">Nombre de usuario:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" required name= "usuario" id="usuario" placeholder="Introduce tu nombre de usuario (nombre y apellido)">
			</div>
		</div>
		<div class="form-group row">
			<label for="email" class="col-sm-2 col-form-label">Email</label>
			<div class="col-sm-10">
				<input type="email" name="email"  class="form-control" id="email" placeholder="Introduce tu email. you@example.com">
			</div>
		</div>
		<div class="form-group row">
			<label for="password" class="col-sm-2 col-form-label">Contraseña</label>
			<div class="col-sm-10">
				<input type="password" name="password" class="form-control" id="password" placeholder="Introduce tu contraseña">
			</div>
		</div>
		<div class="form-group row">
			<label for="password2" class="col-sm-2 col-form-label">Repite tu Contraseña</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" name="password2" id="password2" placeholder="Repite tu contraseña">
			</div>
		</div>
		
		<button class="btn btn-lg btn-success btn-block" type="submit" disabled id="boton">Registrar</button>
	</form>
</div>
<script src="js/registro.js"></script>

<jsp:include page="/templates/footer.jsp"></jsp:include>