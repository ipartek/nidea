package com.ipartek.formacion.nidea.model.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Usuario;

public class LeerFichero {

	//private static final String FILENAME = "C:\\desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_lite.txt";
	private static final String FILENAME = "C:\\repositorio_nuevocurso\\eclipseoxigen\\workspace_nuevo\\nidea\\doc\\personas_lite.txt";
	
	
	final String SQL="INSERT INTO `nidea`.`usuario` (`nombre`, `password`, `id_rol`,`email`,`edad`,`dni`) VALUES (?, '123456', '2',?,?,?);";
	
	
public static void main(String[] args) {
	System.out.println("Comenzamos a Leer Ficheros");
	
	//Array de Usuarios
	Usuario usuario = null;
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	//Declaracion de variables
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

	int error_campos = 0;
int cantidad_menor_de_edad = 0;
	
	try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
		

		while ((sCurrentLine = br.readLine()) != null) {
			usuario = new Usuario();
			
			campos=sCurrentLine.split(",");
			nombre=campos[0];
			ape1=campos[1];
			ape2=campos[2];
			edad=campos[3];
			email=campos[4];
			dni=campos[5];
			rol=campos[6];
			String nombrecompleto= nombre+" "+ape1+" "+ape2;
		
			if (campos.length == 7) {
				usuario.setNombre(nombrecompleto);
				if (Integer.parseInt(edad) > 18) {
					usuarios.add(usuario);
					System.out.println("Linea " + i + " =>"+ nombrecompleto + " "+edad + " " +email + " " +dni+ " " + rol);
				} else {
					cantidad_menor_de_edad++;
				}
			} else {
				error_campos++;
}
			i++;
		}
	
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	int counter = 0;
	while (counter <i) {
		counter = counter + 1;
	
	}
	

	System.out.println(usuarios);
	System.out.println("Errores de campos " + error_campos);

	System.out.println("Linea Leidas:"+ counter);
	System.out.println("Menores de edad:"+ cantidad_menor_de_edad);
	System.out.println("Email repetidos:");
	System.out.println("Personas insertadas:");
	System.out.println("Lineas insertadas + Lineas erroneas");
}
	}


