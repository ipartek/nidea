//hhh
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
	//email      = document.getElementById('email');
	password   = document.getElementById('password');
	rePassword = document.getElementById('rePassword');
	

	validar();	
	
	registrarListenner();
	
	document.getElementById('nombre').addEventListener('input',function(event){
		var nombre_caracteres = event.target.value;
		if (nombre_caracteres.length >= 5){
			var url = "api/registro?nombre=" + nombre_caracteres;
			llamadaAjax(url, "nombre");
		}	else {
			document.getElementById('nombre').style.border = "none";
		}	
	});
	



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
	
	/*if ( email.value == "" ){
		boton.disabled = true;
		return;
	}else{
		boton.disabled = false;
	}*/
	

	
	
}


function registrarListenner(){
	
	console.log('registrarListenner');
	nombre.addEventListener("blur", validar );
	//email.addEventListener("blur", validar );
	password.addEventListener("blur", validar );
	rePassword.addEventListener("blur", validar );
}

function llamadaAjax(url, field){
	
	//llamada Ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		//llamada terminada y correcta
	    if (this.readyState == 4 && this.status == 200) {
	    	var data = this.responseText;
	    	console.log(data);
	    	
	    	// Si es true NO permito registrarse
	    	
	    	if(field == "nombre"){
	    		document.getElementById('usuario').style.border = "1px solid red";
	    		document.getElementById('mesgNombre').innerHTML = "Nombre Existente";
	    	}else{
	    		document.getElementById('email').style.border = "1px solid red";
	    		document.getElementById('#msgEmail').innerHTML = "Email Existente";
	    	}
	    	document.getElementById('boton').disabled = true;
	    	   
		   } else if (this.readyState == 4 && this.status == 204){
			   var data = this.responseText;
		    	console.log(data);
		   };
		   xhttp.open("GET", url , true);
			xhttp.send();
	}
	}
		}