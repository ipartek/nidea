/*
 *  Llamada Ajax para filtrar usuarios por nombre y cargar un select options
 */
function buscarUsuario (event){
				
	//console.log('buscarUsuario: click %o', event);
	var nombreBuscar =event.target.value;// coge sobre que elemento se ha hecho el evento y recoge el value
	console.log ('nombre %s', nombreBuscar);
	var url ="api/usuario?nombre="+nombreBuscar;//url a la que llamamos
	
	var options ='';//todos los options que metemos en el select
	var select = document.getElementById('sUsuarios');
	select.innerHTML ="";//Elimina options antiguas
	
	//llamada ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(){
		//llamada terminada y correcta
		if(this.readyState == 4 && this.status ==200) {
			var data =JSON.parse(this.responseText);
			console.log ('retorna datos %o', data);
			data.forEach( el => {
				options += '<option value="'+el.id +'">'+el.nombre +'</option>';
			});			
			
		} else if (this.readyState == 4 && this.status ==204){
			console.log ('sin datos');						
			options = '<option value ="">Sin datos</option>';			
		}
		select.innerHTML=options;
	
	};
	xhttp.open("GET", url, true);
	xhttp.send();	
}