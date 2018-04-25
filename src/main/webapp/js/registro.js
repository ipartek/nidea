function confpassword(){
	var password = document.getElementById('fcontraseña');
	var password2 = document.getElementById('fcontraseña2');
	var valor1= password.value;
	var valor2= password2.value;
	if(valor1==valor2)
		{
		console.log("contraseña correcta");
		
		}
	else{
		console.log("contraseña incorrecta");
		password2.setCustomValidity("contraseñas incorrectas");
	}
}