package com.ipartek.formacion.nidea.pojo;

public class Usuario {

	public static final int ROL_ADMIN = 1;
	public static final int ROL_USER = 2;

	private int id;
	private String nombre;
	private String pass;
	private String email;
	private Rol rol;

	public Usuario() {
		super();
		this.id = -1;
		this.pass = "";
		this.nombre = "";
		this.email = "";
		this.rol = new Rol();
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", pass=" + pass + ", email=" + email + ", rol=" + rol
				+ "]";
	}
}
