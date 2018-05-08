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
            
            if(request.status>=200 && request.status<=203){
                
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
function chequeoUsuario(){
	
	var usuario=document.getElementById('nombre');
	nombre=usuario.value;
	var nameError=document.getElementById('nameError');
	
	 let promesa=ajax('GET','http://localhost:8080/usuario/');
     promesa.then(data=>{
         console.log(data)});
     let c2=data[1];
     console.log(data[1]); 
	
	// console.log('buscarUsuario: click %o', event);
	var nombreBuscar = event.target.value;
	console.log('nombre %s', nombreBuscar);
	var url = "api/usuario?nombre=" + nombreBuscar;
	
	var options = '';
	var select = document.getElementById('sUsuarios');
	var usuario=document.getElementById('nombre');
		nombre=usuario.value;
	var nameError=document.getElementById('nameError');
	
	// llamada Ajax
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	// llamada terminada y correcta
        if (this.readyState == 4 && this.status == 200) {
        	var data = JSON.parse(this.responseText);
            console.log('retorna datos %o', data); 
       }else if (this.readyState == 4 && this.status == 204){
    	 select.innerHTML = '<option value="">-- Sin Coincidencias --</option>';
       }
    };
    xhttp.open("GET", url , true);
	xhttp.send(); 
	if(nombre.lenght>4){
		if(nombre=JSON.parse(this.responseText).nombre){
			nameError.setAttribute("style","display:none");
		}else{
			nameError.setAttribute("style","display:inline");
		}
				
		}
}

	  			
