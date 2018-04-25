window.document.onload=function(e){
	
console.log('Document Loaded: registro.js')
var boton = document.getElementById('boton');
var nombre = document.getElementById('name');
var email = document.getElementById('email');
var password = document.getElementById('password');
var password2 =document.getElementById('confirm');
validar();
}
function validar(){
	console.log('validar');
	
	if(nombre==""){
		boton.disable=true;
		return;
	}
}
