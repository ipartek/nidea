package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class LoginUserController
 */
@WebServlet("/login-user")
public class LoginUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private HashMap<Integer, String> usuarios = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		usuarios = new HashMap<Integer, String>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

		try {
			// recoger parametros
			int id = Integer.parseInt(request.getParameter("id"));
			String nombre = request.getParameter("usuario");
			Usuario usuario = new Usuario(id, nombre);

			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(15);
			session.setAttribute("uPublic", usuario);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher("frontoffice/index.jsp").forward(request, response);
		}

	}

}
