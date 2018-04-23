package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarRegistros {
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspaces2\\nidea\\doc\\personas_little.txt";
	public void conectar() throws SQLException {
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(URL);
			//Importante para que te haga el rollback cuando falle y no te guarde nada en la bbdd y comite los cambios cuanto tu le digas
			con.setAutoCommit(false);
			//comitar cambios al terminar el proceso
			con.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			//si hay fallo, rollback para dejar la BBDD como estaba
			con.rollback();
			
		}
	}
	
	public void leerArchivo() {
		
		System.out.println("Comenzamos a leer fichero");
		System.out.println("********************************************************************************************");
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

				String sCurrentLine;
				int i = 0;
				String campos [];
				String nombre;
				String ape1;
				
				

				while ((sCurrentLine = br.readLine()) != null) {
					//System.out.println(sCurrentLine);
					campos = sCurrentLine.split(",");
					nombre = campos[0];
					ape1= campos[1];
					System.out.println("Linea " + i + " =>" +nombre + " "+ ape1);
					
					i++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
