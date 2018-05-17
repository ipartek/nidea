package com.ipartek.formacion.nidea.ejemplos;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLParserJsoup {

	public static void main(String[] args) throws IOException {

		/*
		 * Document doc = Jsoup.connect("http://example.com/").get(); String title =
		 * doc.title();
		 * 
		 * System.out.println("TITULO: " + title);
		 * 
		 * Elements anclas = doc.getElementsByTag("a"); for (Element ancla : anclas) {
		 * 
		 * String urlPagina2 = ancla.attr("href"); Document doc2 =
		 * Jsoup.connect(urlPagina2).get();
		 * 
		 * System.out.println("TITULO PAGINA: " + doc2.title());
		 * 
		 * }
		 * 
		 */

		//Para intentar entrar al servidor cogiendo los datos por Jsoup e introducir materiales nuevos a la BBDD
		String url = "http://192.168.0.42:8080/nidea/login";
		String urlMaterial = "http://192.168.0.42:8080/nidea/backoffice/materiales?op=1";

		Connection.Response response = Jsoup.connect(url).method(Connection.Method.POST).data("usuario", "admin")
				.data("password", "admin").execute();

		Document docBackoffice = response.parse();
		System.out.println(docBackoffice.getElementsByTag("h1").get(0).text());
		String cookieSession = response.cookies().get("JSESSIONID");

		// Connection.Response response2 =
		// Jsoup.connect(url).method(Connection.Method.GET).execute();
		//
		// Document docMateriales = response2.parse();
		// Element botonCrear = docMateriales.getElementsByTag("button").get(0);

		Connection.Response responseCrearMaterial = Jsoup
				.connect("http://192.168.0.42:8080/nidea/backoffice/materiales?op=2").method(Connection.Method.POST)
				.data("nombre", "Nuevo Material").data("precio", "11.0").cookie("JSESSIONID", cookieSession).execute();

		// Document docBackoffdsdicewrsewte = response.parse();

	}

}
