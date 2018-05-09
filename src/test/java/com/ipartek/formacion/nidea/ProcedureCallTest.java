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

	final static String URL ="jdbc:mysql://localhost/nidea?user=root&password=root";
	static String nombre;
	static String pass;
	static String mail;
	int id;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		//TODO insertado usuario mock o de prueba
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//TODO eliminado usuario mock o de prueba	
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}
	
	@Test
	public void testPageGetUserById() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			final String SQL = "{ call pa_get_user_by_id(?,?,?)}";
			try(Connection con = DriverManager.getConnection(URL);
					CallableStatement cs = con.prepareCall(SQL)) {
				int idUsuario =38900;
				String nombre ="Alexis Matney Forfoleda";
				String mail ="AlexisJMatney@mailinator.com";
				//registrando parametros de entrada y salida
				cs.setInt(1, idUsuario);
				cs.registerOutParameter(2, java.sql.Types.VARCHAR);
				cs.registerOutParameter(3, java.sql.Types.VARCHAR);
				// ejecutamos procedimientos
				cs.execute();
				//recoger parametros
				assertEquals(nombre, cs.getString(2));
				assertEquals(mail, cs.getString(3));
			}
			
			//DEBERIAMOS HABER INSERTADO UN USUARIO
			//PARA COMPROBAR QUE LO ENCONTRAMOS MEDIANTE EL PA
			assertTrue(true);
		} catch (Exception e) {
			fail("Error: "+ e.getMessage());
		}
	}
	
	@Test
	public void testPageInsertUser() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			final String SQL = "{ call `pa_create_user`(?, ?, ?, ?)}";
			try(Connection con = DriverManager.getConnection(URL);
					CallableStatement cs = con.prepareCall(SQL)) {
				id =-1;
				nombre ="UsuarioPrueba";
				pass = "123456";
				mail ="usuario@usuario.es";
				//registrando parametros de entrada y salida
				cs.setString(1, nombre);
				cs.setString(2, pass);
				cs.setString(3, mail);
				cs.registerOutParameter(4, java.sql.Types.INTEGER);
				
				// ejecutamos procedimientos
				cs.execute();
				//recoger parametros
				id = cs.getInt(4);
				assertTrue(id > 0);				
			}			
			
		} catch (Exception e) {
			fail("Error: "+ e.getMessage());
		}
	}
	
	@Test
	public void testPageDeleteUser() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			final String SQL = "{ call `pa_delete_user`(?)}";
			try(Connection con = DriverManager.getConnection(URL);
					CallableStatement cs = con.prepareCall(SQL)) {
				id =44887;
				//registrando parametros de entrada y salida
				cs.setInt(1, id);
				
				// ejecutamos procedimientos
				 boolean resultado = cs.execute();
				//recoger parametros				
				assertTrue(resultado == true);				
			}			
			
		} catch (Exception e) {
			fail("Error: "+ e.getMessage());
		}
	}

}
