window.document.onload =function(e){
	var boton =document.getElementById('boton');
	var nombre =document.getElementById('nombre');
	var email =document.getElementById('email');
	var password =document.getElementById('password');
	var rePassword =document.getElementById('rePassword');
	
	registrarListenner();

validar();
}

function validar(){
	console.log('validar');
	if (nombre == ""){
		boton.disabled = true;
		return;
	}
	else 
		boton.disabled = false;
}

function registrarListenner(){
	console.log('registrarListenner');
	nombre.addEventListener("blur", validar());
	
}