package com.ipartek.formacion.nidea.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Rol {

	@Min(value = 1, message = "Seleccione un rol valido")
	private int id;
	
	@NotNull(message = "El nombre del rol no puede ser nulo")
	private String nombre;

	public Rol() {
		super();
		this.id = -1;
		this.nombre = "";
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
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}

}
