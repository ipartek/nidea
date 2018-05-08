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

	validar();	
	
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
	

	
	
}


function registrarListenner(){
	
	console.log('registrarListenner');
	nombre.addEventListener("blur", validar );
	email.addEventListener("blur", validar );
	password.addEventListener("blur", validar );
	rePassword.addEventListener("blur", validar );
}
function ajax(method,url,data=null){
    
    return new Promise(function(resolve,reject){
        
       let request;
        
        if(window.XMLHttpRequest){
            request = new XMLHttpRequest();
        }else if(window.ActiveObject){
            request=new ActiveObject("Msxml2.XMLHTTP")
        }else{
            reject("Tu navegador no soporta llamadas Ajax")
        }
        
        request.onreadystatechange=function(){
        if(request.readyState==4){
            
            if(request.status>=200 && request.status<=299){
                
            resolve(JSON.parse(request.responseText));
        }else{
            reject(Error(request.statusText));
        }
        }
        };
        
        request.open('GET', url, true);
        request.setRequestHeader("content-type","application/json")
        request.send(null);
        //resolve(datos)
        //reject(error)
        
    })
 
}