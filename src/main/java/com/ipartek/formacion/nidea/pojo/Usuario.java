package com.ipartek.formacion.nidea.pojo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario {

	public static final int ROL_ADMIN = 1;
	public static final int ROL_USER = 2;
	
	public static final int USUARIO_INDEFINIDO = -1;

	@NotNull(message = "El id de usuario no puede ser nulo")
	private int id;
	
	@NotNull(message = "El nombre de usuario no puede ser nulo")
	@Size(min = 1, max= 45, message = "El tamaño del nombre del usuario no esta entre 1 y 45")
	private String nombre;
	
	@NotNull(message = "La pass no puede ser nula")
	private String pass;
	
	@NotNull(message = "La pass no puede ser nula")
	private String confirm_pass;	
	
	@NotNull(message = "Seleccione un rol valido")
	@Valid
	private Rol rol;
	
	@NotNull(message = "El email no puede ser nulo")
	private String email;

	public Usuario() {
		super();
		this.id = USUARIO_INDEFINIDO;
		this.pass = "";
		this.nombre = "";
		this.rol = new Rol();
		this.email = "";
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getConfirm_pass() {
		return confirm_pass;
	}

	public void setConfirm_pass(String confirm_pass) {
		this.confirm_pass = confirm_pass;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario(int id2, String nombre2) {
		this();
		this.id = id2;
		this.nombre = nombre2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", pass=" + pass + ", rol=" + rol + ", email=" + email
				+ "]";
	}
}
