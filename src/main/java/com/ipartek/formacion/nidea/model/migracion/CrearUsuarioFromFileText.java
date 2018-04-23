package com.ipartek.formacion.nidea.model.migracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;


/**
 * 
 * 
 * 
 *      Lineas Leidas:                100004
 *      Personas insertadas:             789 
 *      Lineas erroneas (7campos) :       34
 *      Menores de Edad:                 567  
 *      Email repetidos:                  34
 *      Nombre repetidos:                  5
 *      -------------------------------------
 *      Lineas insertadas + erroneas: 100004
 *      
 * 
 * 
 * 
 * 
 * 
 * 
 * @author ur00
 *
 */

public class CrearUsuarioFromFileText {

	public static void main(String[] args) throws SQLException {
	
		System.out.println("Crear Usuarios desde un fichero de texto");
		final String URL = "jdbc:mysql://localhost/nidea?user=root&password=";
		final String SQL = "INSERT INTO `usuario` (`nombre`, `password`, `id_rol`) VALUES ( ? , '123456', '2');";
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
		
			
			for ( int i=0; i<5 ; i++) {
				
				pst = con.prepareStatement(SQL);
				pst.setString(1, "usuario" + i );
				
				if ( 1 == pst.executeUpdate()) {
					System.out.println( i + " usuario insertado");
					
					if ( i == 2 ) {
						throw new Exception("Lanzo adrede para probar roolback");
					}
					
				}else {
					System.out.println("*** " + i + " Error al insertar usuario");
				}
				
			}
			
			//comitar cambios al terminar el proceso
			con.commit();
			
			
		}catch (Exception e) {
			e.printStackTrace();
			//si hay fallo rollback para dejar la bbdd como estaba
			con.rollback();
			
		}finally {
			//cerrar recursos en orden inverso
			if ( pst != null ) {
				pst.close();
			}
			if( con != null ) {
				con.close();
			}
		}	
		
		
	}

}
