package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;

import com.ipartek.formacion.nidea.model.MaterialDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;

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
	
	/**
	 * Se ejecuta solo la 1º vez que llaman al Servlet
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoMaterial = MaterialDAO.getInstance();
//		factory = Validation.buildDefaultValidatorFactory();
//		validator = factory.getValidator();
	}

	/**
	 * Se ejecuta cuando Paramos Servidor de Aplicaciones
	 */
	@Override
	public void destroy() {
		super.destroy();
		daoMaterial = null;
//		validator = null;
//		factory = null;
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
				//mostrarFormulario(request);
				break;
			case OP_ELIMINAR:
				//eliminar(request, response);
				break;
			case OP_BUSQUEDA:
				//buscar(request);
				break;
			case OP_GUARDAR:
				//guardar(request);
				break;
			case OP_MOSTRAR_CATALOGO:
				materiales = mostrarCatalogo(request);
				break;
			default:
				listar(request, usuario);
				break;
			}

		} catch (Exception e) {
			alert = new Alert();
			e.printStackTrace();

		} finally {
			request.setAttribute("alert", alert);
			request.setAttribute("materiales", materiales);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}

		
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
