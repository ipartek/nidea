package com.ipartek.formacion.nidea.controller.backoffice;

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
 * Servlet implementation class BackOfficeUsuariosController
 */
@WebServlet("/backoffice/usuarios")
public class BackofficeUsuariosController extends HttpServlet implements Operable{
	private static final long serialVersionUID = 1L;

	private static final String VIEW_FORM = "/backoffice/usuarios/form.jsp";
	private static final String VIEW_INDEX = "/backoffice/usuarios/index.jsp";

	private RequestDispatcher dispatcher;
	private Alert alert;
	private String view = "";

	private UsuarioDAO dao;
	private RolDAO rolDAO;

	private int id_usuario;
	private String nombre_usuario;
	private String password;
	private Rol rol;

	private String search;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
			case OP_MOSTRAR_FORMULARIO:
				mostrarFormulario(request);
				break;

			case OP_BUSQUEDA:
				buscar(request);
				break;

			case OP_ELIMINAR:
				eliminar(request);
				break;

			case OP_GUARDAR:
				guardar(request);
				break;

			default:
				listar(request);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher(VIEW_INDEX);
			listar(request);
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}
	}

	private void listar(HttpServletRequest request) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = dao.getAll();

		request.setAttribute("usuarios", usuarios);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void recogerParametros(HttpServletRequest request) {
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

		rol = null;
		if (request.getParameter("id_rol") != null) {
			rol = rolDAO.getById(Integer.parseInt(request.getParameter("id_rol")));
		}
	}

	private void mostrarFormulario(HttpServletRequest request) {
		Usuario usuario = new Usuario();

		if (id_usuario == -1) {
			request.setAttribute("usuario", usuario);
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
		} else {
			usuario = dao.getById(id_usuario);
			request.setAttribute("usuario", usuario);
			dispatcher = request.getRequestDispatcher(VIEW_FORM);
		}

		ArrayList<Rol> roles = new ArrayList<Rol>();
		roles = rolDAO.getAll();
		request.setAttribute("roles", roles);
	}

	private void guardar(HttpServletRequest request) {
		Usuario usuario = new Usuario();
		usuario.setId(id_usuario);
		usuario.setNombre(nombre_usuario);
		usuario.setPass(password);
		usuario.setRol(rol);

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
				if (dao.save(usuario)) {
					alert = new Alert("Usuario guardado", Alert.TIPO_PRIMARY);
				} else {
					alert = new Alert("Lo sentimos pero ya existe el nombre de usuario", Alert.TIPO_WARNING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("roles", rolDAO.getAll());
		request.setAttribute("usuario", usuario);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
	}

	private void buscar(HttpServletRequest request) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = dao.getByName(search);
		request.setAttribute("usuarios", usuarios);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);
	}

	private void eliminar(HttpServletRequest request) {
		if (dao.delete(id_usuario)) {
			alert = new Alert("Se ha eliminado el registro: " + id_usuario, Alert.TIPO_DANGER);
		} else {
			alert = new Alert("Ha habido un error eliminando", Alert.TIPO_WARNING);
		}

		listar(request);

	}
}
