package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;
import java.sql.PreparedStatement;


public class CrearUsuarioFromFileText {
	public static void main(String[] args) throws SQLException {
		System.out.println("Crear usuarios desde un fichero de texto");
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String sql = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES (?, '123456', ?, '2');";
		final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace3\\nidea\\doc\\personas_little.txt";
		Connection con = null;
		PreparedStatement pst= null;
			
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				String sCurrentLine;
				
				String campos[];
				String nombre ="";
				String ape1 = "";
				String ape2 = "";
				int edad = 0;
				String email = "";
				String dni = "";
				String puesto = "";
				
				int i = 0;
				int camposIncorrectos = 0;
				int menoresEdad = 0;
				int registrosInsertados = 0;
				
			while ((sCurrentLine = br.readLine()) != null) {
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
