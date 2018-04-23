package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Usuario;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.PreparedStatement;

public class CrearUsuariosFromFileText {

	public static void main(String[] args) throws SQLException {

		System.out.println("CREAR USUARIOS DESDE UN FICHERO DE TEXTO");

		// private static final String FILENAME =
		// "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
		final String FILENAME = "C:\\repositorio_nuevocurso\\eclipseoxigen\\workspace_nuevo\\nidea\\doc\\personas_lite.txt";

		final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`) VALUES (?, '123456', '2');";
		Connection con = null;
		PreparedStatement pst = null;
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";

		// Validaciones

		int error_duplicados = 0;
		int linea = 0;
		int insertados = 0;
		int error_campos = 0;
		int menores_de_edad = 0;

		// Declaracion de variables
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String sCurrentLine;
		String nombre = "";
		String ape1 = "";
		String ape2 = "";
		Usuario usuario;
		String edad;
		String email;
		String dni;
		String rol;
		String campos[];
		String nombrecompleto = nombre + " " + ape1 + " " + ape2;
		con = DriverManager.getConnection(URL);

		// Asignacion de valores a las nuevas variables
	
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
					
			
		while ((sCurrentLine = br.readLine()) != null) {

				usuario = new Usuario();
				campos = sCurrentLine.split(",");
				nombre = campos[0];
				ape1 = campos[1];
				ape2 = campos[2];
				edad = campos[3];
				email = campos[4];
				dni = campos[5];
				rol = campos[6];
				
				
				try {
						Class.forName("com.mysql.jdbc.Driver");
						con.setAutoCommit(false); // Si es auto update casca

						if (campos.length == 7) {
					
							usuario.setNombre(nombrecompleto);
					
							usuario.setEmail(email);
							
							if (Integer.parseInt(edad) >= 18) {
								
								usuarios.add(usuario);
								pst.setString(1, usuario.getNombre());
								pst.setString(2, usuario.getEmail());

								if (1 == pst.executeUpdate()) {
										
									System.out.println("Usuario" + usuario.getNombre() + " insertado");
						
								} else {
									
									System.out.println("******Error al insertar");
									
									}

								System.out.println("linea " + linea + "=> " + usuario);
								insertados++;
							} else {
									menores_de_edad++;
									}
						} else {
								error_campos++;
								}
			
					} catch (MySQLIntegrityConstraintViolationException e) {
							
							error_duplicados++;
							e.printStackTrace();
							System.out.println(e.getLocalizedMessage());
							}

				linea++;
			}

	// comitar cambios al terminar el proceso
	con.commit();

	System.out.println("Lineas leidas "+linea);
	System.out.println("Lineas correctas "+insertados);
	System.out.println(usuarios);
	System.out.println("Errores de campos "+error_campos);
	System.out.println("Errores de edad "+menores_de_edad);
	System.out.println("Errores de duplicidad "+error_duplicados);
	
		

	/*
	 * for(int i=0; i<5; i++){
	 * 
	 * pst=con.prepareStatement(SQL); pst.setString(1, "usuario" + i);
	 * 
	 * if(1==pst.executeUpdate()) {
	 * 
	 * System.out.println(i + "Usuario Insertado");
	 * 
	 * /* if(i==2){ throw new Exception("Lanzo adrede una excepcion"); } }
	 * 
	 * else {
	 * 
	 * System.out.println("Error al insertar usuario"); } //Comitar los cambios al
	 * terminar el proceso
	 */

			}catch (Exception e) {
								e.printStackTrace();
								// si hay fallos se hace rollback para devolver la bbdd a su estado anterior
								con.rollback();
							} finally {

										if (pst != null) {
															pst.close();
															}
										if (con != null) {
										con.close();
										}
									}

	}
	
}
