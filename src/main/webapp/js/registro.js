//v2
var resultado;
var nombre;
var boton;
var contraseña;
var reContraseña;
var email;

var nombreValido = false;
var nombreChecked = false;
var emailValido = false;
var contraseñaOK = false;

window.onload = function(e) {

	boton = document.getElementById('boton');
	nombre = document.getElementById('nombre');
	nombreMensaje = document.getElementById('nombreMensaje');
	contra = document.getElementById('contraseña');
	confirmarContraseña = document.getElementById('reContraseña');
	email = document.getElementById('email');
	
	validar();

	registrarListeners();
}

function registrarListeners() {
	nombre.addEventListener("keyup", validar);
	contra.addEventListener("blur", validar);
	confirmarContraseña.addEventListener("blur", validar);
	email.addEventListener("blur", validar);
}

function validar() {
	
	if (this.id == 'nombre') {
		
		if(nombre.value.length>=5){
			nombreChecked=true;			
			validarNombre().then(result=>{
				console.log('Nombre %s', result);
				nombreValido = result;
				if(result == true){
					
					nombre.style.border = "3px solid green";
					nombreMensaje.style.color = "green";
					nombreMensaje.innerText = "Nombre disponible";
					
				} else {
					
					nombre.style.border = "3px solid red";
					nombreMensaje.style.color = "red";
					nombreMensaje.innerText = "Nombre no disponible";		
				}
				toggleButton();
			});			
		} else if (nombreChecked){
			
			nombre.style.border = "3px solid red";
			nombreMensaje.style.color = "red";
			nombreMensaje.innerText = "Nombre demasiado corto";
			
		}
	}
		if (this.id == 'email') {
				
			validarEmail().then(result=>{
				console.log('Email %s', result);
				emailValido = result;
				if(result == true){
					email.style.border = "3px solid green";
				} else {
					email.style.border = "3px solid red";
				}
				toggleButton();
			});	
		}
		
		if(this.id == 'contraseña' || this.id == "reContraseña"){
			console.log(this.value);
			if (contra.value == confirmarContraseña.value && this.value != ''){
				contra.style.border = "3px solid green";
				confirmarContraseña.style.border = "3px solid green";
				contraseñaOK = true;
			} else {
				contra.style.border = "3px solid red";
				confirmarContraseña.style.border = "3px solid red";
				contraseñaOK = false;
			}
			toggleButton();
		}
}

function validarNombre() {

	var nombreBuscar = nombre.value;
	var url = "api/usuario/?nombreExacto=" + nombreBuscar;
	
	return new Promise(function(resolve, reject) {
		ajax("GET", url).then(request=>{
			
			if(request.status == 200){
				resolve(false);
			} else if (request.status == 204){
				resolve(true);
			}			
		});
	});
}

function validarEmail() {

	var emailBuscar = email.value;
	var url = "api/usuario/?email=" + emailBuscar;
	
	return new Promise(function(resolve, reject) {
		ajax("GET", url).then(request=>{
			
			if(request.status == 200){
				resolve(false);
			} else if (request.status == 204){
				resolve(true);
			}			
		});
	});
}

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

function toggleButton(){
	if(nombreValido && emailValido && contraseñaOK){
		boton.disabled = false;
	} else {
		boton.disabled = true;
	}
}