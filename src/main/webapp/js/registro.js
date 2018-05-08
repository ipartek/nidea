/* Libreria para llamadas Ajax XMLHGttpRequest en Vanilla JavaScript
Retornando Promise*/

function comprobarNombre(event){
    var method = "GET";
    var nombre = document.getElementById('nombre');
    var aviso = document.getElementById('avisoNombre');
    	
    var url = "api/nombre?nombre=" + nombre.value;
    var options = '';
		
    var request;

    var promesa = ajax(method,url);
    	
    promesa.then(dato => {
    	aviso.innerHTML = "Usuario ya existe";
    	deshabilitar();
    	})
    	.catch(error => {
    		if (checkPass()){
    			habilitar();
    		}
    		aviso.innerHTML = "Usuario disponible";
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
