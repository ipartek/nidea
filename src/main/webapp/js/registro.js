window.document.onload = function(e){
	console.log("Document loaded: registrp.js")
	
console.log('Document Loaded: registro.js');
	
	var boton      = document.getElementById('boton');
	var nombre     = document.getElementById('nombre');
	var email      = document.getElementById('email');
	var password   = document.getElementById('password');
	var rePassword = document.getElementById('rePassword');

	validar();	
	
	registrarListenner();
}


function validar(){
	
	console.log('validar');
	
	
	if ( nombre == "" ){
		boton.disabled = true;
		return;
	}else{
		boton.disabled = false;
	}
	
}


function registrarListenner(){
	
	console.log('registrarListenner');
	nombre.addEventListener("blur", validar );
}


/*
function confirmPassword() {
	var password = document.getElementById('fcontraseña');
	var password2 = document.getElementById('fcontraseña2');
	var valor1=password.value;
	var valor2=password2.value;
	if (valor1==valor2){
		console.log("Contraseña correcta");
	}else{
		console.log("Contraseña incorrecta");
	}	
}
*/
	
