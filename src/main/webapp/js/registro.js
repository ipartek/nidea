window.document.onload =function(e){
	var boton =document.getElementById('boton');
	var nombre =document.getElementById('nombre');
	var email =document.getElementById('email');
	var password =document.getElementById('password');
	var rePassword =document.getElementById('rePassword');
	//var url= 'api/usuario';
	
	registrarListenner();
	validar();
	buscarNombre(event);
	buscarMail(event);
	checkNombre();
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
var boton=document.getElementById("submit");
var usuarioDisponible;
function buscarNombre(event){
		//console.log('buscarUsuario: click %o', event);
		var nombreBuscar = event.target.value;
		var url = "api/usuario?nombre=" + nombreBuscar;
		
		var nombre = document.getElementById('nombre_mensaje');
		//eliminar options antiguas
		nombre.innerHTML = "";
		
		//llamada Ajax
		if(nombreBuscar!="" && nombreBuscar.length>4){
			//llamada a ajax para comprobarsi existe un nombre
			let promesa = ajax('GET', url);
	        promesa.then(data => {
	        	//llamada terminada y correcta
	        	nombre.innerHTML = "<p style='color:red;'>Ese usuario ya existe</p>";
	            usuarioDisponible=false;	            
	        	
	        	}).catch(error => {
	        		console.warn(error);
	        		nombre.innerHTML = "<p style='color:green;'>Usuario disponible</p>";
	        		usuarioDisponible=true;	        		
	        	});		
	       }
		
	}

function buscarMail(event){
	
	var mailBuscar = event.target.value;
	var url = "api/usuario?mail=" + mailBuscar;
	
	var mail = document.getElementById('confirmar-mail');
	//eliminar options antiguas
	email.innerHTML = "";
	
	//llamada Ajax
	if(mailBuscar!=""){
		//llamada a ajax para comprobarsi existe un nombre
		let promesa = ajax('GET', url);
        promesa.then(data => {
        	//llamada terminada y correcta
        	email.innerHTML = "<p style='color:red;'>Ese mail ya existe</p>";
            mailDisponible=false;	            
        	
        	}).catch(error => {
        		console.warn(error);
        		email.innerHTML = "<p style='color:green;'>Mail disponible</p>";
        		mailDisponible=true;	        		
        	});		
       }
	
}


