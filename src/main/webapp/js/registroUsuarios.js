var resultado;
var nombre;
var boton;
var password;
var confirm_password;
var email;

var nombre_valid = false;

window.onload = function(e) {

	boton = document.getElementById('boton');
	nombre = document.getElementById('nombre');
	pass = document.getElementById('password');
	confirm_pass = document.getElementById('confirm_password');
	email = document.getElementById('email');

	validar();
	
	registrarListeners();
}

function validar() {
	
	console.log(this);
	if(this.id == 'nombre'){
		console.log('Hola');
		console.log(validarNombre());
	}
}

function registrarListeners(){
	nombre.addEventListener("blur", validar);
	pass.addEventListener("blur", validar);
	confirm_pass.addEventListener("blur", validar);
}

function validarNombre() {
	resultado = false;
	var nombreBuscar = nombre.value;
	var url = "api/usuario/?nombreExacto=" + nombreBuscar;

	// llamada ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log('Hay usuarios con el nombre');
			nombre_valid = false;
			nombre.style.border = "3px solid red";

		} else if (this.readyState == 4 && this.status == 204) {
			console.log('Nombre correcto');
			nombre_valid = true;
			nombre.style.border = "3px solid green";
		}		
	};

	xhttp.open("GET", url, true);
	xhttp.send();

	
}
