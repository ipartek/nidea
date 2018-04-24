package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.PreparedStatement;

public class CrearUsuarioFromFileText {
	
	private static final int EDAD_MINIMA =18;
	private static final int NUMERO_CAMPOS =7;
	
	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace3\\nidea\\doc\\personas.txt";
	
	// BBDD
	private static final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
	private static final String sql = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES (?, '123456', ?, 2);";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	static int camposIncorrectos = 0;
	static int menoresEdad = 0;
	static int registrosInsertados = 0;
	static int registroRepetido = 0;
	static int i = 0;
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Crear usuarios desde un fichero de texto");
		
		Connection con = null;
		PreparedStatement pst= null;
		
		String campos[];
		String nombre ="";
		String ape1 = "";
		String ape2 = "";
		int edad = 0;
		String email = "";
		String dni = "";
		String puesto = "";
			
		int i = 0;
					
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String sCurrentLine;
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
				
			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				campos = sCurrentLine.split(",");
				if (campos.length!=NUMERO_CAMPOS) {
					camposIncorrectos++;
				}else {
					nombre = campos[0];
					ape1 = campos[1];
					ape2 = campos[2];
					edad = Integer.parseInt(campos[3]);
					email = campos[4];
					dni = campos[5];
					puesto = campos[6];
					
					if (edad < EDAD_MINIMA) {
						menoresEdad++;
					}else {
						pst = con.prepareStatement(sql);
						pst.setString(1, nombre + " " + ape1 + " " + ape2);
						pst.setString(2, email);
						
						try {
							if (1 ==pst.executeUpdate()) {
								System.out.println("Usuario insertado " + i);
								registrosInsertados++;
								/*if (i==2) {
									throw new Exception("Lanzo adrede para probar rollback");
								}*/
							}else {
								System.out.println("Error al insertar");
							}
						}catch (MysqlDataTruncation e2) {
							registroRepetido ++;
							//e2.printStackTrace();
						} catch (MySQLIntegrityConstraintViolationException e3) {
							registroRepetido ++;
							//e3.printStackTrace();
						} // fin try/catch
						
					} // fin else edad < EDAD_MINIMA
					
				} // fin else campos.length!=NUMERO_CAMPOS
				
			} // fin while
			
			// comitar cambios al termina el proceso
			con.commit();
				
		} catch (Exception e) {
			e.printStackTrace();
			
			// si hay fallos para dejar la BBDD como estaba
			con.rollback();
		}finally {
			montrarResumen();
			
			// cerrar recursos en orden inverso
			if (pst!= null) {
				pst.close();
			}
			if (con!= null) {
				con.close();
			}
		}
	}
	
	static void montrarResumen() {
		System.out.println("RESUMEN");
		System.out.println("------------------");
		System.out.println("Campos leidos: "+ i);
		System.out.println("Fallos número de campos incorrectos: "+ camposIncorrectos);
		System.out.println("Menores de edad: "+ menoresEdad);
		System.out.println("Nuevos registros insertados: "+ registrosInsertados);
	}
}
