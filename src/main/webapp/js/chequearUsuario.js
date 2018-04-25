/*
 * Llamada Ajax para filtrar usuarios por nombre y cargar un select options
 * 
 * 
 * */

function chequearNombre( event ){
	
	var nombre = document.getElementById("name");
	var email=document.getElementById("email");
	email.setAttribute("value", nombre.value+"@"+nombre.value+".com");
	console.log(email.value);
	var nombreBuscar = event.target.value;
	
	var url = "api/register?nombre=" + nombreBuscar;
	/*
	var options = '';
	var select = document.getElementById('sUsuarios');
	// eliminar opstions antiguas
	select.innerHTML = "";
	
	// llamada Ajax
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	// llamada terminada y correcta
        if (this.readyState == 4 && this.status == 200) {
        	var data = JSON.parse(this.responseText);
            console.log('retorna datos %o', data);
            data.forEach( el => {
            	options += '<option value="'+ el.id + '">'+el.nombre+'</option>';
            });
            select.innerHTML = options;
       }else if (this.readyState == 4 && this.status == 204){
    	 select.innerHTML = '<option value="">-- Sin Coincidencias --</option>';
       }
    };
    xhttp.open("GET", url , true);
	xhttp.send(); 
	*/
	  			
}

function chequearPassword( event ){
	
	var nombre = document.getElementById("name");
	var password=document.getElementById("password");
	var password_confirmation=document.getElementById("password_confirmation");
	var nameError=document.getElementById("passwordError");
	var boton=document.getElementById("boton");
	
	boton.disabled=true;
	
	if(password.value!=password_confirmation.value){
		nameError.setAttribute("style", "display:inline;")
	}
	else{
		nameError.setAttribute("style", "display:none;")
		boton.disabled=false;
	}
	  			
}	