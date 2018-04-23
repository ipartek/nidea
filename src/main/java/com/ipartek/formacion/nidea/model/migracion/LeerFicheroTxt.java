package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerFicheroTxt {
	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
		public static void main(String[] args) {
			System.out.println("Comenzamos a leer fichero");
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

				String sCurrentLine;
				int i=0;
				String campos[];
				String nombre;
				String ape1;
				String ape2;
				int edad;
				String email;
				String dni;
				String rol;
				while ((sCurrentLine = br.readLine()) != null) {
					campos=sCurrentLine.split(",");
					nombre=campos[0];
					ape1=campos[1];
					ape2=campos[2];
					edad=Integer.parseInt(campos[3]);
					email=campos[4];
					dni=campos[5];
					rol=campos[6];
					
					if(campos.length==7) {
						if(edad>18) {
							System.out.println("Linea"+i+"=>"+nombre+" "+ape1+" "+ape2+" "+edad+" "+email+" "+dni+" "+rol);
						}else {
							//System.out.println("Menor de edad, no podemos mostrar datos");
							continue;
							
						}
					
				
					}else {
						//System.out.println("Faltan campos");
						continue;
					}
					i++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}