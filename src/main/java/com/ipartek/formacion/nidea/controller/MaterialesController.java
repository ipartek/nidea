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

import com.ipartek.formacion.nidea.model.MaterialDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class MaterialesController
 */
@WebServlet("/materiales")
public class MaterialesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Constantes de operaciones
	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_MOSTRAR_CATALOGO = 3;
	public static final int OP_BUSQUEDA = 14;
	public static final int OP_ELIMINAR = 13;
	public static final int OP_GUARDAR = 2;
	// Constantes de navegación
	private static final String VIEW_INDEX = "frontoffice/misMateriales.jsp";
	private static final String VIEW_FORM = "frontoffice/formMaterial.jsp";
	private static final String VIEW_CATALOGO = "materiales.jsp";
	
	// Variables
	private RequestDispatcher dispatcher;
	private Alert alert;
	private MaterialDAO daoMaterial;
	private String search; // para el buscador por nombre matertial
	private int op; // operacion a realizar
	// parametros del Material
	private int id;
	private String nombre;
	private float precio;
	private Usuario propietario;
	
	ValidatorFactory factory;
	Validator validator;
	
	/**
	 * Se ejecuta solo la 1º vez que llaman al Servlet
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoMaterial = MaterialDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * Se ejecuta cuando Paramos Servidor de Aplicaciones
	 */
	@Override
	public void destroy() {
		super.destroy();
		daoMaterial = null;
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

		doGet(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<Material> materiales = new ArrayList<Material>();
		Alert alert = null;
		
		// recuperar usuario en session
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		try {
			MaterialDAO dao = MaterialDAO.getInstance();
			materiales = dao.getByUserId(usuario.getId());
			
			recogerParametros(request);

			switch (op) {
			case OP_MOSTRAR_FORMULARIO:
				mostrarFormulario(request);
				break;
			case OP_ELIMINAR:
				eliminar(request, response, usuario);
				break;
			case OP_BUSQUEDA:
				//buscar(request);
				break;
			case OP_GUARDAR:
				guardar(request, usuario);
				break;
			case OP_MOSTRAR_CATALOGO:
				materiales = mostrarCatalogo(request);
				break;
			default:
				listar(request, usuario);
				break;
			}
		} catch (MySQLIntegrityConstraintViolationException sql_integrity) {
			alert = new Alert("El material creado ya existe", Alert.TIPO_WARNING);
		} catch (Exception e) {
			alert = new Alert();
			e.printStackTrace();

		} finally {
			request.setAttribute("alert", alert);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	private void eliminar(HttpServletRequest request, HttpServletResponse response, Usuario usuario) {
		

		if (daoMaterial.safeDelete(id, usuario.getId())) {
			alert = new Alert("Material Eliminado id " + id, Alert.TIPO_PRIMARY);
		} else {
			alert = new Alert("Error Eliminando, sentimos las molestias ", Alert.TIPO_WARNING);
		}

		listar(request, usuario);
		
	}

	private void guardar(HttpServletRequest request, Usuario usuario) throws Exception {

		Material material = new Material();

		try {

			material.setId(id);
			material.setNombre(nombre);
			material.setUsuario(usuario);

			if (request.getParameter("precio") != null) {
				precio = Float.parseFloat(request.getParameter("precio"));
				material.setPrecio(precio);
			}
			
			Set<ConstraintViolation<Material>> violations = validator.validate(material);
			if (violations.size() > 0) {
				// Validaciones Incorrectas
				String mensajes = "";
				for (ConstraintViolation<Material> violation : violations) {
					mensajes += violation.getMessage() + "<br>";
					alert = new Alert(mensajes, Alert.TIPO_WARNING);
				}
				// Validaciones OK
			} else {
					if (daoMaterial.save(material)) {
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
		request.setAttribute("alert", alert);
		request.setAttribute("material", material);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
		
	}

	private void mostrarFormulario(HttpServletRequest request) {

		Material material = new Material();
		if (id > -1) {
			material = daoMaterial.getById(id);
		} else {
			alert = new Alert("Nuevo Producto", Alert.TIPO_WARNING);
		}

		request.setAttribute("material", material);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
	}

	/**
	 * Mostrar todo el catálogo de materiales sin opción de editar
	 * @param request
	 * @param materiales2 
	 */
	private ArrayList<Material> mostrarCatalogo(HttpServletRequest request) {

		ArrayList<Material> materiales = new ArrayList<Material>();
		materiales = daoMaterial.getAll();
		// request.setAttribute("materiales", materiales);
		dispatcher = request.getRequestDispatcher(VIEW_CATALOGO);
		
		return materiales;
		
	}

	/**
	 * Recogemos todos los posibles parametros enviados
	 * 
	 * @param request
	 */
	private void recogerParametros(HttpServletRequest request) {

		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		} else {
			op = 0;
		}

		search = (request.getParameter("search") != null) ? request.getParameter("search") : "";

		// recogemos el id del material
		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		} else {
			id = -1;
		}

		// recogemos el nombre del material
		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre");
			nombre = nombre.trim();
		} else {
			nombre = "";
		}

	}
	
	/**
	 * Opcion por defecto al entrar en la lista de materiales
	 * @param request
	 * @param usuario : usuario logeado en sesión
	 */
	private void listar(HttpServletRequest request, Usuario usuario) {

		ArrayList<Material> materiales = new ArrayList<Material>();
		materiales = daoMaterial.getByUserId(usuario.getId());
		request.setAttribute("materiales", materiales);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);

	}


}
