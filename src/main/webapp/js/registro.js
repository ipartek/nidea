window.onload= function(e){
	console.log('Documento registro.js');
	var boton = document.getElementById("boton").disabled = true;
	var usuario = document.getElementById("usuario");
	var email = document.getElementById("email");
	var password = document.getElementById("password");
	var password2 = document.getElementById("password2");
	validar();
	
	registrarListenner();
}



function validar(){
	
	console.log('validar');
	expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	
	if ( usuario.value == "" || email.value == "" || password.value==""|| password2=="" ){
		boton.disabled = true;
		return;
	}else if(!expr.test(email.value)){
		return email.setCustomValidity("Error: La dirección de correo " + email + " es incorrecta.");
		boton.disabled = true;
	}
	else{
		boton.disabled = false;
	}
	
}


function registrarListenner(){
	
	console.log('registrarListenner');
	usuario.addEventListener("blur", validar );
}