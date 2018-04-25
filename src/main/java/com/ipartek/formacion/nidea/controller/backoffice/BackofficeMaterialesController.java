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
import com.ipartek.formacion.nidea.model.MaterialDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class BackofficeMateriales
 */
@WebServlet("/backoffice/materiales")
public class BackofficeMaterialesController extends HttpServlet implements Operable {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_INDEX = "/backoffice/materiales/index.jsp";
	private static final String VIEW_FORM = "/backoffice/materiales/form.jsp";

	private RequestDispatcher dispatcher;
	private Alert alert;
	private MaterialDAO dao;
	private UsuarioDAO usuarioDao;

	// Parametros del material
	private int id;
	private String nombre;
	private float precio;
	private Usuario usuario;

	// Parametros comunes
	private String search; // buscador por nombre material
	private int op; // operacion a realizar

	// Validaciones
	private ValidatorFactory factory;
	private Validator validator;

	// se ejecuta al iniciar la servlet y solo una vez
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = MaterialDAO.getInstance();
		usuarioDao = UsuarioDAO.getInstance();
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

	/**
	 * Une los metodos doGet y doPost
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
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
			// request.setAttribute("materiales", materiales);
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}

	}

	private void guardar(HttpServletRequest request) {

		Material material = new Material();
		material.setId(id);
		material.setNombre(nombre);
		material.setUsuario(usuario);

		// request.setAttribute("usuarios", usuarioDao.getAll());

		try {
			if (request.getParameter("precio") != null) {
				precio = Float.parseFloat(request.getParameter("precio"));
				material.setPrecio(precio);
			}

			// Validaciones Incorrectas
			Set<ConstraintViolation<Material>> violations = validator.validate(material);
			if (violations.size() > 0) {
				String mensajes = "";
				for (ConstraintViolation<Material> violation : violations) {
					mensajes += violation.getMessage() + "<br>";
					alert = new Alert(mensajes, Alert.TIPO_WARNING);
				}
				// Validaciones OK
			} else {
				if (dao.save(material)) {
					alert = new Alert("Material guardado", Alert.TIPO_PRIMARY);
				} else {
					alert = new Alert("Lo sentimos pero ya existe el nombre del material", Alert.TIPO_WARNING);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			alert = new Alert("<b>" + request.getParameter("precio") + "</b> no es un precio correcto",
					Alert.TIPO_WARNING);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("material", material);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);

	}

	private void eliminar(HttpServletRequest request) {
		if (dao.delete(id)) {
			alert = new Alert("Se ha eliminado el registro: " + id, Alert.TIPO_DANGER);
		} else {
			alert = new Alert("Ha habido un error eliminando", Alert.TIPO_WARNING);
		}

		listar(request);

	}

	private void buscar(HttpServletRequest request) {
		ArrayList<Material> materiales = new ArrayList<Material>();
		materiales = dao.getByName(search);
		request.setAttribute("materiales", materiales);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);
	}

	private void listar(HttpServletRequest request) {

		ArrayList<Material> materiales = new ArrayList<Material>();
		materiales = dao.getAll();

		request.setAttribute("materiales", materiales);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}

	private void mostrarFormulario(HttpServletRequest request) {
		Material material = new Material();

		if (id != -1) {
			material = dao.getById(id);
		}
		request.setAttribute("material", material);
		// request.setAttribute("usuarios", usuarioDao.getAll());
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
	}

	/**
	 * recogemos todos los posibles parametros enviados
	 * 
	 * @param request
	 */
	private void recogerParametros(HttpServletRequest request) {

		usuario = null;

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

		if (request.getParameter("id_usuario_cambio") != null
				&& !("-1".equals(request.getParameter("id_usuario_cambio")))) {
			usuario = usuarioDao.getById(Integer.parseInt(request.getParameter("id_usuario_cambio")));
		} else if (request.getParameter("id_usuario") != null) {
			usuario = usuarioDao.getById(Integer.parseInt(request.getParameter("id_usuario")));
		}

	}

	// se ejecuta al parar el servidor de aplicaciones
	@Override
	public void destroy() {
		super.destroy();
		dao = null;
		usuarioDao = null;
		factory = null;
		validator = null;
	}

}
