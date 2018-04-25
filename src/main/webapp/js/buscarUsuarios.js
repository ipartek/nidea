function buscarUsuario( event ){
	//console.log('buscarUsuario: click %o', event);
	var nombreBuscar = event.target.value;
	console.log('nombre %s', nombreBuscar);
	var url = "api/usuario?nombre=" + nombreBuscar;
	
	var options = '';
	var select = document.getElementById('sUsuarios');
	//eliminar opstions antiguas
	select.innerHTML = "";
	
	//llamada Ajax
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	//llamada terminada y correcta
        if (this.readyState == 4 && this.status == 200) {
        	var data = JSON.parse(this.responseText);
            console.log('retorna datos %o', data);
			data.forEach( el => {
            	options += '<option value="'+ el.id + '">'+el.nombre+'</option>';
            });
            
       } else if (this.readyState == 4 && this.status == 204){
    	   options = '<option value=-1>Busqueda sin coincidencias</option>';
       }
      select.innerHTML = options;
    };
    xhttp.open("GET", url , true);
    xhttp.send(); 
}