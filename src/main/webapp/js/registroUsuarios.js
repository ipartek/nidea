var resultado;
var nombre;
var boton;
var password;
var confirm_password;
var email;

var nombre_valid = false;
var email_valid = false;
var pass_valid = false;

window.onload = function(e) {

	boton = document.getElementById('boton');
	nombre = document.getElementById('nombre');
	pass = document.getElementById('password');
	confirm_pass = document.getElementById('confirm_password');
	email = document.getElementById('email');

	validar();

	registrarListeners();
}

function registrarListeners() {
	nombre.addEventListener("blur", validar);
	pass.addEventListener("blur", validar);
	confirm_pass.addEventListener("blur", validar);
	email.addEventListener("blur", validar);
}

/**
 * valida los campos que tienen eventos de validacion asociados
 */
function validar() {
	
	if (this.id == 'nombre') {
			
			validarNombre().then(result=>{
				console.log('Nombre %s', result);
				nombre_valid = result;
				if(result == true){
					nombre.style.border = "3px solid green";
				} else {
					nombre.style.border = "3px solid red";
				}
				toggleButton();
			});			
		}
		
		if (this.id == 'email') {
				
			validarEmail().then(result=>{
				console.log('Email %s', result);
				email_valid = result;
				if(result == true){
					email.style.border = "3px solid green";
				} else {
					email.style.border = "3px solid red";
				}
				toggleButton();
			});	
		}
		
		if(this.id == 'password' || this.id == "confirm_password"){
			console.log(this.value);
			if (pass.value == confirm_pass.value && this.value != ''){
				pass.style.border = "3px solid green";
				confirm_pass.style.border = "3px solid green";
				pass_valid = true;
			} else {
				pass.style.border = "3px solid red";
				confirm_pass.style.border = "3px solid red";
				pass_valid = false;
			}
			toggleButton();
		}
}

/**
 * Contruye la url para hacer la busqueda por nombre a traves de otra promise en
 * la bbdd
 * 
 * @returns promise con valor booleano
 */
function validarNombre() {

	var nombreBuscar = nombre.value;
	var url = "api/usuario/?nombreExacto=" + nombreBuscar;

	return new Promise(function(resolve, reject) {
		validarPromise(url, nombreBuscar).then(function(result) {
			// console.log('resultado nombre %s', result);
			resolve(result);
		});
	});
}

/**
 * Contruye la url para hacer la busqueda por email a traves de otra promise en
 * la bbdd
 * 
 * @returns promise con valor booleano
 */
function validarEmail() {

	var emailBuscar = email.value;
	var url = "api/usuario/?email=" + emailBuscar;
	
	return new Promise (function(resolve,reject){
		validarPromise(url, emailBuscar).then(function(result) {
			// console.log('resultado email %s', result);
			resolve(result);
		});
	});
}

/**
 * valida contra el servicio rest y devuelve una promise
 * 
 * @param url
 *            contiene el parametro de busqueda y el valor
 * @returns true si no hay resultados false si hay resultados
 */
function validarPromise(url) {
	return new Promise(function(resolve, reject) {

		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", url, true);
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log('No Disponible. Hay resultados coincidentes en la bbdd');
				resolve(false);

			} else if (this.readyState == 4 && this.status == 204) {
				console.log('Disponible. No se encontraron resultado en la bbdd');
				resolve(true);
			}
		}
		xhttp.send();
	});
}

/**
 * Cambia el estado del boton de submit en funcion de las validaciones
 */
function toggleButton(){
	if(nombre_valid && email_valid && pass_valid){
		boton.disabled = false;
	} else {
		boton.disabled = true;
	}
}
