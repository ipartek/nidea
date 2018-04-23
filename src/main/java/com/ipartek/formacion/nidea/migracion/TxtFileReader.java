package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class TxtFileReader {
	
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_little.txt";

	public static void main(String[] args) throws SQLException {

		//System.out.println("Leer fichero txt:");
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`, `email`) VALUES (?, '123456', '2', ?);";
		Connection con = null;
		PreparedStatement pst = null;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			// Campos de datos de cada persona
			String sCurrentLine;	// Datos de cada persona
			int num_lineas = 0;
			String persona[];
			String datos[];
			String nombre;
			String ape1;
			String ape2;
			int edad;
			String email;
			String dni;
			String puesto;
			int cont_lineasOK = 0;
			int cont_lineasKO = 0;
			int cont_menores = 0;
			int cont_lineasErroneas = 0;
			int cont_campoRep = 0;
			

			while ((sCurrentLine = br.readLine()) != null) {
				datos = sCurrentLine.split(",");
				// imprDatos(datos, num_lineas);
				
				if (datos.length == 7) {
					nombre = datos[0];
					ape1 = datos[1];
					ape2 = datos[2];
					edad = Integer.parseInt(datos[3]);
					email = datos[4];
					dni = datos[5];
					puesto = datos[6];
					if (edad >= 18) {
						
						try {
							// Conexión a BD
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection(URL);
							con.setAutoCommit(false);
							
							pst = con.prepareStatement(SQL);
							pst.setString(1, nombre+" "+ape1+" "+ape2);
							pst.setString(2, email);
							
							if ( 1 == pst.executeUpdate() ) {
								// System.out.println("usuario nº" + num_lineas + " insertado");
								cont_lineasOK++;
							} else {
								// System.out.println("Error al insertar el usuario nº" + num_lineas);
								cont_lineasKO++;
							}
							// Commitar cambios al terminar el proceso correctamente
							con.commit();
						} catch (MySQLIntegrityConstraintViolationException icve) {
							// icve.printStackTrace();
							// System.out.println("Campo repetido en BD");
							cont_campoRep++;
							con.rollback();
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
					} else {
						// System.out.println("Menor de edad. CONT: " + cont_menores+1);
						cont_menores++;
					}
				} else {
					// System.out.println("Datos de persona incorrectos, no tiene 7 campos. CONT: " + cont_lineasErroneas+1);
					cont_lineasErroneas++;
				}
				num_lineas++;
			}
			mostrarResumen(num_lineas, cont_lineasOK, cont_lineasErroneas, cont_menores, cont_campoRep);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		 System.out.println("Persona #" + num_lineas + ": " 
				 	+ " Nombre: " + nombre 
				 	+ " Apellido1: " + ape1 
				 	+ " Apellido2: " + ape2 
				 	+ " Edad: " + edad 
				 	+ " Email: " + email 
				 	+ " Puesto: " + puesto);
		
	}

}
