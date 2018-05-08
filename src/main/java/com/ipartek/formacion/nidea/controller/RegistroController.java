package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	
	private String view = "";
	//private static final String VIEW_BACKOFFICE = "backoffice/index.jsp";
	private static final String VIEW_REGISTRO = "form.jsp";
	private static final String VIEW_LOGIN = "login.jsp";
	
	private Alert alert = new Alert();
	

	ValidatorFactory factory;
	Validator validator;
	
	private UsuarioDAO daoUsuario;
	
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		daoUsuario = UsuarioDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	@Override
	public void destroy() {
		
		super.destroy();
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

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		alert= null;
		
		try {
			
			//recoger los parametros
			String nombre = request.getParameter("usuario");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			RegistroForm form = new RegistroForm(nombre, email,password, password2);
			
			
			//Validar parametros
				
			//comprobar las contraseñas
				if(password2!=null && password2.equals(password)) {
					//Validacion OK => insert y VIEW_LOGIN
					Set<ConstraintViolation<RegistroForm>> violations = validator.validate(form);
					if (violations.size()== 0) {
						Usuario usuario = new Usuario();
						usuario.setNombre(nombre);
						usuario.setPass(password);
						usuario.setEmail(email);
						
						if(daoUsuario.saveUsuario(usuario)) {
							alert = new Alert("Registro correcto. Introduce tus credenciales", Alert.TIPO_PRIMARY);
							view = VIEW_LOGIN;
						}else {
							alert = new Alert("Lo sentimos el usuario o email ya existe", Alert.TIPO_WARNING);
							view = VIEW_REGISTRO;
						}
						//validacion ERROR => VIEW_REGISTRO
					}else {
						
						String mensajes = "";
						for (ConstraintViolation<RegistroForm> violation : violations) {
							mensajes += violation.getPropertyPath() + ":" +  violation.getMessage() + "<br>";
							
						}
						alert = new Alert(mensajes, Alert.TIPO_WARNING);
						view = VIEW_REGISTRO;
					}
						
				}else {
					//Validacion NOK => VIEW_REGISTRO
					alert = new Alert("No coinciden las contraseñas.", Alert.TIPO_WARNING);
					view = VIEW_REGISTRO;
				}
				
			
			
			alert = new Alert("Registro correcto. Introduce tus credenciales.");
			view = VIEW_LOGIN;
			
		}catch (Exception e) {
			e.printStackTrace();
			alert= new Alert();
			view = VIEW_REGISTRO;
		}finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
		
	}

}
