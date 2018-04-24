package com.ipartek.formacion.nidea.model.migration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerFicheroTxt {
	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_litel.txt";

	public static void main(String[] args) {
		System.out.println("Comenzamos a leer fichero");
		String sCurrentLine;
		int i = 0;
		String campos [];
		String nombre;
		String ape1;
		String ape2;
		String email;
		int edad;
		int menores = 0 ;
		int formato = 0;
		int insert = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			while ((sCurrentLine = br.readLine()) != null) {
				 campos = sCurrentLine.split(",");
				 nombre = campos[0];
				 ape1=campos[1];
				 edad=Integer.parseInt(campos[3]);
				 ape2=campos[2];
				 email=campos[5];
				 if(campos.length==7) {
					 if(edad>17) {
						 insert ++;
						 
					 }else {
						 menores ++;
					 }
					 
					 
				 }else {
					 formato ++;
				 }
				 
				
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			System.out.println("***************************");
			System.out.println("Lineas leidas: "+i);
			System.out.println("Lineas mal formadas: "+formato);
			System.out.println("Menores de edad: "+menores);
			System.out.println("Lineas insertadas: "+insert);
			System.out.println("***************************");
			System.out.println("Lineas totales contadas: "+ (insert+(formato+insert)));
		}

	}

}
