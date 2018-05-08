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
		
		// TODO Insertado usuario mock o testing (usuario prueba)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{call pa_createUser(?,?,?,?,?)}";
			
			try(Connection con = DriverManager.getConnection(URL); 
					CallableStatement cs=con.prepareCall(SQL)){
					int idUsuario = 0;
					String nombre = "guaumiatttuuuyy";
					String pass = "444555";
					String email = "guaumiauuutttu@guau.com";
					int idRol = 2;
					
					//Registrando parametros de entrada y salidsa
					cs.registerOutParameter(1, java.sql.Types.INTEGER);
					cs.setString(2, nombre);
					cs.setString(3, pass);
					cs.setString(4, email);
					cs.setInt(5, idRol);
					
					//ejecutamos procedimiento
					cs.execute();
					
					//Recoger parametros
					if(idUsuario > -1) {
						assertEquals(idUsuario, cs.getInt(1));
					}
					
				}
			
			
		}catch (Exception e) {
			
			fail("Error: "+e.getMessage());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{call pa_createUser(?)}";
			try(Connection con = DriverManager.getConnection(URL); 
				CallableStatement cs=con.prepareCall(SQL)){
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				
				cs.execute();
				/*assertEquals(true);*/
				
			}
		
			
		}catch (Exception e) {
			fail("Error" + e.getMessage());
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
			Class.forName("com.mysql.jdbc.Driver");
			
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL = "{call pa_getUserById(?,?,?)}";
			
			try(Connection con = DriverManager.getConnection(URL); 
				CallableStatement cs=con.prepareCall(SQL)){
				int idUsuario = 3;
				String nombre = "Susan Ball Carre�o";
				String email = "SusanJBall@trashymail.com";
				
				//Registrando parametros de entrada y salidsa
				cs.setInt(1, idUsuario);
				cs.registerOutParameter(2,  java.sql.Types.VARCHAR);
				cs.registerOutParameter(3,  java.sql.Types.VARCHAR);
				
				//ejecutamos procedimiento
				cs.execute();
				
				//Recoger parametros
				assertEquals(nombre, cs.getString(2));
				assertEquals(email, cs.getString(3)); 
				
				
				
				
			}
			
			//TODO comprobar test cuando no existe
			
			
			//deberiamos haber insertado un usuairo para comprobar que lo encontramos mediante el PA
			assertTrue(true);
			
		}catch (Exception e) {
			fail("Error:" + e.getMessage());
		}
		
	}

}
