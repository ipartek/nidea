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
		//TODO insertado usuario Mock o de prueba
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//TODO eliminado usuario Mock o de prueba
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
			final String URL = "jdbc:mysql://localhost/nidea?user=root&password=";
			final String SQL = "{ call pa_get_user_by_id(?,?,?) }";
			
			try( 
					Connection con = DriverManager.getConnection(URL);
					CallableStatement cs = con.prepareCall(SQL)
					
				){
				
				//deberiamos haber insertado un usuario con id 122, 
				//para comprobar que lo encontramos mediante el PA
				int idUsuario = 122;
				String nombre = "Gary Rivera Baos De La Encina";
				String email = "GarySRivera@pookmail.com"; 
				
				//registrando parametros entrada y salida
				cs.setInt( 1, idUsuario);
				cs.registerOutParameter(2, java.sql.Types.VARCHAR);
				cs.registerOutParameter(3, java.sql.Types.VARCHAR);
				
				//ejecutamos procedimiento				
				cs.execute();
								
				//recoger parametros
				assertEquals( nombre, cs.getString(2));
				assertEquals( email , cs.getString(3));
				
			}
			
			//TODO comprobar Test cuando no existe

			
			assertTrue(true);
			
		}catch (Exception e) {
			fail("Error: " + e.getMessage());
		}		
		
	}

}
