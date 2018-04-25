package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.ipartek.formacion.nidea.model.RolDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class RegistroUsuariosController
 */
@WebServlet("/registro")
public class RegistroUsuariosController extends HttpServlet implements Operable {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_LOGIN = "/login.jsp";
	private static final String VIEW_REGISTRO = "/registro.jsp";

	private RequestDispatcher dispatcher;
	private Alert alert;

	private UsuarioDAO dao;
	private RolDAO rolDAO;
	private HttpSession session;

	private int id_usuario;
	private String nombre_usuario;
	private String password;
	private String confirm_password;
	private Rol rol;
	private String email;

	private int op;

	ValidatorFactory factory;
	Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		dao = UsuarioDAO.getInstance();
		rolDAO = RolDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public void destroy() {
		super.destroy();
		dao = null;
		rolDAO = null;
		validator = null;
		factory = null;
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

			alert = null;

			recogerParametros(request);

			switch (op) {

			case OP_GUARDAR:
				guardar(request);
				break;

			default:
				mostrarRegistro(request);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher(VIEW_REGISTRO);
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}
	}

	private void mostrarRegistro(HttpServletRequest request) {

		dispatcher = request.getRequestDispatcher(VIEW_REGISTRO);
	}

	private void recogerParametros(HttpServletRequest request) {

		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		} else {
			op = 0;
		}

		nombre_usuario = (request.getParameter("nombre") != null) ? request.getParameter("nombre").trim() : "";

		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
		} else {
			password = "";
		}

		if (request.getParameter("confirm_password") != null) {
			confirm_password = request.getParameter("confirm_password");
		} else {
			confirm_password = "";
		}

		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
		} else {
			email = "";
		}
	}

	private void guardar(HttpServletRequest request) {

		Usuario usuario = new Usuario();

		usuario.setNombre(nombre_usuario);
		usuario.setPass(password);
		usuario.setConfirm_pass(confirm_password);
		usuario.setEmail(email);
		usuario.getRol().setId(Usuario.ROL_USER);;
		if ("".equals(usuario.getEmail())) {
			usuario.setEmail(usuario.getNombre() + "@" + usuario.getNombre() + ".com");
		}

		try {
			if (!usuario.getPass().equals(usuario.getConfirm_pass())) {
				request.setAttribute("registro", usuario);
				alert = new Alert("Las contraseñas no coinciden", Alert.TIPO_WARNING);
				mostrarRegistro(request);
			} else {

				// Validaciones Incorrectas
				Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
				if (violations.size() > 0) {
					String mensajes = "";
					for (ConstraintViolation<Usuario> violation : violations) {
						mensajes += violation.getMessage() + "<br>";
						alert = new Alert(mensajes, Alert.TIPO_WARNING);
					}
					// Validaciones OK
				} else {
					//se pasa un 1 para reutilizar el mismo metodo del dao
					if (dao.save(usuario, 1)) {
						alert = new Alert("Usuario guardado", Alert.TIPO_PRIMARY);
						dispatcher = request.getRequestDispatcher(VIEW_LOGIN);
					} else {
						alert = new Alert("Lo sentimos pero no se ha podido guardar el usuario el nombre o el email estan repetidos", Alert.TIPO_WARNING);
						dispatcher = request.getRequestDispatcher(VIEW_REGISTRO);
					}
				}
				request.setAttribute("registro", usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

}
