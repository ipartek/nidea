package com.ipartek.formacion.nidea.controller.backoffice;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class BackofficeUsuarioController
 */
@WebServlet("/backoffice/usuarios")
public class BackofficeUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_INDEX = "usuarios/index.jsp";
	private static final String VIEW_FORM = "usuarios/form.jsp";
	private static final String VIEW_INICIO = "index.jsp";

	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_BUSQUEDA = 2;
	public static final int OP_ELIMINAR = 3;
	public static final int OP_GUARDAR = 4;
	public static final int OP_INDEX = 5;

	private RequestDispatcher dispacher;

	private UsuarioDAO dao;
	private Alert alert;

	// PARAMETROS COMUNES
	private String search;// para el buscador por nombre material
	private int op; // operacion a realizar

	// PARAMETOS DEL MATERIAL
	private int id;
	private String nombre;
	private String pass;

	// Se ejecuta una unica vez con la primera peticion que se realiza
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = UsuarioDAO.getInstance();

	}

	// Se ejecuta cuando Paramos el servidor de aplicaciones
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		dao = null;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Antes de Ejecutar doGET o doPost");
		super.service(request, response);
		System.out.println("Despues de Ejecutar doGet o doPost");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			recogerParametros(request);

			switch (op) {
			case OP_MOSTRAR_FORMULARIO:
				mostrarFormulario(request);

				break;
			case OP_ELIMINAR:
				eliminar(request);

				break;
			case OP_BUSQUEDA:
				buscar(request);

				break;
			case OP_GUARDAR:
				guardar(request);

				break;
			/*
			 * case OP_INDEX: index(request);
			 * 
			 * break;
			 */
			default:
				listar(request);
				break;
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			dispacher = request.getRequestDispatcher(VIEW_INDEX);
			alert = new Alert();
			request.setAttribute("alert", alert);

		} finally {

			dispacher.forward(request, response);
			// Reseteamos variables locales
			op = 0;
			id = -1;
			alert = null;

		}

	}

	/*
	 * private void index(HttpServletRequest request) { ArrayList<Usuario> usuarios
	 * = new ArrayList<Usuario>(); usuarios = dao.seach(search);
	 * request.setAttribute("materiales", usuarios); dispacher =
	 * request.getRequestDispatcher(VIEW_INICIO);
	 * 
	 * }
	 */
	private void mostrarFormulario(HttpServletRequest request) {
		Usuario usuario = new Usuario();
		if (id > 0) {
			usuario = dao.getById(id);

		} else {
			usuario.setId(id);
			if (nombre != null) {
				usuario.setNombre(nombre);
			}
			if (pass != null) {
				usuario.setPass(pass);
			}
		}
		request.setAttribute("usuarioSelect", usuario);
		dispacher = request.getRequestDispatcher(VIEW_FORM);

	}

	private void eliminar(HttpServletRequest request) {
		if (dao.delete(id)) {
			alert = new Alert("Usuario Eliminado id " + id, Alert.TIPO_PRIMARY);
		} else {
			alert = new Alert("Error Eliminando, sentimos las molestias ", Alert.TIPO_WARNING);
		}
		listar(request);
		request.setAttribute("alert", alert);

	}

	private void guardar(HttpServletRequest request) {
		boolean crear = false;
		boolean update = false;
		try {
			if (id == -1) {
				//dao.create(nombre, pass);
				alert = new Alert("Creado Nuevo Material ", Alert.TIPO_PRIMARY);
				crear = true;
				update = false;
				request.setAttribute("id", id);
				request.setAttribute("nombre", nombre);
				request.setAttribute("pass", pass);

			} else {
				//dao.update(id, nombre, pass);
				alert = new Alert("Modificado Usuario id: " + id, Alert.TIPO_PRIMARY);
				crear = false;
				update = true;

			}

		//} catch (SQLIntegrityConstraintViolationException e) {
			//alert = new Alert("No podemos crear el usuario por que ya existe =( ", Alert.TIPO_DANGER);

		} catch (Exception e) {
			alert = new Alert("Hubo un problema a la hora de crear, no pudimos crearlo ", Alert.TIPO_DANGER);
		} finally {
			if (crear) {
				listar(request);

			} else {
				request.setAttribute("id", id);
				request.setAttribute("nombre", nombre);
				mostrarFormulario(request);

			}
			nombre = "";
			request.setAttribute("alert", alert);
		}

	}

	private void buscar(HttpServletRequest request) {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		//usuarios = dao.seach(search);
		request.setAttribute("materiales", usuarios);
		dispacher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void listar(HttpServletRequest request) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = dao.getAll();
		request.setAttribute("usuarios", usuarios);
		dispacher = request.getRequestDispatcher(VIEW_INDEX);

	}

	/**
	 * Recogemos e inicializamos todos los posibles parametros que nos pueden mandar
	 * 
	 * @param request
	 */
	private void recogerParametros(HttpServletRequest request) {

		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		}

		search = (request.getParameter("search") != null) ? request.getParameter("search") : "";

		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		}
		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre");
			if (nombre.length() > 0) {
				nombre = nombre.trim();
				if (nombre.length() > 45) {
					nombre = nombre.substring(0, 44);
				}
			} else {
				nombre = null;
			}

		}

	}

}
