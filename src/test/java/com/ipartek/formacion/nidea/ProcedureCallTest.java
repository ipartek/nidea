package com.ipartek.formacion.nidea;

import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcedureCallTest {

	final static String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";

	private static Connection con = null;
	private static CallableStatement cs = null;
	private static String SQL = "";

	private static int idUsuario;
	private static String nombre = "perico de los palotes";
	private static String email = "perico@palotes.com";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);

			SQL = "{ call pa_createUser ( ?, ?, ? ) }";
			cs = con.prepareCall(SQL);

			cs.setString(1, nombre);
			cs.setString(2, email);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);

			cs.execute();

			idUsuario = cs.getInt(3);

		} catch (Exception e) {
			fail("ERROR: " + e.getMessage());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Eliminar el usuario de prueba

		try {

			SQL = "{ call pa_deleteUserById ( ? ) }";
			cs = con.prepareCall(SQL);

			cs.setInt(1, idUsuario);

			cs.execute();

		} catch (Exception e) {
			fail("ERROR: " + e.getMessage());
		} finally {

			if (cs != null) {
				cs.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPaGetUserById() {

		try {

			SQL = "{ call pa_GetUserById ( ?, ?, ? ) }";

			cs = con.prepareCall(SQL);

			// Registramos valores de entrada y salida
			cs.setInt(1, idUsuario);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			// Ejecutar la sentencia
			cs.execute();

			// Recoger parametros y testear
			assertEquals(nombre, cs.getString(2));
			assertEquals(email, cs.getString(3));

			// Registramos valores de entrada y salida de un usuario inexistente para
			// comprobar que devuelve null
			cs.setInt(1, (idUsuario + 1)); //el ultimo id +1 nunca existe
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			// Ejecutar la sentencia
			cs.execute();

			// Recoger parametros y testear
			assertEquals(null, cs.getString(2));
			assertEquals(null, cs.getString(3));

		} catch (Exception e) {
			fail("ERROR: " + e.getMessage());
		}

	}

}
