package com.ipartek.formacion.nidea.migracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Usuario;

public class LeerFicheroTxt {

	private static final String FILENAME = "C:\\Desarrollo\\jee-oxygen\\workspace2\\nidea\\doc\\personas_little.txt";

	public static void main(String[] args) {

		System.out.println("Comenzamos a leer fichero");

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			int i = 0;
			Usuario usuario = null;
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			String campos[];

			int error_campos = 0;
			int error_edad = 0;
			// TODO si alguna linea no tiene 7 campos la linea es erronea
			// TODO las personas menores de 18 años se obvian
			// TODO guardar tbn el email de las personas y no puede ser repetido. Si el
			// email o nombre es repetido el registro es erroneo
			// TODO cuando termine el proceso
			/*
			 * lineas leidas: xxxx 
			 * lineas erroneas (7campos): xxx 
			 * menores de edad: xxxx
			 * email repetidos xxxx 
			 * personas insertadas
			 * 
			 */

			while ((sCurrentLine = br.readLine()) != null) {

				usuario = new Usuario();
				campos = sCurrentLine.split(",");

				if (campos.length == 7) {
					usuario.setNombre(campos[0] + " " + campos[1] + " " + campos[2]);
					if (Integer.parseInt(campos[3]) > 18) {
						usuarios.add(usuario);
						System.out.println("linea " + i + "=> " + usuario);
					} else {
						error_edad++;
					}
				} else {
					error_campos++;
				}

				i++;
			}

			System.out.println(usuarios);
			System.out.println("Errores de campos " + error_campos);
			System.out.println("Errores de edad " + error_edad);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
