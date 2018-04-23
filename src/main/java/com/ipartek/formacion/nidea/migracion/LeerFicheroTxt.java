package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeerFicheroTxt {
	
	static int duplicado=0;
	static int insertadas=0;
	
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
	public static void main(String[] args) {
		
		
		System.out.println("comenzamos a leer fichero");
		System.out.println("--------------------------------");
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			int leidas=0;
			int num_campos=0;
			int menor_edad=0;

			String campos[];
			String nombre;
			String ape1;
			String ape2;
			String age;
			int edad;
			String email;
			String dni;
			String rol;
			String nombreCompleto;
			
			final String URL="jdbc:mysql://localhost/nidea?user=root&password=root";
			final String SQL="INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`, `email`) VALUES (?, '123456', '2', ?);";
			Connection con=null;
			PreparedStatement pst=null;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(URL);
				con.setAutoCommit(false);

				while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				 campos=sCurrentLine.split(",");
				 nombre=campos[0];
				 ape1=campos[1];
				 ape2=campos[2];
				 age=campos[3];
				 email=campos[4];
				 dni=campos[5];
				 rol=campos[6];
				 nombreCompleto=nombre+" "+ape1+" "+ape2;
				 
				 edad=Integer.parseInt(age);
				 
				 if (campos.length!=7) {
					 num_campos++;
				 }
				 else if(18>edad) {
					 menor_edad++;
				 }
				 else {
					pst=con.prepareStatement(SQL);
					pst = con.prepareStatement(SQL);
					pst.setString(1, nombreCompleto);
					pst.setString(2, email);
						
					if (1==pst.executeUpdate()) {
						insertadas++;
						
					}else {
						System.out.println("****Error al insertar usuario ");
						duplicado++;
					}

				 }
				leidas++;	
			}
				
			//comitar cambios al terminar el proceso
			con.commit();
			
			System.out.println("Lineas leidas: "+leidas+ "\n"+"Lineas insertadas: "+leidas+ "\n"+"Registros con numero de campos incorrectos: "+num_campos+ "\n"+"Menores de edad: "+menor_edad+ "\n"+"Nombre/mail duplicados: "+duplicado+ "\n");

			}catch(Exception e){
				e.printStackTrace();
				//si hay un fallo rollback para dejar la base de datos como estaba
				con.rollback();
			}finally {
				//cerrar recursos en orden inverso
				if(pst!=null) {
					pst.close();
				}
				if(con!=null) {
					con.close();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			duplicado++;
		}
		
		

	}
	
	
	

}
