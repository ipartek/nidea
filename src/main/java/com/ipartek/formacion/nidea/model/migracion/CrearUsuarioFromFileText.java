package com.ipartek.formacion.nidea.model.migracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;
import java.sql.PreparedStatement;


public class CrearUsuarioFromFileText {
	public static void main(String[] args) throws SQLException {
		System.out.println("Crear usuarios desde un fichero de texto");
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String sql = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`) VALUES (?, '123456', '2');";
		Connection con = null;
		PreparedStatement pst= null;
			
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
			
			for (int i=0; i<5 ;i++) {
				pst = con.prepareStatement(sql);
				pst.setString(1,"usuario_"+i);
				
				if (1 ==pst.executeUpdate()) {
					System.out.println("Usuario insertado " + i);
					if (i==2) {
						throw new Exception("Lanzo adrede para probar rollback");
					}
				}else {
					System.out.println("Error al insertar");
				}
			}
			// comitar cambios al termina el proceso
			con.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			// si hay fallos para dejar la BBDD como estaba
			con.rollback();
		}finally {
			// cerrar recursos en orden inverso
			if (pst!= null) {
				pst.close();
			}
			if (con!= null) {
				con.close();
			}
		}
	}
}
