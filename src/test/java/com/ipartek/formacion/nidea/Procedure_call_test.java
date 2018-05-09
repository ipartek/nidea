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

public class Procedure_call_test {

	final static String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
	final static String SQL="{call pa_get_user_by_id(?,?,?)}";
	final static String SQL_crear_usuarios="{call pa_create_user(?,?,?,?,?)}";
	final static String SQL_eliminar_usuarios="{call pa_delete_user(?)}";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		//TODO insertado usuario MOCK o de prueba
		try(Connection con= DriverManager.getConnection(URL);
				CallableStatement cs =con.prepareCall(SQL_crear_usuarios)
				){
		int idUsuario=-1;
		String nombre="";
		String email="";
		String pass="";
		int id_rol=2;
		
		//Registrando parametros entrada y salida
		cs.setInt(1,idUsuario);
		if(idUsuario>0) {
			cs.setString(2,nombre);
			cs.setString(3,email);
			cs.setString(4,pass);
			cs.setInt(5,id_rol);
				}
		
	}
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		//TODO eliminado usuario MOCK o de prueba
		
		try(Connection con= DriverManager.getConnection(URL);
				CallableStatement cs =con.prepareCall(SQL_eliminar_usuarios)
				){
			
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			
			
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	
	public void testPageUserById() {
		try {
		Class.forName("com.mysql.jdbc.Driver");

		
		try(Connection con= DriverManager.getConnection(URL);
				CallableStatement cs =con.prepareCall(SQL)
				){
			//Para comprobar que lo encontramos mediante el PA
		int idUsuario=30;
		String nombre="imk";
		String email="imk@gmail.com";
		
		//Registrando parametros entrada y salida
		cs.setInt(1,idUsuario);
		cs.registerOutParameter(2, java.sql.Types.VARCHAR);
		cs.registerOutParameter(3, java.sql.Types.VARCHAR);
		
		//ejecutamos procedimiento
		cs.execute();
		
		//recoger parametros
		
		assertEquals(nombre, cs.getString(2));
		assertEquals(email, cs.getString(3));
		
		
		}
		
		//TODO Comprobar test si no existe
		
		assertTrue(true);
		
		
		
	}catch(Exception e){
		fail("Error:"+e.getMessage());
	}
	}
}
