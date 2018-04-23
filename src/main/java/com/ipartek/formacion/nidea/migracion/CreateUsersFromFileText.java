package com.ipartek.formacion.nidea.migracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.ipartek.formacion.nidea.model.ConnectionManager;

public class CreateUsersFromFileText {

	public static void main(String[] args) throws SQLException {

		System.out.println("Crear usuarios desde un fichero de texto");
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String SQL = "INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`) VALUES (?, '123456', '2');";
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
			
			for (int i = 0; i<5; i++) {
				pst = con.prepareStatement(SQL);
				pst.setString(1, "usuario_" + i);
				
				if ( 1 == pst.executeUpdate() ) {
					System.out.println("usuario nº" + i + " insertado");
				} else {
					System.out.println("Error al insertar el usuario nº" + i);
				}
			}
			
			// Commitar cambios al terminar el proceso correctamente
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// En caso de ocurrir algún error, ejecutar rollback para dejar la BD en el estado anterior
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

}
