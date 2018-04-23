package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
/**
 * --------------------------
 * Ejercicio
 * juntar los dos fichero en uno
 * Si alguno no tiene 7 campos, es erronea y la ignoramos. Ok la que tiene 7 campos
 * Obviaremos las personas menores de 18 años
 * Guardamos el campo email. No puede ser repetido
 * Nombre  + 2 apellidos tampoco puede ser repetido
 * 
 * -----------------
 * 
 * hacer resumen
 * Lineas leidas: 10004
 * PErsonas insertadas 789
*	Lineas erroneas 34
Menores de edad 567
Email repetirods 34
Lineas insertadas + lineas errornas 10004
 * @author Curso
 *
 */
public class LerrFicheroTxt {
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personaslittle.txt";
	public static void main(String[] args) throws Exception {
		
	System.out.println("Comenzamos a leer fichero");
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String sCurrentLine;
			int i = 0;
			String campos[];
			String nombre;
			String ape1;
			String apel2;
			
			int age= 0;
			String email;
			String dni;
			String ocupacion;
			
			
			while ((sCurrentLine = br.readLine()) != null) {
				campos = sCurrentLine.split(","); 
				
				age = Integer.parseInt((campos[3]));
				
				if(campos.length==7 && age >18   ) {
					nombre = campos[0];
					ape1= campos[1];
					apel2= campos[2];
					
					
					email = campos[4];
					dni = campos[5];
					ocupacion = campos [6];
					System.out.println("Linea" + i + " =>" + nombre + " " + ape1);
					i++;
					
				}
				
				else {
				
					System.out.println(i);
					i++;
				}
				
			}

	/*	} catch ( e) {
			e.printStackTrace();
		}*/

	


	}
	}}
