var nombre;

window.document.onload = function (e){
	
	
	validar();
}

function validar(event){

	var btn = document.getElementById('btnRegistrar');
	var nombre = document.getElementById('nombre');
	var email = document.getElementById('email');
	var pass = document.getElementById('pass');
	var pass2 = document.getElementById('pass2');
	
	var url = "api/usuario?nombre=" + nombre;
	var options = '';
	var aviso;
	
	//llamada Ajax
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	//llamada terminada y correcta
        if (this.readyState == 4 && this.status == 200) {
        	deshabilitar();
        } else{
        	if (this.readyState == 4 && this.status == 204 && checkPass()) {
        		habilitar();
        	}
        		
        }
    };
    xhttp.open("GET", url , true);
    xhttp.send(); 
}

function checkPass (){
	var resul = false;
	var pass = document.getElementById('pass');
	var pass2 = document.getElementById('pass2');
	if (pass.equals(pass2)){
		resul= true;
	} else{
		resul=false;
	}
	return resul;
}

function habilitar(){
	btn.disabled=false;
}

function deshabilitar(){
	btn.disabled=true;
}
