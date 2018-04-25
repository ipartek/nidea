function buscarUsuario(event) {
			// console.log('buscar usuario: click %s', event.target.value);
			var nombreBuscar = event.target.value;
			var url = "api/usuario/?nombre=" + nombreBuscar;
			console.log('Nombre: %s', nombreBuscar);

			var options = '';
			var select = document.getElementById("s_usuarios");
			select.innerHTML = '<option value="-1">--Seleccione una opcion--</option>';

			// llamada ajax
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var data = JSON.parse(this.responseText);
					console.log('retorna datos %o', data);
					 data.forEach( el => {
			            	options += '<option value="'+ el.id + '">'+el.nombre+'</option>';
			            });
					select.innerHTML = options;
						
				} else if (this.readyState == 4 && this.status == 204){
					select.innerHTML = '<option value="-1">--Seleccione una opcion--</option>';
				}
			};
			xhttp.open("GET", url, true);
			xhttp.send();
			
		}