package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
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

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.pojo.form.RegistroForm;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String view = "";
	private static final String VIEW_REGISTRO = "registro.jsp";
	private static final String VIEW_LOGIN = "login.jsp";
	private static final int ROL_USUARIO_NORMAL = 2;
			
	ValidatorFactory factory;
	Validator validator;

	private RequestDispatcher dispatcher;
	private Alert alert;
	private UsuarioDAO daoUsuario;
	
	private String nombre;
	private String email;
	private String pass;
	private String pass2;
			
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

		super.service(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW_REGISTRO).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Alert alert = null;
		try {
			recogerParametros(request);
			
			RegistroForm form = new RegistroForm (nombre,email,pass,pass2);
			
			//validar parametros
			
			if (pass2 != null && pass2.equals(pass)) {
				Set<ConstraintViolation<RegistroForm>> violations = validator.validate(form);
				//validacion OK => insert y VIEW_LOGIN
				if (violations.size() == 0 ) {
					
					Usuario usuario = new Usuario();
					Rol rol = new Rol();
					usuario.setNombre(nombre);
					usuario.setPass(pass);
					usuario.setEmail(nombre+"@"+nombre);
					rol.setId(ROL_USUARIO_NORMAL);
					usuario.setRol(rol);
					
					if (daoUsuario.save(usuario)) {
						alert = new Alert("Registro correcto, por favor introduce tus credenciales", Alert.TIPO_PRIMARY);
						view = VIEW_LOGIN;	
					}else {
						
					}
					
				
				//validacion ERROR => VIEW_REGISTRO
				}else {
				
					String mensajes = "";
					for (ConstraintViolation<RegistroForm> violation : violations) {
						mensajes +=  violation.getPropertyPath() + ": " + violation.getMessage() + "<br>";							
					}
					alert = new Alert(mensajes, Alert.TIPO_WARNING);
					view = VIEW_REGISTRO;
				}
			}else {
				
				//validacion ERROR => VIEW_REGISTRO
				alert = new Alert("No coinciden las contraseñas", Alert.TIPO_WARNING);
				view = VIEW_REGISTRO;
			}	
		} catch (SQLIntegrityConstraintViolationException e) {
			alert = new Alert("Lo sentimos pero el Usuario o Email ya existe", Alert.TIPO_WARNING);
			view = VIEW_REGISTRO;	
		} catch (Exception e) {
			e.printStackTrace();
			alert = new Alert();
			view = VIEW_REGISTRO;
		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
	}
	private void recogerParametros(HttpServletRequest request) {
		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre");
		} else {
			nombre = "";
		}
		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
		} else {
			email = "";
		}
		if (request.getParameter("pass") != null) {
			pass = request.getParameter("pass");
		} else {
			pass = "";
		}
		if (request.getParameter("pass2") != null) {
			pass2 = request.getParameter("pass2");
		} else {
			pass2 = "";
		}
	}
}
