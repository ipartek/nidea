package com.ipartek.formacion.nidea.pojo;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Material {

	private int id;

	@NotNull
	@Size(min = 1, max = 45)
	private String nombre;

	@NotNull
	@DecimalMin("0.1")
	@DecimalMax("999999999.99")
	private float precio;

	@NotNull
	private Usuario usuario;

	public Material() {
		super();
		this.id = -1;
		this.nombre = "";
		this.precio = 0f;
		this.usuario = new Usuario();
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

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", usuario=" + usuario + "]";
	}

}
