package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;

public class CrearUsuariosFromFileText {

	public static void main(String[] args) throws SQLException {

		System.out.println("CREAR USUARIOS DESDE UN FICHERO DE TEXTO");

		// private static final String FILENAME =
		// final String FILENAME =
		// "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
		final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";

		// final String FILENAME =
		// "C:\\repositorio_nuevocurso\\eclipseoxigen\\workspace_nuevo\\nidea\\doc\\personas_lite.txt";

		final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`,`email`) VALUES (?, '123456', '2',?);";
		Connection con = null;
		PreparedStatement pst = null;
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";

		// Validaciones

		int duplicados = 0;
		int linea = 0;
		int insertados = 0;
		int error_de_campos = 0;
		int menores_de_edad = 0;

		// Declaracion de variables

		String sCurrentLine;
		String nombre = "";
		String ape1 = "";
		String ape2 = "";
		String email;
		String edad;
		String campos[];

		con = DriverManager.getConnection(URL);

		// Asignacion de valores a las nuevas variables

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			while ((sCurrentLine = br.readLine()) != null) {

				campos = sCurrentLine.split(",");
				nombre = campos[0];
				ape1 = campos[1];
				ape2 = campos[2];
				edad = campos[3];
				email = campos[4];

				String nombrecompleto = nombre + " " + ape1 + " " + ape2;
				try {
						Class.forName("com.mysql.jdbc.Driver");
						con.setAutoCommit(false); // Si es auto update casca

						try {
								if (campos.length != 7) {

								}
						} catch (ArrayIndexOutOfBoundsException e) {
								e.printStackTrace();
						}
						
						try {
								if (campos.length == 7) {

									if (Integer.parseInt(edad) >= 18) {

										pst = con.prepareStatement(SQL);
										pst.setString(1, nombrecompleto);
										pst.setString(2, email);
							
							
										if (1 == pst.executeUpdate()) {

											System.out.println("Usuario" + nombrecompleto + " insertado");

						/*Tercer if*/	} else {

												System.out.println("******Error al insertar");

												}

										System.out.println("linea " + linea + "=> " + nombrecompleto);
										insertados++;
										
					/*Segundo if*/		} else {
													menores_de_edad++;
												}
							/*Primer if*/} else {
										error_de_campos++;
										}
								
						 }catch (MySQLIntegrityConstraintViolationException e) /* Restriccion de datos duplicados */ {

									duplicados++;
									e.printStackTrace();
									System.out.println(e.getLocalizedMessage());
									}


				/* 4 try */catch (MysqlDataTruncation e) /* Limite de longitud de datos */ {
								
							e.printStackTrace();
							nombrecompleto = nombrecompleto.substring(0, 40);
							}

						
				linea++;

				System.out.println(nombrecompleto + email);
			
				}finally {
					
				}
			} //Final del While
			// comitar cambios al terminar el proceso
			con.commit();

			System.out.println("Lineas leidas " + linea);
			System.out.println("Lineas correctas " + insertados);

			System.out.println("Errores de campos " + error_de_campos);
			System.out.println("Errores de edad " + menores_de_edad);
			System.out.println("Errores de duplicidad " + duplicados);
			System.out.println("***************************************");

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

		} //Final del TRY general

		catch (Exception e) {
			e.printStackTrace();
			// si hay fallos se hace rollback,que lo que hace es que no se inserten los
			// datos en la base de datos
			con.rollback();

		} 
		
		finally {

					if (pst != null) {

						pst.close();
						}

					if (con != null) {

						con.close();
						}
				}

	
		}//Final del Main
}
