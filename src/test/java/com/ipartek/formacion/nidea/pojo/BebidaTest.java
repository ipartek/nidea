package com.ipartek.formacion.nidea.pojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BebidaTest {

	static ValidatorFactory factory;
	static Validator validator;
	static Bebida bebida;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// Crear Factoria y Validador
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		factory = null;
		validator = null;
	}

	@Before
	public void setUp() throws Exception {
		bebida = new Bebida();
	}

	@After
	public void tearDown() throws Exception {
		bebida = null;
	}

	@Test
	public void validar() {

		try {

			Set<ConstraintViolation<Bebida>> violations = validator.validate(bebida);

			assertTrue(violations.size() == 2);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void validarNombre() {

		bebida.setPrecio(0.2f);
		bebida.setNombre("ab");

		Set<ConstraintViolation<Bebida>> violations = validator.validate(bebida);

		// si ponemos precio correcto, solo falla el nombre que es == "ab"

		violations = validator.validate(bebida);
		assertTrue(violations.size() == 1);

		Iterator<ConstraintViolation<Bebida>> it = violations.iterator();
		ConstraintViolation<Bebida> violation = null;
		while (it.hasNext()) {
			violation = it.next();
			assertEquals("nombre", violation.getPropertyPath().toString());
			assertEquals("ab", violation.getInvalidValue());
			// violation.getMessage();
		}

	}

}
