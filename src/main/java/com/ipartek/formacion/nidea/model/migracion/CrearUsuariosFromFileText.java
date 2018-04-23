package com.ipartek.formacion.nidea.model.migracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class CrearUsuariosFromFileText {
	
	
public static void main(String[]args) throws SQLException {
	
	System.out.println("CREAR USUARIOS DESDE UN FICHERO DE TEXTO");
	
	final String SQL="INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`) VALUES (?, '123456', '2');";
	Connection con=null;
	PreparedStatement pst=null;
	final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root";
	
	try{
		
		Class.forName("com.mysql.jdbc.Driver");
		
		con = DriverManager.getConnection(URL);
		con.setAutoCommit(false); //Si es auto update casca
		
		for(int i=0; i<5; i++){
			
			pst=con.prepareStatement(SQL);
			pst.setString(1, "usuario" + i);

			if(1==pst.executeUpdate()) {
				
				System.out.println(i + "Usuario Insertado");
				
			/*	if(i==2){
					throw new Exception("Lanzo adrede una excepcion");
				}*/
			}
			
			else {
				
				System.out.println("Error al insertar usuario");
			}
			//Comitar los cambios al terminar el proceso
			con.commit();
			
		}
	} catch (Exception e) {
		e.printStackTrace();
		//Si hay fallo rollback para dejar la bbdd como estaba
		con.rollback();
	}
	finally {//Cerrar recursos en orden inverso
		if(pst !=null) {
			pst.close();
		}
	}
}

/*
private static void edad() {
	if(edad<18) {
		
		
		
	}
}
	
private static void camposrepetidos(){
	if (email && nombre) {
		
		
		
	}

	
}*/

}
