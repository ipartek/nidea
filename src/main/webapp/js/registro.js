/* Libreria para llamadas Ajax XMLHGttpRequest en Vanilla JavaScript
Retornando Promise*/

function comprobarNombre(event){
    const method = "GET";
    var nombre = document.getElementById('nombre');
    var avisoNombre = document.getElementById('avisoNombre');
    	
    var url = `api/nombre?nombre=${nombre.value}`;
    var options = '';
		
    var request;

    var promesaNombre = ajax(method,url);
    	
    promesaNombre.then(result => {
    	let status = result.status;
    	if (status >= "200" && status <=203){
    		avisoNombre.innerHTML = "Usuario ya existe";
    		avisoNombre.classList.add("text-danger");
    		avisoNombre.classList.remove("text-success");
        	deshabilitar();
    	}else if(status == "204"){
    		avisoNombre.classList.add("text-success");
    		avisoNombre.classList.remove("text-danger");
    		avisoNombre.innerHTML = "Usuario disponible";
    		if (checkPass()){
    			habilitar();
    		}	
    	}
    	
    	})
    	.catch(error => {
    		console.warn(error);
    	});
}

function comprobarEmail(event){
    const method = "GET";
    var email = document.getElementById('email');
    var avisoEmail = document.getElementById('avisoEmail');
    	
    var url = `api/email?=${email.value}`;
    var options = '';
		
    var request;

    var promesaEmail = ajax(method,url);
    	
    promesaNombre.then(result => {
    	let status = result.status;
    	if (status >= "200" && status <=203){
    		avisoEmail.innerHTML = "Email ya existe";
    		avisoEmail.classList.add("text-danger");
    		avisoEmail.classList.remove("text-success");
        	deshabilitar();
    	}else if(status == "204"){
    		avisoEmail.classList.add("text-success");
    		avisoEmail.classList.remove("text-danger");
    		avisoEmail.innerHTML = "Email disponible";
    		if (checkPass()){
    			habilitar();
    		}	
    	}
    	
    	})
    	.catch(error => {
    		console.warn(error);
    	});
}

        

function checkPass (){
	var resul = false;
	var pass = document.getElementById('pass');
	var pass2 = document.getElementById('pass2');
	if (pass.value == pass2.value){
		resul= true;
	} else{
		resul=false;
	}
	return resul;
}

function habilitar(){
	var btn = document.getElementById('btnRegistrar');
	btn.disabled=false;
}

function deshabilitar(){
	var btn = document.getElementById('btnRegistrar');
	btn.disabled=true;
}
