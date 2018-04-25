package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.tagext.TryCatchFinally;

import com.ipartek.formacion.nidea.model.ConnectionManager;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Usuario;

public class LeerFicheroTxt {
	
	
	//private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";
	private static final int MENORES = 18;
	private static final int MAXCAMPOS = 7;
	
	private static int menores;
	private static int leidas;
	private static int insertadas;
	private static int camposIncorrectos;
	private static int duplicados;
	
	//base de datos
	private static final String URL ="jdbc:mysql://localhost/nidea?user=root&password=root";
	private static final String SQL = "INSERT INTO Usuario (nombre, password,mail, id_rol) VALUES (?, '123456', ?, '2');";
	private static final String DRIVER ="com.mysql.jdbc.Driver";

	private static Connection con = null;
	private static PreparedStatement pst =null;
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Empiezo leer fichero");	
		

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			int i = 0;
			String campos[];
			String nombre ="";
			String ap1 ="";
			String ap2="";
			String mail="";
			int edad;
			menores = 0;
			leidas = 0;
			insertadas =0;
			camposIncorrectos =0;
			duplicados = 0;			
			int numCampos =0;
			boolean result = false;
			
			while ((sCurrentLine = br.readLine()) != null) {
			   campos=	sCurrentLine.split(",");
			   numCampos = campos.length;
			   nombre =campos[0];
			   ap1= campos[1];
			   ap2= campos[2];
			  
				//System.out.println(sCurrentLine);
			   leidas++;
			   if (numCampos == MAXCAMPOS) {
				   edad = Integer.parseInt(campos[3]);
				   result =comprobarEdad(edad);
				   if (result) {
					   mail = campos[4];
					  result = insertarEnBaseDatos(i, nombre +" " + ap1+" "+ ap2, mail);					 				   
				   }					   
				   if (result) {
						insertadas++;
				   }
				   
			   } else {
				   camposIncorrectos++;
			   }				
				
				i++;
			}
			
			escribirResultado();

		} catch (IOException e) {
			e.printStackTrace();
			
		}
		finally {			
			cerrarRecursos();
		}

	}

	

	private static Boolean insertarEnBaseDatos(int linea, String nombre, String mail) throws SQLException {
		System.out.println("Linea " + linea + " =>" + nombre + " "  + mail);
		boolean result= false;
		
		try  {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
			pst = con.prepareStatement(SQL);
			pst.setString(1, nombre);
			pst.setString(2, mail);
			if (1 == pst.executeUpdate()) {
				result=true;
			}
			//commitar cambios al terminar el proceso
			con.commit();
			
		} catch (Exception e) {
			e.printStackTrace();			
			duplicados++;
			//si hay fallo rollback para dejar la bbdd como estaba
			con.rollback();
		}		
		return result;		
	}

	private static void cerrarRecursos() throws SQLException {
		//cerrar recursos en orden inverso
		if (pst !=null) {
			pst.close();
		}
		if (con !=null) {
			con.close();			
		}
		
	}



	private static boolean comprobarEdad(int campoEdad) {		
		boolean result = false;   
		if (campoEdad >= MENORES) {
			   result = true;
		   }
		   else {
			   menores++;
		   }
		   return result;
	}

	private static void escribirResultado() {
		System.out.println("Lineas leidas: " + leidas);
		System.out.println("Personas insertadas: " + insertadas);
		System.out.println("Lineas error (7 campos): " + camposIncorrectos);
		System.out.println("Lineas error (menores): " + menores);
		System.out.println("Lineas error (duplicados): " + duplicados);
		System.out.println("----------------- ");
		int total = insertadas + camposIncorrectos + menores + duplicados;
		System.out.println("Lineas totales: " + total );
	}
}

//Lineas leidas: 10004
//Personas insertadas: 6908
//Lineas error (7 campos): 1
//Lineas error (menores): 3080
//Lineas error (duplicados): 15
//----------------- 
//Lineas totales: 10004
