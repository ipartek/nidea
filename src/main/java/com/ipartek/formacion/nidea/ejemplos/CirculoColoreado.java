package com.ipartek.formacion.nidea.ejemplos;

public final class CirculoColoreado extends Circulo {

	private String color;

	public CirculoColoreado() {
		super();
		this.color = "blanco";

		System.out.println(Imprimible.BRAILLE);
	}

	@Override
	void dibujar() {
		super.dibujar();

	}

}
