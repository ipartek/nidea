function chequeoUsuario( event ){
	
	// console.log('buscarUsuario: click %o', event);
	var nombreBuscar = event.target.value;
	console.log('nombre %s', nombreBuscar);
	var url = "api/usuario?nombre=" + nombreBuscar;
	
	var options = '';
	var select = document.getElementById('sUsuarios');
	var usuario=document.getElementById('nombre');
		nombre=usuario.value;
	
	
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
		
	}
	
	  			
}