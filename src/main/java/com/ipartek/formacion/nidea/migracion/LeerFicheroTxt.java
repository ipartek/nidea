/**
 * 	---------------------------------------------------------------------------
 *  *Ejercicio: 
 *  _ Juntar los dos programas para insertar todos los campos. 
 *  _ Si alguna linea erronea se rompe porque tiene menos de 7 campos ignorarlo.
 *  _ Las personas menores de 18 años las obviamos.
 *  _ Guardamos el campo email en un campo nuevo único.
 *  ----------------------------------------------------------------------------
 *  
 *   Hacer resumen:						numero
 *  	Lineas leidas:					10004 
 *  	Personas insertadas: 			789
 *  	Lineas erroneas(?campos): 		34
 * 		Menores de Edad: 				567
 * 		email repetidos:				34
 * 		----------------------------------
 * 		Lineas insertadas + erroneas: 	10004
 * 	
 * @author Mikel
 *
 */

package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class LeerFicheroTxt {

	private static final String FILENAME = "C:\\desarrollo\\jee-oxigen\\workspace2\\nidea\\personas.txt";

	// BD
	private static final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
	private static final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`, `email`) VALUES (?, '123456', '2', ?);";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	public static void main(String[] args) throws SQLException {

		Connection con = null;
		PreparedStatement pst = null;

		 System.out.println("Comenzamos a leer fichero");
		 System.out.println("*************************");

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			// Datos del usuario
			String sCurrentLine;
			int nLineas = 0;
			String campos[];
			String nombre;
			String ape1;
			String ape2;

			int edad;
			String email;
			String dni;
			String puesto;
			int countLineaBien = 0;
			int countLineaFail = 0;
			int countMenores = 0;
			int countLineaErronea = 0;
			int countLineaRepetida = 0;

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println("Nº de lineas: " + nLineas);
				campos = sCurrentLine.split(",");

				if (campos.length == 7) {
					nombre = campos[0];
					ape1 = campos[1];
					ape2 = campos[2];
					edad = Integer.parseInt(campos[3]);
					email = campos[4];
					dni = campos[5];
					puesto = campos[6];

					if (edad >= 18) {
						try { //*Asegurar que todo lo que este por encima del try no de problemas 			

							// Creamos la conexion con la BD para realizar la insercción de datos
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection(URL);
							pst = con.prepareStatement(SQL);
							pst.setString(1, nombre + " " + ape1 + " " + ape2);
							pst.setString(2, email);
							/**
							 * Para salvar el error Exception in thread "main"
							 * com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Can't
							 * call rollback when autocommit=true
							 * 
							 * cambiamos la propiedad AutoCommit a false
							 * 
							 *		| | | 
							 *		V V V 
							 */
							con.setAutoCommit(false);
							if (1 == pst.executeUpdate()) {
								countLineaBien++;
							} else {
								countLineaFail++;
							}
							con.commit(); // Commit si la condición es positiva
							
						} catch (MySQLIntegrityConstraintViolationException icve) {
							countLineaRepetida++; // Duplicidad de datos
							con.rollback(); // deshacer los cambios realizados hasta este punto con rollback
						} catch (Exception e) {
							e.printStackTrace();
							con.rollback();// deshacer los cambios realizados hasta este punto con rollback
						} finally {
							if (pst != null) {
								pst.close();// cierre de statement
							}
							if (con != null) {
								con.close();// cierre de connection
							}
						}
					} else {
						countMenores++; // Contador de usuarios menores de edad
					}
				} else {
					countLineaErronea++; // Contador de lineas con error
				}
				nLineas++;
			}
			mostrarResumen(nLineas, countLineaBien, countLineaErronea, countMenores, countLineaRepetida);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarResumen(int nLineas, int lineasBien, int lineasMal, int lineaMenor, int lineaRepetida) {

		System.out.println("----------------------------------------------");
		System.out.println("RESUMEN:");
		System.out.println("----------------------------------------------");
		System.out.println("Lineas leidas: " + nLineas);
		System.out.println("Personas insertadas: " + lineasBien);
		System.out.println("Lineas erroneas: " + lineasMal);
		System.out.println("Menores de edad: " + lineaMenor);
		System.out.println("email repetido: " + lineaRepetida);
		System.out.println("----------------------------------------------");
		System.out.println("Lineas insertadas + erroneas:" + nLineas);

	}

	// TODO inserccion de registros a la BD Heidi
	private static void imprimirUsuarios(String[] campos, int nLineas) {
		String nombre;
		String ape1;
		String ape2;
		String edad;
		String email;
		String puesto;

		nombre = campos[0];
		ape1 = campos[1];
		ape2 = campos[2];
		edad = campos[3];
		email = campos[4];
		puesto = campos[5];
		System.out.println("Usuario #" + nLineas + ": " + " Nombre: " + nombre + " Apellido1: " + ape1 + " Apellido2: "
				+ ape2 + " Edad: " + edad + " Email: " + email + " Puesto: " + puesto);

	}

}