package com.ipartek.formacion.nidea.pojo;

public class Usuario {

	public static final int ROL_ADMIN = 1;
	public static final int ROL_USER = 2;

	private int id;
	private String nombre;
	private String pass;
	private String mail;
	private Rol rol;

	public Usuario() {
		super();
		this.id = -1;
		this.pass = "";
		this.mail ="";
		this.nombre = "Elija un usuario";
		this.rol = new Rol();
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", pass=" + pass + ", mail=" + mail + ", rol=" + rol + "]";
	}
	
	

	
}
