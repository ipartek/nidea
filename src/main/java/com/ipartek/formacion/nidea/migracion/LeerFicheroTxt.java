package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 	---------------------------------------------------------------------------
 *  *Ejercicio: 
 *  _ Juntar los dos programas para insertar todos los campos. 
 *  _ Si alguna linea erronea se rompe porque tiene menos de 7 campos ignorarlo.
 *  _ Las personas menores de 18 años las obviamos.
 *  _ Guardamos el campo email en un campo nuevo único.
 *  ----------------------------------------------------------------------------
 *  
 *   Hacer resumen:						numero
 *  	Lineas leidas:					10004 
 *  	Personas insertadas: 			789
 *  	Lineas erroneas(?campos): 		34
 * 		Menores de Edad: 				567
 * 		email repetidos:				34
 * 		----------------------------------
 * 		Lineas insertadas + erroneas: 	10004
 * 	
 * @author Curso
 *
 */

public class LeerFicheroTxt {
	
	private static final String FILENAME = "C:\\desarrollo\\jee-oxigen\\workspace2\\nidea\\personas.txt";
	

	public static void main(String[] args) {
		System.out.println("Comenzamos a leer fichero");
		System.out.println("*************************");
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			int i =0;
			String campos[];
			String nombre;
			String ape1;
			String ape2;
			
			int edad;
			String email;
			String dni;
			String puesto;
			
		

			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				campos =sCurrentLine.split(",");
				edad= Integer.parseInt((campos[3]));
				
				if (campos.length==7 &&  edad>=18) {
					
				
				nombre = campos[0];
				ape1 = campos[1];
				ape2 = campos[2];
				
				
				email = campos[4];
				dni = campos[5];
				puesto = campos[6];
				
				
				
				
				System.out.println("Nº " + i + " => " + nombre+ " " + ape1+ " " + ape2+ " - Edad: " + edad+ " - DNI: " + dni+ " - Puesto: " + puesto);
				i++;
			}
				else {
					System.out.println(nombre=""+ (ape1 ="NULL ") + (ape2="NULL ") + (edad = 404));
					i++;
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

}
