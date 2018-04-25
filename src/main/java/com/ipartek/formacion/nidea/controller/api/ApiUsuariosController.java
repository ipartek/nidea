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
 * Servlet implementation class ApiUsuariosController
 */
@WebServlet("/api/usuario/")
public class ApiUsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int NOMBRE = 1;
	private static final int EMAIL = 2;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		if (null != request.getParameter("nombre")) {
			// recoger parametros
			String nombre = request.getParameter("nombre");
			if (null == nombre) {
				nombre = "";
			}

			if ("".equals(nombre)) {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
				usuarios = UsuarioDAO.getInstance().getByNameApi(nombre);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(new Gson().toJson(usuarios));
			}

		} else if (null != request.getParameter("nombreExacto")) {

			buscarPorParametro(request.getParameter("nombreExacto"), NOMBRE, response);

		} else if (null != request.getParameter("email")) {

			buscarPorParametro(request.getParameter("email"), EMAIL, response);
//			String email = request.getParameter("email");
//
//			if ("".equals(email)) {
//				response.setStatus(HttpServletResponse.SC_OK);
//			} else {
//				Usuario usuario = new Usuario();
//				usuario = UsuarioDAO.getInstance().getByExactEmailApi(email);
//				if (usuario.getId() == Usuario.USUARIO_INDEFINIDO) {
//					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//				} else {
//					response.setStatus(HttpServletResponse.SC_OK);
//				}
//			}
		}
		out.flush();
	}

	/**
	 * 
	 * @param search
	 *            texto a buscar
	 * @param param
	 *            parametro contra el que se va a buscar
	 * @param response
	 */
	private void buscarPorParametro(String search, int param, HttpServletResponse response) {

		if ("".equals(search)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			Usuario usuario = new Usuario();
			switch (param) {
			case (NOMBRE):
				usuario = UsuarioDAO.getInstance().getByExactNameApi(search);
				break;
			case (EMAIL):
				usuario = UsuarioDAO.getInstance().getByExactEmailApi(search);
				break;
			}
			usuario = UsuarioDAO.getInstance().getByExactEmailApi(search);
			if (usuario.getId() == Usuario.USUARIO_INDEFINIDO) {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
