package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Set;

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
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.pojo.form.RegistroForm;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String view = "";
	private static final String VIEW_REGISTRO = "registro.jsp";
	private static final String VIEW_LOGIN = "login.jsp";

	private Alert alert = new Alert();
	private UsuarioDAO daoUsuario;

	// validaciones
	ValidatorFactory factory;
	Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		daoUsuario = UsuarioDAO.getInstance();
	}

	@Override
	public void destroy() {
		super.destroy();
		factory = null;
		validator = null;
		daoUsuario = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW_REGISTRO).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String nombre = request.getParameter("name");
			String password = request.getParameter("password");
			String password_confirmation = request.getParameter("password_confirmation");
			String email = request.getParameter("email");
			RegistroForm form = new RegistroForm(nombre, email, password, password_confirmation);

			//validar parametros
			
			// comprobar pass == repass
			if ( password_confirmation != null && password_confirmation.equals(password)) {					
									
				Set<ConstraintViolation<RegistroForm>> violations = validator.validate(form);
				//validacion OK => insert y VIEW_LOGIN
				if ( violations.size() == 0 ) {
			
					Usuario usuario = new Usuario();
					Rol rol = new Rol();
					rol.setId(2);
					usuario.setNombre(nombre);
					usuario.setEmail(email);
					usuario.setRol(rol);
					usuario.setPass(password);
					boolean creado;
					creado = daoUsuario.save(usuario);
		
					if (creado) {
						view = VIEW_LOGIN;
						alert = new Alert("Registrado correctamente, inicia sesion con tus credenciales", Alert.TIPO_PRIMARY);
					} else {
						view = VIEW_REGISTRO;
						alert = new Alert("Error al introducir registro, intentalo de nuevo");
					}
				}
				
				//validacion ERROR => VIEW_REGISTRO
				else {
					String mensajes = "";
					for (ConstraintViolation<RegistroForm> violation : violations) {
						mensajes +=  violation.getPropertyPath() + ": " + violation.getMessage() + "<br>";							
					}
					alert = new Alert(mensajes, Alert.TIPO_WARNING);
					view = VIEW_REGISTRO;
				}
			}
			 

		} catch (Exception e) {
			e.printStackTrace();
			view = "registro.jsp";
			alert = new Alert();

		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

}
