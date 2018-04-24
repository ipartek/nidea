package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/**
 * -------------------------- Ejercicio juntar los dos fichero en uno Si alguno
 * no tiene 7 campos, es erronea y la ignoramos. Ok la que tiene 7 campos
 * Obviaremos las personas menores de 18 años Guardamos el campo email. No puede
 * ser repetido Nombre + 2 apellidos tampoco puede ser repetido
 * 
 * -----------------
 * 
 * hacer resumen Lineas leidas: 10004 PErsonas insertadas 789 Lineas erroneas 34
 * Menores de edad 567 Email repetirods 34 Lineas insertadas + lineas errornas
 * 10004
 * 
 * @author Curso
 *
 */
public class LerrFicheroTxt {
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";

	public static void main(String[] args) throws Exception {
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`, `email`) VALUES (?, '123456', '2', ?);";
		Connection con = null;
		PreparedStatement pst = null;

		System.out.println("Comenzamos a leer fichero");

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String sCurrentLine;
			int i = 0;
			String campos[];
			String nombre;
			String ape1;
			String apel2;
			int contador_edad = 0;
			int contador_campos = 0;
			int cont_lineas_insertadas = 0;
			int cont_campoRep = 0;
			int lineas_leidas = 0;
			int age = 0;
			String email;
			String dni;
			String ocupacion;

			while ((sCurrentLine = br.readLine()) != null) {
				campos = sCurrentLine.split(",");

				lineas_leidas++;

				if (campos.length == 7) {

					nombre = campos[0];
					ape1 = campos[1];
					apel2 = campos[2];
					age = Integer.parseInt((campos[3]));

					email = campos[4];
					dni = campos[5];
					ocupacion = campos[6];
					if (age >= 18) {
						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection(URL);
							con.setAutoCommit(false);

							pst = con.prepareStatement(SQL);
							pst.setString(1, nombre + " " + ape1 + " " + apel2);
							pst.setString(2, email);

							if (1 == pst.executeUpdate()) {

								cont_lineas_insertadas++;
							} 

							con.commit();
						} catch (MySQLIntegrityConstraintViolationException icve) {

							cont_campoRep++;
							con.rollback();
						} catch (Exception e) {
							e.printStackTrace();
							con.rollback();
						} finally {

							if (pst != null) {
								pst.close();
							}
							if (con != null) {
								con.close();
							}
						}

						System.out.println("Linea" + i + " =>" + nombre + " " + ape1);

						i++;
					} else {

						i++;
						contador_edad++;
					}
				} else {

					contador_campos++;

				}
				System.out.println("Error edad " + contador_edad);
				System.out.println("----------------");
				System.out.println("Contador campos " + contador_campos);
				System.out.println("----------------");
				System.out.println("Campos repetidos " + cont_campoRep);
				System.out.println("----------------");
				System.out.println("Lineas insertadas " + cont_lineas_insertadas);
				System.out.println("----------------");
				System.out.println("lineas leidas " + lineas_leidas);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
