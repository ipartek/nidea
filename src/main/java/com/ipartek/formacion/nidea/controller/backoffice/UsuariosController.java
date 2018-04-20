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

import com.ipartek.formacion.nidea.model.MaterialDAO;
import com.ipartek.formacion.nidea.model.RolDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;

/**
 * Servlet implementation class UsuariosController
 */
@WebServlet("/backoffice/usuarios")
public class UsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String VIEW_INDEX = "usuarios/index.jsp";
	private static final String VIEW_FORM = "usuarios/form.jsp";
	
	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_BUSQUEDA = 14;
	public static final int OP_ELIMINAR = 13;
	public static final int OP_GUARDAR = 2;

	ValidatorFactory factory;
	Validator validator;

	private RequestDispatcher dispatcher;
	private Alert alert;
	private MaterialDAO daoMaterial;
	private UsuarioDAO daoUsuario;
	private RolDAO daoRol;

	// parametros comunes
	private String search; // para el buscador por nombre matertial
	private int op; // operacion a realizar

	// parametros del Material
	private int id_usuario;
	private String nombre_usuario;
	private String password;
	private int rol_id;
	
	
	/**
	 * Se ejecuta solo la 1º vez que llaman al Servlet
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoMaterial = MaterialDAO.getInstance();
		daoUsuario = UsuarioDAO.getInstance();
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
		daoUsuario = null;
		validator = null;
		factory = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {

		alert = null;
		try {

			recogerParametros(request);

			switch (op) {
			case OP_MOSTRAR_FORMULARIO:
				mostrarFormulario(request);
				break;
			case OP_ELIMINAR:
				eliminar(request, response);
				break;
			case OP_BUSQUEDA:
				buscar(request);
				break;
			case OP_GUARDAR:
				guardar(request);
				break;
			default:
				listar(request);
				break;
			}

		} catch (Exception e) {
			alert = new Alert();
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher(VIEW_INDEX);

		} finally {
			request.setAttribute("alert", alert);
			dispatcher.forward(request, response);
		}
		
	}

	private void recogerParametros(HttpServletRequest request) {
		if (request.getParameter("op") != null) {
			op = Integer.parseInt(request.getParameter("op"));
		} else {
			op = 0;
		}

		search = (request.getParameter("search") != null) ? request.getParameter("search") : "";

		if (request.getParameter("id") != null) {
			id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
		} else {
			id_usuario = -1;
		}

		if (request.getParameter("nombre") != null) {
			nombre_usuario = request.getParameter("nombre");
			nombre_usuario = nombre_usuario.trim();
		} else {
			nombre_usuario = "";
		}
		if(request.getParameter("password")!=null) {
			password = request.getParameter("password");
			
		}else{
			password= "";
		}
		if (request.getParameter("rol_id")!= null) {
			rol_id =Integer.parseInt(request.getParameter("rol_id"));
			
		}
		else {
			rol_id= -1;
		}
		
	}

	private void mostrarFormulario(HttpServletRequest request) {
		Usuario usuario = new Usuario();
		if (id_usuario > -1) {
			usuario = daoUsuario.getById(id_usuario);

		} else {
			alert = new Alert("Nuevo Usuario creado", Alert.TIPO_WARNING);
		}
		ArrayList<Rol> rol= (ArrayList<Rol>) daoRol.getAll();
		request.setAttribute("roles", rol);
		request.setAttribute("usuarios", daoUsuario.getAll());
		
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if (daoUsuario.delete(id_usuario)) {
			alert = new Alert("Usuario Eliminado id " + id_usuario, Alert.TIPO_PRIMARY);
		} else {
			alert = new Alert("Error Eliminando, sentimos las molestias ", Alert.TIPO_WARNING);
		}
		listar(request);
		
	}

	private void buscar(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	private void guardar(HttpServletRequest request) {
		
		Rol rol = new Rol();
		Usuario usuario = new Usuario();

		try {

			usuario.setId(id_usuario);
			usuario.setNombre(nombre_usuario);
			usuario.getRol().setId(rol_id);
			

			if (request.getParameter("pass") != null) {
				password = request.getParameter("pass");
				usuario.setPass(password);
			}

			// Validaciones Incorrectas
			Set<ConstraintViolation<Usuario>> violation = validator.validate(usuario);
			if (violation.size() > 0) {
				String mensajes = "";
				for (ConstraintViolation<Usuario> violations : violation) {
					mensajes += violations.getMessage() + "<br>";
					alert = new Alert(mensajes, Alert.TIPO_WARNING);
				}
				// Validaciones OK
			} else {
				if (daoUsuario.save(usuario)) {
					alert = new Alert("usuario guardado", Alert.TIPO_PRIMARY);
				} else {
					alert = new Alert("Lo sentimos pero ya existe el nombre del material", Alert.TIPO_WARNING);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			alert = new Alert("<b>" + request.getParameter("password") + "</b> no es un precio correcto",
					Alert.TIPO_WARNING);
		}

		request.setAttribute("usuario", usuario);
		dispatcher = request.getRequestDispatcher(VIEW_FORM);
		
		
	}

	private void listar(HttpServletRequest request) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = (ArrayList<Usuario>) daoUsuario.getAll();
		request.setAttribute("usuarios", usuarios);
		dispatcher = request.getRequestDispatcher(VIEW_INDEX);
		
	}

}
