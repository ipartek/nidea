package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Usuario;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.log.Log;

public class CrearUsuariosFromFileText {

	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";

	// Base de datos
	private static final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
	private static final String SQL = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`, email) VALUES ( ?, '123456', '2', ?);";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static final int NUMERO_CAMPOS = 7;
	private static final int EDAD_MINIMA = 18;

	public static void main(String[] args) throws SQLException {

		int error_email = 0;
		int error_nombre = 0;
		int error_nombre_largo = 0;
		int linea = 0;
		int insertados = 0;
		int error_campos = 0;
		int error_edad = 0;

		String sCurrentLine;
		Usuario usuario = null;
		String campos[];
		String exc[];

		System.out.println("Crear usuarios desde un fichero de texto");

		Connection con = null;
		PreparedStatement pst = null;

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL);

			// es importante esto para que no haga autocommit y poder hacer el rollback
			con.setAutoCommit(false);

			pst = con.prepareStatement(SQL);

			while ((sCurrentLine = br.readLine()) != null) {

				campos = sCurrentLine.split(",");
				linea++;
				
				try {
					if (campos.length != NUMERO_CAMPOS) {
						error_campos++;

					} else {
						// se mapea el nombre con los tres primeros campos que son nombres y apellidos
						usuario = mapear(campos);

						// Comprueba si los usuarios tienen la edad minima para entrar en la bbdd
						if (Integer.parseInt(campos[3]) < EDAD_MINIMA) {
							error_edad++;

						} else {
							
							pst.setString(1, usuario.getNombre());
							pst.setString(2, usuario.getEmail());

							if (1 == pst.executeUpdate()) {
								// System.out.println("Usuario" + usuario.getNombre() + " insertado");
								insertados++;
							} else {
								System.out.println("******Error al insertar");
							}
						}
						
					}
				}

				// Comprueba si hay nombres o email duplicados
				catch (MySQLIntegrityConstraintViolationException e) {

					exc = e.getLocalizedMessage().split("'");
					// System.out.println(e.getLocalizedMessage());
					if ("email".equals(exc[exc.length - 1])) {
						error_email++;
					} else if ("nombre_UNIQUE".equals(exc[exc.length - 1])) {
						error_nombre++;
					}
				}

				// Comprueba si los campos son demasiado largos
				catch (MysqlDataTruncation e) {
					error_nombre_largo++;
					// System.out.println(e.getLocalizedMessage());
				}

			} // while

			// comitar cambios al terminar el proceso
			con.commit();

			System.out.println("Lineas leidas " + linea);
			System.out.println("Lineas correctas " + insertados);
			// System.out.println(usuarios);
			System.out.println("Errores de campos " + error_campos);
			System.out.println("Errores de edad " + error_edad);
			System.out.println("Errores de duplicidad de nombre " + error_nombre);
			System.out.println("Errores de nombre demasiado largo " + error_nombre_largo);
			System.out.println("Errores de duplicidad de email " + error_email);

			int errores = error_campos + error_edad + error_nombre + error_nombre_largo + error_email;

			System.out.println("Lineas erroneas " + errores);
			System.out.println("Lineas Totales " + (insertados + errores));

		} catch (Exception e) {
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

	/**
	 * Devuelve un usuario con los 3 primeros campos mapeados para el nombre y el
	 * email con el 5
	 * 
	 * @param campos
	 * @return
	 */
	private static Usuario mapear(String[] campos) {
		Usuario usuario = new Usuario();
		usuario.setNombre(campos[0] + " " + campos[1] + " " + campos[2]);
		usuario.setEmail(campos[4]);
		return usuario;
	}

}
