var nombreValido = false;
var passwordValido = false;
var emailValido = false;

function ajax(method, url, data = null) {
    return new Promise(function (resolve, reject) {

        let request;

        //soporte para navegadores

        if (window.XMLHttpRequest) { // Mozilla, Safari,...
            request = new XMLHttpRequest();
            if (request.overrideMimeType) {
                request.overrideMimeType('text/xml');
                // Ver nota sobre esta linea al final
            }
        } else if (window.ActiveXObject) { // IE
            try {
                request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        }

        //comprobar cambios de estado
        request.onreadystatechange = function () {

            if (request.readyState === 4) {
                if (request.status >= 200 && request.status <= 203) {
                    resolve(JSON.parse(request.responseText));
                } else {
                    reject(Error(request.statusText));
                }
            }

        };

        request.open(method, url, true);
        request.setRequestHeader("content-type", "application/json")
        request.send(JSON.stringify(data));

    });

}

function chequearNombre(event) {

	var name = document.getElementById("name");
	// var email=document.getElementById("email");
	var nombre = "";
	nombre = name.value;
	// email.setAttribute("value", nombre+"@"+nombre+".com");
	console.log(email.value);
	var nombreBuscar = event.target.value;
	var url = "api/register?nombre=" + nombreBuscar;
	var numCaracteres;
	numCaracteres = nombre.length;
	var nameError = document.getElementById("nameError");

	if (numCaracteres > 4) {
		nombreValido = false;
		// llamada Ajax
		
		let jsonData;
	     let promesa = ajax('GET', url);
	     promesa.then(data => {
	    	nombreValido = false;
			console.log('retorna datos %o', data);
			nameError.setAttribute("style", "display:inline;");
	    	 })
	         .catch(error => {
	        	console.warn(error);
	        	nameError.setAttribute("style", "display:none;")
				nombreValido = true;
	        });
	     
		
		/*
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			// llamada terminada y correcta
			if (this.readyState == 4 && this.status == 200) {
				var data = JSON.parse(this.responseText);
				nombreValido = false;
				console.log('retorna datos %o', data);
				nameError.setAttribute("style", "display:inline;")
			} else if (this.readyState == 4 && this.status == 204) {
				nameError.setAttribute("style", "display:none;")
				nombreValido = true;
			}
		};
		xhttp.open("GET", url, true);
		xhttp.send();
		*/

	}
	if (nombreValido && passwordValido && emailValido) {
		boton.disabled = false;
	}

}

function chequearMail(event) {
	emailValido = false;
	var email = document.getElementById("email");
	console.log(email.value);
	var nombreBuscar = event.target.value;
	var url = "api/register?email=" + nombreBuscar;
	var emailError = document.getElementById("emailError");
	

     let jsonData;
     let promesa = ajax('GET', url);
     promesa.then(data => {
    	 console.log(data);
    	 emailValido = false;
    	 emailError.setAttribute("style", "display:inline;");
    	 })
         .catch(error => {
        	 console.warn(error);
        	 emailError.setAttribute("style", "display:none;");
        	 emailValido = true;
        	 });
	
	/*

	// llamada Ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		// llamada terminada y correcta
		if (this.readyState == 4 && this.status == 200) {
			var data = JSON.parse(this.responseText);
			emailValido = false;
			console.log('retorna datos %o', data);
			emailError.setAttribute("style", "display:inline;")
		} else if (this.readyState == 4 && this.status == 204) {
			emailError.setAttribute("style", "display:none;")
			emailValido = true;
		}
	};
	xhttp.open("GET", url, true);
	xhttp.send();
	
	*/

	if (nombreValido && passwordValido && emailValido) {
		boton.disabled = false;
	}

}

function chequearPassword(event) {
	passwordValido = false;
	var name = document.getElementById("name");
	var password = document.getElementById("password");
	var password_confirmation = document
			.getElementById("password_confirmation");
	var passwordError = document.getElementById("passwordError");
	var passwordError2 = document.getElementById("passwordError2");
	var boton = document.getElementById("boton");
	var passlength=password.value.length;


	boton.disabled = true;

	if (password.value != password_confirmation.value) {
		passwordError.setAttribute("style", "display:inline;")

	} else {
		passwordError.setAttribute("style", "display:none;");
		passwordValido = true;
		if(passlength<5 && passlength>46){
			passwordError2.setAttribute("style", "display:inline;");
			passwordValido = false;
		}
		
	}
	
	if(passlength<6||passlength)

	if (nombreValido && passwordValido && emailValido) {
		boton.disabled = false;
	}

}
