	window.onload = function(e){ 
		
		console.log("Document Loaded:registro.js");
	
	var boton=document.getElementById("boton")
	var nombre= document.getElementById("nombre");
	var email= document.getElementById("email");
	var password= document.getElementById("password");
	var password2= document.getElementById("password2");

	validar();	
	
	registrarListener();
}
	
function validar(){
	
	console.log("Validar");
	if(nombre.value==""){
		boton.disabled=true;
		return;
	}
	else{
		boton.disabled=false;
		
	}

}

function registrarListener(){
	console.log("registrarListener");
	
	nombre.addEventListener("blur", validar());
	
}