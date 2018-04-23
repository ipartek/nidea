package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;


public class CrearUsuarioFromFileText {
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspaces2\\nidea\\doc\\personas_little.txt";
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Crear usuarios desde un fichero de texto.");
		
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		final String SQL = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES (?, '123456',?, '2');";
		Connection con = null;
		PreparedStatement pst = null;
		String nombre_usuario = null;
		String email;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(URL);
			//Importante para que te haga el rollback cuando falle y no te guarde nada en la bbdd y comite los cambios cuanto tu le digas
			con.setAutoCommit(false);
			
			/*for(int i= 0; i<5;i++) {
				pst= con.prepareStatement(SQL);
				pst.setString(1, "usuario" +i);
				
				if(1 == pst.executeUpdate()) {
					System.out.println(i+"usuario insertado");
					if(i==2) {
						throw new Exception ("lanzo adrede para probar el rollback");
					}
				}else {
					System.out.println("****"+i+"Error al insertar el usuario");
				}
			}*/
			System.out.println("Comenzamos a leer fichero");
			System.out.println("********************************************************************************************");
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				
					String sCurrentLine;
					int i=0;
					String campos [];
					String nombre;
					String ape1;
					String ape2;
					int edad;
					String dni;
					String rol;
					int j=0;
					for (i = 0; i<5; i++) {
						while ((sCurrentLine = br.readLine()) != null) {
							//System.out.println(sCurrentLine);
							campos = sCurrentLine.split(",");
							nombre = campos[0];
							ape1= campos[1];
							ape2 = campos[2];
							edad = Integer.parseInt(campos[3]);
							
							if(edad > 18) {
								
								email = campos[4];
								dni = campos[5];
								rol = campos[6];
								nombre_usuario = nombre+" "+ape1+" "+ape2;
								//System.out.println("Linea " + i + " =>" +nombre + " "+ ape1 + " "+ape2);
								System.out.println("Nombre: " + nombre_usuario);
								System.out.println("Email: " + email);
								System.out.println("Edad: "+ edad);
								pst= con.prepareStatement(SQL);
								pst.setString(1, nombre_usuario);
								pst.setString(2, email);
								pst.executeUpdate();
							}
							else {
								System.out.println("No es mayor de edad. Por LOPD no se puede agregar a BBDD");
								j++;
							}
							
							
						}
						
						
					}
					System.out.println("----------------------------------------------------------------");
					System.out.println("Total ficheros leidos=>"+i);
					System.out.println("Total menores de edad=>"+j);
					int z = i-j;
					System.out.println("Lineas insertadas en BBDD =>"+z );
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			//comitar cambios al terminar el proceso
			con.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			//si hay fallo, rollback para dejar la BBDD como estaba
			con.rollback();
			
		}finally {
			//cerrar recursos en orden inverso
			if(pst!= null) {
				pst.close();
			}
			if(con!=null) {
				con.close();
			}
			
			
		}
	}

}
