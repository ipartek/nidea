package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearUsuariosFromFileText {

	//private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";
	private static final String FILENAME =  "C:\\repositorio_nuevocurso\\eclipseoxigen\\workspace_nuevo\\nidea\\doc\\personas.txt";

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
			//Importante para que te haga el rollback cuando falle y no te guarde nada en la bbdd y comite los cambios
			con.setAutoCommit(false);

			System.out.println("Comenzamos a leer fichero");
			System.out.println("********************************************************************************************");
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
					
					String sCurrentLine;
					int numLineas=0;
					String campos [];
					String nombre;
					String ape1;
					String ape2;
					int edad;
					String dni;
					String rol;
					int menorEdad=0;
					int contCampos=0;
					int contNombre = 0;
					int contEmail =0;
					
						while ((sCurrentLine = br.readLine()) != null) {
						
							campos = sCurrentLine.split(",");
							try{
								if(sCurrentLine.length()>=7) {
								nombre = campos[0];
								ape1= campos[1];
								ape2 = campos[2];
								nombre_usuario = nombre+" "+ape1+" "+ape2;
								try{
									if(nombre_usuario.length()<45) {
										edad = Integer.parseInt(campos[3]);
										try{
											if(edad >= 18) {
												email = campos[4];
												try {
													if(email.length()<45) {

														dni = campos[5];
														rol = campos[6];
														
														System.out.println("Nombre: " + nombre_usuario);
														System.out.println("Email: " + email);
														System.out.println("Edad: "+ edad);
														pst= con.prepareStatement(SQL);
														pst.setString(1, nombre_usuario);
														pst.setString(2, email);
														try {
															if(1 == pst.executeUpdate()){
																System.out.println("Lineas insertadas");
															}
														}catch (Exception e) {
															e.printStackTrace();
														}
													
													}else {
														contEmail++;
													}
													
													
												}catch (Exception e) {
													e.printStackTrace();
													
													
												}
											}else {
												menorEdad++;
											}
									}catch (Exception e) {
										e.printStackTrace();
									}		
										
							}else {
								contNombre++;
							}
						}catch (Exception e) {
									e.printStackTrace();
									
						}
					}else {
						contCampos++;
					}
				}catch (Exception e) {
						e.printStackTrace();
						
				}
				numLineas++;
			}
						
						
					int contNombreEmail = contNombre + contEmail;
					System.out.println("----------------------------------------------------------------");
					System.out.println("Total lineas leidas =>"+numLineas);
					System.out.println("Total menores de edad =>"+menorEdad);
					System.out.println("Lineas con menos de 7 campos => " +contCampos);
					System.out.println("Lineas erroneas por email y/o nombre => " +contNombreEmail);
				
					int lineasInsertadas = numLineas-contNombreEmail-contCampos-menorEdad;
					System.out.println("Lineas insertadas en BBDD =>"+lineasInsertadas );
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			//comitar cambios al terminar el proceso
			con.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			//si hay fallo, rollback deja la BBDD como estaba,es decir no inserta los datos
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
