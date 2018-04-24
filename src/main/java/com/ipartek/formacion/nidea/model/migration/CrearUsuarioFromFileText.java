package com.ipartek.formacion.nidea.model.migration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ipartek.formacion.nidea.model.ConnectionManager;

public class CrearUsuarioFromFileText {
	// Archivo que leemos
	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas.txt";

	// BBDD
	private static final String URL = "jdbc:mysql://localhost/nidea?user=root&password=root"; // Conexion a la bbdd
	private static final String SQL = "INSERT INTO `usuario`(`nombre`,`password`,`id_rol`,`email`)VALUES(?,'123456','2',?);"; // Insert
																																// que
																																// realizamos
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	// Comprobaciones
	private static int MAXFORMATO = 7;// Numero exacto de campos que debe tener para ser considerado correcto un
										// registro
	private static int MAXEDAD = 17;// Poner la edad ANTERIOR a la que se desee cribar
	private static int i = 0;// variable que usamos para conecer la iteracion por la que vamos del fichero
	
	//Connection inicializado
	static Connection con = null;
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Comenzamos a leer fichero");
		// Campos del txt
		String sCurrentLine;
		String campos[];// variable donde se guardan los datos del usuario
		String nombre;// nombre del que recogemos de campo
		String ape1;// Apellido que recogemos del campo
		String ape2;// Apellido 2 que recogemos del campo
		String email;// mail que recogemos del campo
		// Comprobaciones
		int edad; // edad que recogemos del campo
		int menores = 0;// contador menores
		int formato = 0;// contador de mal formados
		int insert = 0;// contador de insert realizadas
		int duplicado = 0;// contador de duplicidad
		
		

		/**
		 * Comenzamos leyendo el fichero de texto, si esta bien redactado pasamos a
		 * insertarlo con un metodo que nos devuelve true o false, en caso de false es o
		 * bien por duplicidad o por que el nombre estaba mal formado.
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			while ((sCurrentLine = br.readLine()) != null) {
				campos = sCurrentLine.split(",");
				if (MAXFORMATO == campos.length) {

					nombre = campos[0];
					ape1 = campos[1];
					ape2 = campos[2];
					edad = Integer.parseInt(campos[3]);
					email = campos[4];
					if (MAXEDAD < edad) {
						nombre = nombre + " " + ape1 + " " + ape2;
						if (meterRegistros(nombre, email) == false) {
							duplicado++;

						} else {
							insert++;
						}
					} else {
						menores++;
					}
				} else {
					formato++;
				}
				i++;
			}// End while
			con.commit();//Lanzamos el comit cuando acabamos el while y no hubo problemas

		} catch (IOException e) {
			e.printStackTrace();
			// si hay fallo rollback para dejar la bbdd como estaba
			con.rollback();
			System.out.println("Se ejecuto un rollback");
		} finally {
			if (con != null) {
				con.close();
			}
			System.out.println("***************************");
			System.out.println("Lineas leidas: " + i);
			System.out.println("Lineas mal formadas: " + formato);
			System.out.println("Menores de edad: " + menores);
			System.out.println("Lineas insertadas: " + insert);
			System.out.println("Insert duplicadas: " + duplicado);
			System.out.println("***************************");
			System.out.println("Lineas totales contadas: " + (insert + formato + menores + duplicado));
		}
		System.out.println("Crear Usuarios desde un fichero de texto");

	}

	/**
	 * Inserta el registro previo a la criva que pasa para saber si puede insertar
	 * dicho texto recogido del txt
	 * 
	 * @param nombre
	 *            String con el que llamaremos al sujeto (metemos el nombre y
	 *            apellido separados por un espacio)
	 * @param email
	 *            String que guardamos en la bbdd
	 * @return boolean Devuelve true si insertamos en la bbdd si no false y lanzamos
	 *         la excepcion hacia arriba
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public static boolean meterRegistros(String nombre, String email) throws SQLException {
		con = null;
		PreparedStatement pst = null;
		boolean validar = false;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
			pst = con.prepareStatement(SQL);
			pst.setString(1, nombre);
			pst.setString(2, email);
			if (1 == pst.executeUpdate()) {
				System.out.println(" Usuario insertado " + i);
				validar = true;
			} else {
				System.out.println("***** ERROR al insertar usuario ");
			}
			// Comitar cambios al terminar el proceso
			
		} catch (Exception e) {
			e.printStackTrace();
			

		} finally {
			if (pst != null) {
				pst.close();
			}
			return validar;
		}
	}

}
