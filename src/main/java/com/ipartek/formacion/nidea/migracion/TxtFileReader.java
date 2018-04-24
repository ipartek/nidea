/**
 * Clase para leer un fichero con varias lineas e insertarlas como registros en la BD
 * 
 * @author Aingeru Sanchez
 */
package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class TxtFileReader {

	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";
	// Constantes para las validaciones
	private static final int MAYOR_EDAD = 18;
	private static final int NUM_CAMPOS = 7;
	// CONSTANTES para la conexión a BD
	private static final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
	private static final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`, `email`) VALUES (?, '123456', '2', ?);";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	public static void main(String[] args) throws SQLException {

		// System.out.println("Leer fichero txt:");
		Connection con = null;
		PreparedStatement pst = null;

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			// Campos de datos de cada persona
			String sCurrentLine; // Datos de cada persona
			int num_lineas = 0;

			// datos de cada persona
			String datos[];
			String nombre;
			String ape1;
			String ape2;
			int edad;
			String email;
			String dni;
			String puesto;

			// Variables para resumen
			int cont_lineasOK = 0;
			int cont_lineasKO = 0;
			int cont_menores = 0;
			int cont_lineasErroneas = 0;
			int cont_campoRep = 0;

			// Conexión a BD
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);

			while ((sCurrentLine = br.readLine()) != null) {
				datos = sCurrentLine.split(",");
				// imprDatos(datos, num_lineas);

				if (datos.length == NUM_CAMPOS) {
					nombre = datos[0];
					ape1 = datos[1];
					ape2 = datos[2];
					edad = Integer.parseInt(datos[3]);
					email = datos[4];
					dni = datos[5];
					puesto = datos[6];

					if (edad >= MAYOR_EDAD) {

						try {

							pst = con.prepareStatement(SQL);
							pst.setString(1, nombre + " " + ape1 + " " + ape2);
							pst.setString(2, email);

							if (1 == pst.executeUpdate()) {
								// System.out.println("usuario nº" + num_lineas + " insertado");
								cont_lineasOK++;
							} else {
								// System.out.println("Error al insertar el usuario nº" + num_lineas);
								cont_lineasKO++;
							}
						} catch (MySQLIntegrityConstraintViolationException icve) {
							// icve.printStackTrace();
							// System.out.println("Campo ÚNICO repetido en BD");
							cont_campoRep++;
						} catch (MysqlDataTruncation dt) {
							// dt.printStackTrace();
							// System.out.println("Campo demasiado largo");
							cont_campoRep++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						// System.out.println("Menor de edad. CONT: " + cont_menores+1);
						cont_menores++;
					}
				} else { // Menor de edad
					// System.out.println("Datos de persona incorrectos, no tiene 7 campos. CONT: "
					// + cont_lineasErroneas+1);
					cont_lineasErroneas++;
				} // Linea de fichero con número de campos incorrecto
				num_lineas++;
			}
			// Commitar cambios al terminar el proceso correctamente
			con.commit();
			mostrarResumen(num_lineas, cont_lineasOK, cont_lineasErroneas, cont_menores, cont_campoRep);
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			// Cerrar los recursos, por no utilizar try-with-resources
			if (pst != null) {
				pst.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * Muestra el resumen de inserciones en BD
	 * 
	 * @param num_lineas:
	 *            número de lineas leidas del fichero
	 * @param lineasOK:
	 *            lineas correctas
	 * @param lineasKO:
	 *            lineas incorrectas por número de campos incorrecto
	 * @param menores:
	 *            número de registros menores de edad
	 * @param regisRep:
	 *            registros con nombre o email repetidos en BD
	 */
	private static void mostrarResumen(int num_lineas, int lineasOK, int lineasKO, int menores, int regisRep) {

		System.out.println("----------------------------------------------");
		System.out.println("RESUMEN:");
		System.out.println("----------------------------------------------");
		System.out.println("Lineas leidas: " + num_lineas);
		System.out.println("Personas insertadas: " + lineasOK);
		System.out.println("Lineas incorrectas: " + lineasKO);
		System.out.println("Menores de edad: " + menores);
		System.out.println("Registros repetidos en BD: " + regisRep);
		System.out.println("----------------------------------------------");

	}

	/**
	 * Muestra por consola los datos de cada persona
	 * 
	 * @param datos:
	 *            datos de cada persona con todos sus campos
	 * @param num_lineas:
	 *            índice de linea en el fichero
	 */
	private static void imprDatos(String[] datos, int num_lineas) {
		String nombre;
		String ape1;
		String ape2;
		String edad;
		String email;
		String puesto;

		nombre = datos[0];
		ape1 = datos[1];
		ape2 = datos[2];
		edad = datos[3];
		email = datos[4];
		puesto = datos[5];
		System.out.println("Persona #" + num_lineas + ": " + " Nombre: " + nombre + " Apellido1: " + ape1
				+ " Apellido2: " + ape2 + " Edad: " + edad + " Email: " + email + " Puesto: " + puesto);

	}

}
