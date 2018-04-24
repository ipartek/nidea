package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Leemos un fichero de texto plano con 1004 usuarios y lo volcamos a nuestra
 * BBDD.
 * 
 * @author Xabi
 *
 */
public class LeerFicheroTxt {

	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";

	public static void main(String[] args) throws SQLException {

		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`, `email`) VALUES (?, '123456', '2', ?);";
		Connection con = null;
		PreparedStatement pst = null;

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			int i = 0;
			String campos[];
			String nombre;
			String ape1;
			String ape2;
			int edad;
			String email;
			String dni;
			String rol;
			String nombre_solo;

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);

				campos = sCurrentLine.split(",");

				if (campos.length == 7) {

					nombre_solo = campos[0];
					ape1 = campos[1];
					ape2 = campos[2];
					edad = Integer.parseInt(campos[3]);
					email = campos[4];
					dni = campos[5];
					rol = campos[6];
					nombre = nombre_solo + " " + ape1 + " " + ape2;

					if (edad >= 18) {

						try {

							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection(URL);
							con.setAutoCommit(false);

							pst = con.prepareStatement(SQL);
							pst.setString(1, nombre);
							pst.setString(2, email);

							if (1 == pst.executeUpdate()) {
								System.out.println(i + "Usuario insertado");

							} else {
								System.out.println("Error al insertar usuario");
							}

							// Comitar cambios al terminar el proceso
							con.commit();

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("error en la línea" + i); // con.rollback();
						} finally {
							// Cerrar recursos en orden inverso
							if (pst != null) {
								pst.close();
							}
							if (con != null) {
								con.close();
							}

						}
						System.out.println("Linea " + i + "=>" + nombre + " " + ape1 + " " + ape2 + " " + edad + " "
								+ email + " " + dni + " " + rol);

					} else {
						System.out.println(i + " Menor de edad. No podemos mostrar sus datos");
						continue;
					}

				} else {
					continue;
				}

				i++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
