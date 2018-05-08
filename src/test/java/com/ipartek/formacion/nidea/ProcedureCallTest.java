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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TODO deberiamos haber insertado el usuario MOC de prueba para testear el PA
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL= "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL="{CALL pa_create_user(?,?,?)}";
			
			try(
					Connection con=DriverManager.getConnection(URL);
					CallableStatement cs = con.prepareCall(SQL)
				){
				
				int idUsuario=-1;
				String nombre="********";
				String email="*******@*******";
				
				cs.setString(1, nombre);
				cs.setString(2,email);
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				
				//ejecutamos procedimiento
				cs.execute();
				
				assertEquals(nombre, cs.getString(1));
				assertEquals(email, cs.getString(2));
				assertTrue(0<cs.getInt(3));
				
			}
			
			
			Connection con= DriverManager.getConnection(URL);
			

		}
		catch(Exception e){
			fail("Error: " + e.getMessage());
		}
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//TODO deberiamos haber eliminado el usuario MOC de prueba para testear el PA
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
			Class.forName("com.mysql.jdbc.Driver");
			final String URL= "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL="{CALL pa_get_user_by_id(?,?,?)}";
			
			try(
					Connection con=DriverManager.getConnection(URL);
					CallableStatement cs = con.prepareCall(SQL)
				){
				
				int idUsuario=2;
				String nombre="pepe";
				String email="pepe@pepep.com";
				
				cs.setInt(1, idUsuario);
				cs.registerOutParameter(2, java.sql.Types.VARCHAR);
				cs.registerOutParameter(3, java.sql.Types.VARCHAR);
				
				//ejecutamos procedimiento
				cs.execute();
				
				assertEquals(nombre, cs.getString(2));
				assertEquals(email, cs.getString(3));
				
			}
			
			
			Connection con= DriverManager.getConnection(URL);
			
			//deberiamos haber insertado un usuario con id 2,
			//para comprobar que lo encontramos mediante el PA
			assertTrue(true);

		}
		catch(Exception e){
			fail("Error: " + e.getMessage());
		}
	}
	
	

}
