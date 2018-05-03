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
		// TO DO insertado usuario Mock o de prueba
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{ call pa_get_user_by_id(?,?,?) }";
			
			try (
				Connection con = DriverManager.getConnection(URL);
				CallableStatement cs = con.prepareCall(SQL);
			){
				
				// Para comprobar que lo encontramos mediante el PA
				int idUsuario = 13843;
				String nombre = "Iparesc Alante";
				String email = "formacion@iparesc.com";
				
				// Registrando parametros entrada y salida
				cs.setInt(1, idUsuario);
				cs.registerOutParameter(2, java.sql.Types.VARCHAR);
				cs.registerOutParameter(3, java.sql.Types.VARCHAR);
				
				// Ejecutamos procedimiento
				cs.execute();
				
				// Recoger parametros
				assertEquals( nombre, cs.getString(2));
				assertEquals( email, cs.getString(3));
				
			}
			
			// TO DO Comprobar test cuando no existe

			
	
			assertTrue(true);
			
		}catch(Exception e){
			fail("Error:" + e.getMessage());
		}
	}
	
	@Test
	public void testPaCreateUser() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL2 = "{ call pa_create_user(?,?,?,?,?) }";
			
			try (
				Connection con = DriverManager.getConnection(URL);
				CallableStatement cs = con.prepareCall(SQL2);
			){// Para comprobar que lo encontramos mediante el PA
				
				String nombre = "drtfesefgg";
				String email = "dbdrfeeffehhm";
				String password = "123456";
				int id_rol = 2;
				int id = -1;
				
				// Registrando parametros entrada y salida
				cs.setString(1, nombre);
				cs.setString(2, email);
				cs.setString(3, password);
				cs.setInt(4, id_rol);
				cs.registerOutParameter(5, java.sql.Types.INTEGER);
				
				
				// Ejecutamos procedimiento
				cs.execute();
				
				// Recoger parametros
			//	assertEquals( id, cs.getInt(5));
				assertTrue( cs.getInt(5)>0);
			
				
			}
			
			// TO DO Comprobar test cuando no existe

			
	
			assertTrue(true);
			
		}catch(Exception e){
			fail("Error:" + e.getMessage());
		}
	
	}

}
