package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Usuario;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class CrearUsuariosFromFileText {

	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_little.txt";

	public static void main(String[] args) throws SQLException {

		int error_duplicados = 0;
		int linea = 0;
		int insertados = 0;
		int error_campos = 0;
		int error_edad = 0;

		String sCurrentLine;
		Usuario usuario = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String campos[];

		System.out.println("Crear usuarios desde un fichero de texto");
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String sql = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`, email) VALUES ( ?, '123456', '2', ?);";

		Connection con = null;
		PreparedStatement pst = null;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);

			// es importante esto para que no haga autocommit y poder hacer el rollback
			con.setAutoCommit(false);

			pst = con.prepareStatement(sql);

			while ((sCurrentLine = br.readLine()) != null) {

				usuario = new Usuario();
				campos = sCurrentLine.split(",");

				try {
					if (campos.length == 7) {
						usuario.setNombre(campos[0] + " " + campos[1] + " " + campos[2]);
						usuario.setEmail(campos[4]);
						if (Integer.parseInt(campos[3]) >= 18) {
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
							error_edad++;
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
			
			System.out.println("Lineas leidas " + linea);
			System.out.println("Lineas correctas " + insertados);
			System.out.println(usuarios);
			System.out.println("Errores de campos " + error_campos);
			System.out.println("Errores de edad " + error_edad);
			System.out.println("Errores de duplicidad " + error_duplicados);

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

}
