package com.ipartek.formacion.nidea.controller.backoffice;

import java.io.IOException;
import java.sql.SQLException;
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

import com.ipartek.formacion.nidea.model.RolDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


/**
 * Servlet implementation class BackofficeRolesController
 */
@WebServlet("/backoffice/roles")
public class BackofficeRolesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_FORM = "/backoffice/roles/form.jsp";
	private static final String VIEW_INDEX = "/backoffice/roles/index.jsp";

	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_BUSQUEDA = 2;
	public static final int OP_ELIMINAR = 3;
	public static final int OP_GUARDAR = 4;

	private RequestDispatcher dispatcher;
	private Alert alert;
	private RolDAO dao;

	private int id;
	private String nombre;

	private String search;
	private int op;

	// Validaciones
	private ValidatorFactory factory;
	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = RolDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
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

		} catch (

		Exception e) {
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher(VIEW_INDEX);
			listar(request);
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}
	}

	private void buscar(HttpServletRequest request) {
		ArrayList<Rol> roles = new ArrayList<Rol>();
		roles = dao.getByName(search);
		request.setAttribute("roles", roles);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);
	}

	private void eliminar(HttpServletRequest request) {
		if (dao.delete(id)) {
			alert = new Alert("Se ha eliminado el registro: " + id, Alert.TIPO_DANGER);
		} else {
			alert = new Alert("Ha habido un error eliminando", Alert.TIPO_WARNING);
		}
		listar(request);
	}

	private void guardar(HttpServletRequest request) throws SQLException {
		Rol rol = new Rol();
		rol.setId(id);
		rol.setNombre(nombre);
		
		if ("".equals(nombre)) {
			alert = new Alert("El nombre no puede estar vacio. No se ha guardado el registro", Alert.TIPO_WARNING);
		} else {
			try {
				if (dao.save(rol)) {
					alert = new Alert("Se ha guardado el registro", Alert.TIPO_PRIMARY);
				} else {
					alert = new Alert("Ha habido un error al guardar", Alert.TIPO_DANGER);
				}
			} catch (MySQLIntegrityConstraintViolationException e) {
				alert = new Alert("Rol duplicado. No se ha podido guardar", Alert.TIPO_WARNING);
				request.setAttribute("rol", rol);

			} catch (MySQLDataException e) {
				dispatcher = request.getRequestDispatcher(VIEW_FORM);
				alert = new Alert("El nombre solo puede contener 45 caracteres. No se ha guardado el registro",
						Alert.TIPO_WARNING);
			}
		}
		request.setAttribute("rol", rol);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
	}

	private void mostrarFormulario(HttpServletRequest request) {
		Rol rol = new Rol();

		if (id != -1) {
			rol = dao.getById(id);
		}
		request.setAttribute("rol", rol);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
	}

	private void recogerParametros(HttpServletRequest request) {
		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		} else {
			op = 0;
		}

		search = (request.getParameter("search") != null) ? request.getParameter("search") : "";

		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		}

		nombre = (request.getParameter("nombre") != null) ? request.getParameter("nombre").trim() : "";
	}

	private void listar(HttpServletRequest request) {

		ArrayList<Rol> roles = new ArrayList<Rol>();
		roles = dao.getAll();

		request.setAttribute("roles", roles);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);
	}

}
