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
	private static final String VIEW_FORM = "susarios/form.jsp";
       

	ValidatorFactory factory;
	Validator validator;
	
	private RequestDispatcher dispatcher;
	private Alert alert;
	private UsuarioDAO daoUsuario;
	private RolDAO daoRol;
	
	// parametros comunes
	private String search; // para el buscador por nombre matertial
	private int op; // operacion a realizar

	// parametros del Material
	private int id;
	private String nombre;
	private String pass;
	private Rol rol;

	
	/**
	 * Se ejecuta solo la 1º vez que llaman al Servlet
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		daoUsuario = null;
		validator = null;
		factory = null;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Antes de Ejecutar doGET o doPost");

		super.service(request, response);
		System.out.println("Despues de Ejecutar doGET o doPost");
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
	
	/**
	 * Unimos las peticiones doGet y doPost, vamos que hacemos los mismo!!!
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
	
	private void guardar(HttpServletRequest request) {

		Usuario usuario=new Usuario();

		try {

			usuario.setId(id);
			usuario.setNombre(nombre);
			usuario.setPass(pass);
			usuario.setRol(rol);
			



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
				if (daoUsuario.save(usuario)) {
					alert = new Alert("Usuario guardado", Alert.TIPO_PRIMARY);
				} else {
					alert = new Alert("Lo sentimos pero ya existe el usuario", Alert.TIPO_WARNING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			alert = new Alert("no es un usuario correcto",
					Alert.TIPO_WARNING);
		}

		request.setAttribute("usuario", usuario);
		request.setAttribute("roles", daoRol.getAll());
		dispatcher = request.getRequestDispatcher(VIEW_FORM);

	}

}
