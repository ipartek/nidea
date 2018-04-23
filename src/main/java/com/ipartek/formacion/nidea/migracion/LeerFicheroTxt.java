package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerFicheroTxt {
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspaces2\\nidea\\doc\\personas_little.txt";
	
	public static void main(String[] args) {
		
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
