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

import com.ipartek.formacion.nidea.listener.UsuariosConectadosListener;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.pojo.form.RegistroForm;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String view = "";
	private static final String VIEW_LOGIN = "login.jsp";
	private static final String VIEW_REGISTRO = "registro.jsp";
	
	private static UsuarioDAO usuarioDAO;
	
	//validaciones
	ValidatorFactory factory;
	Validator validator;
       
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		usuarioDAO = UsuarioDAO.getInstance();
	}
    
	@Override
	public void destroy() {	
		super.destroy();
		factory = null;
		validator = null;
		usuarioDAO = null;
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
	
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Alert alert = null;
		try {
			
			//recoger parametros
			String nombre = request.getParameter("nombre");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String rePassword = request.getParameter("rePassword");
			
			RegistroForm form = new RegistroForm(nombre, email, password, rePassword);
			
			//validar parametros
				
				// comprobar pass == repass
				if ( rePassword != null && rePassword.equals(password)) {					
										
					
					Set<ConstraintViolation<RegistroForm>> violations = validator.validate(form);
					//validacion OK => insert y VIEW_LOGIN
					if ( violations.size() == 0 ) {
						
						Usuario usuario = new Usuario();
						usuario.setNombre(nombre);
						usuario.setPass(password);
						usuario.setEmail(email);
						if ( usuarioDAO.save(usuario) ) {
							alert = new Alert("Registro correcto, por favor introduce tus credenciales", Alert.TIPO_PRIMARY);
							view = VIEW_LOGIN;	
						}else {
							alert = new Alert("Lo sentimos pero el Usario o Email ya existe", Alert.TIPO_WARNING);
							view = VIEW_REGISTRO;
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
			
		}catch (Exception e) {
			e.printStackTrace();
			alert = new Alert();
			view = VIEW_REGISTRO;
		}finally {
		
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
		
	}

}
