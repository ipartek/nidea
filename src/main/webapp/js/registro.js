

window.onload = function(e){
	
	console.log('Document loaded: registro.js');

	var boton = document.getElementById("boton");
	var nombre = document.getElementById("nombre");
	var email = document.getElementById("email");
	var password = document.getElementById("password");
	var rePassword = document.getElementById("rePassword");
	
	validar();
	
	registrarListener

}


function validar(){
	
	console.log('Validar');
	
	if (nombre == null){
		boton.disabled = true;
		return;
	}

	
}


function registrarListener(){
	nombre.addEventListener("blur", validar);
}


function checkPassword(){
		
	var password = document.getElementById("password");
	var confirmPassword = document.getElementById("password2")
	
	var pass1 = password.value;
	var pass2 = confirmPassword.value;

	if (pass1 != pass2){
	    console.log("Contraseña incorrecta");
	}else{
	    console.log("Contraseña correcta");
	}
	
	console.log("Cambio de foco");
		
	
}











