package com.ipartek.formacion.nidea.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class RegistroForm {
	@NotNull
	@Size (min=3,max=45)
	private String nombre;
	
	@NotNull
	@Size (min=6,max=45)
	private String password;
	
	@NotNull
	@Size (min=6,max=45)
	private String rePassword;
	
	@NotNull
	@Email
	private String email;
	
	
	
	public RegistroForm() {
		super();
		this.nombre="";
		this.password="";
		this.rePassword="";
		this.email="";
	}
	public RegistroForm(String nombre, String email, String password, String rePassword) {
		this.nombre=nombre;
		this.password=password;
		this.rePassword=rePassword;
		this.email=email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "RegistroForm [nombre=" + nombre + ", password=" + password + ", rePassword=" + rePassword + ", email="
				+ email + "]";
	}
	
	


}
