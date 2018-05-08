
var error_pass;
var error_pass1;
var error_pass2;

var boton;
var nombre;
var email;
var password;
var password2;

window.onload= function(e){
	console.log('Documento registro.js');
	var btn = document.getElementById("btn");
	
	error_pass = document.getElementById('error_pass');
	error_pass.style.display = 'none';
	
	error_pass1 = document.getElementById('error_pass1');
	error_pass1.style.display = 'none';
	
	error_pass2 = document.getElementById('error_pass2');
	error_pass2.style.display = 'none';
	
	nombre = document.getElementById("nombre");
	email = document.getElementById("email");
	password = document.getElementById("password");
	password2 = document.getElementById("password2");
	
	validar();
	
	registrarListenner();
}



function validar(){
	if ( nombre.value == "" || nombre.value.length < 5 ){
		error_pass1.style.display = 'block';
		error_pass2.style.display = 'none';
		btn.disabled = true;
		return;
	}else{
		error_pass2.style.display = 'block';
		error_pass1.style.display = 'none';
		btn.disabled = false;
		
	}
	
	if (  password.value !== password2.value ){
		error_pass.style.display = 'block';
		btn.disabled = true;
	}else{
		error_pass.style.display = 'none';
	}
	
	if ( email.value == "" ){
		btn.disabled = true;
		return;
	}else{
		btn.disabled = false;
	}
	
}


function registrarListenner(){
	
	console.log('registrarListenner');
	nombre.addEventListener("blur", validar );
	email.addEventListener("blur", validar );
	password.addEventListener("blur", validar );
	password2.addEventListener("blur", validar );
}