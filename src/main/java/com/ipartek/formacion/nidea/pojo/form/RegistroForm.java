package com.ipartek.formacion.nidea.pojo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class RegistroForm {

	@NotNull
	@Size(min = 3, max = 45)
	private String nombre;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 6, max = 45)
	private String password;

	@NotNull
	@Size(min = 6, max = 45)
	private String rePassword;

	public RegistroForm() {
		super();
		this.nombre = "";
		this.email = "";
		this.password = "";
		this.rePassword = "";
	}

	public RegistroForm(String nombre2, String email2, String password2, String rePassword2) {
		this();
		this.nombre = nombre2;
		this.email = email2;
		this.password = password2;
		this.rePassword = rePassword2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	@Override
	public String toString() {
		return "RegistroForm [nombre=" + nombre + ", email=" + email + ", password=" + password + ", rePassword="
				+ rePassword + "]";
	}
	
	

}
