package com.ipartek.formacion.nidea.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilidadesTest {

	@Test
	public void testLimpiarEspacios() {

		assertEquals("hola que haxe", Utilidades.limpiarEspacios("     hola    que    haxe  "));
		assertEquals("", Utilidades.limpiarEspacios(null));
	}

}
