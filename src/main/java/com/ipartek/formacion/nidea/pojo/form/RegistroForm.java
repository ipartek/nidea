package com.ipartek.formacion.nidea.pojo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

public class RegistroForm {

	@NotNull
	@Size(min=3,max=45)
	private String nombre;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min=6,max=45)
	private String pass;
	
	@NotNull
	@Size(min=6,max=45)
	private String pass2;

	public RegistroForm() {
		super();
		this.nombre = "";
		this.email = "";
		this.pass = "";
		this.pass2 = "";
	}

	public RegistroForm(String nombre, String email , String pass, String pass2) {
		this();
		this.nombre = nombre;
		this.email = email;
		this.pass = pass;
		this.pass2 = pass2;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	@Override
	public String toString() {
		return "RegistroForm [nombre=" + nombre + ", email=" + email + ", pass=" + pass + ", pass2=" + pass2 + "]";
	}
	
	
}
