var boton;
var error_pass;

var nombre;
var email;
var password;
var rePassword;

window.onload = function(e){
	
	console.log('Document Loaded: registro.js');
	
	boton      = document.getElementById('boton');
	error_pass = document.getElementById('error_pass');
	error_pass.style.display = 'none';
	
	nombre     = document.getElementById('nombre');
	email      = document.getElementById('email');
	password   = document.getElementById('password');
	rePassword = document.getElementById('rePassword');

	//validar();	
	
	registrarListenner();
}


function validar(){
	
	console.log('validar');
	
	if (  password.value !== rePassword.value ){
		error_pass.style.display = 'block';
		boton.disabled = true;
	}else{
		error_pass.style.display = 'none';
	}
	
	if ( nombre.value == "" ){
		boton.disabled = true;
		return;
	}else{
		boton.disabled = false;
	}
	
	if ( email.value == "" ){
		boton.disabled = true;
		return;
	}else{
		boton.disabled = false;
	}
	
}//validar


function checkNombre(){
	console.log('checkNombre');
	var nombre_mensaje = document.getElementById('nombre_mensaje');
	
	if ( nombre.value.length > 4 ){
		console.log('checkNombre: llamada ajax');
		
		const url = `api/usuario?nombre=${nombre.value}`;	 	
		let promesaNombre = ajax('GET', url );
		promesaNombre.then( result => {
					console.log(result)
					let status = result.status;				
					if ( status === 200){                // nombre ocupado
						nombre_mensaje.innerHTML = '* Nombre Ocupado';
						nombre_mensaje.classList.add("text-danger");
						nombre_mensaje.classList.remove("text-success");
					}else{                               // nombre libre 204 
						nombre_mensaje.innerHTML = 'Nombre Disponible';
						nombre_mensaje.classList.add("text-success");
						nombre_mensaje.classList.remove("text-danger");
					}
		});		
		
	}else{
		nombre_mensaje.innerHTML = '* Minimo 5 letras';
		nombre_mensaje.classList.add("text-danger");
		nombre_mensaje.classList.remove("text-success");
	}	
}


function registrarListenner(){
	
	console.log('registrarListenner');
	nombre.addEventListener("keyup", checkNombre );
	email.addEventListener("blur", validar );
	password.addEventListener("blur", validar );
	rePassword.addEventListener("blur", validar );
}
