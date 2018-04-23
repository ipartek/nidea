package com.ipartek.formacion.nidea.migracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;


public class CrearUsuariosFromFileText {

	public static void main(String[] args) throws SQLException {
		System.out.println("Crear usuarios desde un fichero de texto");
		final String URL = "jdbc:mysql://localhost/spoty?user=root&password="; 
		final String sql = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`) VALUES (?, '123456', '2');";
		Connection con = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);
			 
			 for (int i=0; i<5; i++) {
				 pst = con.prepareStatement(sql);
				 pst.setString(1, "usuario" + i);
				 
				 if (1==pst.executeUpdate()) {
					 System.out.println("usuario insertado");
					 
					 if (i == 2 ) {
						 throw new Exception("Lanzo adrede para probar rollback");
					 }
				 
				 }
				 else {
				 System.out.println("***Error al insertar usuario***");
				 }
			 }
			 // comitar cambios al terminar el proceso
			 con.commit();

		} catch (Exception e) {
			e.printStackTrace();
			//Si hay fallo rollback para 
			con.rollback();
		}
		finally {
			//Cerrar recursos en orden inverso
			if (pst != null) {
				pst.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

}
