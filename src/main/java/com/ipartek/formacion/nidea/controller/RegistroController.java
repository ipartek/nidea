package com.ipartek.formacion.nidea.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDAO daoUsuario;

	Usuario pojo = new Usuario();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoUsuario = UsuarioDAO.getInstance();

	}

	@Override
	public void destroy() {
		super.destroy();
		daoUsuario = null;
	}

	private static final String VIEW_LOGIN = "login.jsp";
	private static final String VIEW_REGISTRO = "registro.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);

	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Alert alerta = null;
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");

		try {

			if (nombre.equals("")) {
				System.out.println("Nombre vacio");

				if (email.equals("")) {

					System.out.println("Email vacio");

					if (!password.equals(password2)) {
						System.out.println("Contraseñas diferentes");
					}
					if (password == null && password2 == null) {
						System.out.println("El campo contraseña es nulo");
					}
				}

				request.getRequestDispatcher(VIEW_REGISTRO).forward(request, response);
			} else {

				pojo.setEmail(email);
				pojo.setNombre(nombre);
				pojo.setPass(password);

				if (daoUsuario.crear(pojo)) {

					alerta = new Alert("Registro Correcto,introduce tus credenciales");
					request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
				} else {

					alerta = new Alert("Usuario o Email estan repetidos");
					request.getRequestDispatcher(VIEW_REGISTRO).forward(request, response);
				}
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
