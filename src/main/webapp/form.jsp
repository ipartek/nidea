<%@page import="com.ipartek.formacion.nidea.controller.frontoffice.FrontofficeMaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>



<script src="js/ajax.js"></script>
<div class="container">
	<div class="form-group row">
		<a class="btn btn-outline-dark btn-lg" href="login">Volver</a>
	</div>
	<h2 align="center">REGISTRO DE UN NUEVO USUARIO</h2>
	<hr>
	<form name="formRegistro" action="registro" method="post">  
		<div class="form-group row">
			<label for=nombre class="col-sm-2 col-form-label">Nombre de usuario:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" required name= "nombre" id="nombre" placeholder="Introduce tu nombre de usuario (nombre y apellido)" (onfocus)="">
			</div>
			<div class="col-md-3">
               <div class="form-control-feedback">
                  <span id="error_pass1" class="text-danger align-middle">
                      <i class="fa fa-close"> * El usuario ya existe.</i>
                  </span>
              </div>
              <div class="form-control-feedback">
                  <span id="error_pass2" class="text-success align-middle">
                      <i class="fa fa-close"> * El usuario esta libre.</i>
                  </span>
              </div>
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
			<div class="col-md-3">
                <div class="form-control-feedback">
                        <span id="error_pass" class="text-danger align-middle">
                            <i class="fa fa-close"> * Contraseña no coincide</i>
                        </span>
                </div>
            </div>
		</div>
		
		<button class="btn btn-lg btn-success btn-block" type="submit" disabled id="btn">Registrar</button>
	</form>
	
	
	
	
</div>
<script src="js/registro.js"></script>
<script>

	let promesa = ajax('GET','http://localhost:8080/usuario/');
	promesa.then(data => {
        console.log(JSON.parse(data))
        data = JSON.parse(data);
        console.log("Detalle de los usuarios");
        promesaSegunda = ajax('GET', 'http://localhost:8080/usuario/' + data[1].id);)
        .catch(error => {
            console.log(error)
        });
	)
</script>

<jsp:include page="/templates/footer.jsp"></jsp:include>