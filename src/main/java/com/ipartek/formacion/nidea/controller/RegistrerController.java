package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class RegistrerController
 */
@WebServlet("/nuevoregistro")
public class RegistrerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String view = "";
	private static final String VIEW_LOGIN = "login.jsp";
	private static final String VIEW_REGISTRO = "registro.jsp";
	private Alert alert = new Alert();
	private Usuario usuario;
	private String password = null;
	private String password2 = null;
	private String email = null;
	private String nombre = null;

	private UsuarioDAO daoUsuario;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			if (controlParametros(request)) {
				if (password.equals(password2)) {
					crearUsuario();
					if (daoUsuario.save(usuario)) {
						view = VIEW_LOGIN;
						alert = new Alert("Ya puedes logearte " + usuario.getNombre(), Alert.TIPO_PRIMARY);
					} else {
						view = VIEW_REGISTRO;
						alert = new Alert(
								"Alguno de los campos que metites ya existen prueba otra vez, prueba de nuevo");
					}
				} else {
					view = VIEW_REGISTRO;
					alert = new Alert("La contraseña debe ser igual, prueba otra vez =D ");
				}
			} else {
				view = VIEW_REGISTRO;
				alert = new Alert("Por favor rellena todos los campos del formulario, prueba otra vez =D ");

			}

		} catch (Exception e) {
			e.printStackTrace();
			view = VIEW_REGISTRO;
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}

	}

	private void crearUsuario() {
		usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setPass(password);

	}

	private boolean controlParametros(HttpServletRequest request) {

		if (null != request.getParameter("name") && " " != request.getParameter("name")) {
			nombre = request.getParameter("name");
		} else {
			nombre = "";
		}
		if (null != request.getParameter("email") && " " != request.getParameter("email")) {
			email = request.getParameter("email");
		} else {
			email = "";
		}

		if (null != request.getParameter("password") && " " != request.getParameter("password")) {
			password = request.getParameter("password");
		} else {
			password = "";
		}
		if (null != request.getParameter("confirm") && " " != request.getParameter("confirm")) {
			password2 = request.getParameter("confirm");
		} else {
			password2= "";
		}

		if ("" != nombre && "" != email && "" != password && "" != password2) {
			return true;
		} else {
			return false;
		}

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
