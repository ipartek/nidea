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
import org.omg.PortableServer.ID_UNIQUENESS_POLICY_ID;

public class procedureCallTest {

	static int idUsuario =-1;
	static String nombre="Random";
	static String email = "Random@random.com";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			
			final String SQL = "{call pa_create_user(?,?,?)}";
			try (
				Connection con = DriverManager.getConnection(URL);
				CallableStatement cs= con.prepareCall(SQL)
				){
				// registro parametros de entrada y salida
				cs.setString(1, nombre);
				cs.setString(2, email);
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				
				// ejecutar procedimiento
				cs.execute();
				
				// recoger parametros
				idUsuario = cs.getInt(3);
			}
			assert(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			
			final String SQL = "{call pa_delete_user(?)}";
			try (
				Connection con = DriverManager.getConnection(URL);
				CallableStatement cs= con.prepareCall(SQL)
				){
				// registro parametros de entrada y salida
				cs.setInt(1, idUsuario);
				
				// ejecutar procedimiento
				cs.execute();
			}
			assert(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			
			final String SQL = "{call pa_get_user_by_id(?,?,?)}";
			try (
				Connection con = DriverManager.getConnection(URL);
				CallableStatement cs= con.prepareCall(SQL)
				){
				
					// para comprobar que encontramos dato
					int idUsuario = 122;
					String nombre = "Gary Rivera Ba�os De La Encina";
					String email = "GarySRivera@pookmail.com";
					
					// registro parametros de entrada y salida
					cs.setInt(1, idUsuario);
					cs.registerOutParameter(2, java.sql.Types.VARCHAR);
					cs.registerOutParameter(3, java.sql.Types.VARCHAR);
					
					// ejecutar procedimiento
					cs.execute();
					
					// recoger parametros
					assertEquals (nombre, cs.getString(2));
					assertEquals (email, cs.getString(3));
					
					//TODO comprobar test cuando no exista
				}
			assert(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error "+e.getMessage());
		}
	}

}
