package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class leerFicheroTxt {
	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace3\\nidea\\doc\\personas_little.txt";

	public static void main(String[] args) {
		System.out.println("Comenzamos a leer fichero");
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String sCurrentLine;
			
			String campos[];
			String nombre ="";
			String ape1 = "";
			String ape2 = "";
			int edad = 0;
			String email = "";
			String dni = "";
			String puesto = "";
			
			int i = 0;
			int camposIncorrectos = 0;
			int menoresEdad = 0;
			int registrosInsertados = 0;
			
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				
				campos = sCurrentLine.split(",");
				if (campos.length!=7) {
					camposIncorrectos++;
				}else {
					if (Integer.parseInt(campos[3]) < 18) {
						menoresEdad++;
					}else {
						registrosInsertados++;
						nombre = campos[0];
						ape1 = campos[1];
						ape2 = campos[2];
						edad = Integer.parseInt(campos[3]);
						email = campos[4];
						dni = campos[5];
						puesto = campos[6];
					}
				}
				
				System.out.println("linea " + i + " => " + "nombre "+ nombre +" "+ ape1 + " " + ape2);
				System.out.println("edad "+edad);
				System.out.println("email "+email);
				System.out.println("dni "+dni);
				System.out.println("puesto "+puesto);
				System.out.println("------------------------------------");
				i++;
			}
			
			System.out.println("Fallos campos "+ camposIncorrectos);
			System.out.println("Menores "+ menoresEdad);
			System.out.println("Nuevos registros insertados "+ registrosInsertados);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

// si alguna linea no tiene 7 campos es erronea
// las personas menores de 18 años no se muestran
// guardar el email de las personas y no puede ser repetido
// si el nombre+2 ape esta repetido es erroneo

// RESUMEN DE RESULTADOS:
// Lineas leidas:				10004
// Personas insertadas:			---
// Lineas errorneas (7 campos):	---
// Menores de edad:				---
// Nombre o Email repetido:		---
// -------------------------------------
// Lineas insertadas + erroneas 10004
