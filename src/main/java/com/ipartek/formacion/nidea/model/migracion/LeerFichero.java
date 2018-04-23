package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerFichero {

	private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
	final String SQL="INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`,`email`,`edad`,`dni`) VALUES (?, '123456', '2',?,?,?);";
	
	
public static void main(String[] args) {
	System.out.println("Comenzamos a Leer Ficheros");
	String sCurrentLine;
	String nombre;
	String ape1;
	String ape2;
	
	String edad;
	String email;
	String dni;
	String rol;
	String campos[];
	int i=0;
	
	try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
		
		
		
	

		while ((sCurrentLine = br.readLine()) != null) {
		//	System.out.println(sCurrentLine);
			campos=sCurrentLine.split(",");
			nombre=campos[0];
			ape1=campos[1];
			ape2=campos[2];
			edad=campos[3];
			email=campos[4];
			dni=campos[5];
			rol=campos[6];
			
			String nombre1= nombre+" "+ape1+" "+ape2;
			System.out.println("Linea " + i + " =>"+ nombre1 + " "+edad + " " +email + " " +dni+ " " + rol);
			
		
			i++;
		}

	} catch (IOException e) {
		e.printStackTrace();
	}
	
	int counter = 0;
	while (counter <i) {
		counter = counter + 1;
	
	}
	

	
	System.out.println("Linea Leidas:"+ counter);
	System.out.println("Lineas Erroneas:");
	System.out.println("Menores de edad:");
	System.out.println("Email repetidos:");
	System.out.println("Personas insertadas:");
	System.out.println("Lineas insertadas + Lineas erroneas");
}
	}


