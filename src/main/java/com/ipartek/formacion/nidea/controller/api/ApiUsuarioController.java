package com.ipartek.formacion.nidea.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class ApiUsuarioController
 */
@WebServlet("/api/usuario")
public class ApiUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out =response.getWriter();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		//recoger parametros
		String nombre =request.getParameter("nombre");
		if(nombre != null && !"".equals(nombre)) {
			usuarios = (ArrayList<Usuario>)UsuarioDAO.getInstance().getAllApiByName(nombre);
		}
		
		/*ArrayList<Usuario> usuarios =new ArrayList<Usuario>();
		usuarios.add (new Usuario (1,"Usuario 1"));
		usuarios.add (new Usuario (2,"Usuario 2"));
		usuarios.add (new Usuario (3,"Usuario 3"));*/
			 
		
		
		//respuesta
		if(usuarios.size()== 0) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}else {
			//por defecto siempre retorno 200
		}
		
		out.print(new Gson().toJson(usuarios));
		//out.print("{ \"nombre\": \""+ nombre +"\"}");
		out.flush();//para que termine de pintar y libere el buffer
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
