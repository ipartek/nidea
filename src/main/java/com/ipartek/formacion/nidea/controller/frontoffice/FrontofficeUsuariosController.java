package com.ipartek.formacion.nidea.controller.frontoffice;

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

import com.ipartek.formacion.nidea.controller.Operable;
import com.ipartek.formacion.nidea.model.RolDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class FrontofficeUsuariosController
 */
@WebServlet("/frontoffice/usuarios")
public class FrontofficeUsuariosController extends HttpServlet implements Operable {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_FORM = "/frontoffice/usuarios/form.jsp";
	private static final String VIEW_LOGIN = "/login.jsp";
	private static final String VIEW_REGISTRO = "/frontoffice/usuarios/registro.jsp";

	public static final int OP_REGISTRO = 100;
	public static final int OP_GUARDAR_REGISTRO = 101;

	private static final int USUARIO_NUEVO = 1;
	private static final int USUARIO_EXISTENTE = 2;

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

	private String search;
	private int op;
	private int tipo_operacion;

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

			tipo_operacion = USUARIO_EXISTENTE;

			switch (op) {

			case OP_GUARDAR:
				guardar(request);
				break;

			case OP_REGISTRO:
				mostrarRegistro(request);
				break;

			case OP_GUARDAR_REGISTRO:
				guardarRegistro(request);
				break;

			default:
				mostrarFormulario(request);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
			mostrarFormulario(request);
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}
	}

	private void mostrarRegistro(HttpServletRequest request) {

		dispatcher = request.getRequestDispatcher(VIEW_REGISTRO);
	}

	private void guardarRegistro(HttpServletRequest request) {

		if (null != password && !"".equals(password) && password.equals(confirm_password)) {
			rol = new Rol();
			rol.setId(Usuario.ROL_USER);
			tipo_operacion = USUARIO_NUEVO;
			guardar(request);
		} else {
			mostrarRegistro(request);
		}

	}

	private void recogerParametros(HttpServletRequest request) {

		session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		} else {
			op = 0;
		}

		search = (request.getParameter("search") != null) ? request.getParameter("search") : "";

		if (request.getParameter("id") != null) {
			id_usuario = Integer.parseInt(request.getParameter("id"));
		}

		nombre_usuario = (request.getParameter("nombre") != null) ? request.getParameter("nombre").trim() : "";

		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
		}

		if (request.getParameter("confirm_password") != null) {
			confirm_password = request.getParameter("confirm_password");
		}

		rol = null;
		if (null != usuario) {
			if (null != usuario.getRol()) {
				rol = usuario.getRol();
			}
		}

		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
		} else if (null != usuario) {
			email = usuario.getEmail();
		} else {
			email = "";
		}
	}

	private void mostrarFormulario(HttpServletRequest request) {

		session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (null != usuario) {
			request.setAttribute("usuario", usuario);
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
		} else {
			alert = new Alert("Debe estar logeado para editar su usuario", Alert.TIPO_DANGER);
			dispatcher = request.getRequestDispatcher(VIEW_LOGIN);
		}

		ArrayList<Rol> roles = new ArrayList<Rol>();
		roles = rolDAO.getAll();
		request.setAttribute("roles", roles);
	}

	private void guardar(HttpServletRequest request) {

		Usuario usuario = new Usuario();
		if (id_usuario != 0) {
			usuario.setId(id_usuario);
		}
		usuario.setNombre(nombre_usuario);
		usuario.setPass(password);
		usuario.setRol(rol);
		usuario.setEmail(email);
		if ("".equals(usuario.getEmail())) {
			usuario.setEmail(usuario.getNombre() + "@" + usuario.getNombre() + ".com");
		}

		Usuario uSession = new Usuario();
		session = request.getSession();
		if (tipo_operacion == USUARIO_EXISTENTE) {
			uSession = (Usuario) session.getAttribute("usuario");
		}

		try {

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
				if (dao.save(usuario, uSession.getId())) {
					alert = new Alert("Usuario guardado", Alert.TIPO_PRIMARY);
				} else {
					alert = new Alert("Lo sentimos pero no se ha podido guardar el usuario", Alert.TIPO_WARNING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("roles", rolDAO.getAll());

		if (tipo_operacion == USUARIO_EXISTENTE) {
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
			request.setAttribute("usuario", usuario);
		} else {
			mostrarRegistro(request);
		}
	}

}